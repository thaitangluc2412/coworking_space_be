package com.coworkingspace.backend.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coworkingspace.backend.dao.entity.Reservation;
import com.coworkingspace.backend.dao.hibernate.ReservationDao;
import com.coworkingspace.backend.dao.repository.ReservationRepository;
import com.coworkingspace.backend.dto.ReservationDto;
import com.coworkingspace.backend.dto.ReservationListDto;
import com.coworkingspace.backend.dto.RoomCreateDto;
import com.coworkingspace.backend.dto.RoomListDto;
import com.coworkingspace.backend.sdo.CountRoomType;
import com.coworkingspace.backend.sdo.DateStatus;
import com.coworkingspace.backend.sdo.ObjectSdo;
import com.coworkingspace.backend.service.ReservationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationController {

	private ReservationService reservationService;

	private ReservationRepository reservationRepository;

	private ReservationDao reservationDao;

	@PostMapping
	public ResponseEntity<ReservationDto> createReservations(@RequestBody ReservationDto reservationDto) throws NotFoundException {
		ReservationDto reservationDto1 = reservationService.createReservation(reservationDto);
		return new ResponseEntity<>(reservationDto1, HttpStatus.OK);
	}

	@GetMapping("/furthest_valid_date/{roomId}")
	public ResponseEntity<String> getFurthestValidDate(@PathVariable String roomId, @RequestParam String from) throws NotFoundException {
		System.out.println("# Date : " + from);
		String furthestValidDate = reservationService.getFurthestValidDate(roomId, from);
		return new ResponseEntity<>(furthestValidDate, HttpStatus.OK);
	}

	@GetMapping("/date_status/{roomId}")
	public ResponseEntity<List<DateStatus>> getDateStatus(@PathVariable String roomId, @RequestParam int month, @RequestParam int year)
		throws NotFoundException {
		List<DateStatus> dateStatus = reservationService.getDateStatus(roomId, month, year);
		return new ResponseEntity<>(dateStatus, HttpStatus.OK);
	}

	@GetMapping("/get_invalid_date/{roomId}")
	public ResponseEntity<?> getAllInvalidDates(@PathVariable String roomId) throws NotFoundException {
		List<LocalDate> dates = new ArrayList<>(reservationService.getAllInvalidDate(roomId));
		return new ResponseEntity<>(dates, HttpStatus.OK);
	}

	@GetMapping("/by-customer/{customerId}")
	public ResponseEntity<List<ReservationListDto>> getByCustomerId(@PathVariable String customerId) {
		List<ReservationListDto> reservationListDtos = reservationService.getByCustomerId(customerId);
		return new ResponseEntity<>(reservationListDtos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservationListDto> getById(@PathVariable String id) {
		ReservationListDto reservationListDto = reservationService.getById(id);
		return new ResponseEntity<>(reservationListDto, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReservationListDto> updateReservation(@PathVariable String id, @RequestParam String reservationStatusName, @RequestParam String email)
		throws MessagingException {
		ReservationListDto reservationListDto = reservationService.updateReservation(id, reservationStatusName, email);
		return new ResponseEntity<>(reservationListDto, HttpStatus.OK);
	}

	@GetMapping("/get-by-seller-id/{id}")
	public ResponseEntity<List<ReservationListDto>> getBySellerId(@PathVariable String id) {
		List<ReservationListDto> reservationDtos = reservationService.getBySellerId(id);
		return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
	}

	// Get budget for admin page
	@GetMapping("/budget")
	public ResponseEntity<?> getBudget() {
		com.cnpm.workingspace.sdo.Budget budget = reservationService.getBudget();
		return ResponseEntity.ok(budget);
	}

	// Get profit for admin page
	@GetMapping("/profit")
	public ResponseEntity<?> getProfit() {
		double profit = reservationService.getProfit();
		return ResponseEntity.ok(profit);
	}

	// Get lastest reservation for admin page
	@GetMapping("/get-lastest")
	public ResponseEntity<?> getLastestReservation() {
		List<ReservationListDto> reservationListDtos = reservationService.getLatestReservations();
		return ResponseEntity.ok(reservationListDtos);
	}

	@GetMapping("/get-total-perMonth/get")
	public ResponseEntity<?> getToTalPerMonth() {
		List< CountRoomType> countRoomTypes =reservationDao.getToTalPerMonth();
		return ResponseEntity.ok(countRoomTypes);
	}

	@GetMapping("/total")
	public ResponseEntity<?> getTotal() {
		Integer total = reservationRepository.findAll().size();
		return ResponseEntity.ok(total);
	}

	@GetMapping("/page/list")
	@ResponseBody
	public ResponseEntity<?> findByRoomNameOrStatus(
		@RequestParam(value = "value", required = false) String value,
		@RequestParam(name = "page", required = false, defaultValue = "0") int page,
		@RequestParam(name = "size", required = false, defaultValue = "6") int size
	) {

		if (Objects.equals(value, "")) {
			Page<ReservationListDto> reservations = reservationService.findReservationPage(page, size);
			List<ReservationListDto> reservationList = reservations.toList();
			List<Object> cusObjectList = Arrays.asList(reservationList.toArray());

			List<Reservation> allReservations = reservationRepository.findAll();
			int count = allReservations.size();

			ObjectSdo objectSdo = new ObjectSdo(count, cusObjectList);
			return new ResponseEntity<>(objectSdo, HttpStatus.OK);
		}
		Page<ReservationListDto> reservationPage = reservationService.findByRoomNameOrStatusName(value, page, size);
		List<ReservationListDto> reservations = reservationPage.toList();
		int cnt = reservations.size();
		return new ResponseEntity<>(new ObjectSdo(cnt, new ArrayList(reservations)), HttpStatus.OK);
	}
}
