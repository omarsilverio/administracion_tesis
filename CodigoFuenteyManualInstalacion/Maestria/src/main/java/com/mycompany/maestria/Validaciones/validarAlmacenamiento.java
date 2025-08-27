/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Validaciones;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

/**
 *
 * @author omarsilverio
 */
public class validarAlmacenamiento {
    
    public static void validarEntradaCd(JRadioButton radioCd, JComboBox comboCd){
        if(radioCd.isSelected()){
            comboCd.setSelectedIndex(1);
            comboCd.setEnabled(true);
        }else{
            comboCd.setSelectedIndex(0);
            comboCd.setEnabled(false);
        }
        
    }
    
    public static void validarEntradaUsb(JRadioButton radioUsb, JComboBox comboUsb){
        if(radioUsb.isSelected()){
            comboUsb.setSelectedIndex(1);
            comboUsb.setEnabled(true);
        }else{
            comboUsb.setSelectedIndex(0);
            comboUsb.setEnabled(false);
        }
        
    }
    
    public static void validarEntradaTomo(JRadioButton radioTomo, JComboBox comboTomo){
        if(radioTomo.isSelected()){
            comboTomo.setSelectedIndex(1);
            comboTomo.setEnabled(true);
        }else{
            comboTomo.setSelectedIndex(0);
            comboTomo.setEnabled(false);
        }
        
    }
    
    /**
     * @param comboCd
     * @param comboUsb
     * @param comboTomo
     * @return true si todos los campos estan seleccionados como cero
     * Duplicate @return false si al menos un combobox es diferente de cero
     */
    public static boolean thereSelected(JComboBox comboCd, JComboBox comboUsb, JComboBox comboTomo){
        return !(comboCd.getSelectedIndex() == 0 && comboUsb.getSelectedIndex() == 0 && comboUsb.getSelectedIndex() == 0);
    }
    
    
}
