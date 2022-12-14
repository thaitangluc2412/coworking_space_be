package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.dao.entity.Price;
import com.coworkingspace.backend.dao.repository.PriceRepository;
import com.coworkingspace.backend.dto.PriceDto;
import com.coworkingspace.backend.mapper.PriceMapper;
import com.coworkingspace.backend.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceRepository priceRepository;

	@Autowired
	private PriceMapper priceMapper;

	@Override
	public void createPrice(PriceDto priceDto) {
		Price price = priceMapper.priceDtoToPrice(priceDto);
		priceRepository.save(price);
	}

	@Override
	public Price findById(String id) {
		return priceRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found room by Id"));
	}
}
