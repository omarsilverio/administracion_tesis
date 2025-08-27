/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo.Busquedas;

import com.mycompany.maestria.Vista.Colores.Colores;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author omarsilverio
 */
public class RenderTabla{
    
    public static void cambiarEstiloEncabezados(JTable tabla){
        DefaultTableCellRenderer head_render = new DefaultTableCellRenderer(); 
        head_render.setBackground(Colores.colorPrimary);
        head_render.setForeground(Color.white);
        head_render.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setSelectionBackground(new java.awt.Color(51, 51, 51));
        tabla.setSelectionForeground(Color.white);
        tabla.getTableHeader().setDefaultRenderer(head_render);
        /*
        JTableHeader encabezado;
        encabezado = tabla.getTableHeader();
        encabezado.setOpaque(false);
        encabezado.setBackground(Color.black);
        encabezado.setForeground(Color.white);*/
    }
     /**
     * Centra los datos de la tabla ¡PONERLO EN LA TABLA DEL PERSONALIZADA!
     */
    public static void centrarDatos(JTable tabla){
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i < tabla.getColumnCount(); i ++){
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        
        ((DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
    } 
    
    
}
