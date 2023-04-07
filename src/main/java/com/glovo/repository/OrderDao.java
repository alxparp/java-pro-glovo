package com.glovo.repository;

import com.glovo.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> getById(int id);
    List<Order> getAllOrders();
    void save(Order order);

}
