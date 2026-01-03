package com.example.management.JDBC;

import com.example.management.entities.Warehouse;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class WarehouseDatabase {
    Connection con;

    public WarehouseDatabase(){
        try{
            con = Connect.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Warehouse entry) throws SQLException{
        String query = "INSERT INTO warehouses(id,name,location)  VALUES(?,?,?)";

        PreparedStatement p = con.prepareStatement(query);
        p.setLong(1,entry.getId());
        p.setString(2, entry.getName());
        p.setString(3, entry.getLocation());
        p.executeUpdate();
    }

    public int deleteById(Long id){
        String query = "DELETE FROM warehouses WHERE id="+id;
        int i;
        try{
            Statement st = con.createStatement();
            i= st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    public ResultSet findById(Long id){
        String query = "SELECT * FROM warehouses WHERE id ="+id;
        ResultSet rs;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet findAll(){
        String query = "SELECT * FROM warehouses";
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
