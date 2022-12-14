package com.coworkingspace.backend.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "image_storage")
public class ImageStorage extends BaseEntity{

	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "image_storage_id", nullable = false)
	private String id;

	@OneToMany(mappedBy = "imageStorage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	public void addImage(Image image) {
		images.add(image);
		image.setImageStorage(this);
	}

	public void removeImage(Image image) {
		images.remove(image);
		image.setImageStorage(null);
	}
}