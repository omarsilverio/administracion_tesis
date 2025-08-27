/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;

import com.mycompany.maestria.Main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author omarsilverio
 */
public class Usuario {
    private String usuario;
    private String password;
    private boolean remember;  
    private File file;
    private Properties configLogin = new Properties();        
    
    public Usuario() { 
        file = getRutaPropiedades();
        try {            
            cargarLogin();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /**
     * Comprueba si existe el archivo de configuraciones en caso contrario lo crea
     */
    private File getRutaPropiedades(){
        String SO = System.getProperty("os.name");
        File archivoConfiguraciones = null;
        if(SO.equalsIgnoreCase("Mac OS X")){            
           archivoConfiguraciones = new File("/Applications/Control_Maestria/login.properties");
        }else{
           archivoConfiguraciones = new File("C:\\Control_Maestria\\login.properties"); 
        }
           if(archivoConfiguraciones.exists()){
               return archivoConfiguraciones;
           }else{
               
               try {
                   JOptionPane.showMessageDialog(null, "Aqui va bien");
                   crearConfiguraciones(archivoConfiguraciones);
                   return archivoConfiguraciones;
               } catch (IOException ex) {
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
            configLogin.setProperty("USER", "admin");
            configLogin.setProperty("PASSWORD", "admin");
            configLogin.setProperty("REMEMBER", "true");
            configLogin.store(outputStream, "Properties are stored here");
            outputStream.close();
        }
    }
    /**
     * Carga el login en la carpeta de aplicaciones
     */
    private void cargarLogin() throws IOException{  
        if(file != null){
            InputStream inputStream = new FileInputStream(file);
            configLogin.load(inputStream);
            usuario = configLogin.getProperty("USER");            
            password = configLogin.getProperty("PASSWORD");
            remember = Boolean.parseBoolean(configLogin.getProperty("REMEMBER")); 
            inputStream.close();
             
        }        
       
    }   
    /**
     * Edita el usuario
     */
    public boolean setUsuario(String usuario){
        try{
            if(file.exists()){
                OutputStream outputStream = new FileOutputStream(file);
                configLogin.setProperty("USER", usuario);
                configLogin.store(outputStream, "Usuario actualizado");
                this.usuario = usuario;
                outputStream.close();
                return true;
            }           
        }catch(Exception e){
            System.out.println("No se pudo actualizar el usuario error: " + e.toString());
            return false;
        }   
        return false;
    }
    /**
     * Edita el remember
     */
    public void setRemember(boolean remember){
        try{
            if(file.exists()){
                OutputStream outputStream = new FileOutputStream(file);
                configLogin.setProperty("REMEMBER", remember +"");
                configLogin.store(outputStream, "rememeber actualizado");
                this.remember = remember;
                outputStream.close();
            }           
        }catch(Exception e){
            System.out.println("No se pudo actualizar el usuario error: " + e.toString());
        } 
    }
    /**
     * Edita la contraseña
     */
    public boolean setPassword(String password){
        try{
            if(file.exists()){
                OutputStream outputStream = new FileOutputStream(file);
                configLogin.setProperty("PASSWORD", password +"");
                configLogin.store(outputStream, "password actualizado");
                this.password = password;
                outputStream.close();
                return true;
            }           
        }catch(Exception e){
            System.out.println("No se pudo actualizar el usuario error: " + e.toString());
            return false;
        } 
        return false;
    }
    
    public boolean getRemember(){
        return remember;
    }
    public String getUsuario() {
        return usuario;
    }
    

    public String getPassword() {
        return password;
    }
    
}
