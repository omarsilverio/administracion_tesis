/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Validaciones;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author omarsilverio
 */
public class ValidarAutor {

    public ValidarAutor() {
    }
    /**
     * Expresión regular para los apellidos y nombre
     * y el numero de control
     * 
     */
    private static final String EXPNOMBRESYAPELLIDOS = "[A-Z][a-zA-Z]*";
    private static final String NUMERO_CONTROL = "[M|m][0-9]{8}";
    
    /**
     * @param nombre JtextField del nombre del autor de una tesis
     * @param primer_apellido Jtextfield del primer apellido del autor
     * @param segundo_apellido Jtextfield del segundo apellido del autor
     * @param numero_control
     * @return true si todos los JtextField tienen información     * 
     * 
     * Verifica que los jtextField contengan información
     */
    public static boolean isEmpty(JTextField nombre, JTextField primer_apellido, JTextField segundo_apellido, JTextField numero_control){
        return !nombre.getText().equals("") 
                && !primer_apellido.getText().equals("") 
                && !segundo_apellido.getText().equals("")
                && !numero_control.getText().equals("");
    }
    
    
    /**
     * @param nombre JtextField del nombre del autor de una tesis
     * @param primer_apellido Jtextfield del primer apellido del autor
     * @param segundo_apellido Jtextfield del segundo apellido del autor
     * @return retorna true si contiene una cadena valida
     * 
     * Verifica que las cadenas sean validas
     */
    public static boolean isValid(JTextField nombre, JTextField primer_apellido, JTextField segundo_apellido){
        return nombre.getText().matches(EXPNOMBRESYAPELLIDOS)
                || primer_apellido.getText().matches(EXPNOMBRESYAPELLIDOS)
                || segundo_apellido.getText().matches(EXPNOMBRESYAPELLIDOS);
    }
    
    /**
     * @param nombre JtextField del nombre del autor de una tesis
     * @param primer_apellido Jtextfield del primer apellido del autor
     * @param segundo_apellido Jtextfield del segundo apellido del autor
     * 
     * Asigna un contorno rojo a las entradas que no tiene información
     * @param numero_control
     * 
     */
    public static void errorInput(JTextField nombre, JTextField primer_apellido, JTextField segundo_apellido, JTextField numero_control){
        if(nombre.getText().equals("")){
            nombre.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        if(primer_apellido.getText().equals("")){
            primer_apellido.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        if(segundo_apellido.getText().equals("")){
            segundo_apellido.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        if(numero_control.getText().equals("")){
           numero_control.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }
    
    public static void addValidatorNames(JTextField nombre, JTextField primer_apellido, JTextField segundo_apellido){
        deleteNumber(nombre);
        deleteNumber(primer_apellido);
        deleteNumber(segundo_apellido);       
    }
    
    private static void deleteNumber(JTextField temp){
        temp.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent evt){
                char letra = evt.getKeyChar();
                if((int)letra >= 33 
                        && (int)letra <= 64
                        ||((int)letra>=91 
                        && (int)letra<=96)
                        || ((int)letra>=123 
                        && (int)letra<=223)){
                    evt.consume();
                }
            }
        });
    } 
    
   public static boolean validateNumeroControl(String numero_control){
        return numero_control.matches(NUMERO_CONTROL);       
    }
}
