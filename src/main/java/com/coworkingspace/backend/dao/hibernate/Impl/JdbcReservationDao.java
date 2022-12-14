package com.coworkingspace.backend.dao.hibernate.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coworkingspace.backend.dao.entity.Reservation;
import com.coworkingspace.backend.dao.hibernate.ReservationDao;
import com.coworkingspace.backend.mapper.DateMapper;
import com.coworkingspace.backend.mapper.DateStatusMapper;
import com.coworkingspace.backend.sdo.CountRoomType;
import com.coworkingspace.backend.sdo.DateStatus;
import com.coworkingspace.backend.service.ReservationStatusService;

@Repository
public class JdbcReservationDao implements ReservationDao {

	private JdbcTemplate jdbcTemplateObject;
	private EntityManager entityManager;

	@Autowired
	public JdbcReservationDao(JdbcTemplate jdbcTemplateObject, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.jdbcTemplateObject = jdbcTemplateObject;
	}

	@Autowired
	private ReservationStatusService reservationStatusService;

	@Override
	public String getFurthestValidDate(String roomId, String from) {
		String reservationStatusId = reservationStatusService.findByReservationStatusName("APPROVED").getId();
		final String GET_FURTHEST_VALID_DATE = "SELECT DATE(start_date) - INTERVAL 1 DAY AS furthest_date FROM reservation \n" +
			"WHERE room_id = '" + roomId + "'\n" +
			"AND DATE('" + from + "') < DATE(start_date)\n" +
			"AND reservation_status_id = '" + reservationStatusId + "' \n" +
			"ORDER BY DATE(start_date)\n" +
			"LIMIT 1\n";
		String ret = "2069-12-31";
		try {
			ret = jdbcTemplateObject.queryForObject(GET_FURTHEST_VALID_DATE, String.class);
		} catch (Exception e) {
			System.out.println("dont have furthest valid date");
		}
		return ret;
	}

	@Override
	public List<DateStatus> getDateStatus(String roomId, int month, int year) {
		String reservationStatusId = reservationStatusService.findByReservationStatusName("APPROVED").getId();
		List<DateStatus> ret = new ArrayList<>();
		final String sql = "WITH RECURSIVE days AS\n" +
			"(\n" +
			"   SELECT 1 AS day UNION ALL SELECT day + 1 FROM days WHERE day < DAY(LAST_DAY('" + year + "-" + month + "-01'))\n" +
			")\n" +
			"\n" +
			"SELECT d.day, IF(d.day >= DAY(r.start_date) AND d.day <= DAY(r.end_date),TRUE,FALSE) AS status FROM days AS d\n" +
			"LEFT JOIN reservation AS r\n" +
			"ON (\n" +
			"    d.day >= DAY(r.start_date) AND d.day <= DAY(r.end_date)\n" +
			"    AND r.reservation_status_id = '" + reservationStatusId + "'\n" +
			"    AND MONTH(r.start_date) = " + month + "\n" +
			"    AND YEAR(r.start_date) = " + year + "\n" +
			"    AND r.room_id = '" + roomId + "'\n" +
			")\n" +
			"ORDER BY day\n";
		ret.addAll(jdbcTemplateObject.query(sql, new DateStatusMapper()));
		return ret;
	}

	@Override
	public List<LocalDate> getAllInvalidDates(String roomId) throws NotFoundException {
		String reservationStatusId = reservationStatusService.findByReservationStatusName("APPROVED").getId();
		List<LocalDate> ret = new ArrayList<>();
		final String sql = "SELECT DISTINCT gen_date AS date FROM \n" +
			"(SELECT ADDDATE('1970-01-01',t4*10000 + t3*1000 + t2*100 + t1*10 + t0) gen_date FROM\n" +
			" (SELECT 0 t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0,\n" +
			" (SELECT 0 t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,\n" +
			" (SELECT 0 t2 union SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,\n" +
			" (SELECT 0 t3 union SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3,\n" +
			" (SELECT 0 t4 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t4) v\n" +
			"JOIN reservation ON \n" +
			"gen_date >= DATE(start_date) AND gen_date <= DATE(end_date)\n" +
			"AND room_id = '" + roomId + "'\n" +
			"AND reservation_status_id = '" + reservationStatusId + "'\n" +
			"ORDER BY date";
		System.out.println(sql);
		try {
			ret.addAll(jdbcTemplateObject.query(sql, new DateMapper()));
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
		}
		return ret;
	}

	@Override public List<Reservation> getBySellerId(String id) {
		final String GET_BY_SELLER_ID =
			"SELECT reservation.* FROM reservation\n" +
				"JOIN room ON reservation.room_id = room.room_id\n" +
				"JOIN customer ON room.customer_id = customer.customer_id\n" +
				"WHERE customer.customer_id = ?1\n" +
				"ORDER BY reservation.time_create DESC";
		Session session = entityManager.unwrap((Session.class));
		return session.createNativeQuery(GET_BY_SELLER_ID, Reservation.class).setParameter(1, id).getResultList();
	}

	// TODO: đổi reservation_status_id lại cho đúng
	@Override
	public com.cnpm.workingspace.sdo.Budget getBudget() {
		com.cnpm.workingspace.sdo.Budget ret = new com.cnpm.workingspace.sdo.Budget();
		int month = LocalDate.now().getMonthValue();
		int year = LocalDate.now().getYear();
		int preMonth = month - 1;
		int preYear = year;
		if (month == 1) {
			preMonth = 12;
			preYear = year - 1;
		}
		final String sql = "Select sum(reservation.deposit) as budget, sum(reservation.deposit) as percent from reservation \n" +
			"where reservation_status_id = 'tngpi7zVKfwAY0N';";
		try {
			ret = jdbcTemplateObject.queryForObject(sql, (rs, rowNum) -> {
				return new com.cnpm.workingspace.sdo.Budget(
					rs.getDouble("percent"),
					rs.getDouble("budget")
				);
			});
			System.out.println("added");
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
		}
		return ret;
	}

	@Override public List<CountRoomType> getToTalPerMonth() {
		String GET_TOTAL_PER_MONTH = "SELECT sum(total) as total, month(time_create) AS month FROM reservation\n" +
			"WHERE YEAR(time_create) = YEAR(NOW())\n" +
			"AND reservation_status_id = \"tngpi7zVKfwAY0N\"\n" +
			"AND time_create <= NOW()\n" +
			"GROUP BY MONTH(time_create)\n" +
			"ORDER BY MONTH(time_create)";
		List<CountRoomType> ret = new ArrayList<>();
		try {
			ret.addAll(jdbcTemplateObject.query(GET_TOTAL_PER_MONTH, (rs, rowNum) -> {
				return new CountRoomType(rs.getString("month"),
					rs.getInt("total"));
			}));
		} catch (Exception e) {
			System.out.println("dont have room type name");
		}
		return ret;
	}
}
