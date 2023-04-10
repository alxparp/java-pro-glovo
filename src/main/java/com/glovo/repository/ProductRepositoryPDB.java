package com.glovo.repository;

import com.glovo.model.Product;
import com.glovo.repository.dao.ProductDao;
import com.glovo.repository.mapper.ProductRowMapper;
import com.glovo.repository.queries.ProductQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryPDB implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryPDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> getById(int id) {
        Product product = jdbcTemplate.queryForObject(ProductQuery.GET_PRODUCT_BY_ID.getValue(), new ProductRowMapper(), id);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(ProductQuery.GET_ALL_PRODUCTS.getValue(), new ProductRowMapper());
    }

    @Override
    public Integer save(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ProductQuery.SAVE_PRODUCT.getValue(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getCost());
            return ps;
        }, keyHolder);
        return (Integer) keyHolder.getKeys().get("id");
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(ProductQuery.UPDATE_PRODUCT.getValue(), product.getName(), product.getCost(), product.getId());
    }

    @Override
    public void delete(Product product) {
        jdbcTemplate.update(ProductQuery.DELETE_PRODUCT.getValue(), product.getId());
    }
}
