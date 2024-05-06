package ru.dima.bakery.order_system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderProperties {
    @Value("${user.order-properties.position-count}")
    private int positionCount;

    @Value("${user.order-properties.order-time}")
    private int orderTime;

    public int getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(int positionCount) {
        this.positionCount = positionCount;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }
}
