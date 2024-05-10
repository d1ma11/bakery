package ru.dima.bakery.product_preparation_system.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dima.bakery.product_preparation_system.ProductType;

public interface CookService {

    Logger LOGGER = LoggerFactory.getLogger(CookService.class);

    void cook(ProductType productType, int cookingTime) throws InterruptedException;

    default void cookLog(Thread thread, ProductType productType, int cookingTime) {
        LOGGER.info("Началась готовка продукта - {} в потоке {}", productType.name(), Thread.currentThread().getName());
        try {
            Thread.sleep(cookingTime * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Приготовление было прервано");
        }
        LOGGER.info("Готовка продукта {} закончилась в потоке {}", productType.name(),Thread.currentThread().getName());
    }
}
