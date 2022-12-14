package com.coworkingspace.backend.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ward")
public class Ward {
	@Id
	@Column(name = "code", nullable = false)
	private Integer code;

	@Column(name = "name")
	private String name;

	@Column(name = "division_type")
	private String divisionType;

	@Column(name = "codename")
	private String codeName;

	@Column(name = "district_code")
	private Integer districtCode;
}
