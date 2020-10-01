/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan.ppdbonline.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ivanlubuntu
 */
public class C_Config {
    static Connection conn;
    static Statement stmt;
    
    public void config() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ppdbapps?true&serverTimezone=UTC", "root", "");
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static Connection configDB() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/db_ppdbapps?true&serverTimezone=UTC";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.err.println("Koneksi gagal "+ e.getMessage());
        }
        return conn;
    }
}
