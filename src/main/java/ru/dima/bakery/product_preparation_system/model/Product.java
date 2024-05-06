package ru.dima.bakery.product_preparation_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import ru.dima.bakery.order_system.model.OrderProduct;
import ru.dima.bakery.product_preparation_system.ProductType;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private ProductType productType;

    private int count;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderProduct> productInOrders;

    public Product() {
    }

    public Product(Integer id, ProductType productType, int count, List<OrderProduct> productInOrders) {
        this.id = id;
        this.productType = productType;
        this.count = count;
        this.productInOrders = productInOrders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderProduct> getProductInOrders() {
        return productInOrders;
    }

    public void setProductInOrders(List<OrderProduct> productInOrders) {
        this.productInOrders = productInOrders;
    }
}
