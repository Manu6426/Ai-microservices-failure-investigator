package com.manoj.aiincident.inventory.service;

import com.manoj.aiincident.inventory.model.Inventory;
import com.manoj.aiincident.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getByProductCode(String productCode) {
        return inventoryRepository.findByProductCode(productCode);
    }
    public Inventory reserveStock(String productCode, Integer quantity) {

        Inventory inventory = inventoryRepository.findByProductCode(productCode)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + productCode));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient inventory");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);

        return inventoryRepository.save(inventory);
    }
}