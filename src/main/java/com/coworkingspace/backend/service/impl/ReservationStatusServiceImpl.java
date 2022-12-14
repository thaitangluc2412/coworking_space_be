package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.dao.entity.ReservationStatus;
import com.coworkingspace.backend.dao.repository.ReservationStatusRepository;
import com.coworkingspace.backend.service.ReservationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReservationStatusServiceImpl implements ReservationStatusService {

	@Autowired
	private ReservationStatusRepository reservationStatusRepository;

	@Override
	public ReservationStatus findByReservationStatusName(String name) {
		return reservationStatusRepository.findByReservationStatusName(name).orElseThrow(() -> new RuntimeException("Not found status name"));
	}
}
