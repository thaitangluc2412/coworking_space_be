package com.coworkingspace.backend.service;

import java.util.List;

import com.coworkingspace.backend.common.sdo.Email;
import com.coworkingspace.backend.dto.ReservationListDto;

import javax.mail.MessagingException;

import org.springframework.data.crossstore.ChangeSetPersister;

public interface EmailService {
	void sendEmail(Email email) throws MessagingException;

	void sendCreateReservationMail(ReservationListDto reservationListDto, String emailSeller) throws MessagingException, ChangeSetPersister.NotFoundException;

	void sendUpdateReservationMail(ReservationListDto oldReservationDto, String reservationStatusNameNew, String email) throws MessagingException;
}
