package ru.dima.bakery.product_preparation_system.model;

import ru.dima.bakery.product_preparation_system.ProductType;

import java.util.concurrent.atomic.AtomicInteger;

public class Oven implements CookService {
    private final OvenType ovenType;
    private final int capacity;
    private volatile AtomicInteger currentLoad;

    public Oven(OvenType ovenType, int capacity) {
        this.ovenType = ovenType;
        this.capacity = capacity;
        this.currentLoad = new AtomicInteger(0);
    }

    public OvenType getOvenType() {
        return ovenType;
    }

    public int getCapacity() {
        return capacity;
    }

    public AtomicInteger getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(AtomicInteger currentLoad) {
        this.currentLoad = currentLoad;
    }

    @Override
    public void cook(ProductType productType, int cookingTime) {
        Thread cookingThread = new Thread(() -> {
            currentLoad.incrementAndGet();
            System.out.println("Началась готовка продукта - " + productType.name() + " в потоке " + Thread.currentThread().getName());
            try {
                Thread.sleep(cookingTime * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Приготовление было прервано");
            }
            System.out.println("Готовка продукта " + productType.name() + " закончилась в потоке " + Thread.currentThread().getName());
            currentLoad.decrementAndGet();
        });
        cookingThread.setName(ovenType.name() + "-" + currentLoad.get());
        cookingThread.start();
    }
}
