package ru.dima.bakery.order_system.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checkout {
    private final static Logger LOGGER = LoggerFactory.getLogger(Checkout.class);
    private final String checkoutName;
    private boolean isAvailable = true;

    public Checkout(String checkoutName) {
        this.checkoutName = checkoutName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void prepareOrder(int preparingTime) {
        Thread checkoutThread = new Thread(() -> {
            isAvailable = false;
            try {
                Thread.sleep(preparingTime * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("Приготовление заказа было прервано", e);
            }
            LOGGER.info("Приготовление заказа закончилось в на кассе {}", Thread.currentThread().getName());
            isAvailable = true;
        });
        checkoutThread.setName(checkoutName);
        checkoutThread.start();
    }
}
