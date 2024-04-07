package ru.dima.bakery.product_preparation_system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductProperties {

    // Время, необходимое для приготовления продукта
    @Value("#{${user.product-time}}")
    private Map<String, Integer> productTime;

    public Map<String, Integer> getProductTime() {
        return productTime;
    }

    public void setProductTime(Map<String, Integer> productTime) {
        this.productTime = productTime;
    }
}
