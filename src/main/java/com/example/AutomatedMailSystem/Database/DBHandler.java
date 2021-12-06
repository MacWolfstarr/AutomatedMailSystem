package com.example.AutomatedMailSystem.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHandler {

    static Connection dbconn;

    public static Connection createDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "root");

            return dbconn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
