package com.glovo.repository;

import com.glovo.model.Order;
import com.glovo.repository.dao.OrderDao;
import com.glovo.repository.mapper.OrderRowMapper;
import com.glovo.repository.mapper.ProductRowMapper;
import com.glovo.repository.queries.OrderQuery;
import com.glovo.repository.queries.ProductQuery;
import com.glovo.utils.Util;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository("orderRepoPDB")
public class OrderRepositoryPDB implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryPDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Order> getById(int id) {
        return jdbcTemplate.query(
                        OrderQuery.GET_ORDER_BY_ID.getValue(),
                        new OrderRowMapper(),
                        id)
                .stream()
                .findAny()
                .map(o -> {
                    o.setProducts(jdbcTemplate.query(
                            ProductQuery.GET_PRODUCT_BY_ORDER.getValue(),
                            new ProductRowMapper(),
                            o.getId()));
                    return o;
                });
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = jdbcTemplate.query(
                OrderQuery.GET_ALL_ORDERS.getValue(),
                new OrderRowMapper());

        for (Order order: orders) {
            order.setProducts(jdbcTemplate.query(ProductQuery.GET_PRODUCT_BY_ORDER.getValue(), new ProductRowMapper(), order.getId()));
        }
        return orders;
    }

    @Override
    public Integer save(Order order) {
        if (Util.checkNull(order, order.getDate(), order.getCost()))
            throw new IllegalArgumentException("Can't save null order");

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(OrderQuery.SAVE_ORDERS.getValue(), Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, Date.valueOf(order.getDate()));
                ps.setDouble(2, order.getCost());
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");

        } catch (DataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Make sure all order data is spelled correctly", ex);
        }
    }

    @Override
    public void update(Order order) {
        if (Util.checkNull(order, order.getDate(), order.getCost(), order.getId()))
            throw new IllegalArgumentException("Can't update null order");

        try {
            jdbcTemplate.update(OrderQuery.UPDATE_ORDER.getValue(), order.getDate(), order.getCost(), order.getId());
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Make sure all order data is spelled correctly", ex);
        }
    }

    @Override
    public void delete(Integer id) {
        if (Util.checkNull(id))
            throw new IllegalArgumentException("Can't delete null order id");

        try {
            jdbcTemplate.update(OrderQuery.DELETE_ORDER.getValue(), id);
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Can't delete order, check if id is correct", ex);
        }
    }
}
