package ru.dima.bakery.product_preparation_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import ru.dima.bakery.product_preparation_system.ProductType;

@Entity
public class Product {
    @Id
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    private int count;

    public Product() {
    }

    public Product(int count, ProductType productType) {
        this.count = count;
        this.productType = productType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
