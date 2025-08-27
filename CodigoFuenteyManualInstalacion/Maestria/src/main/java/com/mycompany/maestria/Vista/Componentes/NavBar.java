/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Vista.Componentes;

import com.mycompany.maestria.Controlador.Controller;
import com.mycompany.maestria.Vista.Formularios.FormularioEstudiante;
import com.mycompany.maestria.Vista.Formularios.FormularioPrestamo;
import com.mycompany.maestria.Vista.Formularios.FormularioTesis;
import com.mycompany.maestria.Vista.Modulos.ModuloConfiguraciones;
import com.mycompany.maestria.Vista.Modulos.ModuloEstudiantes;
import com.mycompany.maestria.Vista.Modulos.ModuloHistorial;
import com.mycompany.maestria.Vista.Modulos.ModuloAdeudos;
import com.mycompany.maestria.Vista.Modulos.ModuloTesis;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author omarsilverio
 */
public class NavBar extends JMenuBar{
    private JDialog parent;
    private Controller ct = new Controller();
    public NavBar(JDialog parent){
        this.parent = parent;
        initComponents();
        this.setPreferredSize(new Dimension(1103, 30));
        this.setMaximumSize(new Dimension(1211034, 30));
    }
    public void initComponents(){    
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
                
        JMenu menuEstudiantes = new JMenu("Estudiantes");
        JMenu menuTesis = new JMenu("Tesis");
        JMenu menuPrestamos = new JMenu("Prestamos");
        JMenu menuGraficos = new JMenu("Estadísticas");  
        JMenu menuConfiguraciones = new JMenu("Configuraciones");  
        JMenu menuCerrarSesión = new JMenu("Salir");
        
        if(parent instanceof ModuloEstudiantes){           
            menuEstudiantes.setEnabled(false);
        }else if(parent instanceof ModuloTesis){
            menuTesis.setEnabled(false);
        }else if(parent instanceof ModuloHistorial){
            menuGraficos.setEnabled(false);
        }else if(parent instanceof ModuloAdeudos){
            menuPrestamos.setEnabled(false);
        }else if(parent instanceof ModuloConfiguraciones){
            menuConfiguraciones.setEnabled(false);
        }
        
        menuEstudiantes.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){  
                if(menuEstudiantes.isEnabled())
                    abrirEstudiante();
            }           
        });
        
        menuTesis.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(menuTesis.isEnabled())
                    abrirTesis();
            }           
        });
        
        menuPrestamos.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(menuPrestamos.isEnabled())
                    abrirPrestamo();
            }           
        });
        
        menuGraficos.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(menuGraficos.isEnabled())
                    abrirEstadisticas();
            }           
        }); 
        menuConfiguraciones.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(menuConfiguraciones.isEnabled())
                    abrirConfiguraciones();
            }           
        }); 
        menuCerrarSesión.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                salir();
            }           
        });  
        JMenuItem opcAgregarTesis = new JMenuItem("Agregar Tesis");
        JMenuItem opcAgregarEstudiante = new JMenuItem("Agregar Estudiante");
        JMenuItem opcAgregarPrestamo = new JMenuItem("Agregar Prestamo");        
       
        opcAgregarTesis.addActionListener((ActionEvent event) -> {               
            agregarTesis();
        });
        
        opcAgregarEstudiante.addActionListener((ActionEvent event) -> {
            agregarEstudiante();
        });
        
        opcAgregarPrestamo.addActionListener((ActionEvent event) -> {
            agregarPrestamo();
        });  
        
        
        
        this.add(menuEstudiantes);
        this.add(menuTesis);
        this.add(menuPrestamos);
        this.add(menuGraficos); 
        this.add(menuConfiguraciones);
        this.add(menuCerrarSesión);        
    }
    
    private void agregarPrestamo(){
        if(ct.comprobarTablas(parent)){
            FormularioPrestamo form = new FormularioPrestamo(parent);
            configFormulario(form);
        }
    }    
    
    private void agregarEstudiante(){
        if(ct.comprobarTablas(parent)){
            FormularioEstudiante form = new FormularioEstudiante(parent);
            configFormulario(form);        
        }
    }
    
    /**
     * Formulario para agregar una tesis
     */
    public void agregarTesis(){
        if(ct.comprobarTablas(parent)){
            FormularioTesis form = new FormularioTesis(parent);
            configFormulario(form);
        }
    }
    /**
     * Configuración de los formularios
     */
    private void configFormulario(JDialog form){        
        form.pack();
        form.setModal(true);
        form.setLocationRelativeTo(null);        
        form.setVisible(true);
    }
    
    public void abrirEstudiante(){
        if(ct.comprobarTablas(parent)){
            parent.dispose();
            ModuloEstudiantes form = new ModuloEstudiantes();
            form.pack();  
            form.setModal(true);
            form.setLocationRelativeTo(null);
            form.setVisible(true); 
        }
       
    }
    public void abrirConfiguraciones(){
        parent.dispose();
        ModuloConfiguraciones form = new ModuloConfiguraciones();
        form.pack();  
        form.setModal(true);
        form.setLocationRelativeTo(null);
        form.setVisible(true); 
    }
    
    public void abrirTesis(){
        if(ct.comprobarTablas(parent)){
            parent.dispose();        
            ModuloTesis form = new ModuloTesis();
            form.pack();   
            form.setModal(true);
            form.setLocationRelativeTo(null);
            form.setVisible(true);
        }
    }
    
    public void abrirPrestamo(){        
        if(ct.comprobarTablas(parent)){
            parent.dispose();
            ModuloAdeudos form = new ModuloAdeudos();
            form.pack(); 
            form.setModal(true);
            form.setLocationRelativeTo(null);
            form.setVisible(true);  
        }        
    }
    
    public void abrirEstadisticas(){
        if(ct.comprobarTablas(parent)){
            parent.dispose();
            ModuloHistorial form = new ModuloHistorial();
            form.pack();        
            form.setLocationRelativeTo(null);
            form.setVisible(true); 
        }        
    }
    
    public void salir(){
        int resp = JOptionPane.showConfirmDialog(parent, "El programa se cerrará"
                + "\n¿Desea continuar?","Cerrar Programa",JOptionPane.YES_NO_OPTION);
        if(resp == 0)
            System.exit(0);
    }
    
}
