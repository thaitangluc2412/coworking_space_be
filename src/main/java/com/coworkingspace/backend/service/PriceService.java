package com.coworkingspace.backend.service;

import com.coworkingspace.backend.dao.entity.Price;
import com.coworkingspace.backend.dto.PriceDto;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface PriceService {
	void createPrice(PriceDto price);

	Price findById(String id) throws NotFoundException;
}
