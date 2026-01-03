package com.example.management.controllers;


import com.example.management.entities.Order;
import com.example.management.entities.enums.OrderStatus;
import com.example.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/order")
public class OrderController{
    @Autowired
    private OrderService service;

    @PostMapping("/placeOrder")
    public String add(@RequestBody Order entry){
        return service.addOrder(entry);
    }

    @DeleteMapping("/cancelOrder/{id}")
    public String delete(@PathVariable Long id){
        return service.deleteOrder(id);
    }

    @GetMapping("/getOrder/{id}")
    public Order get(@PathVariable Long id){
        return service.getOrderById(id);
    }

    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody OrderStatus status){
        return service.setStatus(id,status);
    }

    @GetMapping("/checkStatus/{id}")
    public OrderStatus checkStatus(@PathVariable Long id){
        return service.checkStatus(id);
    }

}
