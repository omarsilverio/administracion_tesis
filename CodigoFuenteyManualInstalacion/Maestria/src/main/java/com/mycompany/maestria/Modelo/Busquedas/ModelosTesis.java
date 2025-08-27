/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo.Busquedas;

import com.mycompany.maestria.Modelo.Adeudo;
import com.mycompany.maestria.Modelo.Devolucion;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Tesis;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author omarsilverio
 */
public class ModelosTesis {
    
    private List<Tesis> listaTesis;
   
    private static final String[] columTesisAdeudos = {"Nº prestamo","IDtesis","Titulo","Unidades"};
    private static final String[] columTesisStatus = {"IDtesis","Autor","Titulo","Año","Status"};
    private static final String[] columTesisConsult = {"IDtesis","Autor","Titulo","Año","Nº Consultas"};
    

    public ModelosTesis(List<Tesis> listaTesis) {
        this.listaTesis = listaTesis;
    }

    public List<Tesis> getListaTesis() {
        return listaTesis;
    }
    
    
    /**
     * Actualiza la lista 
     */
    public void actualizarLista(){
        this.listaTesis = Tesis.getListTesis();
    } 
    /**
     * @return retorna un modelo de tabla con todas las tesis de la base de datos
     */
    public DefaultTableModel getModelTesis(){        
        Tabla modelTemp = new Tabla(columTesisStatus,0);         
        if(listaTesis != null && !listaTesis.isEmpty())
            listaTesis.forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0)));     
        return modelTemp;
    }
    /**
     * @return retorna un modelo de tabla con todas las tesis de la base de datos
     */
    public  DefaultTableModel getModelTesisAdeudos(Estudiante auxEstudiante){        
        Tabla modelTemp = new Tabla(columTesisAdeudos,0);  
        List<Adeudo> listAdeudoTemp =  auxEstudiante.getAdeudos();
        if(listAdeudoTemp != null && !listAdeudoTemp.isEmpty()){
            listAdeudoTemp.forEach(adeudoAux -> {               
                modelTemp.addRow(getRow(adeudoAux,3));
            });   
        }  
        return modelTemp;
    }
    /**
     * @param busqueda por autor
     * @return retorna un modelo de tabla con todas las coincidencias de busqueda por autor
     */
    public DefaultTableModel buscarAutorTesis(String busqueda, int tabla){
        Tabla modelTemp = new Tabla(columTesisStatus,0); 
        Tabla modelTemp1 = new Tabla(columTesisConsult,0); 
        if(listaTesis != null && !listaTesis.isEmpty()){
            switch(tabla){
                case 0 -> {
                    listaTesis.stream()
                    .filter(TesisPredicados.containsAutor(busqueda))        
                    .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0)));
                     return modelTemp;
                }
                case 1 ->{
                     listaTesis.stream()
                    .filter(TesisPredicados.containsAutor(busqueda))        
                    .forEach(tesisAux -> modelTemp1.addRow(getRow(tesisAux,2)));
                      return modelTemp1;
                }
                default -> {
                      listaTesis.stream()
                        .filter(TesisPredicados.containsAutor(busqueda))        
                        .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0)));
                         return modelTemp;
                    }
            } 
            
        }
        return modelTemp;
    }
    
    /**
     * @param busqueda cadena de busqueda
     * @return retorna un modelo de tabla con todas las coincidencias de busqueda por el titulo
     */
    public DefaultTableModel buscarTituloTesis(String busqueda, int tabla){
        
       Tabla modelTemp = new Tabla(columTesisStatus,0); 
        Tabla modelTemp1 = new Tabla(columTesisConsult,0); 
        
        if(listaTesis != null && !listaTesis.isEmpty()){
            switch(tabla){
                case 0 -> {
                    listaTesis.stream()
                    .filter(TesisPredicados.containsTitulo(busqueda))        
                    .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0)));
                     return modelTemp;
                }
                case 1 ->{
                     listaTesis.stream()
                    .filter(TesisPredicados.containsTituloHistorial(busqueda))        
                    .forEach(tesisAux -> modelTemp1.addRow(getRow(tesisAux,2)));
                     return modelTemp1;
                }
                default -> {
                       listaTesis.stream()
                       .filter(TesisPredicados.containsTitulo(busqueda))        
                        .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0)));
                         return modelTemp;
                     }
            } 
            
        }
        return modelTemp;
    } 
    
    /**
     * @param busqueda cadena de busqueda
     * @return retorna un modelo de tabla con todas las coincidencias de busqueda idTesis
     */    
    public DefaultTableModel buscaridTesis(String busqueda, int tabla){
        Tabla modelTemp = new Tabla(columTesisStatus,0); 
        Tabla modelTemp1 = new Tabla(columTesisConsult,0); 
        if(listaTesis != null && !listaTesis.isEmpty()){
            switch(tabla){
                case 0 -> {
                    listaTesis.stream()
                    .filter(TesisPredicados.containsIdtesis(busqueda))        
                    .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0))); 
                     return modelTemp;
                }
                case 1 ->{
                     listaTesis.stream()
                    .filter(TesisPredicados.containsIdtesisHistorial(busqueda))        
                    .forEach(tesisAux -> modelTemp1.addRow(getRow(tesisAux,2)));
                      return modelTemp1;
                }
                default -> {
                        listaTesis.stream()
                        .filter(TesisPredicados.containsIdtesis(busqueda))        
                        .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0))); 
                         return modelTemp;
                       }
            }                 
        }
        return modelTemp;
    }
    /**
     * @param busqueda cadena de busqueda
     * @return retorna un modelo de tabla con todas las coincidencias por año
     */
    public DefaultTableModel buscarAnioTesis(int busqueda, int tabla){
        Tabla modelTemp = new Tabla(columTesisStatus,0); 
        Tabla modelTemp1 = new Tabla(columTesisConsult,0); 
        if(listaTesis != null && !listaTesis.isEmpty()){
            switch(tabla){
                case 0 -> {
                    listaTesis.stream()
                    .filter(TesisPredicados.containsAnio(busqueda))        
                    .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0))); 
                     return modelTemp;
                }
                case 1 ->{                 
                     listaTesis.stream()
                    .filter(TesisPredicados.containsAnioHistorial(busqueda))        
                    .forEach(tesisAux -> modelTemp1.addRow(getRow(tesisAux,2)));
                      return modelTemp1;
                }
                default -> {
                    listaTesis.stream()
                    .filter(TesisPredicados.containsAnio(busqueda))        
                    .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,0)));
                         return modelTemp;
                }
            }     
                            
        }
        return modelTemp;
    }
    /**
     * 
     */
    public DefaultTableModel tesisHistorial(){
        Tabla modelTemp = new Tabla(columTesisConsult,0); 
         if(listaTesis != null && !listaTesis.isEmpty()){
             listaTesis.stream()
                     .filter(TesisPredicados.numberConsult())
                     .forEach(tesisAux -> modelTemp.addRow(getRow(tesisAux,2)));
         }
         return modelTemp;
    }
    /**
     * @param tesisAux Inserta una tesis a una fila de algún modelo de tabla
     * @return retorna un arreglo de objects.
     */
    private Object[] getRow(Object obj, int encabezados){
         switch(encabezados){
             case 0 ->{
                 Tesis tesisAux = (Tesis)obj;
                  String nombreAutor = new Estudiante(tesisAux.getNumero_control()).toString();
                     return new Object[]{
                                 tesisAux.getIdTesis(),
                                nombreAutor,
                                tesisAux.getTitulo_tesis(),
                                tesisAux.getAnio(),
                                tesisAux.estadoTesis()};                 
             }
             case 1 -> {
                 Tesis tesisAux = (Tesis)obj;
                 String nombreAutor = new Estudiante(tesisAux.getNumero_control()).toString();
                     return new Object[]{
                                 tesisAux.getIdTesis(),
                                nombreAutor,
                                tesisAux.getTitulo_tesis(),
                                tesisAux.getAnio()};   
             }
             case 2 -> {
                 Tesis tesisAux = (Tesis)obj;
                 String nombreAutor = new Estudiante(tesisAux.getNumero_control()).toString();
                     return new Object[]{
                                tesisAux.getIdTesis(),
                                nombreAutor,
                                tesisAux.getTitulo_tesis(),
                                tesisAux.getAnio(),
                                tesisAux.numbersConsults()};
             }
             case 3 -> {
                 Adeudo adeudoAux = (Adeudo)obj;
                 Tesis tesisAux = new Tesis(adeudoAux.getIdTesis());
                  Devolucion devolucionTemp = new Devolucion(adeudoAux.getNumero_prestamo());
                  
                   int usbOcupadas = adeudoAux.getUsb() - devolucionTemp.getUsb();
                   int cdOcupadas = adeudoAux.getCd() - devolucionTemp.getCd();            
                   int tomoOcupadas = adeudoAux.getTomo() - devolucionTemp.getTomo();
                   
                   String cadUnidades = "";
                   if(usbOcupadas > 0) cadUnidades += "USB ";
                   if(cdOcupadas > 0) cadUnidades += "CD ";
                   if(tomoOcupadas > 0) cadUnidades += "TOMO";
                   
                   
                 return new Object[]{
                     adeudoAux.getNumero_prestamo(),
                     tesisAux.getIdTesis(),
                     tesisAux.getTitulo_tesis(),
                     cadUnidades
                 };
             }
             default ->{
                     Tesis tesisAux = (Tesis)obj;
                     String nombreAutor = new Estudiante(tesisAux.getNumero_control()).toString();
                     return new Object[]{
                                 tesisAux.getIdTesis(),
                                nombreAutor,
                                tesisAux.getTitulo_tesis(),
                                tesisAux.getAnio(),
                                tesisAux.estadoTesis()};   
                     
             }
         }
       
    }
    
    
    
     public class TesisPredicados {
         
         public static Predicate<Tesis> containsAutor(String busqueda){
             return (Tesis tesisAux) -> {
                 Estudiante estudianteTemp = new Estudiante(tesisAux.getNumero_control());
                return estudianteTemp.toString().toUpperCase().contains(busqueda.toUpperCase());
            };
         }
         public static Predicate<Tesis> containsAutorHistorial(String busqueda){
             return (Tesis tesisAux) -> {
                 Estudiante estudianteTemp = new Estudiante(tesisAux.getNumero_control());
                return tesisAux.numbersConsults() > 0 && estudianteTemp.toString().toUpperCase().contains(busqueda.toUpperCase());
            };
         }
         public static Predicate<Tesis> containsTitulo(String busqueda){
             return (Tesis tesisAux) -> {                 
                return tesisAux.getTitulo_tesis().toUpperCase().contains(busqueda.toUpperCase());
            };
         }
         public static Predicate<Tesis> containsTituloHistorial(String busqueda){
             return (Tesis tesisAux) -> {                 
                return tesisAux.numbersConsults() > 0 && tesisAux.getTitulo_tesis().toUpperCase().contains(busqueda.toUpperCase());
            };
         }
         public static Predicate<Tesis> containsIdtesis(String busqueda){
             return (Tesis tesisAux) -> {                 
                return tesisAux.getIdTesis().toUpperCase().contains(busqueda.toUpperCase());
            };
         }
         public static Predicate<Tesis> containsIdtesisHistorial(String busqueda){
             return (Tesis tesisAux) -> {                 
                return tesisAux.numbersConsults() > 0 && tesisAux.getIdTesis().toUpperCase().contains(busqueda.toUpperCase());
            };
         }
         public static Predicate<Tesis> containsAnio(int busqueda){
             return (Tesis tesisAux) -> {                 
                return busqueda == tesisAux.getAnio();
            };
         }
         public static Predicate<Tesis> containsAnioHistorial(int busqueda){
             return (Tesis tesisAux) -> {                 
                return tesisAux.numbersConsults() > 0 && busqueda == tesisAux.getAnio();
            };
         }
         public static Predicate<Tesis> numberConsult(){
             return (Tesis tesisAux) -> {                 
                return tesisAux.numbersConsults() > 0;
            };
         }
         
     }
    
    
}
