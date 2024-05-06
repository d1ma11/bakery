package ru.dima.bakery.order_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import ru.dima.bakery.product_preparation_system.model.Product;

@Entity
public class OrderProduct {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private OrderProductKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonBackReference
    private Product product;

    private int productCount;

    public OrderProduct() {
    }

    public OrderProduct(OrderProductKey id, Order order, Product product, int productCount) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.productCount = productCount;
    }

    public OrderProductKey getId() {
        return id;
    }

    public void setId(OrderProductKey id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
