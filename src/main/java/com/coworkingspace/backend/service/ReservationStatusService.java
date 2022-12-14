package com.coworkingspace.backend.service;

import com.coworkingspace.backend.dao.entity.ReservationStatus;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface ReservationStatusService {
	ReservationStatus findByReservationStatusName(String name);
}
