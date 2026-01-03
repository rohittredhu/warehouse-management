package com.example.management.service;

import com.example.management.JDBC.InventoryDatabase;
import com.example.management.entities.Inventory;
import com.example.management.entities.Product;
import com.example.management.entities.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class InventoryService {
    @Autowired
    private InventoryDatabase database;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    public String createInventory(Inventory entry){
        try {
            if(database.save(entry)>0)
                return "inventory created Successfully";
            return "Duplicate id!";
        } catch (SQLException e) {
            return "either of product_id pr warehouse_id doesn't exist";
        }
    }

    public Inventory getInventoryById(Long id){
        Inventory inventory = null;
        try{
            ResultSet rs = database.findById(id);
            if(rs.next()){
                inventory = new Inventory();
                Product p = productService.getProductById(rs.getLong("product_id"));
                Warehouse w = warehouseService.getWarehouseById(rs.getLong("warehouse_id"));
                inventory.setId(id);
                inventory.setStock(rs.getInt("stock"));
                inventory.setProduct(p);
                inventory.setWarehouse(w);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inventory;
    }

    public String updateInventory(Long id,Inventory entry){
        try{
            if(database.update(id,entry)>0){
                return "Inventory updated successfully";
            }
            return "id not found!";
        } catch (SQLException e) {
            return "either of product_id pr warehouse_id doesn't exist";
        }

    }

    public List<Inventory> getAllInventory(){
        Inventory inventory = null;
        List<Inventory> list = new ArrayList<>();
        try{
            ResultSet rs = database.findAll();
            while(rs.next()){
                inventory = new Inventory();
                Product p = productService.getProductById(rs.getLong("product_id"));
                Warehouse w = warehouseService.getWarehouseById(rs.getLong("warehouse_id"));
                inventory.setId(rs.getLong("id"));
                inventory.setStock(rs.getInt("stock"));
                inventory.setProduct(p);
                inventory.setWarehouse(w);
                list.add(inventory);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
