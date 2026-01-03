package com.example.management.controllers;


import com.example.management.entities.Inventory;
import com.example.management.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping("/addInventory")
    public String add(@RequestBody Inventory entry){
        return service.createInventory(entry);
    }

    @GetMapping("/getInventory/{id}")
    public Inventory get(@PathVariable Long id){
        return service.getInventoryById(id);
    }

    @PutMapping("/updateInventory/{id}")
    public String update(@PathVariable Long id,@RequestBody Inventory entry){
        return service.updateInventory(id,entry);
    }
}
