package ru.dima.bakery.product_preparation_system.model;

import ru.dima.bakery.product_preparation_system.ProductType;

public class TeaMachine extends Machine {
    @Override
    public ProductType getProductType() {
        return ProductType.TEA;
    }
}
