package ru.dima.bakery.order_system;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.bakery.exception.NoProductsAreCookedException;
import ru.dima.bakery.order_system.model.*;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProperties orderProperties;
    private final ProductWarehouse productWarehouse;
    private final CheckoutManager checkoutManager;

    public OrderService(
            OrderRepository orderRepository,
            OrderProperties orderProperties,
            ProductWarehouse productWarehouse,
            CheckoutManager checkoutManager) {
        this.orderRepository = orderRepository;
        this.orderProperties = orderProperties;
        this.productWarehouse = productWarehouse;
        this.checkoutManager = checkoutManager;
    }

    /*
    Т.к. после создания заказа, мы его отправляем сразу в доставку, значит мы
    должны передавать город доставки, тип доставки и сам заказ.
    Однако у нас все это содержится в объекте заказа, поэтому не надо парится
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void makeOrder() throws NoProductsAreCookedException {
        saveOrder();

        Checkout checkout = checkoutManager.getAvailableCheckout();
        checkout.prepareOrder(orderProperties.getOrderTime());
    }

    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.FOUND);
    }

    //TODO: нужно закинуть логику метода saveOrder() в класс Checkout, т.к. исходя из логики,
    // заказ должен готовиться в сущности кассы, а не отдельно от этой сущности
    private void saveOrder() throws NoProductsAreCookedException {
        Order order = new Order();
        order.setOrderType(OrderType.randomOrderType());
        order.setCreationTime(LocalDateTime.now());
        order.setCity(CityDelivery.randomCityDelivery());

        Map<Product, Integer> productsInOrder = productWarehouse.getRandomProductsForOrder();

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : productsInOrder.entrySet()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(new OrderProductKey(order.getId(), entry.getKey().getId()));
            orderProduct.setOrder(order);
            orderProduct.setProduct(entry.getKey());
            orderProduct.setProductCount(entry.getValue());
            orderProducts.add(orderProduct);
        }

        order.setProductList(orderProducts);
        orderRepository.save(order);
    }
}
