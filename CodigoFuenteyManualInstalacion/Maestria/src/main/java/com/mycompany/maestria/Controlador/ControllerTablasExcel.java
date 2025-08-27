/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;


import com.mycompany.maestria.Modelo.Almacenamiento;
import com.mycompany.maestria.Modelo.Busquedas.ModelosEstudiantes;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Prestamo;
import com.mycompany.maestria.Modelo.Tesis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 *
 * @author omarsilverio
 */
public class ControllerTablasExcel { 
    private static String headerTesis[] = new String[]{
                "idtesis",
                "Autor",
                "Titulo",
                "Año",
                "usb", "cd","tomo"
    }; 
    private static String headerAdeudos[] = new String[]{
        "Numero Control",
        "Nombre",
        "Primer Apellido",
        "Segundo Apellido",
        "Cant. Adeudos"
    };
    private static String headerEstudiantes[] = new String[]{
        "Numero Control",
        "Nombre",
        "Primer Apellido",
        "Segundo Apellido",
        "Generación"
    };
    private static String headerPrestamosTesis[] = new String[]{
        "Numero Prestamo",
        "Numero de control",
        "Nombre",
        "idTesis",
        "Titulo tesis",
        "Autor",
        "Cant. usb",
        "Cant. cd",
        "Cant. tomo",
        "fecha prestamo"
    };
    
    /**
     * Crea un archivo de excel con las listas de todos los estudiantes
     * @param path donde se va a guardar
     * @param categoria catagoria de alumnos que va a agregar 
     * @throws java.lang.Exception
     */
    public void getListEstudiantesExcel(File path, String categoria) throws Exception{        
        try (FileOutputStream excelTemp = new FileOutputStream(path.getPath() + "/listaAlumnos"+categoria+"s.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = agregarEncabezados(headerEstudiantes,wb,"lista Alumnos " + categoria);
            
            ModelosEstudiantes me = new ModelosEstudiantes(Estudiante.getEstudiantes());
            
            List<Estudiante> estudiantesTemp = me.getListAlumnos(categoria);
            AtomicInteger renglon = new AtomicInteger(); 
            
            estudiantesTemp.stream()
                    .forEach(estudianteAux ->{filaExcel(estudianteAux, sheet,0,renglon);});
            
            wb.write(excelTemp);
        }
    }
    /**
     * Crea un archivo de excel con las listas de todos las tesis
     */
    public void getListTesisExcel(File path) throws Exception{
        FileOutputStream excelTemp = new FileOutputStream(path.getPath() + "/listaTesis.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = agregarEncabezados(headerTesis,wb,"lista Tesis");        
         
        List<Tesis> listaTesis = Tesis.getListTesis();  
        AtomicInteger renglon = new AtomicInteger(); 
        listaTesis.stream()
                .forEach(tesisAux ->{filaExcel(tesisAux, sheet,2,renglon);});  
        
        wb.write(excelTemp);
        excelTemp.close();
         
    }
    /**
     * Crea un archivo de excel con las listas de todos las tesis
     */
    public void getListAdeudosAlumnosExcel(File path) throws Exception{
        FileOutputStream excelTemp = new FileOutputStream(path.getPath() + "/listaAdeudosEstudiante.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = agregarEncabezados(headerAdeudos,wb,"Lista Adeudos");    
        
        ModelosEstudiantes me = new ModelosEstudiantes(Estudiante.getEstudiantes());
        List<Estudiante> listaEstudiantesAdeudos = me.getListAlumnosAdeudos(); 
        AtomicInteger renglon = new AtomicInteger(); 
        listaEstudiantesAdeudos.stream()
                .forEach(tesisAux ->{filaExcel(tesisAux, sheet,1,renglon);});  
        
        wb.write(excelTemp);
        excelTemp.close();
         
    }
     /**
     * Crea un archivo de excel con las listas de todos las tesis
     */
    public void getPrestamosTesisExcel(File path) throws Exception{
        FileOutputStream excelTemp = new FileOutputStream(path.getPath() + "/listaPrestamosTesis.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = agregarEncabezados(headerPrestamosTesis,wb,"Lista Prestamos");    
        List<Prestamo> listaPrestamos = Prestamo.getPrestamos();
        
        AtomicInteger renglon = new AtomicInteger(); 
       listaPrestamos.stream()
                .forEach(tesisAux ->{filaExcel(tesisAux, sheet,3,renglon);});  
        
        wb.write(excelTemp);
        excelTemp.close();
         
    }
    private void filaExcel(Object obj,XSSFSheet sheet, int select, AtomicInteger renglones){
        //AtomicInteger renglon = new AtomicInteger();         
        String [] fila = obtenerFila(obj,select);
        short celdaS = (short)renglones.incrementAndGet();                   
        XSSFRow filaExcel = sheet.createRow(celdaS);
                   AtomicInteger celdaC = new AtomicInteger(-1); 
                   Arrays.stream(fila).forEach(valor ->{
                       XSSFCell celda = filaExcel.createCell((short)celdaC.incrementAndGet()); 
                       celda.setCellValue(valor);
                    });  
    }   
   
    /**
     * agrega una nueva hoja en excel con los encabezados establecidos
     */
    private XSSFSheet agregarEncabezados(String[] arreTemp, XSSFWorkbook wb, String nombreSheet){
        XSSFSheet sheet = wb.createSheet(nombreSheet); 
        XSSFCellStyle estilo = stiloEncabezados(wb);
         XSSFRow row = sheet.createRow((short)0); 
        //Se agrega los encabezados
         for(int i = 0; i < arreTemp.length; i ++){
            XSSFCell celda = row.createCell((short)i);                        
            celda.setCellValue((String)arreTemp[i]);
            celda.setCellStyle(estilo);
        }     
         return sheet;        
    }
    
    private String[] obtenerFila(Object obj, int selec){ 
        switch(selec){
            case 0 -> {
                Estudiante estudianteAux = (Estudiante)obj;
                return new String[]{
                estudianteAux.getNumero_control(),
                estudianteAux.getNombre(), 
                estudianteAux.getApellido_paterno(),
                estudianteAux.getApellido_materno(),
                estudianteAux.getGeneracion()};
            }
            case 1 -> {
                Estudiante estudianteAux = (Estudiante)obj;
                return new String[]{
                estudianteAux.getNumero_control(),
                estudianteAux.getNombre(), 
                estudianteAux.getApellido_paterno(),
                estudianteAux.getApellido_materno(),
                estudianteAux.adeudos() +""};
            }
            case 2 -> {
                Tesis tesisAux = (Tesis)obj;
                Estudiante estudianteAux = new Estudiante(tesisAux.getNumero_control());
                Almacenamiento almacenamientoAux = new Almacenamiento(tesisAux.getIdTesis());
                
                return new String[]{
                tesisAux.getIdTesis(),
                estudianteAux.toString(),
                tesisAux.getTitulo_tesis(),
                tesisAux.getAnio() + "",
                almacenamientoAux.getUsb() + " unidades",
                almacenamientoAux.getCd() + " unidades",
                almacenamientoAux.getTomo() + " unidades"};
            }
            case 3->{
                Prestamo prestamoAux = (Prestamo)obj;
                Estudiante estudianteAux = new Estudiante(prestamoAux.getNumero_control());
                Tesis tesisAux = new Tesis(prestamoAux.getIdTesis());
                Estudiante autorAux = new Estudiante(tesisAux.getNumero_control());
                
                return new String[]{
                    prestamoAux.getNumero_prestamo() + "",
                    prestamoAux.getNumero_control(),
                    estudianteAux.toString(),
                    tesisAux.getIdTesis(),
                    tesisAux.getTitulo_tesis(),
                    autorAux.toString(),
                    prestamoAux.getUsb()+ "",
                    prestamoAux.getCd()+ "",
                    prestamoAux.getTomo()+ "",
                    prestamoAux.getFecha_prestamo()
                };
            }
        };
        return null;        
    }  

    
    
    /**
     * Diseño de la tabla
     */
    
    public XSSFCellStyle stiloEncabezados(XSSFWorkbook wb){
        //fuente de la celda
        XSSFFont fuente = wb.createFont();
        fuente.setFontHeightInPoints((short)11);
        fuente.setFontName("Arial");
        fuente.setBold(true);
        //estilo de la celda
        XSSFCellStyle estiloCelda = wb.createCellStyle();
        estiloCelda.setWrapText(true);                
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        estiloCelda.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloCelda.setFont(fuente);
        return estiloCelda;        
    }
    
    
}
