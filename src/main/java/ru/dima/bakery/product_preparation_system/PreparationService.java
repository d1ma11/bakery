package ru.dima.bakery.product_preparation_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dima.bakery.exception.NotEnoughRawException;
import ru.dima.bakery.product_preparation_system.model.*;

import java.util.List;
import java.util.Optional;

@Service
public class PreparationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PreparationService.class);
    private final ProductRepository productRepository;
    private final ProductProperties productProperties;
    private final CookingManager cookingManager;
    private final Warehouse warehouse;

    public PreparationService(ProductRepository productRepository,
                              ProductProperties productProperties,
                              CookingManager cookingManager,
                              Warehouse warehouse) {
        this.productRepository = productRepository;
        this.productProperties = productProperties;
        this.cookingManager = cookingManager;
        this.warehouse = warehouse;
    }

    public void makeProduct(ProductType productType) {
        try {
            warehouse.checkWarehouse(productType);
        } catch (NotEnoughRawException e) {
            LOGGER.error(e.getMessage(), e);
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

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

    //TODO: нужно закинуть логику метода saveProduct() в класс Oven и Machine, т.к. исходя из логики,
    // продукт должен готовиться в сущности печки, а не отдельно от этой сущности
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
