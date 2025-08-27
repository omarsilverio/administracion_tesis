/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo.Busquedas;

import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Prestamo;
import com.mycompany.maestria.Modelo.Tesis;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author omarsilverio
 */
public class ModelosPrestamos {
    
    private List<Prestamo> listaPrestamo;
    
    private static final String[] columnTesisPrestamo = {"IdTesis","Autor","Titulo","Fecha Prestamo"};
    private static final String[] columnAdeudosEstudiantes = {"Número de Control","Nombre","Primer Apellido","Segundo Apellido","Cant. Adeudos"};
    private static final String[] columnAdeudosTesis = {"IDtesis","Autor","Titulo","Año","Cant. Consultas"};
        private static final String[] columnEstudiantesAdeudos = {"Número Prestamo","Numero de Control","Nombre","Cant. Unidades","Fecha Prestamo"};

    
    public ModelosPrestamos(List<Prestamo> listaPrestamo){
        this.listaPrestamo = listaPrestamo;
    }
    /**
     * Actualiza la lista 
     */
      
    /**
     * Obtiene los prestamos de un estudiante
     */
    public DefaultTableModel getPrestamosEstudiante(String numero_control){
        Tabla modelTemp = new Tabla(columnTesisPrestamo,0);         
        if(listaPrestamo != null && !listaPrestamo.isEmpty()){
            listaPrestamo.stream()
                         .filter(PrestamosPredicados.filtroAdeudosEstudiante(numero_control))
                         .forEach(prestamoAux -> modelTemp.addRow(filaPrestamoEstudiante(prestamoAux)));
        }
         return modelTemp;
    }
    /**
     * Obtiene todos los prestamos por estudiantes
     */
    public DefaultTableModel getModelPrestamosEstudiantes(){
        Tabla modelTemp = new Tabla(columnAdeudosEstudiantes,0);
        if(listaPrestamo != null && !listaPrestamo.isEmpty()){
            listaPrestamo.stream()
                    .filter(prestamoAux -> !prestamoAux.getStatus())
                    .collect(Collectors.groupingBy(Prestamo::getNumero_control,Collectors.counting()))
                    .forEach((k, v) -> modelTemp.addRow(getRowEstudiante(k,v)));
        }   
        return modelTemp;
    }
    /**
     * Obtiene todos los prestamos por tesis
     */
    public DefaultTableModel getModelPrestamosTesis(){
        Tabla modelTemp = new Tabla(columnAdeudosTesis,0);
        if(listaPrestamo != null && !listaPrestamo.isEmpty()){
            listaPrestamo.stream()                    
                    .collect(Collectors.groupingBy(Prestamo::getIdTesis,Collectors.counting()))
                    .forEach((k, v) -> modelTemp.addRow(getRowTesis(k,v)));
        }   
        return modelTemp;
    }
    
     /**
     * Obtiene un modelo de una tabla con datos de todos los alumnos con adedudos
     */
    public static DefaultTableModel getModelAlumnosAdeudos(Tesis tesisAux){
       Tabla modelTemp = new Tabla(columnEstudiantesAdeudos,0);
        List<Prestamo> listPrestamos = tesisAux.getPrestamos();
        if(listPrestamos != null && !listPrestamos.isEmpty())
            listPrestamos
                    .stream()                   
                    .forEach(prestamoAux -> modelTemp.addRow(getRow(prestamoAux)));        
        return modelTemp;
    } 
    
   private static Object[] getRow(Prestamo prestamoAux){
       Estudiante estudianteTemp = new Estudiante(prestamoAux.getNumero_control());
       int unidades = prestamoAux.getCd() + prestamoAux.getTomo() + prestamoAux.getUsb();
       return new Object[]{
           prestamoAux.getNumero_prestamo(),
           estudianteTemp.getNumero_control(),
           estudianteTemp.toString(),          
           unidades,
           prestamoAux.getFecha_prestamo()           
       };
   }
    
    private Object[] getRowTesis(String idTesis, long cant){
        Tesis tesisAux = new Tesis(idTesis);
        Estudiante estudianteAux = new Estudiante(tesisAux);
        return new Object[]{
            tesisAux.getIdTesis(),
            estudianteAux.toString(),
            tesisAux.getTitulo_tesis(),
            tesisAux.getAnio(),
            cant
        };
    } 
    
    private Object[] getRowEstudiante(String numero_control, long cant){
        Estudiante estudianteAux = new Estudiante(numero_control);
        return new Object[]{
            estudianteAux.getNumero_control(),
            estudianteAux.getNombre(),
            estudianteAux.getApellido_paterno(),
            estudianteAux.getApellido_materno(),
            cant
        };
    } 
    
    public Object[] filaPrestamoEstudiante(Prestamo aux){
        Tesis tesisTemp = new Tesis(aux.getIdTesis());
        Estudiante estudianteTemp = new Estudiante(tesisTemp.getNumero_control());        
        return new Object[]{tesisTemp.getIdTesis(),estudianteTemp.toString(),tesisTemp.getTitulo_tesis(), aux.getFecha_prestamo()};
    }
    
    public class PrestamosPredicados {
        public static Predicate<Prestamo> filtroAdeudosEstudiante(String numero_control) {
            return (Prestamo prestamoAux) -> {
                return prestamoAux.getNumero_control().equals(numero_control) && !prestamoAux.getStatus();
            };
        }
    }
    
    
}
