package com.glovo.repository;

import com.glovo.model.Order;
import com.glovo.model.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class OrderRepository implements OrderDao {

    private final List<Product> products1 = List.of(
            new Product(1, "Burger", 100),
            new Product(2, "Potato Free", 80.5),
            new Product(3, "Ice Cream", 60));
    private final List<Product> products2 = List.of(
            new Product(4, "Coffee", 40),
            new Product(5, "Cake", 40)
    );

    private final List<Order> DB = List.of(
            new Order(1, LocalDate.now(), 240.5, products1),
            new Order(2, LocalDate.now(), 80, products2));

    @Override
    public Optional<Order> getById(int id) {
        return DB.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
    }

    @Override
    public List<Order> getAllOrders() {
        return DB;
    }

    @Override
    public void save(Order order) {
        int randomNum = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        Order newOrder = new Order(randomNum, order.getDate(), order.getCost(), new ArrayList<>(order.getProducts()));
        DB.add(newOrder);
    }
}
