package com.coworkingspace.backend.dao.entity;

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
@Getter
@Setter
@Entity
@Table(name = "utility")
public class Utility extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "utility_id", nullable = false)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "value", nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "utility_storage_id", nullable = false)
	private UtilityStorage utilityStorage;
}
