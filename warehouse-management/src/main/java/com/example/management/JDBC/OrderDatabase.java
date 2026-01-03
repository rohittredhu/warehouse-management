package com.example.management.JDBC;


import com.example.management.entities.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderDatabase {
    Connection con;

    public OrderDatabase(){
        try{
            con = Connect.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Order entry){
        String query = "INSERT INTO orders(id,product_id,quantity,status,inventory_id) VALUES(?,?,?,?,?)";

        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setLong(1,entry.getId());
            p.setLong(2,entry.getProduct().getId());
            p.setInt(3,entry.getQuantity());
            p.setString(4,entry.getStatus().name());
            p.setLong(5,entry.getInventory().getId());
            p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Long id,Order order){
        String query = "UPDATE orders SET status =? WHERE id=?";
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1,order.getStatus().name());
            p.setLong(2,id);
            p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet findById(Long id){
        String query = "SELECT * FROM orders WHERE id = ?";
        ResultSet rs;
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setLong(1,id);
            rs = p.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
