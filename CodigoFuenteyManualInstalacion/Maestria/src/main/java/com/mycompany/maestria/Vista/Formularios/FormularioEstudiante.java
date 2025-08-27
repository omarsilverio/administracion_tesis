/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.maestria.Vista.Formularios;

import com.mycompany.maestria.Controlador.ControllerEstudiante;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Validaciones.ValidacionEntradas;
import com.mycompany.maestria.Validaciones.ValidarAutor;
import com.mycompany.maestria.Vista.Colores.Colores;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author omarsilverio
 */
public class FormularioEstudiante extends javax.swing.JDialog {   
    private int STATE = 0;  
    private JTable tablaEstudiante;
    private ControllerEstudiante ce;
    
    /**
     * Creates new form FormularioEstudiante
     */
    public FormularioEstudiante(JDialog parent) {
        super(parent);
        initComponents();
        letreroCampoVacio.setVisible(false);
        btnGuardar.setVisible(false);
        btnAgregar.setEnabled(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor,txtApellidoPAutor,txtApellidoMAutor);
    }
    public FormularioEstudiante(JDialog parent, JTable tablaEstudiante) {
        super(parent);
        initComponents();        
        this.tablaEstudiante = tablaEstudiante;
        letreroCampoVacio.setVisible(false);
        btnGuardar.setVisible(false);
        btnAgregar.setEnabled(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor,txtApellidoPAutor,txtApellidoMAutor);
    }
    public FormularioEstudiante(JDialog parent, JTable tablaEstudiante, ControllerEstudiante ce) {
        super(parent);
        initComponents();
        this.ce = ce;
        this.tablaEstudiante = tablaEstudiante;
        letreroCampoVacio.setVisible(false);
        btnGuardar.setVisible(false);
        btnAgregar.setEnabled(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor,txtApellidoPAutor,txtApellidoMAutor);
    }
    /**
     * Establece un formulario para la edición
     * @param e estudiante
     */
    public void justEdit(Estudiante e){        
        rellenarCamposEstudiante(e);
        btnAgregar.setVisible(false);        
        btnGuardar.setVisible(true);
        txtNumeroControl.setEnabled(false);
        letreroConsulta.setText("Edita un Estudiante");
        STATE = 2;
    }
    /**
     * Establece un formulario para lectura
     * @param e estudiante
     */
    public void justRead(Estudiante e){             
        rellenarCamposEstudiante(e);
        btnGuardar.setVisible(false);
        btnAgregar.setVisible(false);
        setEnabledEntradas(false);
        letreroCampoVacio.setVisible(false);
        letreroConsulta.setText("Visualiza un Estudiante");        
        STATE = 1;
    }
    /**
     * Bloquea o desbloquea las entradas para que no sean o no editables
     * @param enable
     */
    private void setEnabledEntradas(boolean enable){
        txtNumeroControl.setEditable(enable);
        txtNombreAutor.setEditable(enable);
        txtApellidoPAutor.setEditable(enable);
        txtApellidoMAutor.setEditable(enable);
        txtGeneracion.setEditable(enable);
        cmbStatus.setEnabled(enable);
    }
    
    /**
     * Comprueba si los datos pueden agregarse
     * @return
     */
    private boolean comprobarEntradas(){         
        JTextField temp = ValidacionEntradas.getJTextFieldEmpty(
                txtNumeroControl, txtNombreAutor,txtApellidoPAutor,
                txtApellidoMAutor, txtGeneracion    
        );        
        if(temp != null){ 
             temp.requestFocus();
             Alertas.mensajeAdvertencia(this, "¡No puedes dejar campos vacíos!");
             return false;
        }    
        if(cmbStatus.getSelectedIndex() < 1){
            Alertas.mensajeAdvertencia(this, "¡Seleccione el status del alumno!");
            return false;
        }             
        return true;
    }
   
     /**
    * Mustra un mensaje de error con un borde rojo en la entrada numero de control
    */
    private void setErrorNumeroControl(String mensaje){
         btnAgregar.setEnabled(false);
         txtNumeroControl.setBorder(new LineBorder(Color.RED));
         letreroCampoVacio.setText(mensaje);
         letreroCampoVacio.setVisible(true);
    }
    /**
     * Rellena solo las entradas con la información del estudiante
     * @param auxEstudiante
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
     * crea un nuevo estudiante con los valores de la estranadas
     */
    private Estudiante getEstudiante(){
        String numero_control = txtNumeroControl.getText().toUpperCase();  
        String nombre = txtNombreAutor.getText();
        String primer_apellido = txtApellidoPAutor.getText();
        String segundo_apellido = txtApellidoMAutor.getText();  
        String generacion = txtGeneracion.getText();
        int status = cmbStatus.getSelectedIndex();
        return new Estudiante(numero_control,nombre,primer_apellido,segundo_apellido,status ,generacion);
    }   
    /**
     * actualiza la tabla de los estudinatez cuando se agrega un nuevo dato
     */
    private void actualizarAddTabla(Estudiante estudianteAux){         
        if(ce != null){
            if(ce.addEstudianteList(estudianteAux, this)){
                if(tablaEstudiante != null){
                    ((DefaultTableModel)(tablaEstudiante.getModel())).addRow(new Object[]{
                    estudianteAux.getNumero_control(),
                    estudianteAux.getNombre(),
                    estudianteAux.getApellido_paterno(),
                    estudianteAux.getApellido_materno()
                    });
                    int posicion = tablaEstudiante.getRowCount() - 1;
                    tablaEstudiante.getSelectionModel().setSelectionInterval(posicion, posicion);
                }
            }           
        }
   }
    /**
     * actualiza la tabla de los estudiantes cuando ocurre alguna actualización
     */
    private void actualizarUpdateTabla(Estudiante estudianteAux){
       if(tablaEstudiante != null){
           int rowSelected = tablaEstudiante.getSelectedRow();           
           tablaEstudiante.setValueAt(estudianteAux.getNumero_control(), rowSelected, 0);
           tablaEstudiante.setValueAt(estudianteAux.getNombre(), rowSelected, 1);
           tablaEstudiante.setValueAt(estudianteAux.getApellido_paterno(), rowSelected, 2);
           tablaEstudiante.setValueAt(estudianteAux.getApellido_materno(), rowSelected, 3);
        }
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
                setErrorNumeroControl("¡Estudiante Registrado!");
                txtNumeroControl.selectAll();  
                return;         
             } else{
                txtNumeroControl.setBorder(new LineBorder(new Color(91,218,28))); //color RGB verde
                txtGeneracion.setText(ControllerEstudiante.getGeneracion(numero_control));
                letreroCampoVacio.setVisible(false);
                btnAgregar.setEnabled(true);   
                return;                
            }                    
        }         
        setErrorNumeroControl("¡Número de control incorrecto!");
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

        panelTitulo = new javax.swing.JPanel();
        letreroConsulta = new javax.swing.JLabel();
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
        panelBotones = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        panelTitulo.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        panelTitulo.setMinimumSize(new java.awt.Dimension(201, 25));
        panelTitulo.setPreferredSize(new java.awt.Dimension(201, 25));
        panelTitulo.setLayout(new java.awt.BorderLayout());

        letreroConsulta.setBackground(Colores.colorPrimary);
        letreroConsulta.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        letreroConsulta.setForeground(new java.awt.Color(255, 255, 255));
        letreroConsulta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letreroConsulta.setText("Información Estudiante");
        letreroConsulta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        letreroConsulta.setOpaque(true);
        panelTitulo.add(letreroConsulta, java.awt.BorderLayout.NORTH);

        getContentPane().add(panelTitulo);

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

        getContentPane().add(areaAutor);

        panelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        panelBotones.add(btnCancelar);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        panelBotones.add(btnAgregar);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelBotones.add(btnGuardar);

        getContentPane().add(panelBotones);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:        
        if(comprobarEntradas()){
            Estudiante estudianteAux = getEstudiante();            
            actualizarAddTabla(estudianteAux);
            this.dispose();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:       
        if(comprobarEntradas()){
            Estudiante estudianteTemp = getEstudiante();
            if(ce.updateEstudiante(estudianteTemp, this))  
                actualizarUpdateTabla(estudianteTemp);
            this.dispose();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNumeroControlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroControlFocusLost
        if(STATE == 0)
            comprobarNumeroControl();
    }//GEN-LAST:event_txtNumeroControlFocusLost

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaAutor;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel letreroCampoVacio;
    private javax.swing.JLabel letreroConsulta;
    private javax.swing.JLabel letreroNombre;
    private javax.swing.JLabel letreroNumeroControl;
    private javax.swing.JLabel letreroPrimerApellido;
    private javax.swing.JLabel letreroSegundoApellido;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JTextField txtApellidoMAutor;
    private javax.swing.JTextField txtApellidoPAutor;
    private javax.swing.JTextField txtGeneracion;
    private javax.swing.JTextField txtNombreAutor;
    private javax.swing.JTextField txtNumeroControl;
    // End of variables declaration//GEN-END:variables
}
