package com.coworkingspace.backend.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.coworkingspace.backend.common.sdo.Email;
import com.coworkingspace.backend.common.sdo.EmailProperty;
import com.coworkingspace.backend.dto.ReservationListDto;
import com.coworkingspace.backend.service.EmailService;
import com.github.difflib.text.DiffRowGenerator;
import com.coworkingspace.backend.common.utils.StringDiffUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

	public static final String CREATE_EMAIL_TEMPLATE = "create-email.html";
	public static final String UPDATE_EMAIL_TEMPLATE = "update-email.html";
	public static final String EMAIL_SUBJECT = "[coworking-space] Update for room \"%s\"";

	private final DiffRowGenerator diffRowGenerator;
	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;

	@Value("#{'${coworkingspace.fe.url}' + '/'}")
	private String homeUrl;

	@Override public void sendEmail(Email email) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(
			message,
			MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
			StandardCharsets.UTF_8.name()
		);

		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setCc(email.getCc().toArray(new String[0]));

		Context context = new Context();
		context.setVariables(email.getProperties());
		String html = templateEngine.process(email.getTemplate(), context);
		helper.setText(html, true);
		ClassPathResource logo = new ClassPathResource("static.images/logo.png");
		ClassPathResource coworking = new ClassPathResource("static.images/coworkingspace.png");
		helper.addInline("logo", logo);
		helper.addInline("coworking", coworking);
		emailSender.send(message);
	}

	@Override public void sendCreateReservationMail(ReservationListDto reservationListDto, String emailSeller) throws MessagingException {
		EmailProperty emailProperty = new EmailProperty();
		emailProperty.setProperty("customerName", reservationListDto.getCustomerName());
		emailProperty.setProperty("email", reservationListDto.getEmail());
		emailProperty.setProperty("phone", reservationListDto.getPhoneNumber());
		emailProperty.setProperty("startDate", reservationListDto.getStartDate());
		emailProperty.setProperty("endDate", reservationListDto.getEndDate());
		emailProperty.setProperty("reservationStatusName", reservationListDto.getReservationStatusName());
		emailProperty.setProperty("total", reservationListDto.getTotal() + "$");
		emailProperty.setProperty("timeCreate", reservationListDto.getTimeCreate());
		emailProperty.setProperty("homeUrl", homeUrl);
		emailProperty.setProperty("issueUrl", homeUrl + "manage/businessDetail/" + reservationListDto.getId());
		sendEmail(Email.builder()
			.to(emailSeller)
			.cc(Collections.emptyList())
			.properties(emailProperty.getProperties())
			.subject(String.format(EMAIL_SUBJECT, reservationListDto.getRoomName()))
			.template(CREATE_EMAIL_TEMPLATE)
			.build());
	}

	@Override
	public void sendUpdateReservationMail(ReservationListDto oldReservationDto, String reservationStatusNameNew, String email) throws MessagingException {
		EmailProperty emailProperty = new EmailProperty();
		emailProperty.setProperty("customerName", oldReservationDto.getCustomerName());
		emailProperty.setProperty("email", oldReservationDto.getEmail());
		emailProperty.setProperty("phone", oldReservationDto.getPhoneNumber());
		emailProperty.setProperty("startDate", oldReservationDto.getStartDate());
		emailProperty.setProperty("endDate", oldReservationDto.getEndDate());
		emailProperty.setProperty("reservationStatusName", oldReservationDto.getReservationStatusName(), reservationStatusNameNew);
		emailProperty.setProperty("total", oldReservationDto.getTotal() + "$");
		emailProperty.setProperty("timeUpdate", oldReservationDto.getTimeUpdate());
		emailProperty.setProperty("homeUrl", homeUrl);
		emailProperty.setProperty("issueUrl", homeUrl + "reservation/" + oldReservationDto.getId());

		sendEmail(Email.builder()
			.to(email)
			.cc(Collections.emptyList())
			.properties(emailProperty.getProperties())
			.subject(String.format(EMAIL_SUBJECT, oldReservationDto.getRoomName()))
			.template(UPDATE_EMAIL_TEMPLATE)
			.build());
	}
}
