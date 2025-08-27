/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Validaciones;

import com.mycompany.maestria.Modelo.Almacenamiento;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


/**
 *
 * @author omarsilverio
 */
public class ValidacionEntradas {   
     /**
     * Expresión regular para los apellidos y nombre
     * y el numero de control
     * 
     */
    private static final String EXPNOMBRESYAPELLIDOS = "[A-Z][a-zA-Z]*";
    private static final String NUMERO_CONTROL = "[M|m][0-9]{8}";
    
    
    public static boolean hadEmpty(JTextField...componentes){
        if(componentes != null && componentes.length > 0){
            for(int i = 0; i < componentes.length; i ++){
                if(componentes[i].getText().equals(""))
                    return true;                             
            }
            return false;
        }
        return false;
    }
    /**
     * @param componentes arreglo de entradas
     * @return retorna verdader si todos las entradas JTextfield estan vacias 
     * en un formulario
     */
    public static boolean isEmptyAll(JTextField...componentes){
        if(componentes != null && componentes.length > 0){
            int cont = 0;
            for(int i = 0; i < componentes.length; i ++){
                if(componentes[i].getText().equals("")){
                   cont ++; 
                }
                                                
            }
            return cont == componentes.length;            
        }
        return false;
    }
    /**
     * @param componentes arreglo de entradas o JTexfields
     * @return Regresa rl primer JTextField vacío que encuentre
     * retorna null si no hay ninguno vacío
     * 
     */
    public static JTextField getJTextFieldEmpty(JTextField...componentes){
         if(componentes != null && componentes.length > 0){
            for(int i = 0; i < componentes.length; i ++){
                if(componentes[i].getText().equals(""))
                    return componentes[i];                             
            }
            return null;
        }
        return null;
    }
    
    
    
    /**
     * Valida que las entradas sean solo letras y elimina a los numero
     */
    public static void addValidatorStrings(JTextField...entradas){
        if(entradas!=null && entradas.length > 0){
            for(int i = 0; i < entradas.length; i++)
                        deleteNumber(entradas[i]);            
            
        }              
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
    
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    /**
     * Valida que las entradas sean solo numero y elimina todas las letras
     * 
     */
    public static void addValidatorNumber(JTextField...entradas){
        if(entradas!=null && entradas.length > 0){
            for(int i = 0; i < entradas.length; i++)
                        deleteText(entradas[i]);
        } 
    }
    
    private static void deleteText(JTextField temp){
        temp.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent evt){
                char letra = evt.getKeyChar();
                if(!((int)letra >= 48 
                        && (int)letra <= 57)){
                    evt.consume();
                }
            }
        });
    }
    /**
     */
    public static void addValidatorRadio(JRadioButton radioButton, JComboBox comboBox){
        if(radioButton.isSelected()){            
            comboBox.setSelectedIndex(1);
            comboBox.setEnabled(true);
        }else{            
            comboBox.setSelectedIndex(0);
            comboBox.setEnabled(false);
        }        
    }
    
    /**
     * @param nombre JtextField del nombre del autor de una tesis
     * @param primer_apellido Jtextfield del primer apellido del autor
     * @param segundo_apellido Jtextfield del segundo apellido del autor
     * @return retorna true si contiene una cadena valida
     * 
     * Verifica que las entradas de los datos del Estudiante sean correctas
     */
    public static boolean isValidStudent(String numero_control,String nombre, String primer_apellido, String segundo_apellido){
        return
                numero_control.matches(NUMERO_CONTROL)
                ||nombre.matches(EXPNOMBRESYAPELLIDOS)
                || primer_apellido.matches(EXPNOMBRESYAPELLIDOS)
                || segundo_apellido.matches(EXPNOMBRESYAPELLIDOS);
    }   
    
    public static boolean isAlmacenamientoValid(JComboBox...combos){        
        for(int i = 0; i < combos.length; i ++){
            if(combos[i].getSelectedIndex() > 0){
                return true;
            }
        }
        return false;
    }
    
}
