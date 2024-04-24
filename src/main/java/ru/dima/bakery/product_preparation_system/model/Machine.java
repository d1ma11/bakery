package ru.dima.bakery.product_preparation_system.model;

import ru.dima.bakery.product_preparation_system.ProductType;

public abstract class Machine implements CookService {
    private boolean isAvailable = true;

    public boolean isAvailable() {
        return isAvailable;
    }

    public abstract ProductType getProductType();

    @Override
    public void cook(ProductType productType, int cookingTime) {
        Thread cookingThread = new Thread(() -> {
            isAvailable = false;
            System.out.println("Началась готовка продукта - " + productType.name() + " в потоке " + Thread.currentThread().getName());
            try {
                Thread.sleep(cookingTime * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Приготовление было прервано");
            }
            System.out.println("Готовка продукта " + productType.name() + " закончилась в потоке " + Thread.currentThread().getName());
            isAvailable = true;
        });
        cookingThread.setName(productType.name().toLowerCase() + "-machine");
        cookingThread.start();
    }
}
