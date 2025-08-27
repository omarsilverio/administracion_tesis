/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.maestria.Vista.Formularios;

import com.mycompany.maestria.Controlador.ControllerEstudiante;
import com.mycompany.maestria.Controlador.ControllerTesis;
import com.mycompany.maestria.Modelo.Almacenamiento;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Tesis;
import com.mycompany.maestria.Validaciones.ValidacionEntradas;
import com.mycompany.maestria.Validaciones.ValidarAutor;
import com.mycompany.maestria.Vista.Colores.Colores;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author omarsilverio
 */
public class FormularioTesis extends javax.swing.JDialog {

    /**
     * Creates new form FormularioTesis
     */
    private ControllerTesis ct;
    //0 para agregar, 1 para ver la información de una tesis y 2 para actualizar una tesis
    private int STATE = 0;   
    private JTable tablaTesis;
    
    public FormularioTesis(JDialog parent) {
        super(parent);        
        initComponents();        
        btnGuardar.setVisible(false);
        btnAgregarTesis.setEnabled(false);
        letreroCampoVacio.setVisible(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor, txtApellidoPAutor, txtApellidoMAutor);   
        ValidacionEntradas.addValidatorNumber(txtAnioTesis);
    }
    public FormularioTesis(JDialog parent, JTable tablaTesis, ControllerTesis ct) {
        super(parent); 
        this.ct = ct;
        this.tablaTesis = tablaTesis;
        initComponents();        
        btnGuardar.setVisible(false);
        btnAgregarTesis.setEnabled(false);
        letreroCampoVacio.setVisible(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor, txtApellidoPAutor, txtApellidoMAutor);   
        ValidacionEntradas.addValidatorNumber(txtAnioTesis);
    }
        
    /**
     * Rellena las entradas con la información del estudiante
     */
    private void rellenarCamposEstudiante(Estudiante auxEstudiante){
        int status = auxEstudiante.getStatus();
        txtNumeroControl.setText(auxEstudiante.getNumero_control());
        txtNombreAutor.setText(auxEstudiante.getNombre());
        txtApellidoPAutor.setText(auxEstudiante.getApellido_paterno());
        txtApellidoMAutor.setText(auxEstudiante.getApellido_materno());
        txtGeneracion.setText(auxEstudiante.getGeneracion());
        cmbStatus.setSelectedIndex(status);
    }
    /**
     * Rellena las entradas con la información de la tesis
     */
    private void rellenarCamposTesis(Tesis tesisAux, Almacenamiento almaceAux){
        txtTituloTesis.setText(tesisAux.getTitulo_tesis());
        txtAnioTesis.setText(tesisAux.getAnio() + "");        
        cantCD.setSelectedIndex(almaceAux.getCd());
        cantTomo.setSelectedIndex(almaceAux.getTomo());
        cantUsb.setSelectedIndex(almaceAux.getUsb());
    }
    /**
     * Activa o desactiva las entradas del estudiante
     */
    private void setEnabledEntradasEstudiante(boolean enable){       
        txtNombreAutor.setEditable(enable);
        txtApellidoPAutor.setEditable(enable);
        txtApellidoMAutor.setEditable(enable);
        txtGeneracion.setEditable(enable);
        cmbStatus.setEnabled(enable);
    }
    /**
     * Activa o desactiva las entradas de la informacion de tesis
     */
    private void setEnabledEntradasTesis(boolean enable){
       txtTituloTesis.setEditable(enable);
       txtAnioTesis.setEditable(enable);             
       cantCD.setEnabled(enable);
       cantTomo.setEnabled(enable);
       cantUsb.setEnabled(enable);      
       
    }
    /**
     * Actualiza la tabla cuando ocurre alguna actualización
     */
    private void actualizarUpdateTabla(Tesis tesisAux){
       if(tablaTesis != null){
           int rowSelected = tablaTesis.getSelectedRow(); 
           Estudiante autor = new Estudiante(tesisAux.getNumero_control());
           tablaTesis.setValueAt(tesisAux.getIdTesis(), rowSelected, 0);
           tablaTesis.setValueAt(autor.toString(), rowSelected, 1);
           tablaTesis.setValueAt(tesisAux.getTitulo_tesis(), rowSelected, 2);
           tablaTesis.setValueAt(tesisAux.getAnio(), rowSelected, 3);
           tablaTesis.setValueAt(tesisAux.estadoTesis(), rowSelected, 4);
        }
   }
    /**
     * Establece si el formulario es solo para lectura
     */
    public void justRead(Estudiante e, Tesis t, Almacenamiento a){
        letreroCampoObligatorio.setText("Agregado el " + t.getFecha_registro());       
        rellenarCamposEstudiante(e);
        rellenarCamposTesis(t, a);
        setEnabledEntradasEstudiante(false);
        setEnabledEntradasTesis(false);
        btnGuardar.setVisible(false);
        btnAgregarTesis.setVisible(false);
        txtNumeroControl.setEditable(false);
        letreroCampoVacio.setVisible(false);
        letreroSubtitulo.setText("Visualiza Una Tesis");
        STATE = 1;
    }
    /**
     * Establece si el formulario es para edición
     */
    public void justEdit(Estudiante e, Tesis t, Almacenamiento a){
        letreroCampoObligatorio.setText("Puedes modificar sólo la información de la tesis");        
        rellenarCamposEstudiante(e);
        rellenarCamposTesis(t, a);
        setEnabledEntradasEstudiante(false);
        setEnabledEntradasTesis(true);        
        btnAgregarTesis.setVisible(false);
        btnGuardar.setVisible(true);
        txtNumeroControl.setEnabled(false);
        letreroSubtitulo.setText("Edita una Tesis");
        STATE = 2;
    }
    /**
     * Recupera los datos de entrada del estudiante
     */
    private Estudiante getEstudiante(){                
        String numero_control = txtNumeroControl.getText().toUpperCase();         
        String nombreEstudiante = txtNombreAutor.getText();
        String primerApellido = txtApellidoPAutor.getText();
        String segundoApellido = txtApellidoMAutor.getText();
        String generacion = txtGeneracion.getText();
        int status = cmbStatus.getSelectedIndex();
        return new Estudiante(numero_control, nombreEstudiante, primerApellido, segundoApellido,status,generacion);        
    }
     /**
     * Recupera los datos de entrada de la tesis
     */
    private Tesis getTesis(){    
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha_actual = dtf.format(LocalDateTime.now());
        String numero_control = txtNumeroControl.getText().toUpperCase();  
        String idTesis = "TE-" + numero_control;
        String tituloTesis = txtTituloTesis.getText();
        int anio = Integer.parseInt(txtAnioTesis.getText());        
        return new Tesis(idTesis,tituloTesis,anio,numero_control,fecha_actual);
    }
    /**
     * Recupera los datos de entrada del almacenamiento
     */
    private Almacenamiento getAlmacenamiento(Tesis temp){ 
        int cd = cantCD.getSelectedIndex();
        int tomo = cantTomo.getSelectedIndex();
        int usb = cantUsb.getSelectedIndex();
        return new Almacenamiento(temp.getIdTesis(), usb, cd, tomo);        
    }  
   
    /**
     * Comprueba si los datos pueden agregarse
     */
    private boolean comprobarEntradas(){         
        JTextField temp = ValidacionEntradas.getJTextFieldEmpty(
                txtNumeroControl, txtNombreAutor,txtApellidoPAutor,txtApellidoMAutor
               ,txtAnioTesis,txtGeneracion
        );   
        if(temp != null){ 
             temp.requestFocus();
             Alertas.mensajeAdvertencia(this, "¡No puedes dejar campos vacíos!");
             return false;
        }
        if(txtTituloTesis.getText().isEmpty()){
            txtTituloTesis.requestFocus();
            Alertas.mensajeAdvertencia(this, "¡No puedes dejar campos vacíos!");
            return false;
        }
        if(cmbStatus.getSelectedIndex() < 1){
            cmbStatus.requestFocus();
            Alertas.mensajeAdvertencia(this, "¡Seleccione el status del alumno!");
             return false;            
        }       
        if(!ValidacionEntradas.isAlmacenamientoValid(cantCD,cantTomo,cantUsb)){
            Alertas.mensajeAdvertencia(this, "¡El almacenamiento es incorrecto!");
            return false; 
        }
        return true;        
    }   
    /**
     * Actualiza la información de una tesis
     */
    private void actualizarRegistro(){
        if(comprobarEntradas()){            
            Tesis tesisAux = getTesis();
            Almacenamiento almacenamientoAux = getAlmacenamiento(tesisAux);
            if(ct.updateTesis(tesisAux, almacenamientoAux,this)){
                actualizarUpdateTabla(tesisAux);
                this.dispose();
            }                        
        }        
    }
    /*
    * Mustra un mensaje de error con un borde rojo en la entrada numero de control
    */
    private void setErrorNumeroControl(String mensaje){
         btnAgregarTesis.setEnabled(false);
         txtNumeroControl.setBorder(new LineBorder(Color.RED));
         letreroCampoVacio.setText(mensaje);
         letreroCampoVacio.setVisible(true);
    }
    /**
     * Comprueba que el numero de control este correcto
     */
    private void comprobarNumeroControl(){
        String numero_control = txtNumeroControl.getText();    
        if(numero_control.isEmpty()){
           setErrorNumeroControl("¡Campo vacío!");
           return;
        }
        boolean isValidNC = ValidarAutor.validateNumeroControl(numero_control);        
        if(isValidNC){            
            if(Estudiante.isRegister(numero_control)){
                Estudiante estudianteAux = new Estudiante(numero_control);
                if(!estudianteAux.hadTesis()){
                    rellenarCamposEstudiante(estudianteAux);                     
                    numeroControlCorrecto(numero_control);
                    txtTituloTesis.requestFocus();
                    txtTituloTesis.selectAll();
                    setEnabledEntradasEstudiante(false);
                    return;
                }else{
                    setEnabledEntradasEstudiante(true);
                    setErrorNumeroControl("¡Tesis Registrada!");
                    txtNumeroControl.selectAll();  
                    return;
                }
                
             } else{
                setEnabledEntradasEstudiante(true);
                numeroControlCorrecto(numero_control);
                return;                
            }                    
        }  
        setEnabledEntradasEstudiante(true);
        setErrorNumeroControl("¡Número de control incorrecto!");
    }
    
    private void numeroControlCorrecto(String numero_control){
        txtNumeroControl.setBorder(new LineBorder(new Color(91,218,28))); //color RGB verde
        txtGeneracion.setText(ControllerEstudiante.getGeneracion(numero_control));
        letreroCampoVacio.setVisible(false);
        btnAgregarTesis.setEnabled(true);
    }
    private void actualizarAddTabla(Tesis tesisAux, Estudiante estudianteAux, Almacenamiento almacenamientoAux){        
        if(ct != null){
            if(ct.addTesisList(tesisAux, almacenamientoAux, this)){
                if(tablaTesis != null){
                    ((DefaultTableModel)(tablaTesis.getModel())).addRow(new Object[]{
                    tesisAux.getIdTesis(),
                    estudianteAux.toString(),
                    tesisAux.getTitulo_tesis(),
                    tesisAux.getAnio(),
                    tesisAux.estadoTesis()
                     }); 
                    int posicion = tablaTesis.getRowCount() - 1;
                    tablaTesis.getSelectionModel().setSelectionInterval(posicion, posicion);
                }
            }
        }
    }
    
    /**
     * Cierra la ventana
     */
    public void cerrarVentana(){
        boolean val1 = ValidacionEntradas.isEmptyAll(
                 txtNumeroControl, txtNombreAutor, txtApellidoPAutor,
                 txtApellidoMAutor, txtAnioTesis,txtGeneracion);
        if(val1 || STATE == 1){
            this.dispose();
            return;
        }
        else
            if(Alertas.cerrarModal(this))
                this.dispose();
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);            
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        areaFormulario = new javax.swing.JPanel();
        areaInstrucciones = new javax.swing.JPanel();
        letreroCampoObligatorio = new javax.swing.JLabel();
        areaAutor = new javax.swing.JPanel();
        txtNombreAutor = new javax.swing.JTextField();
        txtApellidoMAutor = new javax.swing.JTextField();
        txtApellidoPAutor = new javax.swing.JTextField();
        letreroNombre = new javax.swing.JLabel();
        letreroPrimerApellido = new javax.swing.JLabel();
        letreroSegundoApellido = new javax.swing.JLabel();
        letreroNumeroControl = new javax.swing.JLabel();
        txtNumeroControl = new javax.swing.JTextField();
        letreroCampoVacio = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtGeneracion = new javax.swing.JTextField();
        areaTesis = new javax.swing.JPanel();
        letreroTituloTesis = new javax.swing.JLabel();
        letreroAnio = new javax.swing.JLabel();
        txtAnioTesis = new javax.swing.JTextField();
        cantCD = new javax.swing.JComboBox<>();
        cantTomo = new javax.swing.JComboBox<>();
        cantUsb = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTituloTesis = new javax.swing.JTextArea();
        panelBotones = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnAgregarTesis = new javax.swing.JButton();
        letreroSubtitulo = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        areaFormulario.setLayout(new javax.swing.BoxLayout(areaFormulario, javax.swing.BoxLayout.Y_AXIS));

        areaInstrucciones.setMaximumSize(new java.awt.Dimension(672, 26));
        areaInstrucciones.setPreferredSize(new java.awt.Dimension(672, 26));
        areaInstrucciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        letreroCampoObligatorio.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        letreroCampoObligatorio.setForeground(new java.awt.Color(255, 0, 0));
        letreroCampoObligatorio.setText("Todos los campos son obligatorios *");
        areaInstrucciones.add(letreroCampoObligatorio);

        areaFormulario.add(areaInstrucciones);

        areaAutor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMACIÓN DEL ESTUDIANTE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        areaAutor.setMaximumSize(new java.awt.Dimension(900, 300));
        areaAutor.setMinimumSize(new java.awt.Dimension(640, 290));
        areaAutor.setPreferredSize(new java.awt.Dimension(640, 300));
        areaAutor.setLayout(new java.awt.GridBagLayout());

        txtNombreAutor.setMaximumSize(new java.awt.Dimension(11, 26));
        txtNombreAutor.setMinimumSize(new java.awt.Dimension(80, 26));
        txtNombreAutor.setPreferredSize(new java.awt.Dimension(35, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 331;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 6, 0, 97);
        areaAutor.add(txtNombreAutor, gridBagConstraints);

        txtApellidoMAutor.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 97);
        areaAutor.add(txtApellidoMAutor, gridBagConstraints);

        txtApellidoPAutor.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 97);
        areaAutor.add(txtApellidoPAutor, gridBagConstraints);

        letreroNombre.setText("* Nombre(s):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 79, 0, 0);
        areaAutor.add(letreroNombre, gridBagConstraints);

        letreroPrimerApellido.setText("* Primer Apellido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 48, 0, 0);
        areaAutor.add(letreroPrimerApellido, gridBagConstraints);

        letreroSegundoApellido.setText("* Segundo apellido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 36, 0, 0);
        areaAutor.add(letreroSegundoApellido, gridBagConstraints);

        letreroNumeroControl.setText(" * Número de control:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 22, 0, 0);
        areaAutor.add(letreroNumeroControl, gridBagConstraints);

        txtNumeroControl.setMaximumSize(new java.awt.Dimension(80, 35));
        txtNumeroControl.setMinimumSize(new java.awt.Dimension(80, 35));
        txtNumeroControl.setPreferredSize(new java.awt.Dimension(80, 35));
        txtNumeroControl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumeroControlFocusLost(evt);
            }
        });
        txtNumeroControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumeroControlKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroControlKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 0);
        areaAutor.add(txtNumeroControl, gridBagConstraints);

        letreroCampoVacio.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        letreroCampoVacio.setForeground(new java.awt.Color(255, 0, 0));
        letreroCampoVacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letreroCampoVacio.setText("¡Número de control incorrecto!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 6, 0, 97);
        areaAutor.add(letreroCampoVacio, gridBagConstraints);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----Seleccione una opción----", "Estudiante", "Titulado", "Inactivo" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 40, 0);
        areaAutor.add(cmbStatus, gridBagConstraints);

        jLabel4.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 115, 0, 0);
        areaAutor.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Generación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 84, 0, 0);
        areaAutor.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 97);
        areaAutor.add(txtGeneracion, gridBagConstraints);

        areaFormulario.add(areaAutor);

        areaTesis.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMACIÓN DE LA TESIS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        areaTesis.setMaximumSize(new java.awt.Dimension(900, 250));
        areaTesis.setMinimumSize(new java.awt.Dimension(640, 250));
        areaTesis.setPreferredSize(new java.awt.Dimension(640, 300));
        areaTesis.setLayout(new java.awt.GridBagLayout());

        letreroTituloTesis.setText("* Título Tesis:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(48, 54, 0, 0);
        areaTesis.add(letreroTituloTesis, gridBagConstraints);

        letreroAnio.setText("* Año:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 103, 0, 0);
        areaTesis.add(letreroAnio, gridBagConstraints);

        txtAnioTesis.setMaximumSize(new java.awt.Dimension(81, 32));
        txtAnioTesis.setPreferredSize(new java.awt.Dimension(81, 32));
        txtAnioTesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnioTesisKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        areaTesis.add(txtAnioTesis, gridBagConstraints);

        cantCD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 11, 68, 0);
        areaTesis.add(cantCD, gridBagConstraints);

        cantTomo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 68, 111);
        areaTesis.add(cantTomo, gridBagConstraints);

        cantUsb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        cantUsb.setSelectedIndex(1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 68, 0);
        areaTesis.add(cantUsb, gridBagConstraints);

        jLabel1.setText("CD:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 119, 0, 0);
        areaTesis.add(jLabel1, gridBagConstraints);

        jLabel2.setText("TOMO:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 12, 0, 0);
        areaTesis.add(jLabel2, gridBagConstraints);

        jLabel3.setText("USB:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 0);
        areaTesis.add(jLabel3, gridBagConstraints);

        txtTituloTesis.setColumns(20);
        txtTituloTesis.setRows(5);
        txtTituloTesis.setMaximumSize(new java.awt.Dimension(240, 70));
        txtTituloTesis.setMinimumSize(new java.awt.Dimension(240, 70));
        txtTituloTesis.setPreferredSize(new java.awt.Dimension(240, 68));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 171;
        gridBagConstraints.ipady = -25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(48, 6, 0, 111);
        areaTesis.add(txtTituloTesis, gridBagConstraints);

        areaFormulario.add(areaTesis);

        panelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBotones.setMaximumSize(new java.awt.Dimension(900, 70));
        panelBotones.setMinimumSize(new java.awt.Dimension(308, 43));
        panelBotones.setPreferredSize(new java.awt.Dimension(308, 43));

        btnCancelar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        panelBotones.add(btnCancelar);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelBotones.add(btnGuardar);

        btnAgregarTesis.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btnAgregarTesis.setText("Agregar");
        btnAgregarTesis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTesisActionPerformed(evt);
            }
        });
        panelBotones.add(btnAgregarTesis);

        areaFormulario.add(panelBotones);

        getContentPane().add(areaFormulario, java.awt.BorderLayout.CENTER);

        letreroSubtitulo.setBackground(Colores.colorPrimary);
        letreroSubtitulo.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        letreroSubtitulo.setForeground(new java.awt.Color(255, 255, 255));
        letreroSubtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letreroSubtitulo.setText("Agrega una nueva tesis");
        letreroSubtitulo.setMaximumSize(new java.awt.Dimension(672, 22));
        letreroSubtitulo.setMinimumSize(new java.awt.Dimension(672, 22));
        letreroSubtitulo.setOpaque(true);
        letreroSubtitulo.setPreferredSize(new java.awt.Dimension(672, 22));
        getContentPane().add(letreroSubtitulo, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarTesisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTesisActionPerformed
        if(comprobarEntradas()){
            Estudiante estudianteAux = getEstudiante();            
            Tesis tesisAux = getTesis();            
            Almacenamiento almAux = getAlmacenamiento(tesisAux);
            
            if(estudianteAux != null && tesisAux != null && almAux != null){
                if(!estudianteAux.isRegister()){
                    ControllerEstudiante.addEstudiante(estudianteAux, this);
                }
                actualizarAddTabla(tesisAux, estudianteAux,almAux);
                this.dispose();
            }else{
                Alertas.mensajeError(this, "No se pudo recuperar las entradas");

            }
        }
    }//GEN-LAST:event_btnAgregarTesisActionPerformed

    private void txtNumeroControlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroControlKeyReleased
        if(STATE == 0)
            comprobarNumeroControl();
    }//GEN-LAST:event_txtNumeroControlKeyReleased

    private void txtNumeroControlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroControlKeyTyped
        String numero_control = txtNumeroControl.getText();
        if(numero_control.length() == 9){           
           evt.consume();
       } 
    }//GEN-LAST:event_txtNumeroControlKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarVentana();
    }//GEN-LAST:event_formWindowClosing

    private void txtNumeroControlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroControlFocusLost
        if(STATE == 0)
            comprobarNumeroControl();
    }//GEN-LAST:event_txtNumeroControlFocusLost
    
    private void txtAnioTesisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnioTesisKeyTyped
       String numero_control = txtAnioTesis.getText();
        if(numero_control.length() == 4){           
           evt.consume();
       } 
    }//GEN-LAST:event_txtAnioTesisKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       actualizarRegistro();
    }//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaAutor;
    private javax.swing.JPanel areaFormulario;
    private javax.swing.JPanel areaInstrucciones;
    private javax.swing.JPanel areaTesis;
    private javax.swing.JButton btnAgregarTesis;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cantCD;
    private javax.swing.JComboBox<String> cantTomo;
    private javax.swing.JComboBox<String> cantUsb;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel letreroAnio;
    private javax.swing.JLabel letreroCampoObligatorio;
    private javax.swing.JLabel letreroCampoVacio;
    private javax.swing.JLabel letreroNombre;
    private javax.swing.JLabel letreroNumeroControl;
    private javax.swing.JLabel letreroPrimerApellido;
    private javax.swing.JLabel letreroSegundoApellido;
    private javax.swing.JLabel letreroSubtitulo;
    private javax.swing.JLabel letreroTituloTesis;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JTextField txtAnioTesis;
    private javax.swing.JTextField txtApellidoMAutor;
    private javax.swing.JTextField txtApellidoPAutor;
    private javax.swing.JTextField txtGeneracion;
    private javax.swing.JTextField txtNombreAutor;
    private javax.swing.JTextField txtNumeroControl;
    private javax.swing.JTextArea txtTituloTesis;
    // End of variables declaration//GEN-END:variables
}
