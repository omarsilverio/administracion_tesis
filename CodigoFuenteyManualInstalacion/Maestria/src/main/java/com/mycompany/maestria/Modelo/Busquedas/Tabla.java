/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo.Busquedas;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author omarsilverio
 */
public class Tabla extends DefaultTableModel{
    
    public Tabla(String[] column, int rows){
        super(column,rows);
        
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
    
    
}
