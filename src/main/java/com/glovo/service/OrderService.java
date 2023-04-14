package com.glovo.service;

import com.glovo.model.Order;
import com.glovo.model.OrderAndProduct;
import com.glovo.model.Product;
import com.glovo.repository.dao.OrderAndProductDao;
import com.glovo.repository.dao.OrderDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final OrderAndProductDao orderAndProductDao;
    private final TransactionTemplate transactionTemplate;

    public OrderService(@Qualifier("orderRepoPDB") OrderDao orderDao,
                        OrderAndProductDao orderAndProductDao,
                        PlatformTransactionManager transactionManager) {
        this.orderDao = orderDao;
        this.orderAndProductDao = orderAndProductDao;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderDao.getById(id);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public void saveOrder(Order order) {
        transactionTemplate.executeWithoutResult(action -> {
            Integer orderId = orderDao.save(order);

            for (Product product : order.getProducts()) {
                if (product.getId() != null) {
                    orderAndProductDao.save(new OrderAndProduct(orderId, product.getId()));
                }
            }
        });
    }

    public void updateOrder(Order order) {
        transactionTemplate.executeWithoutResult(action -> {
            orderDao.update(order);
            orderAndProductDao.delete(order);
            orderAndProductDao.save(order);
        });
    }

    public void deleteOrder(Integer id) {
        orderDao.delete(id);
    }

}













