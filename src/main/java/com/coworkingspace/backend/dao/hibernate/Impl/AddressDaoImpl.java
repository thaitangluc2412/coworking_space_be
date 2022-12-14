package com.coworkingspace.backend.dao.hibernate.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coworkingspace.backend.dao.entity.District;
import com.coworkingspace.backend.dao.entity.Ward;
import com.coworkingspace.backend.dao.hibernate.AddressDao;

@Repository
public class AddressDaoImpl implements AddressDao {
	private static final String GET_DISTRICT = "SELECT * FROM District WHERE province_code = ?1";
	private static final String GET_WARD = "SELECT * FROM Ward WHERE district_code = ?1";
	private static final String GET_WARD_BY_ID = "SELECT * FROM Ward WHERE code = ?1";
	@Autowired
	private EntityManager entityManager;

	@Override public List<District> getDistrict(Integer provinceCode) {
		Session session = entityManager.unwrap((Session.class));
		return session.createNativeQuery(GET_DISTRICT, District.class).setParameter(1, provinceCode).getResultList();
	}

	@Override public List<Ward> getWard(Integer districtCode) {
		Session session = entityManager.unwrap((Session.class));
		return session.createNativeQuery(GET_WARD, Ward.class).setParameter(1, districtCode).getResultList();
	}

	@Override public Ward getWardById(Integer code) {
		Session session = entityManager.unwrap((Session.class));
		return session.createNativeQuery(GET_WARD_BY_ID, Ward.class).setParameter(1, code).getSingleResult();
	}
}
