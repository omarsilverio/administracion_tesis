/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Validaciones;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author omarsilverio
 */
public class ValidarTesis {
    
    /**
     * @param titulo_tesis JtextField el titulo de una tesis
     * @param tema Jtextfield el tema de una tesis
     * @param anio Jtextfield el anño de una tesis
     * @return true si todos los JtextField tienen información     * 
     * 
     * Verifica que los jtextField contengan información
     */
     public static boolean isEmpty(JTextField titulo_tesis, JTextField tema, JTextField anio){
        return !titulo_tesis.getText().equals("") 
                && !tema.getText().equals("") 
                && !anio.getText().equals("");
    }
     
     /**
     * @param titulo_tesis JtextField el titulo de una tesis
     * @param tema Jtextfield el tema de una tesis
     * @param anio Jtextfield el anño de una tesis
     * 
     * Asigna un contorno rojo a las entradas que no tiene información
     * 
     */
    public static void errorInput(JTextField titulo_tesis, JTextField tema, JTextField anio){
        if(titulo_tesis.getText().equals("")){
            titulo_tesis.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }else{             
             titulo_tesis.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        }
        if(tema.getText().equals("")){
            tema.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }else{
            tema.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        }
        if(anio.getText().equals("") || !validarAnio(Integer.parseInt(anio.getText()))){
            anio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }else{
            anio.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        }
    }
    /**
    public static void validateName(JTextField titulo_tesis){
        titulo_tesis.addFocusListener(new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                consult c = new consult();
                String cad = titulo_tesis.getText();
                if(c.tesisTitleExist(cad)){
                    JOptionPane.showMessageDialog(null, "El titulo de esta tesis ya se encuentra registrado");
                    titulo_tesis.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                }else{
                    titulo_tesis.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                }
                
            }
        });
        
    }*/
    
    public static void validarTextAnio(JTextField anio){
         anio.addKeyListener(new KeyAdapter(){
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
    
    public static boolean validarAnio(int anio){        
            return (anio >= 2000 && anio <= 2040);
    }
    
}
