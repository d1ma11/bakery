package ru.dima.bakery.product_preparation_system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductByProductType(ProductType productType);

    Page<Product> findAll(Pageable pageable);
}
