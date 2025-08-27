/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;

import com.mycompany.maestria.Controlador.Controller;
import com.mycompany.maestria.Controlador.conectionBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author omarsilverio
 */
public class Prestamo {
    private int numero_prestamo = -1;
    private String numero_control;
    private String fecha_prestamo;
    private String idTesis; 
    private boolean status;
    private int usb;
    private int cd;
    private int tomo;
    
    private final conectionBDD CONEXION_BDD = new conectionBDD();
    private PreparedStatement pstatement;

    public Prestamo(int numero_prestamo, String numero_control, String fecha_prestamo, String idTesis, boolean status,int usb, int cd, int tomo) {  
        this.numero_prestamo = numero_prestamo;
        this.numero_control = numero_control;
        this.fecha_prestamo = fecha_prestamo;
        this.idTesis = idTesis;    
        this.status = status;
        this.usb = usb;
        this.cd = cd;
        this.tomo = tomo;
    }  
     public Prestamo(String numero_control, String idTesis, boolean status,int usb, int cd, int tomo) {          
        this.numero_control = numero_control;
        this.fecha_prestamo = fecha_prestamo;
        this.idTesis = idTesis;    
        this.status = status;
        this.usb = usb;
        this.cd = cd;
        this.tomo = tomo;
    }  
    
    public Prestamo(int numero_prestamo){
        getPrestamoBDD(numero_prestamo);
    }
    
    public int getUsb() {
        return usb;
    }

    public void setUsb(int usb) {
        this.usb = usb;
    }

    public int getCd() {
        return cd;
    }

    public int getNumero_prestamo() {
        return numero_prestamo;
    }

    public void setNumero_prestamo(int numero_prestamo) {
        this.numero_prestamo = numero_prestamo;
    }
    

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getTomo() {
        return tomo;
    }

    public void setTomo(int tomo) {
        this.tomo = tomo;
    } 
    
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNumero_control() {
        return numero_control;
    }

    public void setNumero_control(String numero_control) {
        this.numero_control = numero_control;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(String idTesis) {
        this.idTesis = idTesis;
    }
    /**
     * Obtiene todos los prestamos que no se han devuelto por tesis por tesis
     */
    public static int[] getPrestamosTesis(String idTesis){
        String sql = String.format(""
                + "SELECT SUM(usb)usb, SUM(cd)cd, SUM(tomo)tomo " +
                  "FROM %s WHERE idTesis = ? AND status = ? " +
                  "ORDER BY usb,cd,tomo", conectionBDD.TABLAPRESTAMOS);
        conectionBDD CONEXION_BDD = new conectionBDD();
        PreparedStatement pstatement;
        int [] unidadesPrestadas = new int[3];
        
         try{
            if(!CONEXION_BDD.coneccion()) return new int[]{0,0,0};
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return new int[]{0,0,0};} 
            
            pstatement.setString(1,idTesis); 
            pstatement.setBoolean(2,false); 
            pstatement.execute();
            
             if(!pstatement.execute()) return null;
             ResultSet result = pstatement.getResultSet(); 
             if(result.next()){
                 unidadesPrestadas[0] = result.getInt("usb");
                 unidadesPrestadas[1] = result.getInt("cd");
                 unidadesPrestadas[2] = result.getInt("tomo");
             }
            CONEXION_BDD.cerrar();             
            return unidadesPrestadas;                
         }catch(SQLException ex){
             CONEXION_BDD.cerrar();
             System.out.println(ex);
             return new int[]{0,0,0};
          }
    }
    
    /**
  * Agrega el prestamo a la base de datos
  */
    public boolean addPrestamoBDD(){
        if(!numero_control.isEmpty() && !idTesis.isEmpty()){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fecha_prestamo = dtf.format(LocalDateTime.now());            
            String sql = String.format("INSERT INTO %s VALUES(?,?,?,?,?,?,?);", 
                        conectionBDD.TABLAPRESTAMOS);
            try{
                if(!CONEXION_BDD.coneccion()) return false;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}                
                
                pstatement.setString(1, numero_control);
                pstatement.setString(2, fecha_prestamo);
                pstatement.setString(3, idTesis);
                pstatement.setBoolean(4, status);
                pstatement.setInt(5, usb);
                pstatement.setInt(6, cd);
                pstatement.setInt(7, tomo);
                pstatement.executeUpdate();
                CONEXION_BDD.cerrar();   
                return true;            
            }catch(SQLException ex){   
                System.out.print(ex);
                return false;
            }
        }
        return false;
    } 
    
    /**
     * Cambia el estado del prestamo
     */
    public boolean updateStatusPrestamoBDD(){
        String sql = String.format(
                "UPDATE %s SET status = ? WHERE numero_prestamo = ?",
                conectionBDD.TABLAPRESTAMOS);        
        try{
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
            
            pstatement.setBoolean(1, status); 
            pstatement.setInt(2, numero_prestamo);                 
            pstatement.executeUpdate();                
            CONEXION_BDD.cerrar();
            return true;
        }catch(SQLException ex){  
            CONEXION_BDD.cerrar();
            System.out.print(ex);
            return false;
        }
        
    }  
    /**
     * @param numero_control identificador del estudiante
     * @return Obtiene el prestamo
     */
    public void getPrestamoBDD(int numero_prestamo){        
            String sql = String.format("SELECT * FROM %s WHERE numero_prestamo = ?;",
                conectionBDD.TABLAPRESTAMOS);
            try{
                if(!CONEXION_BDD.coneccion()) return;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return;}
            
                pstatement.setInt(1, numero_prestamo);
                //pstatement.setBoolean(2, false);
                pstatement.execute();
                ResultSet resultRegistro = pstatement.getResultSet(); 
                    
                if(resultRegistro != null){
                    resultRegistro.next();
                    this.numero_prestamo = resultRegistro.getInt("numero_prestamo");
                    numero_control = resultRegistro.getString("numero_control");
                    fecha_prestamo = resultRegistro.getString("fecha_prestamo");
                    idTesis = resultRegistro.getString("idTesis");                    
                    status = resultRegistro.getBoolean("status");
                    usb = resultRegistro.getInt("usb");
                    cd = resultRegistro.getInt("cd");
                    tomo = resultRegistro.getInt("tomo");                                                                      
                }  
                CONEXION_BDD.cerrar();                 
             }catch(SQLException ex){
                 CONEXION_BDD.cerrar(); 
                System.out.println(ex);
             }  
    } 
    
    public boolean hadDevolucion(){
        String sql = String.format("SELECT EXISTS(SELECT 1 FROM %s WHERE numero_prestamo = %d);",
                conectionBDD.TABLADEVOLUCIONES, numero_prestamo);
        return Controller.getResult(sql);
    }
    
    /**
     * Obtiene los prestamos fisicos de una tesis
     */
    public static List<Prestamo> getPrestamos(Tesis temp){
        String sql = String.format("SELECT * FROM %s WHERE idTesis = ? AND status = false;",conectionBDD.TABLAPRESTAMOS);
        List<Prestamo> listTemp = new LinkedList<>();
        conectionBDD CONEXION_BDD = new conectionBDD();
        PreparedStatement pstatement;
        try{
            if(!CONEXION_BDD.coneccion()) return null;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return null;}
            
            pstatement.setString(1, temp.getIdTesis());
            pstatement.execute();
            ResultSet result = pstatement.getResultSet();
            
            if(result != null){
                while(result.next()){  
                    int numero_prestamo = result.getInt("numero_prestamo");
                    String numero_control = result.getString("numero_control");
                    String fecha_prestamo = result.getString("fecha_prestamo");
                    String idtesis = result.getString("idTesis");                    
                    boolean status = result.getBoolean("status");
                    int usb = result.getInt("usb");
                    int cd = result.getInt("cd");
                    int tomo = result.getInt("tomo");
                    Prestamo aux = new Prestamo(numero_prestamo,numero_control,fecha_prestamo, idtesis,status,usb,cd,tomo);
                    listTemp.add(aux);
                }
                CONEXION_BDD.cerrar();
                return listTemp;
            }
            CONEXION_BDD.cerrar();                
            return null; 
        }catch(SQLException e){
            CONEXION_BDD.cerrar();
            return null;
        } 
    }
    
    /**
     * Obtiene una lista con todos los prestamos
     */
    public static List<Prestamo> getPrestamos(){
        String sql = String.format("SELECT * FROM %s;",conectionBDD.TABLAPRESTAMOS); 
        List<Prestamo> listTemp = new LinkedList<>();
        conectionBDD CONEXION_BDD = new conectionBDD();
        PreparedStatement pstatement;
        
        try{
            if(!CONEXION_BDD.coneccion()) return null;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return null;}
             
            pstatement.execute();
            ResultSet result = pstatement.getResultSet();
            
            if(result != null){
                while(result.next()){  
                    int numero_prestamo = result.getInt("numero_prestamo");
                    String numero_control = result.getString("numero_control");
                    String fecha_prestamo = result.getString("fecha_prestamo");
                    String idtesis = result.getString("idTesis");                    
                    boolean status = result.getBoolean("status");
                    int usb = result.getInt("usb");
                    int cd = result.getInt("cd");
                    int tomo = result.getInt("tomo");
                    Prestamo aux = new Prestamo(numero_prestamo,numero_control,fecha_prestamo, idtesis,status,usb,cd,tomo);
                    listTemp.add(aux);
                }
                CONEXION_BDD.cerrar();
                return listTemp;
            }
            CONEXION_BDD.cerrar();                
            return null; 
        }catch(SQLException e){
            CONEXION_BDD.cerrar();
            return null;
        }       
    } 
}
