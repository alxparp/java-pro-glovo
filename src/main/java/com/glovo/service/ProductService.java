package com.glovo.service;

import com.glovo.model.Product;
import com.glovo.repository.dao.ProductDao;
import com.glovo.utils.Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Optional<Product> getProductById(Integer id) {
        return productDao.getById(id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public void deleteProduct(Integer id) {
        productDao.delete(id);
    }

}
