package com.coworkingspace.backend.dao.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer extends BaseEntity{
	@Id
	@GenericGenerator(name = "id_gen", strategy = "com.coworkingspace.backend.common.utils.GenerateUUID")
	@GeneratedValue(generator = "id_gen")
	@Column(name = "customer_id")
	private String id;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "behavior_id", nullable = false, insertable=false, updatable=false)
	private Set<Behavior> behaviors = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", nullable = false, insertable=false, updatable=false)
	private Set<Review> reviews = new HashSet<>();

	@Column(name = "enable")
	private Boolean enable = false;

	@Override
	public boolean equals(Object o){
		if (this == o) {
			return true;
		}
		if (!(o instanceof Customer)){
			return false;
		}
		Customer customer = (Customer) o;
		return Objects.equals(id, customer.id) && Objects.equals(email, customer.email);
	}

	@Override
	public int hashCode(){
		return Objects.hash(id, email);
	}

	public static int indexOf(List<Customer> objectList, Customer o) {
		if (o == null) {
			for (int i = 0; i < objectList.size(); i++)
				if (objectList.get(i) == null)
					return i;
		} else {
			for (int i = 0; i < objectList.size(); i++)
				if (Objects.equals(o, objectList.get(i)))
					return i;
		}
		return -1;
	}
}
