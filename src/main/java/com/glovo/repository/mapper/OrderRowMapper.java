package com.glovo.repository.mapper;

import com.glovo.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Order.builder()
                .id(resultSet.getInt("id"))
                .date(resultSet.getDate("date").toLocalDate())
                .cost(resultSet.getDouble("cost"))
                .build();
    }
}
