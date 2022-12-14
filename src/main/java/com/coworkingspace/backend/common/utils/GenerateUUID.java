package com.coworkingspace.backend.common.utils;


import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class GenerateUUID implements IdentifierGenerator {
	private static final Random rand = new Random();

	private GenerateUUID() {
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return rand.ints(48, 123)
				.filter(num -> (num < 58 || num > 64) && (num < 91 || num > 96))
				.limit(15)
				.mapToObj(c -> (char) c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
				.toString();
	}
}