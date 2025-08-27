/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo.Busquedas;

import com.mycompany.maestria.Modelo.Estudiante;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author omarsilverio
 */
public class ModelosEstudiantes {
    
    private List<Estudiante> listaEstudiantes;
    private static final String[] columnEstudiante = {"Número de Control","Nombre","Primer Apellido","Segundo Apellido"};
    private static final String[] columnEstudianteAdeudo = {"Número de Control","Nombre","Primer Apellido","Segundo Apellido","Cant. Adeudos"};
             
    public ModelosEstudiantes(List<Estudiante> listaEstudiantes){
        this.listaEstudiantes = listaEstudiantes;
    }
    
    public void actualizarLista(){
        this.listaEstudiantes = Estudiante.getEstudiantes();
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }
    
    
    /**
     * Obtiene un modelo de una tabla con datos de todos los alumnos dependiendo de la categoria
     */
    public DefaultTableModel getModelAlumnos(String categoria){
        Tabla modelTemp = new Tabla(columnEstudiante,0); 
        if(listaEstudiantes!= null && !listaEstudiantes.isEmpty())
            listaEstudiantes
                    .stream()
                    .filter(EstudiantesPredicados.filtroCategoria(categoria))
                    .forEach(estudianteAux -> modelTemp.addRow(getRow(estudianteAux,1)));        
        return modelTemp;
    }
    /**
     * Obtiene un modelo de una tabla con datos de todos los alumnos con adedudos
     */
    public DefaultTableModel getModelAlumnoAdeudo(){
        Tabla modelTemp = new Tabla(columnEstudianteAdeudo,0);
        if(listaEstudiantes!= null && !listaEstudiantes.isEmpty())
            listaEstudiantes
                    .stream()
                    .filter(EstudiantesPredicados.hadAdeudos())
                    .forEach(estudianteAux -> modelTemp.addRow(getRow(estudianteAux,2)));        
        return modelTemp;
    } 
   
    /**
     * Obtiene un modelo de una tabla con datos de todos los alumnos con adedudos
     */
    public List<Estudiante> getListAlumnosAdeudos(){
        List<Estudiante> modelTemp = new LinkedList();
        if(listaEstudiantes!= null && !listaEstudiantes.isEmpty())
            listaEstudiantes
                    .stream()
                    .filter(EstudiantesPredicados.hadAdeudos())
                    .forEach(estudianteAux -> modelTemp.add(estudianteAux));        
        return modelTemp;
    } 
    /**
     * Obtiene una de una tabla con datos de todos los alumnos dependiendo de la categoria
     */
    public List<Estudiante> getListAlumnos(String categoria){
        List<Estudiante> modelTemp = new LinkedList();
        if(!listaEstudiantes.isEmpty())
            listaEstudiantes
                    .stream()
                    .filter(EstudiantesPredicados.filtroCategoria(categoria))
                    .forEach(estudianteAux -> modelTemp.add(estudianteAux));        
        return modelTemp;
    }
    
    /**
     * Retorna un modelo de estudiantes por el numero de control
     */
    public DefaultTableModel buscarNumeroControl(String busqueda, String categoria){
        Tabla modelTemp = new Tabla(columnEstudiante,0);       
        if(listaEstudiantes!= null && !listaEstudiantes.isEmpty()){
            listaEstudiantes.stream()
                    .filter(EstudiantesPredicados.filtroCategoria(categoria))
                    .filter(EstudiantesPredicados.containsNumeroControl(busqueda))
                    .forEach(estudianteAux -> modelTemp.addRow(getRow(estudianteAux,1)));
        }
        return modelTemp;
    }
    
    /**
     * Retorna un modelo de estudinates por numero de control
     */
    public DefaultTableModel buscarNombre(String busqueda, String categoria){
        Tabla modelTemp = new Tabla(columnEstudiante,0);          
        if(listaEstudiantes!= null && !listaEstudiantes.isEmpty()){
            listaEstudiantes.stream()
                    .filter(EstudiantesPredicados.filtroCategoria(categoria))
                    .filter(EstudiantesPredicados.containsNombre(busqueda))
                    .forEach(estudianteAux -> modelTemp.addRow(getRow(estudianteAux,1)));
        }
        return modelTemp;
    }
    /**
     * @return retorna un modelo de tabla estudiantes que tienen adeudos
     * por numero de control
     */
   public DefaultTableModel buscarNumeroControlAdeudos(String busqueda){
        Tabla modelTemp = new Tabla(columnEstudianteAdeudo,0);  
        
        if(listaEstudiantes != null && !listaEstudiantes.isEmpty()){
            listaEstudiantes.stream()                    
                    .filter(EstudiantesPredicados.containsNumeroControl(busqueda))
                    .filter(EstudiantesPredicados.hadAdeudos())                    
                    .forEach(estudianteAux -> modelTemp.addRow(getRow(estudianteAux,2)));
        }
        return modelTemp;
    }
    /**
     * @return retorna un modelo de tabla estudiantes que tienen adeudos
     * por su nombre
     */
    public DefaultTableModel buscarNombreAdeudos(String busqueda){
        Tabla modelTemp = new Tabla(columnEstudianteAdeudo,0);  
        
        if(listaEstudiantes != null && !listaEstudiantes.isEmpty()){
            listaEstudiantes.stream()                    
                    .filter(EstudiantesPredicados.containsNombre(busqueda))
                    .filter(EstudiantesPredicados.hadAdeudos())                    
                    .forEach(estudianteAux -> modelTemp.addRow(getRow(estudianteAux,2)));
        }
        return modelTemp;
    }
    
    /**
     * Obtiene una fila para un tableModel
     */
    public Object[] getRow(Estudiante estudianteAux, int encabezados){
        switch(encabezados){
            case 1 -> {return new Object[]{
                            estudianteAux.getNumero_control(),
                            estudianteAux.getNombre(),
                            estudianteAux.getApellido_paterno(),
                            estudianteAux.getApellido_materno()};            
            }
            case 2 -> {return new Object[]{
                            estudianteAux.getNumero_control(),
                            estudianteAux.getNombre(),
                            estudianteAux.getApellido_paterno(),
                            estudianteAux.getApellido_materno(),
                            estudianteAux.adeudos()};            
            }
            default -> {return new Object[]{
                            estudianteAux.getNumero_control(),
                            estudianteAux.getNombre(),
                            estudianteAux.getApellido_paterno(),
                            estudianteAux.getApellido_materno()};            
            }
        }
    } 
    /**
     *Condiciones de busquedas
     */
    public class EstudiantesPredicados {
        public static Predicate<Estudiante> filtroCategoria(String categoria) {
            return (Estudiante estudianteAux) -> {
                return estudianteAux.getStatusCadena().equalsIgnoreCase(categoria);
            };
        }
        
        public static Predicate<Estudiante> containsNumeroControl(String busqueda){
            return (Estudiante estudianteAux) -> {
                return estudianteAux.getNumero_control().toUpperCase().contains(busqueda.toUpperCase());
            };
        }
        
        public static Predicate<Estudiante> containsNombre(String busqueda){
            return (Estudiante estudianteAux) -> {
                return estudianteAux.toString().toUpperCase().contains(busqueda.toUpperCase());
            };
        }
        
         public static Predicate<Estudiante> hadAdeudos(){
             return(Estudiante estudianteAux)-> {
                 return estudianteAux.adeudos() > 0;
             };
         }
    }
}
