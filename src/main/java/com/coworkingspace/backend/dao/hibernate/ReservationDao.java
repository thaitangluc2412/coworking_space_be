package com.coworkingspace.backend.dao.hibernate;

import com.coworkingspace.backend.dao.entity.Reservation;
import com.coworkingspace.backend.dto.ReservationDto;
import com.coworkingspace.backend.dto.ReservationListDto;
import com.coworkingspace.backend.sdo.CountRoomType;
import com.coworkingspace.backend.sdo.DateStatus;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservationDao {
	List<DateStatus> getDateStatus(String roomId, int month, int year) throws NotFoundException;
	String getFurthestValidDate(String roomId, String from) throws NotFoundException;
	List<LocalDate> getAllInvalidDates(String roomId) throws NotFoundException;
	List<Reservation> getBySellerId(String id);
	com.cnpm.workingspace.sdo.Budget getBudget();
	List<CountRoomType> getToTalPerMonth();
}
