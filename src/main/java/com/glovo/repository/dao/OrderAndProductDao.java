package com.glovo.repository.dao;

import com.glovo.model.Order;
import com.glovo.model.OrderAndProduct;

public interface OrderAndProductDao {
    void save(OrderAndProduct orderAndProduct);
    void save(Order order);
    void delete(Order order);

}
