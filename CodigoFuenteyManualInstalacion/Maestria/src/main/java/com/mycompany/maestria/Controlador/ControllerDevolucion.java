/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;

import com.mycompany.maestria.Modelo.Busquedas.ModelosTesis;
import com.mycompany.maestria.Modelo.Devolucion;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Prestamo;
import com.mycompany.maestria.Modelo.Tesis;
import com.mycompany.maestria.Vista.Formularios.FormularioTipoPrestamo;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author omarsilverio
 */
public class ControllerDevolucion {
    private ModelosTesis mt;
    public ControllerDevolucion(){
        mt = new ModelosTesis(Tesis.getListTesis());
    }
    public ControllerDevolucion(List<Tesis> listaTesis){
        mt = new ModelosTesis(listaTesis);
    }
    public static boolean devolverTodasUnidades(Prestamo prestamoDevolucion){
        int usbDevolucion = prestamoDevolucion.getUsb();
        int cdDevolucion = prestamoDevolucion.getCd();
        int tomoDevolucion = prestamoDevolucion.getTomo();
        Devolucion devolucionTemp = new Devolucion(prestamoDevolucion.getNumero_prestamo(), usbDevolucion, cdDevolucion, tomoDevolucion);
        if(devolucionTemp.addDevolucionBDD()){
            prestamoDevolucion.setStatus(true);
           return prestamoDevolucion.updateStatusPrestamoBDD();            
        }
        return false;
    }  
    
    
    public  boolean devolverUnidades(Prestamo prestamo, int usb, int cd, int tomo){
        Devolucion devolucionTemp = new Devolucion(prestamo.getNumero_prestamo(),usb,cd,tomo);
         if(devolucionTemp.addDevolucionBDD()){
             Devolucion devolucionAux = new Devolucion(prestamo.getNumero_prestamo());
             if(prestamo.getUsb() - devolucionAux.getUsb() == 0 && 
                prestamo.getTomo() - devolucionAux.getTomo() == 0 &&
                prestamo.getCd() - devolucionAux.getCd() == 0){
                  prestamo.setStatus(true);
                  return prestamo.updateStatusPrestamoBDD();
             }
             return true;
         }
         return false;
        
    }
    
     /**
     */
    public void dialogAlmacenamientoDevolcion(JDialog parent,JTable tabla, Estudiante estudianteAux){
        int row = tabla.getSelectedRow();     
        if(row > -1){
            TableModel model = tabla.getModel();
            int numero_prestamo = Integer.parseInt(model.getValueAt(row, 0).toString()); //fila, columna = 0 
            FormularioTipoPrestamo form = new FormularioTipoPrestamo(parent, numero_prestamo, tabla,this);
            configFormulario(form);            
        }
        
    }
    public void actualizarLista(){
        mt.actualizarLista();
    }
    public DefaultTableModel getAdeudosEstudiantes(Estudiante estudianteTemp){         
       return mt.getModelTesisAdeudos(estudianteTemp);
    }
    
    /**
     * Configuración de los formularios
     */
    private static void configFormulario(JDialog form){
        form.pack();
        form.setModal(true);
        form.setLocationRelativeTo(null);        
        form.setVisible(true);      
    }  
}
