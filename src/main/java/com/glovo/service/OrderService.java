package com.glovo.service;

import com.glovo.model.Order;
import com.glovo.model.OrderAndProduct;
import com.glovo.model.Product;
import com.glovo.repository.dao.OrderAndProductDao;
import com.glovo.repository.dao.OrderDao;
import com.glovo.repository.dao.ProductDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final OrderAndProductDao orderAndProductDao;
    private final TransactionTemplate transactionTemplate;

    public OrderService(@Qualifier("orderRepoPDB") OrderDao orderDao,
                        ProductDao productDao,
                        OrderAndProductDao orderAndProductDao,
                        PlatformTransactionManager transactionManager) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.orderAndProductDao = orderAndProductDao;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Optional<Order> getOrderById(int id) {
        return orderDao.getById(id);
    }

    public void saveOrder(Order order) {
        transactionTemplate.executeWithoutResult(action -> {
            Integer orderId = orderDao.save(order);

            for (Product product : order.getProducts()) {
                if (product.getId() == null) {
                    Integer productId = productDao.save(product);
                    orderAndProductDao.save(new OrderAndProduct(orderId, productId));
                } else {
                    productDao.update(product);
                }
            }
        });
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

}













