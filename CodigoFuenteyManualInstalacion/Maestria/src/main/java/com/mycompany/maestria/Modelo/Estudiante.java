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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author omarsilverio
 */
public class Estudiante{
    
    private String numero_control;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String status;
    private String generacion;
    
    private PreparedStatement pstatement;
    private final conectionBDD CONEXION_BDD = new conectionBDD();

    public Estudiante(String numero_control, String nombre, String apellido_paterno, String apellido_materno, String status, String generacion){        
        this.numero_control = numero_control;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.status = status;
        this.generacion = generacion;
    }
    public Estudiante(String numero_control, String nombre, String apellido_paterno, String apellido_materno, int status, String generacion){        
        this.numero_control = numero_control;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.generacion = generacion;
        setStatus(status);
    }
    
    public Estudiante(String numero_control, String nombre, String apellido_paterno, String apellido_materno, int status){        
        this.numero_control = numero_control;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        setStatus(status);
    }
    
    public Estudiante(String numero_control){
        getEstudianteBDD(numero_control);
    }
    public Estudiante(Tesis aux){
        getEstudianteBDD(aux.getNumero_control());
    }
    /**
     * @param opc
     * 1 -> Estudiante
     * 2 -> Titulado
     * 3 -> Inactivo
     */
    public void setStatus(int opc){
        switch(opc){
            case 1 -> {status = "Estudiante";}
            case 2 -> {status = "Titulado";}
            case 3 -> {status = "Inactivo";}
            default -> {status = "Estudiante";}
        };
    }  
    
    public String getGeneracion(){       
        return generacion;
    }
    
    public int getStatus(){
        switch(status){
            case "Estudiante" -> {return 1;}
            case "Titulado" -> {return  2;}
            case "Inactivo" -> {return  3;}
            default -> {return 0;}
        }
    }
    public String getStatusCadena(){        
        return status;
    }
    
     /**
     * @param numero_control identificador del estudiante
     * @return Obtiene un estudiante de la base de datos
     */
    public void getEstudianteBDD(String numero_control){
        if(!numero_control.isEmpty()){
            String sql = String.format("SELECT * FROM %s WHERE UPPER(numero_control) = UPPER(?);"
                        ,conectionBDD.TABLAESTUDIANTE);
            try{
                if(!CONEXION_BDD.coneccion()) return;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return;}
                
                pstatement.setString(1, numero_control);
                pstatement.execute();                 
                ResultSet result = pstatement.getResultSet();
                 
                if(result != null){
                    if(result.next()){
                        this.numero_control = result.getString("numero_control");
                        this.nombre = result.getString("nombre");
                        this.apellido_paterno = result.getString("primer_apellido");
                        this.apellido_materno = result.getString("segundo_apellido");
                        this.status = result.getString("status");
                        this.generacion = result.getString("generacion");                      
                        
                     }
                }     
                CONEXION_BDD.cerrar(); 
             }catch(SQLException ex){
                 CONEXION_BDD.cerrar(); 
                 System.out.println(ex);
             }            
        }        
    }  
    
    /**
     * agrega un nuevo estudiante a la base de datos
     * @param temp 
     * @return true si se agrego a la base de datos
     */
    public boolean addEstudianteBDD(){        
        String sql = String.format("INSERT INTO %s VALUES(?,?,?,?,?,?);", conectionBDD.TABLAESTUDIANTE);
        try {
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
             
            pstatement.setString(1, numero_control);
            pstatement.setString(2, nombre);
            pstatement.setString(3, apellido_paterno);
            pstatement.setString(4, apellido_materno);
            pstatement.setString(5, status);
            pstatement.setString(6, generacion);
            pstatement.executeUpdate();
            CONEXION_BDD.cerrar();
            return true;                 
        } catch (SQLException ex){                
                return false;
        }  
    }  
    
    /**
     * @param tesis
     * @return retorna true si la actualización se realizó
     */
     public boolean updateEstudianteBDD(){
        String sql = String.format("UPDATE %s SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, status = ?,  generacion = ? "
                + "WHERE numero_control = ?", conectionBDD.TABLAESTUDIANTE);
        try{
             if(!CONEXION_BDD.coneccion()) return false;
             pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
             if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
             
             pstatement.setString(1,nombre);
             pstatement.setString(2,apellido_paterno);
             pstatement.setString(3,apellido_materno);
             pstatement.setString(4,status);
             pstatement.setString(5,generacion);
             pstatement.setString(6,numero_control);
             pstatement.executeUpdate();                
             CONEXION_BDD.cerrar();
             return true;             
        }catch(SQLException ex){           
            return false;
        }        
    } 
    /**
     * Elimina el estudiante actual de la base de datos
     */
    public boolean removeEstudiante(){
        String sql = String.format("DELETE FROM %s WHERE numero_control = ?",conectionBDD.TABLAESTUDIANTE);
        try{
            
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
            
            pstatement.setString(1,numero_control);
            pstatement.executeUpdate();                
            CONEXION_BDD.cerrar();
            return true;           
                      
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }   
        
    }
    
    public String getNumero_control() {
        return numero_control;
    }

    public void setNumero_control(String numero_control) {
        this.numero_control = numero_control;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre_autor) {
        this.nombre = nombre_autor;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }
    
    /**
     * @param numero_control
     * @return true si el alumno ya tiene una tesis registrada en la base de datos
    */
    public boolean hadTesis(){       
        String sql = String.format(
        "SELECT EXISTS(SELECT 1 FROM %s WHERE UPPER(numero_control)=UPPER('%s'));",                 
         conectionBDD.TABLATESIS,numero_control);        
        return Controller.getResult(sql);
    }    
    
    
    /**
     * @return retorna true si el estudiante ya esta registrado
     */
    public boolean isRegister(){
        String sql = String.format(
        "SELECT EXISTS(SELECT 1 FROM %s WHERE UPPER(numero_control)=UPPER('%s'));"
        ,conectionBDD.TABLAESTUDIANTE,numero_control);
        return Controller.getResult(sql);       
    }
    
    /**
     * @return retorna true si el estudiante ya esta registrado
     */
    public static boolean isRegister(String numero_control){
        String sql = String.format(
        "SELECT EXISTS(SELECT 1 FROM %s WHERE UPPER(numero_control)=UPPER('%s'));"
        ,conectionBDD.TABLAESTUDIANTE,numero_control);
        return Controller.getResult(sql);       
    }
    
    /**
     * @return regresa la lista de adeudos del alumno
     */
    public List<Adeudo> getAdeudos(){
        List<Adeudo> listaTemp = new LinkedList<Adeudo>();
        String sql = String.format(
        "SELECT idTesis,usb,cd,tomo,numero_prestamo FROM prestamos WHERE  status = false AND numero_control = ?;"
        ,conectionBDD.TABLAPRESTAMOS);
        try{
             if(!CONEXION_BDD.coneccion()) return null;
             pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);             
             if(pstatement == null){ CONEXION_BDD.cerrar(); return null;}
             
             pstatement.setString(1,numero_control);               
             if(!pstatement.execute()){ CONEXION_BDD.cerrar(); return null;}
             
              ResultSet result = pstatement.getResultSet();                 
             if(result == null) return null;
             while(result.next()){ 
                 String idTesis = result.getString("idTesis");
                 int usb = result.getInt("usb");
                 int cd = result.getInt("cd");
                 int tomo = result.getInt("tomo");
                 int numero_prestamo = result.getInt("numero_prestamo");
                 Adeudo temp = new Adeudo(numero_prestamo,idTesis, usb, cd, tomo);
                 listaTemp.add(temp);                
             }
             CONEXION_BDD.cerrar();
             return listaTemp; 
        }catch(Exception e){
            CONEXION_BDD.cerrar();
            System.out.println(""+ e);
            return null;
        }
        
        
    }
    
    /**
     * @return retorna > 0 si tiene prestamos fisicos
     */
    public int adeudos(){
        String sql = String.format(
        "SELECT SUM(usb),SUM(cd),SUM(tomo) FROM %s WHERE numero_control = ? AND status = ? GROUP BY numero_control;"
        ,conectionBDD.TABLAPRESTAMOS);
        int cantAdeudos = 0;
        try{
             if(!CONEXION_BDD.coneccion()) return 0;
             pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);             
             if(pstatement == null){ CONEXION_BDD.cerrar(); return 0;}
             
             pstatement.setString(1,numero_control);
             pstatement.setBoolean(2,false);    
             if(!pstatement.execute()) return 0;
             
             ResultSet result = pstatement.getResultSet();                 
             if(result == null) return 0;
             if(result.next()){ 
                int usb = result.getInt(1);  
                int cd = result.getInt(2);  
                int tomo = result.getInt(3); 
                cantAdeudos= usb + cd + tomo;  
             }              
             CONEXION_BDD.cerrar();
             
             return cantAdeudos;   
             
        }catch(SQLException ex){ 
            CONEXION_BDD.cerrar();
            System.out.println(ex);
            return 0;
        }             
    }
    
    /**
     * Obtiene la lista de todos los estudiantes
     */
    public static List<Estudiante> getEstudiantes(){
        String sql = String.format("SELECT * FROM %s;", conectionBDD.TABLAESTUDIANTE);
        List<Estudiante> listaTemp = new LinkedList<>();
        conectionBDD CONEXION_BDD = new conectionBDD();
        PreparedStatement pstatement;        
        try{
            if(!CONEXION_BDD.coneccion()) return null;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);            
            pstatement.execute();
            ResultSet result = pstatement.getResultSet();
            
            if(result != null){
                result.next();
                do{                     
                    Estudiante estTemp = new Estudiante(
                        result.getString("numero_control"),
                        result.getString("nombre"),
                        result.getString("primer_apellido"),
                        result.getString("segundo_apellido"),
                        result.getString("status"),
                        result.getString("generacion")
                    ); 
                    listaTemp.add(estTemp);
                }while(result.next());
                CONEXION_BDD.cerrar();
                return listaTemp;
            }
            CONEXION_BDD.cerrar();
        }catch(SQLException ex){
            return null;
        }
        return null;
    }
    
    @Override
    public String toString(){
        return String.format("%s %s %s", nombre,apellido_paterno,apellido_materno);
    }
}
