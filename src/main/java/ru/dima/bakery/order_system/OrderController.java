package ru.dima.bakery.order_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dima.bakery.order_system.model.Order;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/test_getProducts")
    public List<Product> getRandomProducts() {
        return orderService.getRandomProducts();
    }

    @GetMapping("/make")
    public void makeOrder() {
        orderService.makeOrder();
    }

    @GetMapping("/")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
