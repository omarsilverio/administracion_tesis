/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;

import com.mycompany.maestria.Modelo.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author omarsilverio
 * Realiza una conección a la base de datos,
 * aqui se configura la información
 */
public class conectionBDD {
   private Connection conn;
   private final BaseDeDatos BDD = new BaseDeDatos();
    //Son los nombres de tablas de la base de datos  
    public static final String TABLAALMACENAMIENTO = "Almacenamiento";
    public static final String TABLAESTUDIANTE = "Estudiante";
    public static final String TABLATESIS = "Tesis";    
    public static final String TABLAPRESTAMOS = "Prestamos";
    public static final String TABLADEVOLUCIONES = "Devoluciones";
     
    public conectionBDD(){       
        
    } 
    /**
     *@return Realiza la coneccion 
     */
    public boolean coneccion(){
        this.conn = null;            
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(BDD.getURLDATABASE(),  BDD.getUSUARIO(), BDD.getPASSWORD());
                return conn != null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error: " + e.toString());
                return false;
            }
    }
    /**
     * @return muestra la conección
     */
    public Connection getConnection(){
        if(conn == null)  JOptionPane.showMessageDialog(null,"No se pudo establecer una conexión con el driver JDBC","Error",JOptionPane.ERROR_MESSAGE);
        return conn;
    }
    
    public void cerrar(){
        try {
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(conectionBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }      
   
}
