package com.coworkingspace.backend.dao.entity;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room")
public class Room extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "room_id", nullable = false)
	private String id;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "price_id", nullable = false)
	private Price price;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "room_type_id", nullable = false)
	private RoomType roomType;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_storage_id", nullable = false)
	private ImageStorage imageStorage;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "utility_storage_id", nullable = false)
	private UtilityStorage utilityStorage;

	@Column(name = "room_name", nullable = false)
	private String roomName;

	@Column(name = "average_rating")
	private Double averageRating = 0.0;

	@Column(name = "address", nullable = false)
	private String address;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "province_id", nullable = false)
	private Province province;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "district_id", nullable = false)
	private District district;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ward_id", nullable = false)
	private Ward ward;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "behavior_id", nullable = false, insertable=false, updatable=false)
	private Set<Behavior> behaviorItems = new HashSet<>();

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "enable")
	private Boolean enable = true;

	@Override
	public boolean equals(Object o){
		if (this == o) {
			return true;
		}
		if (!(o instanceof Room)){
			return false;
		}
		Room room = (Room) o;
		return Objects.equals(id, room.id) && Objects.equals(roomName, room.roomName);
	}

	@Override
	public int hashCode(){
		return Objects.hash(id, roomName);
	}
}
