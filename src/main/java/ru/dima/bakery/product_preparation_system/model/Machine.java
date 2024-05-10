package ru.dima.bakery.product_preparation_system.model;

import ru.dima.bakery.product_preparation_system.ProductType;

public abstract class Machine implements CookService {

    private boolean isAvailable = true;
    protected String machineName;

    public abstract ProductType getProductType();

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void cook(ProductType productType, int cookingTime) {
        Thread cookingThread = new Thread(() -> {
            isAvailable = false;
            cookLog(Thread.currentThread(), productType, cookingTime);
            isAvailable = true;
        });
        cookingThread.setName(machineName);
        cookingThread.start();
    }
}
