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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author omarsilverio
 */
public class Tesis {
    
    private String idTesis;
    private String titulo_tesis;    
    private int anio;    
    private String numero_control;
    private String fecha_registro;
    
    private PreparedStatement pstatement; 
    private final conectionBDD CONEXION_BDD = new conectionBDD();

    public Tesis(String idTesis, String titulo_tesis, int anio, String numero_control, String fecha_registro) {
        this.idTesis = idTesis;
        this.titulo_tesis = titulo_tesis;        
        this.anio = anio;        
        this.numero_control = numero_control;
        this.fecha_registro = fecha_registro;
    }
    
    public Tesis(String idTesis){
        getTesisBDD(idTesis);
    }    
    

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    
    
    public String getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(String idTesis) {
        this.idTesis = idTesis;
    }

    public String getTitulo_tesis() {
        return titulo_tesis;
    }

    public void setTitulo_tesis(String titulo_tesis) {
        this.titulo_tesis = titulo_tesis;
    }
    

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }


    public String getNumero_control() {
        return numero_control;
    }

    public void setNumero_control(String numero_control) {
        this.numero_control = numero_control;
    }
    /**
     * @param tesis
     * @return retorna true si la actualización se realizó
     */
     public boolean updateTesisBDD(){
        String sql = String.format("UPDATE %s SET titulo_tesis = ?, anio = ? "
                + "WHERE idtesis = ?", conectionBDD.TABLATESIS);
        try{
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
            
            pstatement.setString(1,titulo_tesis);                
            pstatement.setInt(2,anio);
            pstatement.setString(3,idTesis);
            pstatement.executeUpdate();                
            CONEXION_BDD.cerrar();
            return true;              
        }catch(SQLException ex){
            System.out.println(ex);
            CONEXION_BDD.cerrar();
            return false;
        }        
    } 
    /**
     * Agrega una tesis a la base de datos
     */
    public boolean addTesisBDD(){        
        String sql = String.format("INSERT INTO %s VALUES(?,?,?,?,?)",conectionBDD.TABLATESIS);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha_registro = dtf.format(LocalDateTime.now());
            try {
               if(!CONEXION_BDD.coneccion()) return false;
               pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
               if(pstatement == null){ CONEXION_BDD.cerrar(); return false;} 
               
               pstatement.setString(1, idTesis);
               pstatement.setString(2, titulo_tesis);                
               pstatement.setInt(3, anio);
               pstatement.setString(4, numero_control);
               pstatement.setString(5, fecha_registro);
               pstatement.executeUpdate();  
               CONEXION_BDD.cerrar();
               return true;
            } catch (SQLException ex) {
                CONEXION_BDD.cerrar();
                System.out.println(ex);
                return false;
            }          
    }    
    /**
     * Elimina el estudiante actual de la base de datos
     */
    public boolean removeTesis(){
        String sql = String.format("DELETE FROM %s WHERE idTesis = ?",conectionBDD.TABLATESIS);
        try{
            
            if(!CONEXION_BDD.coneccion()) return false;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return false;}
            
            pstatement.setString(1,idTesis);
            pstatement.executeUpdate();                
            CONEXION_BDD.cerrar();
            return true;           
                      
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }   
        
    }
    /**
     * Obtiene una tesis de la base de datos
     */
    /**
     * Regresa una tesis por su id
     * @param idTesis identificador de la tesis
     * @return Obtiene la tesis solicitada
     */
    public void getTesisBDD(String idTesis){
        if(!idTesis.equals("")){
            String sql = String.format("SELECT * FROM %s WHERE idTesis = '%s';",conectionBDD.TABLATESIS,idTesis);
            try{
                if(!CONEXION_BDD.coneccion()) return;
                pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
                if(pstatement == null){ CONEXION_BDD.cerrar(); return;}                
                pstatement.execute();                 
                ResultSet resultTesis = pstatement.getResultSet();
                
                if(resultTesis != null){
                    resultTesis.next();  
                    this.idTesis = resultTesis.getString("idTesis");
                    titulo_tesis = resultTesis.getString("titulo_tesis");                    
                    anio = resultTesis.getInt("anio");                                      
                    numero_control = resultTesis.getString("numero_control");
                    fecha_registro = resultTesis.getString("fecha_registro");
                }   
                CONEXION_BDD.cerrar();
             }catch(SQLException ex){
                CONEXION_BDD.cerrar();
                System.out.println(ex);
             }
        }        
    }     
   
    
     /**
     * @return una lista con todas las tesis
    **/
    public static List<Tesis> getListTesis(){
        String sql = String.format("SELECT * FROM %s;",conectionBDD.TABLATESIS);
        List<Tesis> listaTemp = new LinkedList<>();
        PreparedStatement pstatement; 
        conectionBDD CONEXION_BDD = new conectionBDD();
        try{
            if(!CONEXION_BDD.coneccion()) return null;
            pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
            if(pstatement == null){ CONEXION_BDD.cerrar(); return null;}
            
            pstatement.execute();
            ResultSet tesis = pstatement.getResultSet();
            
            if(tesis != null){
                while(tesis.next()){
                    Tesis temp = new Tesis(
                        tesis.getString("idTesis"),
                        tesis.getString("titulo_tesis"),                                
                        tesis.getInt("anio"),
                        tesis.getString("numero_control"),
                        tesis.getString("fecha_registro"));                    
                    listaTemp.add(temp);
                }
                CONEXION_BDD.cerrar();
                return listaTemp;
            }
            CONEXION_BDD.cerrar();            
            return null;
        }catch(SQLException ex){
            CONEXION_BDD.cerrar();
            System.out.println(ex);
            return null;
        }       
    }
    
    public int numbersConsults(){
        String sql = String.format(
        "SELECT count(*) FROM %s WHERE idTesis = ? GROUP BY idTesis;"
        ,conectionBDD.TABLAPRESTAMOS);
        int cantConsultas = 0;
        
        try{
             if(!CONEXION_BDD.coneccion()) return 0;
             pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
             if(pstatement == null){ CONEXION_BDD.cerrar(); return 0;}
             
             pstatement.setString(1,idTesis);                        
             pstatement.execute();  
             
             ResultSet result = pstatement.getResultSet();                 
             if(result == null) return 0;
             if(result.next())  cantConsultas = result.getInt(1);                 
             CONEXION_BDD.cerrar();
             
             return  cantConsultas; 
             
        }catch(SQLException ex){ 
            System.out.println(ex);
            return  cantConsultas;
        }           
    }
    //Obtiene una lista de la información de los prestamos de la tesis seleccionada
    public List<Prestamo> getPrestamos(){
         String sql = String.format(
        "SELECT * FROM %s WHERE idTesis = ?;"
        ,conectionBDD.TABLAPRESTAMOS);
         List<Prestamo> listaTemp = new LinkedList<>();
         
          try{
             if(!CONEXION_BDD.coneccion()) return null;
             pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);
             if(pstatement == null){ CONEXION_BDD.cerrar(); return null;}
             
             pstatement.setString(1,idTesis);                        
             pstatement.execute();  
             
             ResultSet result = pstatement.getResultSet();                 
             if(result == null) return null;
             while(result.next()){
                 Prestamo prestamoAux = new Prestamo(
                         result.getInt("numero_prestamo"),
                         result.getString("numero_control"),
                         result.getString("fecha_prestamo"),
                         result.getString("idTesis"),
                         result.getBoolean("status"),
                         result.getInt("usb"),
                         result.getInt("cd"),
                         result.getInt("tomo")
                 );
                 listaTemp.add(prestamoAux);
             }
                            
             CONEXION_BDD.cerrar();
             
             return  listaTemp; 
             
        }catch(SQLException ex){ 
            CONEXION_BDD.cerrar();
            System.out.println(ex);
            return  null;
        } 
         
         
    }
    
    @Override
    public String toString(){
        return idTesis + " " + titulo_tesis;
    }
    
   
    
    /**
     * @return retorna las unidades disponibles
     * index 0 -> usb
     * index 1 -> cd
     * index 2 -> tomo
     */
    public int[] getUnidadesDisponibles(){        
         String sql = String.format(
        "SELECT numero_prestamo,usb,cd,tomo FROM %s WHERE idtesis = ? AND status = ?;",conectionBDD.TABLAPRESTAMOS);
         Almacenamiento almacenamientoTemp = new Almacenamiento(idTesis);
          try{
             if(!CONEXION_BDD.coneccion()) return null;
             pstatement = CONEXION_BDD.getConnection().prepareStatement(sql);             
             if(pstatement == null){ CONEXION_BDD.cerrar(); return new int[]{0,0,0};}             
             
              pstatement.setString(1,idTesis);
              pstatement.setBoolean(2, false);               
              if(!pstatement.execute()){ CONEXION_BDD.cerrar(); return new int[]{0,0,0};}
             
               ResultSet result = pstatement.getResultSet();
               if(result == null || result.wasNull()){
                   
                   return new int[]{almacenamientoTemp.getUsb(),almacenamientoTemp.getCd(), almacenamientoTemp.getTomo()};
               }
               int usbDisponibles = 0;
               int cdDisponibles = 0;
               int tomoDisponibles = 0;
               while(result.next()){
                   int numero_prestamo = result.getInt("numero_prestamo");
                   Devolucion devolucionTemp = new Devolucion(numero_prestamo);
                   usbDisponibles += (result.getInt("usb") - devolucionTemp.getUsb());
                   cdDisponibles += (result.getInt("cd") - devolucionTemp.getCd());
                   tomoDisponibles += (result.getInt("tomo") - devolucionTemp.getTomo());
               }
               usbDisponibles = almacenamientoTemp.getUsb() - usbDisponibles;
               cdDisponibles = almacenamientoTemp.getCd() - cdDisponibles;
               tomoDisponibles = almacenamientoTemp.getTomo() - tomoDisponibles;
               
               CONEXION_BDD.cerrar();
               return new int[]{usbDisponibles,cdDisponibles,tomoDisponibles};
          }catch(SQLException ex){              
             CONEXION_BDD.cerrar();
             System.out.println(ex);
             return new int[]{0,0,0};
          }
         
    } 
    
    public String estadoTesis(){        
        int[] arreTemp = getUnidadesDisponibles();
        if(arreTemp[0] <= 0 && arreTemp[1] <= 0 && arreTemp[2] <= 1)
            return "No Disponible";
        return "Disponible";
    }
}
