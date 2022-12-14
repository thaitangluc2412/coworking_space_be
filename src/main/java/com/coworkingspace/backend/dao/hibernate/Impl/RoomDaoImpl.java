package com.coworkingspace.backend.dao.hibernate.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dao.entity.District;
import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dao.hibernate.RoomDao;
import com.coworkingspace.backend.mapper.DateStatusMapper;
import com.coworkingspace.backend.sdo.CountRoomType;

@Repository
public class RoomDaoImpl implements RoomDao {
	private final String GET_ROOM_FILTER = "SELECT room.*" +
		"FROM room\n" +
		"JOIN price ON room.price_id = price.price_id\n" +
		"JOIN room_type ON room.room_type_id = room_type.room_type_id\n" +
		"JOIN province ON room.province_id = province.code\n" +
		"WHERE room_type.room_type_id IN (SELECT room_type.room_type_id FROM room_type WHERE room_type.room_type_id = COALESCE(?1, room_type.room_type_id))\n" +
		"AND province.code IN (SELECT province.code FROM province WHERE province.code = COALESCE(?2, province.code))\n" +
		"AND room.room_name LIKE ?3\n" +
		"AND province.name LIKE ?4\n" +
		"AND province.codename LIKE ?4\n" +
		"AND price.day_price > ?5 AND price.day_price < ?6\n" +
		"ORDER BY room.time_create DESC ";

	private EntityManager entityManager;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public RoomDaoImpl(EntityManager entityManager, JdbcTemplate jdbcTemplateObject) {
		this.entityManager = entityManager;
		this.jdbcTemplateObject = jdbcTemplateObject;
	}

	@Override
	public List<Room> getWithFilter(String typeRoomId, String provinceId, String roomName, String cityName, String minPrice, String maxPrice) {
		Double min = 0.0;
		Double max = 1000000.0;
		Session session = entityManager.unwrap((Session.class));
		if (roomName == null)
			roomName = "";
		if (cityName == null)
			cityName = "";
		if (!(minPrice == null)) {
			min = Double.valueOf(minPrice);
		}
		if (!(maxPrice == null)) {
			max = Double.valueOf(maxPrice);
		}
		String list1 = "%" + roomName + "%";
		String list2 = "%" + cityName + "%";

		return session.createNativeQuery(GET_ROOM_FILTER, Room.class).setParameter(1, typeRoomId).setParameter(2, provinceId).setParameter(3, list1)
			.setParameter(4, list2).setParameter(5, min).setParameter(6, max).getResultList();
	}

	@Override
	public List<CountRoomType> getCountRoomType() {
		final String COUNT_ROOM_TYPE = "Select count(room.room_type_id) as `value`, room_type.room_type_name as roomTypeName \n" +
			"from room\n" +
			"JOIN room_type\n" +
			"ON room.room_type_id = room_type.room_type_id\n" +
			"GROUP BY room.room_type_id";
		List<CountRoomType> ret = new ArrayList<>();
		try {
			ret.addAll(jdbcTemplateObject.query(COUNT_ROOM_TYPE, (rs, rowNum) -> {
				return new CountRoomType(rs.getString("roomTypeName"),
					rs.getInt("value"));
			}));
		} catch (Exception e) {
			System.out.println("dont have room type name");
		}
		return ret;
	}

	@Override public List<Customer> findAllByBehavior() {
		String GET_CUSTOMER_BY_BEHAVIOR =
			"select customer.* from Customer join behavior on customer.customer_id = behavior.customer_id where behavior.`time` > 0";
		Session session = entityManager.unwrap((Session.class));
		return session.createNativeQuery(GET_CUSTOMER_BY_BEHAVIOR, Customer.class).getResultList();
	}
}
