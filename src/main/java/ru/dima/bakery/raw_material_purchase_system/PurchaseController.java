package ru.dima.bakery.raw_material_purchase_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dima.bakery.raw_material_purchase_system.model.Raw;

import java.util.List;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/raw/{rawType}")
    @ResponseStatus(HttpStatus.CREATED)
    public void purchaseRaw(
            @PathVariable("rawType") RawType rawType,
            @RequestParam int count
    ) throws InterruptedException {
        purchaseService.purchaseRaw(rawType, count);
    }

    @GetMapping("/raw")
    public ResponseEntity<List<Raw>> getAllRaws() {
        return purchaseService.getAllRaws();
    }
}
