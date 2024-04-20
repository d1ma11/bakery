package ru.dima.bakery.product_preparation_system.model;

import ru.dima.bakery.product_preparation_system.ProductType;

public interface CookService {
    void cook(ProductType productType, int cookingTime) throws InterruptedException;
}
