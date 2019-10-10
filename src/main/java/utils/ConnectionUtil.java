/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flash
 */
public class ConnectionUtil {
    Connection com = null;
    public static Connection conDB()
    {
        String bd = "jdbc:mysql://localhost/mydb";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con  = DriverManager.getConnection(bd,user,password);
            return con;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
