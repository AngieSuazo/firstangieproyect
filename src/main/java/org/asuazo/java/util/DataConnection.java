package org.asuazo.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    private static String url = "jdbc:mysql://localhost:3306/javaproyect";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;

    public static Connection getInstance() throws SQLException{
        if (connection == null){
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

}
