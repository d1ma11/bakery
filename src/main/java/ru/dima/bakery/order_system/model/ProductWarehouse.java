package ru.dima.bakery.order_system.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.dima.bakery.product_preparation_system.ProductRepository;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class ProductWarehouse {
    private final ProductRepository productRepository;
    private final Random random = new Random();

    public ProductWarehouse(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getRandomProductsForOrder() {
        int index = 0; // Используется индекс 0, т.к. пока в нашей базе данных есть только 6 рецептов продуктов
        Page<Product> productPage = productRepository.findAll(PageRequest.of(index, random.nextInt(1, 7)));
        List<Product> productList = Collections.emptyList();

        if (productPage.hasContent()) {
            productList = productPage.getContent();
        }

        return correctProductsCountInOrder(productList);
    }

    private List<Product> correctProductsCountInOrder(List<Product> productList) {
        int maxProductsInOrder = 6;
        int orderSize = productList.size();
        int count = maxProductsInOrder / orderSize;

        for (Product product : productList) {
            product.setCount(count);
        }

        return productList;
    }
}
