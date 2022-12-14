package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.dao.entity.Price;
import com.coworkingspace.backend.dto.PriceDto;
import org.mapstruct.Mapper;

@Mapper
public interface PriceMapper {
	Price priceDtoToPrice(PriceDto priceDto);

	PriceDto priceToPriceDto(Price price);
}
