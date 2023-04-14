package com.glovo.api;

import com.glovo.model.Order;
import com.glovo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public void saveOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
    }

    @PutMapping
    public void updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
    }

    @DeleteMapping(path = "{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

}
