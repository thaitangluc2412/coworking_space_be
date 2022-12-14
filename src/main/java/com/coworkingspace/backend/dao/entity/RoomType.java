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
@Table(name = "room_type")
@Getter
@Setter
public class RoomType extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "room_type_id", nullable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "image_storage_id", nullable = false)
	private ImageStorage imageStorage;

	@Lob
	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "room_type_name", nullable = false)
	private String roomTypeName;

	@Lob
	@Column(name = "description")
	private String description;
}
