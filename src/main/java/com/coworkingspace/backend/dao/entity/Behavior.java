package com.coworkingspace.backend.dao.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "behavior")
@Getter
@Setter
public class Behavior extends BaseEntity{

	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "behavior_id")
	private String id;

	@Column(name = "time")
	private Integer time;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Override
	public boolean equals(Object o){
		if (this == o) {
			return true;
		}
		if (!(o instanceof Behavior)){
			return false;
		}
		Behavior behavior = (Behavior) o;
		return Objects.equals(id, behavior.id) && Objects.equals(time, behavior.time);
	}

	@Override
	public int hashCode(){
		return Objects.hash(id, time);
	}
}
