/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;

import com.mycompany.maestria.Modelo.Almacenamiento;
import com.mycompany.maestria.Modelo.Busquedas.ModelosTesis;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Tesis;
import com.mycompany.maestria.Validaciones.ValidacionEntradas;
import com.mycompany.maestria.Vista.Componentes.AlertaConfirmacion;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import com.mycompany.maestria.Vista.Formularios.FormularioTesis;

import java.io.File;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 *
 * @author omarsilverio
 */
public class ControllerTesis {
    
    private ModelosTesis mt;
    private final ControllerTablasExcel archivo = new ControllerTablasExcel();
    
    public ControllerTesis(){
        mt = new ModelosTesis(Tesis.getListTesis());
    }
    
    public ControllerTesis(List<Tesis> listaTesis){
        mt = new ModelosTesis(listaTesis);
    }
    
    /**
     * Busqueda por el numero de control de la tesis
     */
    private DefaultTableModel buscarNumeroControl(String busqueda, int tabla){        
        if(!busqueda.isEmpty()){
           DefaultTableModel temp = mt.buscaridTesis(busqueda,tabla);
           if(temp != null) return temp;
        }
        return null;
    }    
    /**
     * Busqueda por el nombre del autor de la tesis
     */
    private DefaultTableModel buscarAutor(String busqueda, int tabla){        
        if(!busqueda.isEmpty()){
           DefaultTableModel temp = mt.buscarAutorTesis(busqueda,tabla);
           if(temp != null) return temp;
        }
        return null;
    }
    /**
     * Busqueda por titulo de la tesis
     */
    private DefaultTableModel buscarTitulo(String busqueda, int tabla){        
        if(!busqueda.isEmpty()){
           DefaultTableModel temp = mt.buscarTituloTesis(busqueda,tabla);
           if(temp != null) return temp;
        }
        return null;
    }
     /**
     * Busqueda por año de la tesis
     */
    private DefaultTableModel buscarAnio(String busqueda, int tabla){        
        if(ValidacionEntradas.isNumeric(busqueda)){
            int anio = Integer.parseInt(busqueda);
            if( anio > 1998 && anio < 2050){
                DefaultTableModel temp = mt.buscarAnioTesis(anio,tabla);
                if(temp != null) return temp;
            }
        }           
        return null; 
    }
    /**
     * @return regresa un modelo con todas las tesis
     */
    public DefaultTableModel allTesis(){
        DefaultTableModel temp = mt.getModelTesis();
        if(temp != null) return temp;        
        return null;
    }
    /**
     * @return regresa un modelo con todas las tesis con consultas
     */
    public DefaultTableModel historialTesis(){
        DefaultTableModel temp = mt.tesisHistorial();
        if(temp != null) return temp;
        return null;
    }
    
    /**
     * Controla las busquedas según sea la selección de busqueda
    */
     public DefaultTableModel busquedas(String busqueda, int select, int tabla){            
        switch(select){
            case 0 -> {return buscarTitulo(busqueda,tabla);}
            case 1 -> {return buscarAnio(busqueda,tabla);}
            case 2 -> {return buscarAutor(busqueda,tabla);}
            case 3 -> {return buscarNumeroControl(busqueda,tabla);}   
            default -> {return allTesis();}
        } 
     }
     /**
      * Obtiene la tesis seleccionada en una tabla
      */
     public static Tesis getTesisSeleccionada(JTable tabla){
        int row = tabla.getSelectedRow();     
        if(row > -1){
            TableModel model = tabla.getModel();
            String idTesis = (String)model.getValueAt(row, 0); //fila, columna = 0        
            Tesis tesisTemp = new Tesis(idTesis);         
            if(tesisTemp != null) return tesisTemp;
        }
        return null; 
     }       
    /**
     * Actualisa la lista de las tesis
     */
    public void actualizarLista(){
        mt.actualizarLista();       
    }     
   
   
    
      /**
     * Formulario de solo lectura
     */
    public static void dialogViewTesis(JTable tabla, JDialog parent){
         Tesis tesisAux = getTesisSeleccionada(tabla);
         if(tesisAux == null) return;
         
         Estudiante estudianteTemp = new Estudiante(tesisAux.getNumero_control());        
         Almacenamiento almTemp = new Almacenamiento(tesisAux.getIdTesis());            
         FormularioTesis form = new FormularioTesis(parent);
         form.justRead(estudianteTemp, tesisAux, almTemp);
         configFormulario(form);
    }
    
    /**
     * Formulario de solo lectura
     */
    public static void dialogViewTesis(Tesis tesisAux, JDialog parent){         
         if(tesisAux == null) return;
         
         Estudiante estudianteTemp = new Estudiante(tesisAux.getNumero_control());        
         Almacenamiento almTemp = new Almacenamiento(tesisAux.getIdTesis());            
         FormularioTesis form = new FormularioTesis(parent);
         form.justRead(estudianteTemp, tesisAux, almTemp);
         configFormulario(form);
    }
    /**
     * Formulario edición de una tesis
     */
    public void dialogEditTesis(JTable tabla, JDialog parent){
         Tesis sele = getTesisSeleccionada(tabla);
         if(sele == null) return;
         
         Estudiante estudianteTemp = new Estudiante(sele.getNumero_control());        
         Almacenamiento almTemp = new Almacenamiento(sele.getIdTesis());
         
         FormularioTesis form = new FormularioTesis(parent,tabla,this);
         form.justEdit(estudianteTemp, sele, almTemp);
         configFormulario(form);   
    }
    /**
     * Formulario para agregar una tesis
     */
    public static void dialogAddTesis(JDialog parent){
        FormularioTesis form = new FormularioTesis(parent);
        configFormulario(form);
    }
    public void dialogAddTesis(JDialog parent, JTable tablaTesis){
        FormularioTesis form = new FormularioTesis(parent,tablaTesis, this);
        configFormulario(form);
    }
    
    public void eliminarTesis(JDialog parent, JTable tabla){
        Tesis tesisAux = getTesisSeleccionada(tabla);
        if(tesisAux == null) return;        
        AlertaConfirmacion form = new AlertaConfirmacion(parent, tesisAux, tabla, this);
        configFormulario(form);
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
    
    
   
    /**
     * Genera un reporte en una tabla de excel
     */
    public void generarReporte(JFileChooser seleccionarCarpeta, JDialog parent){
        int seleccion = seleccionarCarpeta.showSaveDialog(parent);
        if (seleccion == JFileChooser.APPROVE_OPTION){
            File fichero = seleccionarCarpeta.getSelectedFile();
            try{               
               archivo.getListTesisExcel(fichero);
               Alertas.mensajeExito(parent, "Los datos se guardarón correctamente");
               
            }catch(Exception e){
                Alertas.mensajeError(parent, e.toString());
            }
        }
    }
    
    
     /**
     * @param temp
     * Agrega a un estudiante a la base de datos con los datos de las entradas
     */
    public static void addTesis(Tesis tesisAux,Almacenamiento almacenamientoAux ,JDialog parent){        
        if(tesisAux != null && almacenamientoAux != null){            
            boolean result = tesisAux.addTesisBDD() && almacenamientoAux.addAlmacenamientoBDD();            
            if(result){
                Alertas.mensajeExito(parent, "¡El registro se agregó correctamente!");                
            }  else{
                Alertas.mensajeError(parent, "¡Ocurrió algún error!\n al intentar "
                        + "agregar el registro a la base de datos!");                
            }  
        }        
        
    }
    public boolean addTesisList(Tesis tesisAux,Almacenamiento almacenamientoAux ,JDialog parent){        
        if(tesisAux != null && almacenamientoAux != null){           
            if(tesisAux.addTesisBDD() && almacenamientoAux.addAlmacenamientoBDD() ){
                mt.getListaTesis().add(tesisAux);
                Alertas.mensajeExito(parent, "¡El registro se agregó correctamente!");  
                return true;
            }  else{
                 Alertas.mensajeError(parent, "¡Ocurrió algún error!\n al intentar "
                        + "agregar el registro a la base de datos!");  
                return false;
            }  
        }        
        return false;
    }
    /**
     * Actualiza a la tesis y su almacenamiento
     */
    public boolean updateTesis(Tesis temp, Almacenamiento almacenamientoTemp,JDialog parent){
        if(temp != null){
            if(Alertas.actualizarRegistro(parent, temp.getIdTesis())){                 
                if(temp.updateTesisBDD() && almacenamientoTemp.updateAlmacenamientoBDD()){                    
                    mt.getListaTesis().removeIf(tesisAux -> tesisAux.getIdTesis().equalsIgnoreCase(temp.getIdTesis()));
                    mt.getListaTesis().add(temp);
                     Alertas.mensajeExito(parent, "¡El registro se actualizó correctamente!");
                    return true;
                }
                else
                    Alertas.mensajeError(parent, "¡Ocurrió algún error!\n al intentar "
                        + "actualizar el registro a la base de datos!");  
            }  
        } 
         return false;
    }     
    
    /**
     * Actualiza a un estudiante en la base de datos
     */
    public  boolean removeTesis(Tesis temp, JDialog parent) {
        String idTesis = temp.getIdTesis();
        if (temp != null) {            
            if (temp.removeTesis()) {
                mt.getListaTesis().removeIf(tesisAux -> tesisAux.getIdTesis().equalsIgnoreCase(idTesis));
                Alertas.mensajeExito(parent, "¡El registro se elimino correctamente!");
                    return true;
            } else {
                Alertas.mensajeError(parent, "¡Ocurrió algún error!\n al intentar "
                        + "eliminar el registro a la base de datos!");  
            }
            
        }
        return false;
    }
}  