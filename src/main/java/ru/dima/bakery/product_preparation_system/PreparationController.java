package ru.dima.bakery.product_preparation_system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dima.bakery.exception.NotEnoughRawException;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.util.List;

@RestController
public class PreparationController {

    private final PreparationService preparationService;

    public PreparationController(PreparationService preparationService) {
        this.preparationService = preparationService;
    }

    @PostMapping("/preparation/{productType}")
    public void makeProduct(
            @PathVariable("productType") ProductType productType
    ) {
        preparationService.makeProduct(productType);
    }

    @GetMapping("/product")
    public List<Product> getAllProducts() {
        return preparationService.getAllProducts();
    }
}
