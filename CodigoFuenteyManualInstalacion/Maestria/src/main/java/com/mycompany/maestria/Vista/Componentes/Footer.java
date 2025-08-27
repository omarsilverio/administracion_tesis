/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Vista.Componentes;

import com.mycompany.maestria.Vista.Colores.Colores;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author omarsilverio
 */
public class Footer extends JPanel{
    
    private final JLabel txtDireccion;
    private final JLabel txtDepartamento;
    private final JLabel txtEscuela;
    private static final String DIRECCION = "Avenida Ing. Víctor Bravo Ahuja No. 125 Esquina Calzada Tecnológico, C.P. 68030";
    private static final String DEPARTAMENTO = "División de estudios de posgrado e investigación";
    private static final String ESCUELA = "Instituto Tecnológico de Oaxaca";

    public Footer(){
        txtDireccion = new JLabel(DIRECCION);
        txtDepartamento = new JLabel(DEPARTAMENTO);
        txtEscuela = new JLabel(ESCUELA);
        this.setLayout(new FlowLayout(10, 20, 10));
        this.setBackground(Colores.colorPrimary);
        this.initComponents();
    }
    
    private void initComponents(){
        txtDireccion.setForeground(Color.white);
        txtDepartamento.setForeground(Color.white);
        txtEscuela.setForeground(Color.white);
        
        this.add(txtEscuela);
        this.add(txtDireccion);
        this.add(txtDepartamento);
        
    }
    
}
