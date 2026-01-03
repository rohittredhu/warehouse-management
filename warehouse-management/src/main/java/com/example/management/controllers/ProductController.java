package com.example.management.controllers;

import com.example.management.entities.Product;
import com.example.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SuppressWarnings("unused")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public String add(@RequestBody Product entry){
        return service.addProduct(entry);
    }

    @GetMapping("/getAllProduct")
    public List<Product> getAll(){
        return service.getAllProduct();
    }

    @GetMapping("/getProduct/{id}")
    public Product get(@PathVariable Long id){
        return service.getProductById(id);
    }

    @GetMapping("/getproduct/{sku}")
    public Product get(@PathVariable String sku){
        return service.getProductBySku(sku);
    }

    @GetMapping("/getProducts/{category}")
    public List<Product> getByCategory(@PathVariable String category){
        return service.getProductByCategory(category);
    }
}
