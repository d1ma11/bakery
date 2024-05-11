package ru.dima.bakery.product_preparation_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dima.bakery.product_preparation_system.model.Product;

import java.util.List;

@RestController
public class PreparationController {

    private final PreparationService preparationService;

    public PreparationController(PreparationService preparationService) {
        this.preparationService = preparationService;
    }

    @PostMapping("/preparation/{productType}")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeProduct(
            @PathVariable("productType") ProductType productType
    ) {
        preparationService.makeProduct(productType);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        return preparationService.getAllProducts();
    }
}
