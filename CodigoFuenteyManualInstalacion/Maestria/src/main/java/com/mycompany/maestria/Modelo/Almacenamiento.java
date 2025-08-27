/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;

import com.mycompany.maestria.Controlador.conectionBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author omarsilverio
 */
public class Almacenamiento {
    
    private String idTesis;
    private int usb;
    private int cd;
    private int tomo;
    
    private PreparedStatement pstatement; 
    private final conectionBDD CONEXION_BDD = new conectionBDD();

    public Almacenamiento(String idTesis, int usb, int cd, int tomo){
        this.idTesis = idTesis;
        this.usb = usb;
        this.cd = cd;
        this.tomo = tomo;
    }   
    
    public Almacenamiento(String idTesis){
        getAlmacenamientoBDD(idTesis);
    }
    
    public Almacenamiento(Tesis tesis){
        getAlmacenamientoBDD(tesis.getIdTesis());
    }
    
    public String getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(String idTesis) {
        this.idTesis = idTesis;
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

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getTomo() {
        return tomo;
    }

    public void setTomo(int tomo) {
        this.tomo = tomo;
    }
    
    public void getAlmacenamientoBDD(String idTesis){
        if(!idTesis.isEmpty()){
             String sql = String.format("SELECT * FROM %s WHERE idTesis = ?;"
                     ,conectionBDD.TABLAALMACENAMIENTO);
             try{
                if(!CONEXION_BDD.coneccion()) return;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return;}
                
                pstatement.setString(1, idTesis);
                pstatement.execute();
                ResultSet resultAlmacenamiento = pstatement.getResultSet();
                
                if(resultAlmacenamiento != null){
                     resultAlmacenamiento.next(); 
                     idTesis = resultAlmacenamiento.getString("idTesis");
                     usb = resultAlmacenamiento.getInt("usb");
                     cd = resultAlmacenamiento.getInt("cd");
                     tomo = resultAlmacenamiento.getInt("tomo");                                        
                 } 
                 CONEXION_BDD.cerrar();
             }catch(SQLException ex){
                 CONEXION_BDD.cerrar();
                 System.out.print(ex);
             }
        }
       
    } 
    
    /**
     * @param a
     * @return
     */
    public boolean addAlmacenamientoBDD(){
        String sql = String.format("INSERT INTO %s VALUES(?,?,?,?)",conectionBDD.TABLAALMACENAMIENTO);        
        try {
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
            
            pstatement.setString(1, idTesis);
            pstatement.setInt(2, usb);
            pstatement.setInt(3, cd);
            pstatement.setInt(4, tomo);                
            pstatement.executeUpdate();  
            CONEXION_BDD.cerrar();
            return true;
        }catch (SQLException ex) {  
            CONEXION_BDD.cerrar();
            return false;
        } 
    } 
    
    /**
     * @param almacenamiento
     * @return retorna true si la actualización se realizó
     */
    public boolean updateAlmacenamientoBDD(){
        String sql = String.format(
                "UPDATE %s SET cd = ?, tomo = ?, usb = ? WHERE idtesis = ?",
                conectionBDD.TABLAALMACENAMIENTO);
        try{
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
            
            pstatement.setInt(1,cd);
            pstatement.setInt(2,tomo);
            pstatement.setInt(3,usb);
            pstatement.setString(4, idTesis);
            pstatement.executeUpdate();                
            CONEXION_BDD.cerrar();
            return true;  
              
        }catch(SQLException ex){
            CONEXION_BDD.cerrar();
            System.out.println(ex);
            return false;
        }       
    }    
    
    
}
