/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;

import com.mycompany.maestria.Modelo.Busquedas.ModelosEstudiantes;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Vista.Componentes.AlertaConfirmacion;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import com.mycompany.maestria.Vista.Formularios.FormularioEstudiante;
import java.io.File;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author omarsilverio
 */
public class ControllerEstudiante {

    private ModelosEstudiantes me;
    private final ControllerTablasExcel archivosExcel = new ControllerTablasExcel();

    public ControllerEstudiante() {
        this.me = new ModelosEstudiantes(Estudiante.getEstudiantes());
    }

    public ControllerEstudiante(List<Estudiante> me) {
        this.me = new ModelosEstudiantes(me);
    }
    
    /**
     * Genera un reporte en una tabla de excel
     */
    public void generarReporte(JFileChooser seleccionarCarpeta, JDialog parent, String categoria){
        int seleccion = seleccionarCarpeta.showSaveDialog(parent);
        if (seleccion == JFileChooser.APPROVE_OPTION){
            File fichero = seleccionarCarpeta.getSelectedFile();
            try{               
               archivosExcel.getListEstudiantesExcel(fichero, categoria);
               Alertas.mensajeExito(parent, "El archivo se guardo correctamente");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static String getGeneracion(String numero_control) {
        int generacionInicio = Integer.parseInt(numero_control.substring(1, 3)) + 2000;
        int genracionFinal = generacionInicio + 2;
        return generacionInicio + "-" + genracionFinal;

    }

    /**
     * Busqueda por el numero de control de la tesis
     */
    private DefaultTableModel buscarNumeroControl(String busqueda, String categoria) {
        if (!busqueda.isEmpty() && !categoria.isEmpty()) {
            DefaultTableModel temp = me.buscarNumeroControl(busqueda, categoria);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Busqueda por el anio
     */
    private DefaultTableModel buscarNombreEstudiante(String busqueda, String categoria) {
        if (!busqueda.isEmpty() && !categoria.isEmpty()) {
            DefaultTableModel temp = me.buscarNombre(busqueda, categoria);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }

    /**
     * @return regresa un modelo con todos los estudiantes con adeudos
     */
    public DefaultTableModel allEstudiantes(String categoria) {
        if (!categoria.isEmpty()) {
            DefaultTableModel temp = me.getModelAlumnos(categoria);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Controla las busquedas según sea la selección de busqueda
     */
    public DefaultTableModel busquedas(String busqueda, String categoria, int select) {
        switch (select) {
            case 0 -> {
                return buscarNumeroControl(busqueda, categoria);
            }
            case 1 -> {
                return buscarNombreEstudiante(busqueda, categoria);
            }
            default -> {
                return allEstudiantes(categoria);
            }
        }
    }

    /**
     * Obtiene el estudiante seleccionado en una tabla
     */
    public static Estudiante getEstudianteSeleccionado(JTable tabla) {
        int row = tabla.getSelectedRow();
        if (row > -1) {
            TableModel model = tabla.getModel();
            String numero_control = (String) model.getValueAt(row, 0); //fila, columna = 0        
            Estudiante tesisTemp = new Estudiante(numero_control);
            if (tesisTemp != null) {
                return tesisTemp;
            }
        }
        return null;
    }

    

    public void eliminarEstudiante(JDialog parent, JTable tabla) {
        Estudiante estudianteAux = getEstudianteSeleccionado(tabla);
        if (estudianteAux == null) return;        
        AlertaConfirmacion form = new AlertaConfirmacion(parent, estudianteAux,tabla,this);
        configFormulario(form);        
    }

    public static void dialogoViewEstudiante(JDialog parent, JTable tabla) {
        Estudiante estudianteAux = getEstudianteSeleccionado(tabla);
        if (estudianteAux == null) return;
        FormularioEstudiante form = new FormularioEstudiante(parent);
        form.justRead(estudianteAux);
        configFormulario(form);

    }

    public void dialogoEditEstudiante(JDialog parent, JTable tabla) {
        Estudiante estudianteAux = getEstudianteSeleccionado(tabla);
        if (estudianteAux == null) return;        
        FormularioEstudiante form = new FormularioEstudiante(parent,tabla,this);
        form.justEdit(estudianteAux);
        configFormulario(form);       
    }

    public void dialogoAddEstudiante(JDialog parent) {
        FormularioEstudiante form = new FormularioEstudiante(parent);
        configFormulario(form);        
    }
    
    public void dialogoAddEstudiante(JDialog parent, JTable tablaEstudiantes) {
        FormularioEstudiante form = new FormularioEstudiante(parent,tablaEstudiantes,this);
        configFormulario(form);        
    }

    /**
     * Configuración de los formularios
     */
    private static void configFormulario(JDialog form) {
        form.pack();
        form.setModal(true);
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }
   

    /**
     * @param temp Agrega a un estudiante a la base de datos con los datos de
     * las entradas
     */
    public static boolean addEstudiante(Estudiante estudianteAux, JDialog parent) {
        if (estudianteAux != null && !estudianteAux.isRegister()) {           
            if (estudianteAux.addEstudianteBDD()) {
                Alertas.mensajeExito(parent, "¡El registro se agregó correctamente!");
                return true;
            } else {
                Alertas.mensajeError(parent, "¡Ocurrió algún error!\n no se pudo agregar a la base de datos");
                return false;
            }
        } else {
            Alertas.mensajeAdvertencia(parent, "¡El registro ya se encuentra registrado!");
        }
        return false;
    }
    public boolean addEstudianteList(Estudiante estudianteAux, JDialog parent) {
        if (estudianteAux != null && !estudianteAux.isRegister()) {          
            if (estudianteAux.addEstudianteBDD()) {
                me.getListaEstudiantes().add(estudianteAux);
                 Alertas.mensajeExito(parent, "¡El registro se agregó correctamente!");
                return true;
            } else {
               Alertas.mensajeError(parent, "Ocurrió algún error!\n no se pudo agregar en la base de datos");
                return false;
            }
        } else {
            Alertas.mensajeAdvertencia(parent, "¡El registro ya se encuentra registrado!");
        }
        return false;
    }

    /**
     * Actualiza a un estudiante en la base de datos
     */
    public  boolean updateEstudiante(Estudiante temp, JDialog parent) {
        if (temp != null) {
            String numero_control = temp.getNumero_control();
            if (Alertas.actualizarRegistro(parent, temp.getNumero_control())) {
                if (temp.updateEstudianteBDD()) {
                    me.getListaEstudiantes().removeIf(estudianteAux -> estudianteAux.getNumero_control().equalsIgnoreCase(numero_control));
                    me.getListaEstudiantes().add(temp);
                    Alertas.mensajeExito(parent, "¡El registro se actualizó correctamente!");
                    return true;
                } else {
                     Alertas.mensajeError(parent, "Ocurrió algún error!\n no se pudo actualizar en la base de datos");
                }
            }
        }
        return false;
    }
     /**
     * Actualiza a un estudiante en la base de datos
     */
    public  boolean removeEstudiante(Estudiante temp, JDialog parent) {
        String numero_control = temp.getNumero_control();
        if (temp != null) {            
            if (temp.removeEstudiante()) {
                me.getListaEstudiantes().removeIf(estudianteAux -> estudianteAux.getNumero_control().equalsIgnoreCase(numero_control));
                Alertas.mensajeExito(parent, "¡El registro se eliminó correctamente!");

                    return true;
            } else {
                Alertas.mensajeError(parent, "Ocurrió algún error!\n no se pudo eliminar en la base de datos");
            }
            
        }
        return false;
    }

}
