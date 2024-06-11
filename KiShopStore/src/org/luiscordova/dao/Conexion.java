package org.luiscordova.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis Cordova
 */
public class Conexion {
    private Connection conexion;
    private static Conexion instance;
    
    public Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKiShop?useSSL=false","2023302_IN5BV","abc123**");
            //Conexion en casa
            // conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKiShop?useSSL=false","root","14/09/2020sRrpgfyt");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKiShop?useSSL=false", "root", "abc123**");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public static Conexion getInstance(){
        if (instance == null){
            instance = new Conexion();
        }
        return instance;
    }

    public Conexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
