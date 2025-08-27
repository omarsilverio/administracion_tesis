/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Controlador;

import com.mycompany.maestria.Modelo.AlmacenamientoPrestamo;
import com.mycompany.maestria.Modelo.Busquedas.ModelosEstudiantes;
import com.mycompany.maestria.Modelo.Busquedas.ModelosPrestamos;
import com.mycompany.maestria.Modelo.Devolucion;

import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Prestamo;
import com.mycompany.maestria.Modelo.Tesis;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import com.mycompany.maestria.Vista.Componentes.AlumnosAdeudos;
import com.mycompany.maestria.Vista.Formularios.FormularioAdeudo;
import com.mycompany.maestria.Vista.Formularios.FormularioPrestamo;
import com.mycompany.maestria.Vista.Formularios.FormularioTipoPrestamo;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author omarsilverio
 */
public class ControllerPrestamo { 
    
    private ModelosEstudiantes me;    
    private final ControllerTablasExcel cte = new ControllerTablasExcel();
    
    
    private List<AlmacenamientoPrestamo> carrito = new ArrayList<AlmacenamientoPrestamo>();
   
    
    
    public ControllerPrestamo(){
        this.me = new ModelosEstudiantes(Estudiante.getEstudiantes());
    }
    
    public ControllerPrestamo(List<Estudiante> me){
        this.me = new ModelosEstudiantes(me);
    }
    
    /**
     * Busqueda por el numero de control de la tesis
     */
    private DefaultTableModel buscarNumeroControlAdeudos(String busqueda){        
        if(!busqueda.isEmpty()){
           DefaultTableModel temp = me.buscarNumeroControlAdeudos(busqueda);
           if(temp != null) return temp;
        }
        return null;
    }
    
    
    /**
     * Actualisa la lista de las tesis
     */
    
    public void actualizarLista(){
        me.actualizarLista();       
    }
    /**
     * Busqueda por el anio
     */
    
    private DefaultTableModel buscarNombreEstudianteAdeudos(String busqueda){        
          if(!busqueda.isEmpty()){
           DefaultTableModel temp = me.buscarNombreAdeudos(busqueda);
           if(temp != null) return temp;
        }
        return null;
    }
    
    /**
     * @return regresa un modelo con todos los estudiantes con adeudos
     */
    public DefaultTableModel allEstudiantesAdeudos(){
        DefaultTableModel temp = me.getModelAlumnoAdeudo();
        if(temp != null) return temp;        
        return null;
    }
    
    /**
     * @return regresa un modelo con todos los estudiantes con adeudos
     */
    public static DefaultTableModel estudiantesAdeudos(Tesis tesisAux){
        DefaultTableModel temp = ModelosPrestamos.getModelAlumnosAdeudos(tesisAux);
        if(temp != null) return temp;        
        return null;
    }
     /**
     * Formulario de solo lectura
     */
    public static void dialogViewPrestamo(JTable tabla, JDialog parent){
         Tesis tesisAux = ControllerTesis.getTesisSeleccionada(tabla);
         if(tesisAux == null) return;       
                    
         AlumnosAdeudos form = new AlumnosAdeudos(tesisAux,parent);         
         configFormulario(form);
    }
    
    /**
     * Controla las busquedas según sea la selección de busqueda
    */
     public DefaultTableModel busquedas(String busqueda, int select){            
        switch(select){
            case 0 -> {return buscarNumeroControlAdeudos(busqueda);}
            case 1 -> {return  buscarNombreEstudianteAdeudos(busqueda);}            
            default -> {return allEstudiantesAdeudos();}
        } 
     }
     
     /**
     * Abre una ventana para visualizar los adeudos de un estudiante
     */
    public void dialogoViewAdeudo(Estudiante temp, JDialog parent){
        FormularioAdeudo form = new FormularioAdeudo(parent, true, temp);
        configFormulario(form);
        actualizarLista();
    }
   
    /**
     * Abre una ventana para agregar un nuevo prestamo
     */
    public void dialogoAdd(JDialog parent){
        FormularioPrestamo form = new FormularioPrestamo(parent);
        configFormulario(form);
        actualizarLista();
    }
     public void dialogoAdd(JDialog parent, JTable tablaAdeudos){
        carrito = new ArrayList<AlmacenamientoPrestamo>();
        FormularioPrestamo form = new FormularioPrestamo(parent,tablaAdeudos, this);
        configFormulario(form);
        actualizarLista();
    }
    /**
     * Genera un reporte en una tabla de excel
     */
    public void generarReporte(JFileChooser seleccionarCarpeta, JDialog parent){
        int seleccion = seleccionarCarpeta.showSaveDialog(parent);
        if (seleccion == JFileChooser.APPROVE_OPTION){
            File fichero = seleccionarCarpeta.getSelectedFile();
            try{               
               cte.getListTesisExcel(fichero);
               JOptionPane.showMessageDialog(parent, "Los datos se guardarón correctamente");
               
            }catch(Exception e){
                JOptionPane.showMessageDialog(parent, e);
            }
        }
    }
    
     /**
     * @param temp Agrega a un estudiante a la base de datos con los datos de
     * las entradas
     */
    public static boolean addPrestamo(Prestamo prestamoAux, JDialog parent) {
        if (prestamoAux != null) {
            boolean result = prestamoAux.addPrestamoBDD();
            if (result) {
                Alertas.mensajeExito(parent, "¡El registro se actualizó correctamente!");
                return result;
            } else {
                Alertas.mensajeError(parent, "Ocurrió algún error!\n no se pudo agregar en la base de datos");
                return result;
            }
        } else {
            Alertas.mensajeError(parent, "Ocurrió algún error!\n valor nulo");
        }
        return false;
    }
    
    public boolean addPrestamoList(Prestamo prestamoAux, JDialog parent) {
        if (prestamoAux != null) {            
            if (prestamoAux.addPrestamoBDD()) {             
                Alertas.mensajeExito(parent, "¡El registro se actualizó correctamente!");
                return true;
            } else {
                Alertas.mensajeError(parent, "Ocurrió algún error!\n no se pudo agregar en la base de datos");
                return false;
            }
        } else {
                Alertas.mensajeError(parent, "Ocurrió algún error!\n valor nulo");
        }
        return false;
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
    public void generarReporteTesisAdeudos(JFileChooser seleccionarCarpeta, JDialog parent){
        int seleccion = seleccionarCarpeta.showSaveDialog(parent);
        if (seleccion == JFileChooser.APPROVE_OPTION){
            File fichero = seleccionarCarpeta.getSelectedFile();
            try{               
               cte.getPrestamosTesisExcel(fichero);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    /**
     * Genera una grafica con las tesis consultadas segun sea la cantidad
     */
     public DefaultCategoryDataset getDatosGrafica(int etiqueta, int cant){
        DefaultCategoryDataset datosTemp = new DefaultCategoryDataset();  
        List<Tesis> listTemp = Tesis.getListTesis();
        if(listTemp != null){
            listTemp.stream()
                    .sorted(Comparator.comparing(Tesis::numbersConsults).reversed())
                    .limit(cant)
                    .forEach(tesisAux -> addGrafico(datosTemp, tesisAux.getIdTesis(), tesisAux.numbersConsults(), etiqueta));
        }
        return datosTemp;               
    }
     
    public void addGrafico(DefaultCategoryDataset datosTemp,String idTesis, long cant, int etiqueta){
        Tesis tesisAux = new Tesis(idTesis);
        Estudiante estudianteAux = new Estudiante(tesisAux.getNumero_control());
        switch(etiqueta){
            case 0 ->{datosTemp.addValue(cant, "idTesis", idTesis);}
            case 1 ->{datosTemp.addValue(cant, "Autor", estudianteAux.toString());}
            case 2 ->{datosTemp.addValue(cant, "Titulo", tesisAux.getTitulo_tesis());}
        }        
    }
    
    public boolean contains(String idTesis){
        if(carrito != null && !carrito.isEmpty()){            
            List<AlmacenamientoPrestamo>list = 
                    carrito.stream()
                    .filter(almacenamientoTemp -> almacenamientoTemp.getIdTesis().equals(idTesis)).toList();
            
            return !list.isEmpty();
        }
        return false;
    }
    
    private static void agregarItems(JComboBox aux, int cant){
        if(cant <= 0){
            aux.addItem("0");
            aux.setEnabled(false);
            return;
        }
        for(int i = 0; i <= cant; i ++){
                aux.addItem(i);
        }
    }
    /**
     * Configura las entradas para el almacenamiento de una tesis
     */
    public static void configurarComboBox(Prestamo prestamoAux, JComboBox usb, JComboBox cd, JComboBox tomo){     
           
            if(!prestamoAux.hadDevolucion()){
                int usbDisponibles = prestamoAux.getUsb();
                int cdDisponibles = prestamoAux.getCd();            
                int tomoDisponibles = prestamoAux.getTomo();
              
                agregarItems(usb, usbDisponibles);
                agregarItems(cd, cdDisponibles);
                agregarItems(tomo, tomoDisponibles);  
            }else{
                
            }        
        
    }
     /**
     * Configura las entradas para el almacenamiento de una tesis
     */
    public static void getUnidadesDisponibles(Tesis tesisAux, JComboBox usb, JComboBox cd, JComboBox tomo){   
           
            if(tesisAux != null){
                int unidadesDisponibles[] = tesisAux.getUnidadesDisponibles();
                int usbDisponibles = unidadesDisponibles[0];
                int cdDisponibles = unidadesDisponibles[1];            
                int tomoDisponibles = unidadesDisponibles[2];
                
                agregarItems(usb, usbDisponibles);
                agregarItems(cd, cdDisponibles);
                agregarItems(tomo, tomoDisponibles);  
            }else{
                agregarItems(usb, 0);
                agregarItems(cd, 0);
                agregarItems(tomo, 0);  
            }        
        
    }
    /**
     * Configura las unidades de un adeudo
     */
    public static void obtenerUnidadesAlumnoAdeudo(Prestamo prestamoAux, JComboBox usb, JComboBox cd, JComboBox tomo){        
        if(prestamoAux != null){
            Devolucion devolucionTemp = new Devolucion(prestamoAux.getNumero_prestamo());
            int usbOcupadas = prestamoAux.getUsb() - devolucionTemp.getUsb();
            int cdOcupadas = prestamoAux.getCd() - devolucionTemp.getCd();            
            int tomoOcupadas = prestamoAux.getTomo() - devolucionTemp.getTomo();
                
            agregarItems(usb, usbOcupadas);
            agregarItems(cd, cdOcupadas);
            agregarItems(tomo, tomoOcupadas);  
        }else{
            agregarItems(usb, 0);
            agregarItems(cd, 0);
            agregarItems(tomo, 0);  
        }
                 
        
        
    }    
          
     public void dialogoAddCarrito(JDialog parent, JTable tabla, JList<String> lista) {
        Tesis tesisSelec = ControllerTesis.getTesisSeleccionada(tabla);
        if(estadoTesis(tesisSelec)){
            if(tesisSelec != null && !contains(tesisSelec.getIdTesis())){            
                FormularioTipoPrestamo form = new FormularioTipoPrestamo(parent, tesisSelec, this,lista); 
                configFormulario(form);
            } else{
                Alertas.mensajeError(parent, "La tesis ya esta en lista de selección");
            }  
        } else{
            Alertas.mensajeError(parent, "La tesis no esta disponible");
        }  
    }
     public boolean estadoTesis(Tesis temp){        
        int[] arreTemp = temp.getUnidadesDisponibles();
        if(arreTemp[0] <= 0 && arreTemp[1] <= 0 && arreTemp[2] <= 1)
            return false;
        return true;
    }
    /**
     * @return retorna la lista del carrito
     */
    public List<AlmacenamientoPrestamo> getCarrito(){
        return carrito;
    }
     
    /**
     * Agrega una tesis al carrito
     */
    public void addCarrito(AlmacenamientoPrestamo almacenamientoTemp){
        carrito.add(almacenamientoTemp);   
    }
    /**
     * Eliminar del carrito
     */
    public void deleteCarrito(int pos, JList<String> listaCarrito){
        if(carrito != null && !carrito.isEmpty()){
            carrito.remove(pos);
            DefaultListModel modelo = (DefaultListModel)listaCarrito.getModel();            
            modelo.remove(pos);
        }
    }
}
