package com.example.management.service;


import com.example.management.JDBC.OrderDatabase;
import com.example.management.entities.Inventory;
import com.example.management.entities.Order;
import com.example.management.entities.Product;
import com.example.management.entities.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Component
public class OrderService {
    private static final Map<OrderStatus, List<OrderStatus>> transitions = Map.of(
            OrderStatus.PENDING, List.of(OrderStatus.APPROVED, OrderStatus.CANCELLED),
            OrderStatus.APPROVED, List.of(OrderStatus.DISPATCHED, OrderStatus.CANCELLED),
            OrderStatus.DISPATCHED, List.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED),
            OrderStatus.COMPLETED, List.of(),
            OrderStatus.CANCELLED, List.of()
    );

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderDatabase database;

    public String addOrder(Order entry){
        Long id = entry.getProduct().getId();
        Product p = productService.getProductById(id);
        Order order = getOrderById(entry.getId());

        if(order!=null)return "Duplicate id";

        if(p==null)return "product with id "+id+" doesn't exist";
        else{
            List<Inventory> inventoryList = inventoryService.getAllInventory();

            Collections.sort(inventoryList, new Comparator<Inventory>() {
                @Override
                public int compare(Inventory o1, Inventory o2) {
                    return o2.getStock()-o1.getStock();
                }
            });
            for(var i:inventoryList){
                if(i.getProduct().getId().equals(id)){
                    if(i.getStock()>=entry.getQuantity()){
                        entry.setStatus(OrderStatus.PENDING);
                        entry.setInventory(i);
                        database.save(entry);
                        return "Order Placed Successfully";
                    }
                }
            }
        }
        return "Out of stock";
    }

    public String deleteOrder(Long id){
        Order order = getOrderById(id);
        if(order == null)return "Order with"+id+"doesn't exist";
        if(order.getStatus() == OrderStatus.DISPATCHED){
            Inventory inventory = order.getInventory();
            inventory.setStock(order.getQuantity()+inventory.getStock());
            inventoryService.updateInventory(inventory.getId(),inventory);
        }
        order.setStatus(OrderStatus.CANCELLED);
        database.update(id,order);
        return "Order cancelled";
    }

    public Order getOrderById(Long id){
        ResultSet rs =  database.findById(id);
        Order order = null;
        try{
            if(rs.next()){
                order = new Order();
                order.setId(id);
                order.setProduct(productService.getProductById(rs.getLong("product_id")));
                order.setQuantity(rs.getInt("quantity"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setInventory(inventoryService.getInventoryById(rs.getLong("inventory_id")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    public String setStatus(Long id, OrderStatus newStatus) {
        Order order = getOrderById(id);

        if (order == null) {
            return "Order with id " + id + " doesn't exist";
        }

        OrderStatus currentStatus = order.getStatus();
        if (currentStatus == OrderStatus.COMPLETED || currentStatus == OrderStatus.CANCELLED) {
            return "Cannot change order status once it is " + currentStatus;
        }
        if (!transitions.get(currentStatus).contains(newStatus)) {
            return "Invalid status transition: " + currentStatus + " → " + newStatus;
        }
        if (newStatus == OrderStatus.CANCELLED) {
            return "Go to /cancelOrder to cancel";
        }
        if (newStatus == OrderStatus.DISPATCHED && currentStatus == OrderStatus.APPROVED) {
            Inventory inventory = inventoryService.getInventoryById(order.getInventory().getId());
            inventory.setStock(inventory.getStock() - order.getQuantity());
            inventoryService.updateInventory(inventory.getId(), inventory);
        }
        order.setStatus(newStatus);
        database.update(id, order);

        return "Order status updated successfully to " + newStatus;
    }


    public OrderStatus checkStatus(Long id){
        ResultSet rs  = database.findById(id);
        try {
            if(rs.next()){
                return OrderStatus.valueOf(rs.getString("status"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
