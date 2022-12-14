package com.coworkingspace.backend.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "review_id", nullable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Column(name = "rating")
	private Double rating;

	@Lob
	@Column(name = "content")
	private String content;

	@Column(name = "enable")
	private Boolean enable = true;

	@Override
	public boolean equals(Object o){
		if (this == o) {
			return true;
		}
		if (!(o instanceof Review)){
			return false;
		}
		Review review = (Review) o;
		return Objects.equals(id, review.id) && Objects.equals(rating, review.rating);
	}

	@Override
	public int hashCode(){
		return Objects.hash(id, rating);
	}
}