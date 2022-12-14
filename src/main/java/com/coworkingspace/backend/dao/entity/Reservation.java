package com.coworkingspace.backend.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity{

	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "reservation_id")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@ManyToOne(fetch = FetchType.LAZY ,optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(optional = false)
	@JoinColumn(name = "reservation_status_id", nullable = false)
	private ReservationStatus reservationStatus;

	@Column(name = "start_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(name = "end_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@Column(name = "total", nullable = false)
	private Double total;

	@Column(name = "deposit", nullable = false)
	private Double deposit;

	@Column(name = "reviewed")
	private Boolean reviewed = false;

	@Column(name = "enable")
	private Boolean enable = true;
}