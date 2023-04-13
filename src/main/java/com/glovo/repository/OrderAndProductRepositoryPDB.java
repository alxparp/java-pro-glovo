package com.glovo.repository;

import com.glovo.exception.GlovoDaoException;
import com.glovo.model.Order;
import com.glovo.model.OrderAndProduct;
import com.glovo.repository.dao.OrderAndProductDao;
import com.glovo.repository.queries.OrderAndProductQuery;
import com.glovo.utils.Util;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository("orderAndProductPDB")
public class OrderAndProductRepositoryPDB implements OrderAndProductDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderAndProductRepositoryPDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(OrderAndProduct orderAndProduct) {
        if (Util.checkNull(orderAndProduct, orderAndProduct.getProductId(), orderAndProduct.getOrderId()))
            throw new IllegalArgumentException("Can't save null orderAndProduct connection");

        try {
            jdbcTemplate.update(
                    OrderAndProductQuery.SAVE_ORDER_PRODUCT.getValue(),
                    orderAndProduct.getOrderId(),
                    orderAndProduct.getProductId());
        } catch (DataAccessException ex) {
            throw new GlovoDaoException(ex.getMessage());
        }

    }

    @Override
    public void save(Order order) {
        if (Util.checkNull(order, order.getId(), order.getProducts()))
            throw new IllegalArgumentException("Can't save null orderAndProduct connections");

        try {
            jdbcTemplate.batchUpdate(
                    OrderAndProductQuery.SAVE_ORDER_PRODUCT.getValue(),
                    new BatchPreparedStatementSetter() {

                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, order.getId());
                            ps.setInt(2, order.getProducts().get(i).getId());
                        }

                        public int getBatchSize() {
                            return order.getProducts().size();
                        }

                    });
        } catch (DataAccessException ex) {
            throw new GlovoDaoException(ex.getMessage());
        }
    }

    @Override
    public void delete(Order order) {
        if (Util.checkNull(order, order.getId()))
            throw new IllegalArgumentException("Can't delete null orderAndProduct connections");

        try {
            jdbcTemplate.update(OrderAndProductQuery.DELETE_ORDER_PRODUCT.getValue(), order.getId());
        } catch (DataAccessException ex) {
            throw new GlovoDaoException(ex.getMessage());
        }

    }
}
