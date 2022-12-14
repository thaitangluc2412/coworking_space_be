package com.coworkingspace.backend.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation_status")
@Getter
@Setter
public class ReservationStatus extends BaseEntity{

	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "reservation_status_id", nullable = false)
	private String id;

	@Column(name = "reservation_status_name", nullable = false)
	private String reservationStatusName;
}
