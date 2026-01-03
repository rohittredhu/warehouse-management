package com.example.management.JDBC;

import com.example.management.entities.Product;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class ProductDatabase {
    Connection con;
    public ProductDatabase(){
        try{
            con = Connect.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int save(Product entry){
        String query = "INSERT INTO products(id,sku,name,category,price,description)  VALUES(?,?,?,?,?,?)";
        int i;
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setLong(1,entry.getId());
            p.setString(2, entry.getSku());
            p.setString(3, entry.getName());
            p.setString(4, entry.getCategory());
            p.setDouble(5, entry.getPrice());
            p.setString(6, entry.getDescription());
            i=p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    public ResultSet findAll(){
        String query = "SELECT * FROM products";
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet findByCategory(String category){
        String query = "SELECT * FROM products WHERE category = '"+category+"'";
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet findById(Long id){
        String query = "SELECT * FROM products WHERE id = "+id;
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet findBySku(String sku){
        String query = "SELECT * FROM products WHERE sku = '"+sku+"'";
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
