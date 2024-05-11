package ru.dima.bakery.order_system.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.dima.bakery.exception.NoProductsAreCookedException;
import ru.dima.bakery.product_preparation_system.ProductRepository;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.util.*;

@Component
public class ProductWarehouse {
    private final ProductRepository productRepository;
    private final Random random = new Random();

    public ProductWarehouse(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Выбираются случайные продукты из таблицы продуктов в случайном количестве
     *
     * @return список продуктов для заказа
     * @throws NoProductsAreCookedException в случае отсутствия продуктов на складе
     */
    public Map<Product, Integer> getRandomProductsForOrder() throws NoProductsAreCookedException {
        int index = 0; // Используется индекс 0, т.к. пока есть только 6 рецептов продуктов
        Page<Product> productPage = productRepository.findAllByCountIsGreaterThanEqual(1, PageRequest.of(index, random.nextInt(1, 7)));
        Map<Product, Integer> productMap = new HashMap<>();

        if (productPage.hasContent()) {
            productPage.stream().forEach(product -> productMap.put(product, 0));

            return setQuantityForProductInOrder(productMap);
        }
        throw new NoProductsAreCookedException("Пока еще не приготовлен ни один продукт! Склад пустой! Ждите!");
    }

    /**
     * Задает случайное количество каждого продукта в заказе. НО! Максимальное количество всех продуктов
     * в заказе может быть 6 (т.е. 6 кофе, либо 3 чая и 3 кофе, либо 2 чая, 2 кофе и 2 пирога с мясом)
     *
     * @param productMap случайный список продуктов, который получен методом {@link ProductWarehouse#getRandomProductsForOrder()}
     * @return список продуктов с корректным числом позиций
     */
    private Map<Product, Integer> setQuantityForProductInOrder(Map<Product, Integer> productMap) {
        int maxProductsInOrder = 6;
        int orderSize = productMap.size();
        int count = maxProductsInOrder / orderSize;

        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            Product product = entry.getKey();
            int productCountInWarehouse = product.getCount();
            if (productCountInWarehouse > count) {
                entry.setValue(count);
                product.setCount(productCountInWarehouse - count);
            } else {
                entry.setValue(productCountInWarehouse);
                product.setCount(0);
            }
        }

        return productMap;
    }
}
