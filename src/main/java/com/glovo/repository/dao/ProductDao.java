package com.glovo.repository.dao;

import com.glovo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<Product> getById(Integer id);
    List<Product> getAllProducts();
    Integer save(Product product);
    void update(Product product);
    void delete(Integer id);

}
