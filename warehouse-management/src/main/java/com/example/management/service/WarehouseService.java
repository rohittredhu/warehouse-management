package com.example.management.service;

import com.example.management.JDBC.WarehouseDatabase;
import com.example.management.entities.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class WarehouseService {

    @Autowired
    private WarehouseDatabase database;

    public String addWarehouse(Warehouse entry){
        try{
            database.save(entry);
            return "warehouse added successfully";
        } catch (SQLException e) {
            return "something went wrong";
        }
    }

    public String deleteWarehouse(Long id){
        if(database.deleteById(id)>0) return "warehouse deleted successfully";

        return "warehouse with such id not found!";
    }

    public Warehouse getWarehouseById(Long id){
        ResultSet rs;
        Warehouse warehouse = new Warehouse();
        try{
            rs = database.findById(id);
            if(rs.next()){
                warehouse.setId(id);
                warehouse.setLocation(rs.getString("location"));
                warehouse.setName(rs.getString("name"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return warehouse;
    }

    public List<Warehouse> getAllWarehouse(){
        ResultSet rs;
        List<Warehouse>list = new ArrayList<>();
        try{
            rs = database.findAll();
            while(rs.next()){
                Warehouse warehouse = new Warehouse();

                warehouse.setId(rs.getLong("id"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setName(rs.getString("name"));

                list.add(warehouse);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
