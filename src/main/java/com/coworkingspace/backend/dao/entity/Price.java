package com.coworkingspace.backend.dao.entity;


import lombok.AllArgsConstructor;
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
@Table(name = "price")
public class Price extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "price_id", nullable = false)
	private String id;

	@Column(name = "day_price")
	private Double dayPrice = 0.0;

	@Column(name = "month_price")
	private Double monthPrice = 0.0;

	@Column(name = "year_price")
	private Double yearPrice = 0.0;

	public Price(Double dayPrice, Double monthPrice, Double yearPrice) {
		this.dayPrice = dayPrice;
		this.monthPrice = monthPrice;
		this.yearPrice = yearPrice;
	}

}