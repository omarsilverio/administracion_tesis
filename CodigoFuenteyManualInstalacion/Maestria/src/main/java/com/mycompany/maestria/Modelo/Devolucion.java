/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;

import com.mycompany.maestria.Controlador.conectionBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author omarsilverio
 */
public class Devolucion {
    
    private int numero_prestamo;
    private int usb;
    private int cd;
    private int tomo;
    private String fecha_devolucion;
    
    private PreparedStatement pstatement;
    private final conectionBDD CONEXION_BDD = new conectionBDD();

    public Devolucion(int numero_prestamo, int usb, int cd, int tomo) {
        this.numero_prestamo = numero_prestamo;
        this.usb = usb;
        this.cd = cd;
        this.tomo = tomo;        
    }

    public Devolucion(int numero_prestamo) {
        this.numero_prestamo = numero_prestamo;
        this.getAllDevolucion(numero_prestamo);
    }

    public int getNumero_prestamo() {
        return numero_prestamo;
    }

    public void setNumero_prestamo(int numero_prestamo) {
        this.numero_prestamo = numero_prestamo;
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

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }
    
     /**
  * Agrega el prestamo a la base de datos
  */
    public boolean addDevolucionBDD(){
        if(numero_prestamo > -1){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fecha_devolucion = dtf.format(LocalDateTime.now());            
            String sql = String.format("INSERT INTO %s VALUES(?,?,?,?,?);", 
                        conectionBDD.TABLADEVOLUCIONES);
            try{
                if(!CONEXION_BDD.coneccion()) return false;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}                
                
                pstatement.setInt(1, numero_prestamo);                
                pstatement.setInt(2, usb);
                pstatement.setInt(3, cd);
                pstatement.setInt(4, tomo);
                pstatement.setString(5, fecha_devolucion);
                pstatement.executeUpdate();
                CONEXION_BDD.cerrar();   
                return true;            
            }catch(SQLException ex){ 
                CONEXION_BDD.cerrar();
                System.out.print(ex);
                return false;
            }
        }
        return false;
    }
    
    private void getAllDevolucion(int numero_prestamo){
        if(numero_prestamo > -1){
            String sql = String.format("SELECT numero_prestamo,sum(usb)usb,sum(cd)cd,sum(tomo)tomo FROM %s"
                    + " WHERE numero_prestamo = ? GROUP BY numero_prestamo;",conectionBDD.TABLADEVOLUCIONES);
            try{
                if(!CONEXION_BDD.coneccion()) return;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return;}
                
                pstatement.setInt(1, numero_prestamo);
                pstatement.execute();                 
                ResultSet result = pstatement.getResultSet();
                
                if(result != null || !result.wasNull()){
                    if(result.next()){                       
                        this.usb = result.getInt("usb");
                        this.cd = result.getInt("cd");
                        this.tomo = result.getInt("tomo");
                     }
                }else{
                    usb = 0;
                    cd = 0;
                    tomo = 0;
                }     
                CONEXION_BDD.cerrar();                 
            }catch(SQLException ex){
                 CONEXION_BDD.cerrar(); 
                 System.out.println(ex);
            }
        }
    }
    
    
}
