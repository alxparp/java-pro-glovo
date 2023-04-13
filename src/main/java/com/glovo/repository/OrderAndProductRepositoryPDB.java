package com.glovo.repository;

import com.glovo.model.OrderAndProduct;
import com.glovo.repository.dao.OrderAndProductDao;
import com.glovo.repository.queries.OrderAndProductQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("orderAndProductPDB")
public class OrderAndProductRepositoryPDB implements OrderAndProductDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderAndProductRepositoryPDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(OrderAndProduct orderAndProduct) {
        jdbcTemplate.update(
                OrderAndProductQuery.SAVE_ORDER_PRODUCT.getValue(),
                orderAndProduct.getOrderId(),
                orderAndProduct.getProductId());
    }
}
