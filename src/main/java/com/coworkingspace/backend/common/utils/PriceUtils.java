package com.coworkingspace.backend.common.utils;

import com.coworkingspace.backend.dao.entity.Price;
import com.coworkingspace.backend.dto.PriceDto;

public class PriceUtils {
	private PriceUtils() {
	}

	public static Price createOrUpdatePrice(Price price,
	                                        PriceDto priceDto) {
		if (price.getId() == null) {
			price = new Price();
		}
		price.setDayPrice(priceDto.getDayPrice());
		price.setMonthPrice(priceDto.getMonthPrice());
		price.setYearPrice(priceDto.getYearPrice());
		return price;
	}

}
