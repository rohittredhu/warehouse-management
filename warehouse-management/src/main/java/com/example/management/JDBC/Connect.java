package com.example.management.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/inventory";
        String root ="root" ;
        String password = "xzFZHLdSEpnT23a@";

        return DriverManager.getConnection(url,root,password);
    }
}
