package ru.dima.bakery.product_preparation_system;

import org.springframework.stereotype.Service;
import ru.dima.bakery.product_preparation_system.model.*;

import java.util.Optional;

@Service
public class PreparationService {
    private final ProductRepository productRepository;
    private final ProductProperties productProperties;
    private final CookingManager cookingManager;
    private final Warehouse warehouse;

    public PreparationService(ProductRepository productRepository, ProductProperties productProperties, CookingManager cookingManager, Warehouse warehouse) {
        this.productRepository = productRepository;
        this.productProperties = productProperties;
        this.cookingManager = cookingManager;
        this.warehouse = warehouse;
    }

    public void makeProduct(ProductType productType) {
        if (!warehouse.checkWarehouse(productType)) {
            System.err.println("Не хватает сырья для приготовления продута - " + productType.name());
            return;
        }

        if (productType == ProductType.TEA || productType == ProductType.COFFEE) {
            Machine machine = cookingManager.getAvailableMachine(productType);
            machine.cook(productType, productProperties.getProductTime().get(productType.name()));
        } else {
            Oven oven = cookingManager.getAvailableOven();
            oven.cook(productType, productProperties.getProductTime().get(productType.name()));
        }
        saveProduct(productType);
    }

    private void saveProduct(ProductType productType) {
        Optional<Product> existingProduct = productRepository.findProductByProductType(productType);
        Product product;

        if (existingProduct.isPresent()) {
            product = existingProduct.get();
        } else {
            product = new Product();
            product.setProductType(productType);
        }
        product.setCount(product.getCount() + 1);
        productRepository.save(product);

        warehouse.useRaws(productType);
    }
}
