package com.coworkingspace.backend.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "utility_storage")
public class UtilityStorage extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "utility_storage_id", nullable = false)
	private String id;

	@OneToMany(mappedBy = "utilityStorage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Utility> utilities = new ArrayList<>();

	public void addUtility(Utility utility) {
		utilities.add(utility);
		utility.setUtilityStorage(this);
	}

	public void removeUtility(Utility utility) {
		utilities.remove(utility);
		utility.setUtilityStorage(null);
	}
}
