package com.manoj.aiincident.inventory.controller;

import com.manoj.aiincident.inventory.model.Inventory;
import com.manoj.aiincident.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return inventoryService.saveInventory(inventory);
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<Inventory> getInventory(
            @PathVariable String productCode) {

        return inventoryService.getByProductCode(productCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{productCode}/reserve")
    public Inventory reserveInventory(
            @PathVariable String productCode,
            @RequestParam Integer quantity) {

        return inventoryService.reserveStock(productCode, quantity);
    }
}