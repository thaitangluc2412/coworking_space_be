package com.coworkingspace.backend.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DateMapper implements RowMapper<LocalDate> {
    @Override
    public LocalDate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getDate("date").toLocalDate();
    }
}
