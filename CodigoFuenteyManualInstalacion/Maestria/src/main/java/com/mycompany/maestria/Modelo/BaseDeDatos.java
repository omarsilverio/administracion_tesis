/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author omarsilverio
 */
public class BaseDeDatos {
    private final File file;
    private Properties configBDD = new Properties();
    private  int PORT; 
    private  String NAMEDATABASE;
    private  String HOST;
    private  String URLDATABASE;
    //inicia sesión aqui 
    private  String USUARIO;
    private  String PASSWORD;
    //San los nombres de tablas de la base de datos  
    public static final String TABLAALMACENAMIENTO = "Almacenamiento";
    public static final String TABLAESTUDIANTE = "Estudiante";
    public static final String TABLATESIS = "Tesis";    
    public static final String TABLAPRESTAMOS = "Prestamos";

    public BaseDeDatos() { 
        file = getRutaPropiedades();
        cargarConfiguracionesBDD();
        
    }
    
    private File getRutaPropiedades(){
        String SO = System.getProperty("os.name");
        File archivoConfiguraciones = null;
        if(SO.equalsIgnoreCase("Mac OS X")){            
           archivoConfiguraciones = new File("/Applications/Control_Maestria/confBDD.properties");
        }else{
           archivoConfiguraciones = new File("C:\\Control_Maestria\\confBDD.properties"); 
        }    
        
           if(archivoConfiguraciones.exists()){
               return archivoConfiguraciones;
           }else{
               
               try {
                   crearConfiguraciones(archivoConfiguraciones);
                   return archivoConfiguraciones;
               } catch (IOException ex) {
                   JOptionPane.showMessageDialog(null, "Holaaa" + ex);
                   System.out.println(ex);
                   return null;
               }
           }
    }
    
    /**
     * Crea las configuraciones del inicio de sesión con los valores por defecto
     */
    private void crearConfiguraciones(File archivoConfiguraciones) throws FileNotFoundException, IOException{
        try (OutputStream outputStream = new FileOutputStream(archivoConfiguraciones)) {            
            configBDD.setProperty("PORT", "5432");
            configBDD.setProperty("HOST", "127.0.0.1");
            configBDD.setProperty("NAME_DATABASE", "TesisMaestria");
            configBDD.setProperty("USER_DATABASE", "postgres");
            configBDD.setProperty("PASSWORD", "1234");
            configBDD.store(outputStream, "Properties are stored here");
            outputStream.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
    }
    
    /**
     * Recupera la información de las configuraciones
     */
    private void cargarConfiguracionesBDD(){
        try{
            if(file != null){
                InputStream inputStream = new FileInputStream(file);
                configBDD.load(inputStream);
                PORT = Integer.parseInt(configBDD.getProperty("PORT"));
                HOST = configBDD.getProperty("HOST");
                NAMEDATABASE = configBDD.getProperty("NAME_DATABASE");
                USUARIO = configBDD.getProperty("USER_DATABASE");
                PASSWORD = configBDD.getProperty("PASSWORD");           
                URLDATABASE = String.format("jdbc:postgresql://%s:%d/%s", HOST,PORT,NAMEDATABASE);          
                inputStream.close();
            }          
        }catch(IOException | NumberFormatException e){
            JOptionPane.showMessageDialog(null, "No se pudo obtener las configuraciones de la bdd \n"
                    + "Error: " + e.toString());            
        }  
    }
    
    public boolean guardarCambios(){
        try{
            OutputStream outputStream = new FileOutputStream(file);
            configBDD.setProperty("PORT", PORT+"");
            configBDD.setProperty("HOST", HOST);
            configBDD.setProperty("NAME_DATABASE", NAMEDATABASE);
            configBDD.setProperty("USER_DATABASE", USUARIO);
            configBDD.setProperty("PASSWORD", PASSWORD);
            configBDD.store(outputStream, "Valores actualizados");
            outputStream.close();
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
       
    }
    /*
    public boolean comprobarConexion(){
        
    }*/

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getNAMEDATABASE() {
        return NAMEDATABASE;
    }

    public void setNAMEDATABASE(String NAMEDATABASE) {
        this.NAMEDATABASE = NAMEDATABASE;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public String getURLDATABASE() {
        return URLDATABASE;
    }

    public void setURLDATABASE() {
        URLDATABASE = String.format("jdbc:postgresql://%s:%d/%s", HOST,PORT,NAMEDATABASE);
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }    
}
