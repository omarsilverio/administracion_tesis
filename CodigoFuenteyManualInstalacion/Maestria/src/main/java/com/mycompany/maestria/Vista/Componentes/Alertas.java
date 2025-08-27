/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Vista.Componentes;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author omarsilverio
 */
public class Alertas {
    
    public static void campoVacio(JDialog dialog){
        JOptionPane.showMessageDialog(dialog, "¡Los campos no deben estar vacíos!",
                "Campo Vacío", JOptionPane.WARNING_MESSAGE);
    }   
    
    
    public static void numeroControlIncorrecto(JDialog dialog){
        JOptionPane.showMessageDialog(dialog, "El número de control es incorrecto ejemp. M18161234",
                "Campo Vacío", JOptionPane.WARNING_MESSAGE);
    }
    
    public static boolean eliminarRegistro(JDialog dialog){
        int resp = JOptionPane.showConfirmDialog(dialog, "Advertencia: se eliminarán toda la información de este \n estudiante inluyendo tesis y prestamos. \n ¿Desea continuar?"                
                ,"Eliminar Registro",JOptionPane.YES_NO_OPTION);
        return resp == 0; 
    }
    
    public static boolean cerrarModal(JDialog dialog){
        int resp = JOptionPane.showConfirmDialog(dialog, "El valor de las entradas se perderán"
                + "\n¿Desea continuar?","Cerrar Ventana",JOptionPane.YES_NO_OPTION);
        return resp == 0; 
    }
    
    public static boolean agregarTesis(JDialog dialog,String cadena){
        int resp = JOptionPane.showConfirmDialog(dialog, "¿Desea agregar a: " +cadena+ "?"
                ,"Agregar Registro",JOptionPane.YES_NO_OPTION);
        return resp == 0; 
    }
    
    public static void valorRegistrado(JDialog dialog){
        JOptionPane.showMessageDialog(dialog, "El registro se agregó con exito",
                "Campo Vacío", JOptionPane.OK_OPTION);
    }
    public static void valorRepetido(JDialog dialog){
        JOptionPane.showMessageDialog(dialog, "¡El alumno ya tiene una tesis registrada!","Registro repetido",JOptionPane.WARNING_MESSAGE);
    }
    
     public static boolean actualizarRegistro(JDialog dialog,String cadena){
        int resp = JOptionPane.showConfirmDialog(dialog, "¿Desea actualizar a: " +cadena+ "? \n Advertencia: Los cambios no se pueden deshacer"
                ,"Actualizar Registro",JOptionPane.YES_NO_OPTION);
        return resp == 0; 
    }
     
    public static void actualizancionCancelada(JDialog dialog){
        JOptionPane.showMessageDialog(dialog, "¡El registro no se actualizó¡",
                "Campo Vacío", JOptionPane.OK_OPTION);
    }
    
    public static void actualizancionAceptada(JDialog dialog){
        JOptionPane.showMessageDialog(dialog, "¡El registro se actualizó correctamente¡",
                "registro actualizado", JOptionPane.OK_OPTION);
    }
    
     public static boolean almacenarTesis(JDialog dialog){
        int resp = JOptionPane.showConfirmDialog(dialog, "Verifique que la Tesis este \n  en buen estado antes de almacenarlo."
                ,"Actualizar Registro",JOptionPane.YES_NO_OPTION);
        return resp == 0; 
    }
     
    public static boolean almacenarTodo(JDialog dialog){
        int resp = JOptionPane.showConfirmDialog(dialog, "Verifique que todas las tesis esten en buen estado \n"
                + "¿Desea recuperar todo?","Almacenar Todo",JOptionPane.YES_NO_OPTION);
        return resp == 0; 
    }
    
    public static void mensajeExito(JDialog parent, String text){
        JOptionPane.showMessageDialog(parent,text);
    }
    public static void mensajeError(JDialog parent, String text){
        JOptionPane.showMessageDialog(parent,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    public static void mensajeAdvertencia(JDialog parent, String text){
        JOptionPane.showMessageDialog(parent,text,"Advertencia",JOptionPane.WARNING_MESSAGE);
    }
    public static void mensajeExito(JFrame parent, String text){
        JOptionPane.showMessageDialog(parent,text);
    }
    public static void mensajeError(JFrame parent, String text){
        JOptionPane.showMessageDialog(parent,text,"Error",JOptionPane.ERROR_MESSAGE);
    }
    public static void mensajeAdvertencia(JFrame parent, String text){
        JOptionPane.showMessageDialog(parent,text,"Advertencia",JOptionPane.WARNING_MESSAGE);
    }
    
}
