package ru.dima.bakery.order_system;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dima.bakery.order_system.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}
