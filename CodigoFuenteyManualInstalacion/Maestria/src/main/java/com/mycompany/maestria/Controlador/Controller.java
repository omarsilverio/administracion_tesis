/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author omarsilverio
 */
public class Controller {   
    
    private static PreparedStatement statement;
    private static final conectionBDD CONEXION_BDD = new conectionBDD();
    
    /**
     * Este codigo retorna expresiones booleans
     */
    public static boolean getResult(String sql){
        try{
            if(!CONEXION_BDD.coneccion()) return false;
            statement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(statement == null){ CONEXION_BDD.cerrar(); return false;}
            
            statement.execute();
            ResultSet result =  statement.getResultSet();
            result.next();
            CONEXION_BDD.cerrar();            
            boolean resulBol = result.getBoolean(1);
            return resulBol;
        }catch(SQLException e){
            return false;
        }     
    }
    /**
     * Crea la carpeta de configuraciones en el directorio especificado
     */
    public static void crearCarpeta(JFrame dialog){
        String SO = System.getProperty("os.name");       
        File directorioCarpeta = null;
        if(SO.equalsIgnoreCase("Mac OS X")){
            directorioCarpeta = new File("/Applications/Control_Maestria");
        }
        else{ 
            directorioCarpeta = new File("C:\\Control_Maestria");
        }
        
           if(!directorioCarpeta.exists()){
               if(!directorioCarpeta.mkdirs()){
                   Alertas.mensajeError(dialog, "No se pudo crear la carpeta de instalación no existe el directorio:\n"
                           + directorioCarpeta.toPath());
               }                 
           }
       
            
        
       
    }
     public boolean comprobarTablas(JFrame parent){
        try {
            conectionBDD CONEXION_BDD = new conectionBDD(); 
            if(!CONEXION_BDD.coneccion()) return false;
                    
            DatabaseMetaData dbm =CONEXION_BDD.getConnection().getMetaData();
            ResultSet res = dbm.getTables(null, null, null, new String[] {"TABLE"});
            List<String> tablas = new LinkedList<String>();
            if(res == null){ 
                CONEXION_BDD.cerrar();
                JOptionPane.showMessageDialog(parent, "No se encontrarón las tablas en la base de datos,"
                        + " \n Porfavor agreguelas para continuar");
                return false;                
            }
            while (res.next()) {
                tablas.add(res.getString("TABLE_NAME"));                  
            }
            if(tablas.size() != 5){
                CONEXION_BDD.cerrar();
                JOptionPane.showMessageDialog(parent, "Hacen falta una o mas tablas en la base de datos,"
                        + " \n Porfavor agreguelas para continuar");
                return false;
            }   
            
            CONEXION_BDD.cerrar();            
            return true;
        }catch(Exception e){            
            JOptionPane.showMessageDialog(null, "No se pudo establecer una conexión con la bdd");
            return false;

        }
    }
     public boolean comprobarTablas(JDialog parent){
        try {
            conectionBDD CONEXION_BDD = new conectionBDD(); 
            if(!CONEXION_BDD.coneccion()) return false;
                    
            DatabaseMetaData dbm =CONEXION_BDD.getConnection().getMetaData();
            ResultSet res = dbm.getTables(null, null, null, new String[] {"TABLE"});
            List<String> tablas = new LinkedList<String>();
            if(res == null){ 
                CONEXION_BDD.cerrar();
                JOptionPane.showMessageDialog(parent, "No se encontrarón las tablas en la base de datos,"
                        + " \n Porfavor agreguelas para continuar");
                return false;                
            }
            while (res.next()) {               
                tablas.add(res.getString("TABLE_NAME"));                  
            }
            if(tablas.size() != 5){
                CONEXION_BDD.cerrar();
                JOptionPane.showMessageDialog(parent, "Hacen falta una o mas tablas en la base de datos,"
                        + " \n Porfavor agreguelas para continuar");
                return false;
            }   
            
            CONEXION_BDD.cerrar();            
            return true;
        }catch(Exception e){            
            JOptionPane.showMessageDialog(null, "No se pudo establecer una conexión con la bdd");
            return false;

        }
    }
    
}
