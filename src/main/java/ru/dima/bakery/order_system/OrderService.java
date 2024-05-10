package ru.dima.bakery.order_system;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.bakery.order_system.model.*;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Transactional(propagation = Propagation.SUPPORTS)
    public void makeOrder() {
        Checkout checkout = checkoutManager.getAvailableCheckout();
        checkout.prepareOrder(orderProperties.getOrderTime());

        saveOrder();
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    private void saveOrder() {
        Order order = new Order();
        order.setOrderType(OrderType.randomOrderType());
        order.setCreationTime(LocalDateTime.now());
        order.setCity(CityDelivery.randomCityDelivery());

        List<Product> productsInOrder = productWarehouse.getRandomProductsForOrder();

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Product product : productsInOrder) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(new OrderProductKey(order.getId(), product.getId()));
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setProductCount(product.getCount());
            orderProducts.add(orderProduct);
        }

        order.setProductList(orderProducts);
        orderRepository.save(order);
    }
}
