package com.coworkingspace.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.coworkingspace.backend.common.utils.PathUtils;
import com.coworkingspace.backend.configuration.CloudinaryConfig;
import com.coworkingspace.backend.dao.entity.Behavior;
import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dao.entity.Image;
import com.coworkingspace.backend.dao.entity.ImageStorage;
import com.coworkingspace.backend.dao.entity.Review;
import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dao.hibernate.RoomDao;
import com.coworkingspace.backend.dao.repository.BehaviorRepository;
import com.coworkingspace.backend.dao.repository.CustomerRepository;
import com.coworkingspace.backend.dao.repository.ReviewRepository;
import com.coworkingspace.backend.dao.repository.RoomRepository;
import com.coworkingspace.backend.dto.ImageDto;
import com.coworkingspace.backend.dto.RoomCreateDto;
import com.coworkingspace.backend.dto.RoomListDto;
import com.coworkingspace.backend.mapper.ImageMapper;
import com.coworkingspace.backend.mapper.RoomMapper;
import com.coworkingspace.backend.service.RoomService;

import lombok.AllArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
	private final String FOLDER_PATH = "rooms";
	private Cloudinary cloudinary;
	private CloudinaryConfig cloudinaryConfig;
	private RoomRepository roomRepository;
	private RoomMapper roomMapper;
	private ImageMapper imageMapper;
	private RoomDao roomDao;
	private BehaviorRepository behaviorRepository;
	private CustomerRepository customerRepository;
	private ReviewRepository reviewRepository;

	@Transactional
	@Override
	public void createRoom(RoomCreateDto roomCreateDto, MultipartFile[] files) {
		try {
			List<ImageDto> imageDtos = new ArrayList<>();
			imageDtos.addAll(saveImage(files));
			Thread thread = new Thread(() -> {
				roomCreateDto.setImages(imageDtos);
				Room room = null;
				try {
					room = roomMapper.roomCreateDtoToRoom(roomCreateDto);
				} catch (NotFoundException e) {
					throw new RuntimeException(e);
				}
				roomRepository.save(room);
			});
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<RoomCreateDto> getAll() {
		return roomRepository.findAll()
			.stream()
			.map(room -> {
				try {
					return roomMapper.roomToRoomCreateDto(room);
				} catch (NotFoundException e) {
					throw new RuntimeException(e);
				}
			})
			.collect(
				Collectors.toList());
	}

	@Override
	public Room findById(String id) {
		return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found room"));
	}

	@Override
	public RoomCreateDto findByRoomId(String roomId, String customerId) throws NotFoundException {
		Room room = findById(roomId);
		if (customerId != null) {
			Optional<Behavior> behaviorOptional = behaviorRepository.findByCustomerId(customerId);
			if (behaviorOptional.isPresent()) {
				Behavior behavior = behaviorOptional.get();
				behavior.setTime(behavior.getTime() + 1);
				behaviorRepository.save(behavior);
			} else {
				Behavior newBehavior = new Behavior();
				newBehavior.setTime(1);
				newBehavior.setRoom(room);
				newBehavior.setCustomer(customerRepository.findById(customerId).orElseThrow());
				behaviorRepository.save(newBehavior);
			}
		}
		return roomMapper.roomToRoomCreateDto(room);
	}

	@Override
	public List<RoomListDto> findByCustomerId(String id) {
		return roomRepository.getByCustomerIdAndEnableIsTrue(id).stream().map(room -> roomMapper.roomToRoomListDto(room)).collect(Collectors.toList());
	}

	@Override
	public void deleteRoom(String id) {
		Room room = findById(id);
		room.setEnable(false);
		roomRepository.save(room);
	}

	@Override
	public Page<RoomListDto> findRoomPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Room> roomsPage = roomRepository.findAll(pageable);
		return roomsPage.map(room -> roomMapper.roomToRoomListDto(room));
	}

	@Override public Page<RoomListDto> findRoomByRoomName(String roomName, int page, int size) {
		return roomRepository.findRoomByRoomNameContaining(roomName, PageRequest.of(page, size)).map(room -> roomMapper.roomToRoomListDto(room));
	}

	@Override
	public RoomCreateDto updateRoom(String id,
		RoomCreateDto roomCreateDto,
		MultipartFile[] files) throws NotFoundException {
		Room room = findById(id);
		roomCreateDto.setId(id);
		boolean needUpdate = files != null;
		if (needUpdate) {
			deleteFolderCloudinary(room);
			List<ImageDto> imageDtos = new ArrayList<>();
			imageDtos.addAll(saveImage(files));
			if (roomCreateDto.getImages() != null ) {
				imageDtos.addAll(roomCreateDto.getImages());
			}
			roomCreateDto.setImages(imageDtos);
		} else {
			roomCreateDto.setImages(room.getImageStorage()
				.getImages()
				.parallelStream()
				.map(en -> imageMapper.imageToImageDto(en))
				.collect(
					Collectors.toList()));
		}
		roomCreateDto.setImageStorageId(room.getImageStorage().getId());
		roomRepository.save(roomMapper.roomCreateDtoToRoom(roomCreateDto));
		return roomCreateDto;
	}

	@Override
	public List<RoomListDto> getWithFilter(String typeRoomId, String provinceId, String roomName, String cityName, String minPrice, String maxPrice) {
		return roomDao.getWithFilter(typeRoomId, provinceId, roomName, cityName, minPrice, maxPrice).stream().map(room -> roomMapper.roomToRoomListDto(room))
			.collect(Collectors.toList());
	}

	public void deleteFolderCloudinary(Room room) {
		if (room.getImageStorage() != null) {
			ImageStorage imageStorage = room.getImageStorage();
			if (imageStorage.getImages() != null) {
				List<Image> images = imageStorage.getImages();
				if (images.size() > 0) {
					String folderPath = PathUtils.getParentFolder(images.get(0).getUrl());
					try {
						System.out.println("folder Path : " + folderPath);
						cloudinary.api().deleteResourcesByPrefix(folderPath, Map.of());
						cloudinary.api().deleteFolder(folderPath, Map.of());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private List<ImageDto> saveImage(MultipartFile[] files) {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		final String path = String.format("%s/%s", FOLDER_PATH, uuidAsString);
		final String folderPath = PathUtils.decoratePath(path);
		int[] idx = new int[1];
		idx[0] = 0;
		List<ImageDto> imageDtos = new ArrayList<>();
		if (files == null) {
			return Collections.emptyList();
		}
		Arrays.asList(files).stream().forEach(file -> {
			String fileName = "image_" + idx[0];
			try {
				Map ret = cloudinary.uploader()
					.upload(
						file.getBytes(),
						ObjectUtils.asMap(
							"folder",
							cloudinaryConfig.getCloudPath() + folderPath,
							"public_id",
							fileName
						)
					);
				imageDtos.add(new ImageDto(null, ret.get("url").toString(), fileName));
				++idx[0];
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return imageDtos;
	}

	@Override public List<RoomListDto> favoriteRoom(String id) {
		List<Room> roomIterable = roomRepository.findAll();
		List<Customer> customers = roomDao.findAllByBehavior();
//		List<Customer> customers = customerRepository.findAll();
		var rooms = new ArrayList<>(roomIterable);

		var matrix = generateRatingMatrix(rooms, customers);

		var customerOptional = customerRepository.findById(id);

		if (customerOptional.isEmpty()){

			return roomRepository.findTop6ByOrderByAverageRatingDesc().stream().map(room -> roomMapper.roomToRoomListDto(room)).collect(Collectors.toList());
		}

		var customer = customerOptional.get();
//		int u = customers.indexOf(customer);
		int u = Customer.indexOf(customers, customer);
		double aRU = getAverageRating(u, matrix);

		List<Pair> userSimilarities = new ArrayList<>();
		for (int v = 0; v < customers.size(); v++){
			if (v != u){
				double aRV = getAverageRating(v, matrix);
				var value = userSimilarityCaculate(u, aRU, v, aRV, matrix, rooms, customer, customers.get(v));
				if (value != 0){
					userSimilarities.add(new Pair(v, value));
				}
			}
		}
		Collections.sort(userSimilarities);

		if (userSimilarities.size() > 30){
			userSimilarities = userSimilarities.subList(0, 30);
		}

		var listItems = new ArrayList<Integer>();

		userSimilarities.forEach(user -> {
			for (int i = 0; i < matrix[0].length; i++){
				if (matrix[user.getIndex()][i] != 0 && !listItems.contains(i)){
					listItems.add(i);
				}
			}
		});

		var prediction = predictionRating(aRU, userSimilarities, listItems, matrix);
		var result = prediction.stream().sorted(Comparator.comparing(Pair::getValue)).map(pair -> rooms.get(pair.getIndex())).collect(Collectors.toList());
		if (result.isEmpty()) {
			return roomRepository.findTop6ByOrderByAverageRatingDesc().stream().map(room -> roomMapper.roomToRoomListDto(room)).collect(Collectors.toList());
		}
		return result.stream().map(room -> roomMapper.roomToRoomListDto(room)).collect(Collectors.toList());
	}

	private double[][] generateRatingMatrix(List<Room> rooms, List<Customer> customers) {
		int m = customers.size();
		int n = rooms.size();

		var ratingMatrix = new double[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				var customer = customers.get(i);
				var room = rooms.get(j);
				List<Review> reviewList = reviewRepository.findReviewByCustomerIdAndRoomId(customer.getId(), room.getId());
				if (reviewList.size() > 0) {
					Double sum = reviewList.stream().map(Review::getRating).reduce(0d, Double::sum);
					double avg = sum / reviewList.size();
					ratingMatrix[i][j] = avg;
				} else {
					ratingMatrix[i][j] = 0d;
				}
			}
		}
		return ratingMatrix;
	}

	private double getAverageRating(int indexOfUser, double[][] matrix) {
		double sum = 0d;
		if (matrix.length == 0) return 0;
		for (int i = 0; i < matrix[0].length; i++) {
			sum += matrix[indexOfUser][i];
		}
		return sum / matrix[0].length;
	}

	private double userSimilarityCaculate(int u, Double aRU, int v, Double aRV, double[][] matrix, List<Room> rooms, Customer user, Customer user2) {
		double numerator = 0d;
		double denominator1 = 0d;
		double denominator2 = 0d;

		for (int i = 0; i < matrix[0].length; i++) {
			var wu = TF_IDF(user, rooms.get(i), matrix.length);
			var wv = TF_IDF(user2, rooms.get(i), matrix.length);
			numerator += (matrix[u][i] * wu - aRU) * (matrix[v][i] * wv - aRV);
			denominator1 += (matrix[u][i] * wu - aRU) * (matrix[v][i] * wu - aRU);
			denominator2 += (matrix[v][i] * wv - aRV) * (matrix[v][i] * wv - aRV);
		}

		return numerator / Math.sqrt(denominator1 * denominator2);
	}

	private double TF_IDF(Customer user, Room room, int userLength) {
		var behaviorOptional = user.getBehaviors().stream().filter(b -> b.getRoom().equals(room)).findFirst();
		if (behaviorOptional.isEmpty()) {
			return 0;
		}

		Behavior behavior = behaviorOptional.get();
		double total = user.getBehaviors().stream().map(Behavior::getTime).reduce(0, Integer::sum);
		if (total == 0) {
			return 0;
		}

		var tf = behavior.getTime() / total;
		var pop = room.getBehaviorItems().stream().map(Behavior::getTime).reduce(0, Integer::sum);
		var idf = Math.log10((double) userLength / (1 + pop));
		return tf * idf;
	}

	private ArrayList<Pair> predictionRating(double aRU, List<Pair> customers, List<Integer> rooms, double[][] matrix) {
		var result = new ArrayList<Pair>();
		for (Integer room : rooms) {
			double numerator = 0d;
			double denominator = 0d;
			for (Pair customer : customers) {
				var aRV = getAverageRating(customer.getIndex(), matrix);
				numerator += customer.getValue() * (matrix[customer.getIndex()][room] - aRV);
				denominator += Math.abs(customer.getValue());
			}
			result.add(new Pair(room, aRU + numerator / denominator));
		}
		return result;
	}

	static class Pair implements Comparable<Pair> {
		private Integer index;
		private Double value;

		public Pair(Integer index, Double value) {
			this.index = index;
			this.value = value;
		}

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public Double getValue() {
			return value;
		}

		public void setValue(Double value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Pair pair = (Pair) o;
			return Objects.equals(index, pair.index) && Objects.equals(value, pair.value);
		}

		@Override
		public int hashCode() {
			return Objects.hash(index, value);
		}

		@Override
		public int compareTo(Pair e) {
			if (value == null || e.value == null) {
				return 0;
			}
			return value.compareTo(e.value);
		}
	}

}
