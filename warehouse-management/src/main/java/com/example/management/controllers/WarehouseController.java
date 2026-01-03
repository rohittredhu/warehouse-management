package com.example.management.controllers;

import com.example.management.entities.Warehouse;
import com.example.management.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService service;

    @PostMapping("/addWarehouse")
    public String add(@RequestBody Warehouse entry){
        return service.addWarehouse(entry);
    }

    @DeleteMapping("/deleteWarehouse/{id}")
    public String delete(@PathVariable Long id){
        return service.deleteWarehouse(id);
    }

    @GetMapping("/getWarehouse/{id}")
    public Warehouse get( @PathVariable Long id){
        return service.getWarehouseById(id);
    }

    @GetMapping("/getWarehouses")
    public List<Warehouse> get(){
        return service.getAllWarehouse();
    }
}
