package com.example.management.service;

import com.example.management.JDBC.ProductDatabase;
import com.example.management.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class ProductService {
    @Autowired
    private ProductDatabase database;

    public String addProduct(Product entry){
        if(database.save(entry)>0)return "Product added successfully";
        return "something went wrong";
    }

    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        ResultSet rs;

        try{
            rs = database.findAll();
            while(rs.next()){
                Product product = new Product();

                product.setId(rs.getLong("id"));
                product.setSku(rs.getString("sku"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));

                list.add(product);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Product> getProductByCategory(String category){
        List<Product> list = new ArrayList<>();
        ResultSet rs;

        try{
            rs = database.findByCategory(category);
            while(rs.next()){
                Product product = new Product();

                product.setId(rs.getLong("id"));
                product.setSku(rs.getString("sku"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));

                list.add(product);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Product getProductBySku(String sku){

        ResultSet rs = database.findBySku(sku);
        Product product=null;
        try {
            if(rs.next()){
                product = new Product();

                product.setId(rs.getLong("id"));
                product.setSku(rs.getString("sku"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public Product getProductById(Long id){
        ResultSet rs = database.findById(id);
        Product product=null;
        try {
            if(rs.next()){
                product = new Product();

                product.setId(rs.getLong("id"));
                product.setSku(rs.getString("sku"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
}
