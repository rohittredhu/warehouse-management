package com.example.management.JDBC;


import com.example.management.entities.Inventory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class InventoryDatabase {
    Connection con;
    public InventoryDatabase(){
        try{
            con = Connect.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int save(Inventory entry) throws SQLException{
        String query = "INSERT INTO inventory(id,product_id,warehouse_id,stock)  VALUES(?,?,?,?)";

        PreparedStatement p = con.prepareStatement(query);
        p.setLong(1,entry.getId());
        p.setLong(2, entry.getProduct().getId());
        p.setLong(3, entry.getWarehouse().getId());
        p.setLong(4, entry.getStock());
        return p.executeUpdate();

    }

    public ResultSet findById(Long id) throws SQLException{
        String query = "SELECT * FROM inventory WHERE id ="+id;

        Statement st = con.createStatement();
        return st.executeQuery(query);
    }

    public int update(Long id, Inventory entry) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE inventory SET ");
        List<Object> params = new ArrayList<>();

        if (entry.getWarehouse() != null && entry.getWarehouse().getId() != null) {
            query.append("warehouse_id=?, ");
            params.add(entry.getWarehouse().getId());
        }
        if (entry.getProduct() != null && entry.getProduct().getId() != null) {
            query.append("product_id=?, ");
            params.add(entry.getProduct().getId());
        }
        if (entry.getStock() != null) {
            query.append("stock=?, ");
            params.add(entry.getStock());
        }

        if (params.isEmpty()) {
            return 0;
        }
        query.setLength(query.length() - 2);

        query.append(" WHERE id=?");
        params.add(id);

        PreparedStatement p = con.prepareStatement(query.toString());

        for (int i = 0; i < params.size(); i++) {
            p.setObject(i + 1, params.get(i));
        }

        return p.executeUpdate();
    }

    public ResultSet findAll() throws SQLException{
        String query = "SELECT * FROM inventory";

        Statement st = con.createStatement();
        return st.executeQuery(query);
    }

}
