package ru.dima.bakery.product_preparation_system.model;

import ru.dima.bakery.product_preparation_system.ProductType;

public class CoffeeMachine extends Machine {
    @Override
    public ProductType getProductType() {
        return ProductType.COFFEE;
    }
}
