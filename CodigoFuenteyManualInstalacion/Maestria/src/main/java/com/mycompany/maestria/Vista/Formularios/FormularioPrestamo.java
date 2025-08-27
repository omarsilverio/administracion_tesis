/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.maestria.Vista.Formularios;


import com.mycompany.maestria.Controlador.ControllerEstudiante;
import com.mycompany.maestria.Controlador.ControllerPrestamo;
import com.mycompany.maestria.Controlador.ControllerTesis;
import com.mycompany.maestria.Modelo.AlmacenamientoPrestamo;
import com.mycompany.maestria.Modelo.Busquedas.RenderTabla;
import com.mycompany.maestria.Modelo.Estudiante;
import com.mycompany.maestria.Modelo.Prestamo;
import com.mycompany.maestria.Validaciones.ValidacionEntradas;
import com.mycompany.maestria.Validaciones.ValidarAutor;
import com.mycompany.maestria.Vista.Colores.Colores;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author omarsilverio
 */
public class FormularioPrestamo extends javax.swing.JDialog {  
    private final DefaultListModel<String> d = new DefaultListModel(); 
    private final ControllerTesis ct = new ControllerTesis();    
    private ControllerPrestamo cp;
    private JTable tablaAdeudos;    
     
    /**
     * Creates new form PrestarTesis
     * @param usuario
     */
    public FormularioPrestamo(JDialog parent) {
        super(parent);        
        initComponents();
        mostrarTodo();
        btnVerInfo.setEnabled(false);
        btnQuitar.setEnabled(false);
        listaTesis.setModel(d);
        letreroError.setVisible(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor, txtApellidoPAutor, txtApellidoMAutor);
        RenderTabla.cambiarEstiloEncabezados(tablaTesisDisponibles);
        RenderTabla.cambiarEstiloEncabezados(tablaAdeudos);
    }
    
    public FormularioPrestamo(JDialog parent, JTable tablaAdeudos, ControllerPrestamo cp) {
        super(parent);  
        this.tablaAdeudos = tablaAdeudos;
        this.cp = cp;        
        initComponents();
        mostrarTodo();
        btnVerInfo.setEnabled(false);
        btnQuitar.setEnabled(false);
        listaTesis.setModel(d);
        letreroError.setVisible(false);
        ValidacionEntradas.addValidatorStrings(txtNombreAutor, txtApellidoPAutor, txtApellidoMAutor);
        RenderTabla.cambiarEstiloEncabezados(tablaTesisDisponibles);
        RenderTabla.cambiarEstiloEncabezados(tablaAdeudos);
    }
    
    /**
     * Buscar una tesis en la base de datos
     */
    private void buscar(){
        int select = cmbBuscarPor.getSelectedIndex();
        String busqueda = txtBusqueda.getText();
        DefaultTableModel temp = ct.busquedas(busqueda, select,0);
        if(temp == null) return;
        mostrarResultadosBusquedas(temp, "¡No se encontrarón coincidencias!"); 
        botonesAcciones(false);
     }
    
    /**
     * Muestra el resultado de las busqueda
     * 
     */
    private void mostrarResultadosBusquedas(DefaultTableModel temp, String mensaje){
        if(temp.getRowCount() > 0){
                tablaTesisDisponibles.setModel(temp);
                RenderTabla.centrarDatos(tablaTesisDisponibles);
                scrollTabla.setVisible(true);                
                letreroTabla.setVisible(false);                
            }else{
                scrollTabla.setVisible(false);
                letreroTabla.setText(mensaje);
                letreroTabla.setVisible(true);                
            }        
    } 
    /**
     * Muestra todas las tesis disponibles
     */
    private void mostrarTodo(){        
        DefaultTableModel temp = ct.allTesis();
        mostrarResultados(temp, "¡Lista Vacía!");              
        botonesAcciones(false);
    }
    private void actualizarLista(Estudiante estudianteAux){
        if(cp != null){
            List<AlmacenamientoPrestamo> listaAlmacenamiento = cp.getCarrito();
            if(listaAlmacenamiento != null || !listaAlmacenamiento.isEmpty() || estudianteAux !=null){   
                if(!estudianteAux.isRegister()){
                    ControllerEstudiante.addEstudiante(estudianteAux, this);
                }
                listaAlmacenamiento.forEach(almacenamiento -> {
                    Prestamo prestamoAux = new Prestamo(
                            estudianteAux.getNumero_control(),
                            almacenamiento.getIdTesis(),
                            almacenamiento.getStatus(),
                            almacenamiento.getUsb(),
                            almacenamiento.getCd(),
                            almacenamiento.getTomo()
                    );
                    if(prestamoAux != null){
                        cp.addPrestamoList(prestamoAux, this);
                    }                               
                });
                cp.actualizarLista();
                tablaAdeudos.setModel(cp.allEstudiantesAdeudos());
                this.dispose();
            }
            
        }
        
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

        letreroSubtitulo = new javax.swing.JLabel();
        areaFormulario = new javax.swing.JPanel();
        areaAutor = new javax.swing.JPanel();
        txtNombreAutor = new javax.swing.JTextField();
        txtApellidoMAutor = new javax.swing.JTextField();
        txtApellidoPAutor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNumeroControl = new javax.swing.JTextField();
        letreroError = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtGeneracion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        panelBarra = new javax.swing.JPanel();
        panelBusqueda = new javax.swing.JPanel();
        letreroBuscarPor = new javax.swing.JLabel();
        cmbBuscarPor = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnMostrarTodo = new javax.swing.JButton();
        panelBarraHerramientas = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnVerInfo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        panelTesis = new javax.swing.JPanel();
        panelTabla = new javax.swing.JPanel();
        letreroInstrucciones = new javax.swing.JLabel();
        panelAreaTabla = new javax.swing.JPanel();
        scrollTabla = new javax.swing.JScrollPane();
        tablaTesisDisponibles = new javax.swing.JTable();
        letreroTabla = new javax.swing.JLabel();
        areaTesisSeleccionadas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaTesis = new javax.swing.JList<>();
        btnQuitar = new javax.swing.JButton();
        areaBotones = new javax.swing.JPanel();
        btnRealizarPrestamo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        letreroSubtitulo.setBackground(Colores.colorPrimary);
        letreroSubtitulo.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        letreroSubtitulo.setForeground(new java.awt.Color(255, 255, 255));
        letreroSubtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letreroSubtitulo.setText("Realizar Prestamo");
        letreroSubtitulo.setOpaque(true);
        getContentPane().add(letreroSubtitulo, java.awt.BorderLayout.PAGE_START);

        areaFormulario.setLayout(new javax.swing.BoxLayout(areaFormulario, javax.swing.BoxLayout.Y_AXIS));

        areaAutor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS DEL ESTUDIANTE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        areaAutor.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        areaAutor.setMaximumSize(new java.awt.Dimension(17267, 300));
        areaAutor.setMinimumSize(new java.awt.Dimension(500, 300));
        areaAutor.setPreferredSize(new java.awt.Dimension(864, 300));
        areaAutor.setLayout(new java.awt.GridBagLayout());

        txtNombreAutor.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        areaAutor.add(txtNombreAutor, gridBagConstraints);

        txtApellidoMAutor.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 0);
        areaAutor.add(txtApellidoMAutor, gridBagConstraints);

        txtApellidoPAutor.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 0);
        areaAutor.add(txtApellidoPAutor, gridBagConstraints);

        jLabel7.setText("* Nombre(s):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 203, 0, 0);
        areaAutor.add(jLabel7, gridBagConstraints);

        jLabel8.setText("* Primer Apellido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 172, 0, 0);
        areaAutor.add(jLabel8, gridBagConstraints);

        jLabel9.setText("* Segundo Apellido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 158, 0, 0);
        areaAutor.add(jLabel9, gridBagConstraints);

        jLabel1.setText(" * Número de Control:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(38, 144, 0, 0);
        areaAutor.add(jLabel1, gridBagConstraints);

        txtNumeroControl.setMinimumSize(new java.awt.Dimension(11, 32));
        txtNumeroControl.setPreferredSize(new java.awt.Dimension(81, 32));
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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 226;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 6, 0, 0);
        areaAutor.add(txtNumeroControl, gridBagConstraints);

        letreroError.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        letreroError.setForeground(new java.awt.Color(255, 0, 0));
        letreroError.setText("¡Número de control incorrecto!");
        letreroError.setMaximumSize(new java.awt.Dimension(215, 16));
        letreroError.setMinimumSize(new java.awt.Dimension(215, 16));
        letreroError.setPreferredSize(new java.awt.Dimension(215, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(38, 6, 0, 108);
        areaAutor.add(letreroError, gridBagConstraints);

        jLabel2.setText("Generación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 208, 0, 0);
        areaAutor.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        areaAutor.add(txtGeneracion, gridBagConstraints);

        jLabel3.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 239, 0, 0);
        areaAutor.add(jLabel3, gridBagConstraints);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione una Opción--", "Estudiante", "Titulado", "Inactivo" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        areaAutor.add(cmbStatus, gridBagConstraints);

        areaFormulario.add(areaAutor);

        panelBarra.setMinimumSize(new java.awt.Dimension(32767, 96));
        panelBarra.setPreferredSize(new java.awt.Dimension(974, 80));
        panelBarra.setLayout(new javax.swing.BoxLayout(panelBarra, javax.swing.BoxLayout.Y_AXIS));

        panelBusqueda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBusqueda.setMaximumSize(new java.awt.Dimension(32767, 45));
        panelBusqueda.setMinimumSize(new java.awt.Dimension(32767, 45));
        panelBusqueda.setPreferredSize(new java.awt.Dimension(974, 45));
        panelBusqueda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        letreroBuscarPor.setText("Buscar por:");
        panelBusqueda.add(letreroBuscarPor);

        cmbBuscarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Título", "Año", "Autor", "Número Control" }));
        cmbBuscarPor.setAutoscrolls(true);
        cmbBuscarPor.setMaximumSize(new java.awt.Dimension(573232, 73273));
        panelBusqueda.add(cmbBuscarPor);

        txtBusqueda.setMaximumSize(new java.awt.Dimension(500, 26));
        txtBusqueda.setMinimumSize(new java.awt.Dimension(500, 26));
        txtBusqueda.setPreferredSize(new java.awt.Dimension(500, 26));
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        panelBusqueda.add(txtBusqueda);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panelBusqueda.add(btnBuscar);

        btnMostrarTodo.setText("MostrarTodo");
        btnMostrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTodoActionPerformed(evt);
            }
        });
        panelBusqueda.add(btnMostrarTodo);

        panelBarra.add(panelBusqueda);

        panelBarraHerramientas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBarraHerramientas.setMaximumSize(new java.awt.Dimension(32767, 46));
        panelBarraHerramientas.setMinimumSize(new java.awt.Dimension(32767, 46));
        panelBarraHerramientas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnVerInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view.png"))); // NOI18N
        btnVerInfo.setToolTipText("Ver información de la tesis");
        btnVerInfo.setFocusable(false);
        btnVerInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVerInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerInfoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVerInfo);
        jToolBar1.add(jSeparator1);

        panelBarraHerramientas.add(jToolBar1);

        panelBarra.add(panelBarraHerramientas);

        areaFormulario.add(panelBarra);

        panelTesis.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        panelTesis.setMinimumSize(new java.awt.Dimension(100, 100));
        panelTesis.setLayout(new javax.swing.BoxLayout(panelTesis, javax.swing.BoxLayout.LINE_AXIS));

        panelTabla.setLayout(new javax.swing.BoxLayout(panelTabla, javax.swing.BoxLayout.Y_AXIS));

        letreroInstrucciones.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        letreroInstrucciones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        letreroInstrucciones.setText("Presione doble click o click derecho sobre una tesis para seleccionarla.");
        letreroInstrucciones.setOpaque(true);
        panelTabla.add(letreroInstrucciones);

        panelAreaTabla.setMaximumSize(new java.awt.Dimension(2147483647, 300));
        panelAreaTabla.setPreferredSize(new java.awt.Dimension(454, 300));
        panelAreaTabla.setLayout(new java.awt.CardLayout());

        scrollTabla.setMaximumSize(new java.awt.Dimension(32767, 400));

        tablaTesisDisponibles.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        tablaTesisDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "idTesis", "Autor", "Titulo", "Año"
            }
        ));
        tablaTesisDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaTesisDisponiblesMousePressed(evt);
            }
        });
        scrollTabla.setViewportView(tablaTesisDisponibles);

        panelAreaTabla.add(scrollTabla, "card2");

        letreroTabla.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        letreroTabla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letreroTabla.setText("¡No se encontrarón coincidencias!");
        panelAreaTabla.add(letreroTabla, "card3");

        panelTabla.add(panelAreaTabla);

        panelTesis.add(panelTabla);

        areaTesisSeleccionadas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TESIS SELECCIONADAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        areaTesisSeleccionadas.setMaximumSize(new java.awt.Dimension(272, 350));
        areaTesisSeleccionadas.setPreferredSize(new java.awt.Dimension(272, 350));
        areaTesisSeleccionadas.setLayout(new javax.swing.BoxLayout(areaTesisSeleccionadas, javax.swing.BoxLayout.Y_AXIS));

        listaTesis.setToolTipText("");
        listaTesis.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaTesisValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listaTesis);

        areaTesisSeleccionadas.add(jScrollPane2);

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        areaTesisSeleccionadas.add(btnQuitar);

        panelTesis.add(areaTesisSeleccionadas);

        areaFormulario.add(panelTesis);

        areaBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        areaBotones.setPreferredSize(new java.awt.Dimension(864, 45));
        areaBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 5));

        btnRealizarPrestamo.setText("Realizar Prestamo");
        btnRealizarPrestamo.setEnabled(false);
        btnRealizarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarPrestamoActionPerformed(evt);
            }
        });
        areaBotones.add(btnRealizarPrestamo);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        areaBotones.add(btnCancelar);

        areaFormulario.add(areaBotones);

        getContentPane().add(areaFormulario, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
  
    private void botonesAcciones(boolean state){
        btnVerInfo.setEnabled(state);
    }      
    
    /**
     * Muestra los resultados en la tabla
     */
    public void mostrarResultados(DefaultTableModel temp, String mensaje){
        if(temp != null){
                tablaTesisDisponibles.setModel(temp);
               // this.centrarDatos();
                scrollTabla.setVisible(true);                
                letreroTabla.setVisible(false);
            }else{
                scrollTabla.setVisible(false);
                letreroTabla.setText(mensaje);
                letreroTabla.setVisible(true);
            }        
    }  
    /*
    * Mustra un mensaje de error con un borde rojo en la entrada numero de control
    */
    private void setErrorNumeroControl(String mensaje){
         btnRealizarPrestamo.setEnabled(false);
         txtNumeroControl.setBorder(new LineBorder(Color.RED));
         letreroError.setText(mensaje);
         letreroError.setVisible(true);         
    }
    /**
     * Rellena solo las entradas del estudiante
     */
    private void rellenarCamposEstudiante(Estudiante auxEstudiante){
        txtNumeroControl.setText(auxEstudiante.getNumero_control());
        txtNombreAutor.setText(auxEstudiante.getNombre());
        txtApellidoPAutor.setText(auxEstudiante.getApellido_paterno());
        txtApellidoMAutor.setText(auxEstudiante.getApellido_materno());    
        txtGeneracion.setText(auxEstudiante.getGeneracion());
        cmbStatus.setSelectedIndex(auxEstudiante.getStatus());
    }
     /**
     * Activa o desactiva las entradas del estudiante
     */
    private void setEnabledEntradasEstudiante(boolean enable){       
        txtNombreAutor.setEnabled(enable);
        txtApellidoPAutor.setEnabled(enable);
        txtApellidoMAutor.setEnabled(enable);
        txtGeneracion.setEnabled(enable);
        cmbStatus.setEnabled(enable);
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
            Estudiante estudianteTemp = new Estudiante(numero_control);
            if(estudianteTemp.isRegister()){                
                rellenarCamposEstudiante(estudianteTemp);
                setEnabledEntradasEstudiante(false);                  
            }else
                txtGeneracion.setText(ControllerEstudiante.getGeneracion(numero_control));            
            txtNumeroControl.setBorder(new LineBorder(new Color(91,218,28))); //color RGB verde
           
            letreroError.setVisible(false);
            btnRealizarPrestamo.setEnabled(true);  
            return;             
        }else{ 
            setErrorNumeroControl("¡Numero de control incorrecto!");
            setEnabledEntradasEstudiante(true); 
        }
    }
    /**
     * Recupera los datos de entrada de la tesis
     */
    private Estudiante getEstudiante(){
        String numero_control = txtNumeroControl.getText().toUpperCase();  
        String nombre = txtNombreAutor.getText();
        String primer_apellido = txtApellidoPAutor.getText();
        String segundo_apellido = txtApellidoMAutor.getText();  
        int status = 1;
        return new Estudiante(numero_control,nombre,primer_apellido,segundo_apellido,status);
    }
    
    /**
     * @param temp
     * Agrega a un estudiante a la base de datos con los datos de las entradas
     */
    /**
     * Agrega el estudiante a la base de datos
     */
    public void agregarEstudiante(){        
        if(comprobarEntradas()){
            Estudiante estudianteAux = getEstudiante(); 
            ControllerEstudiante.addEstudiante(estudianteAux, this);                     
            this.dispose();
        }
    } 
    /**
     * Comprueba si los datos pueden agregarse
     */
    private boolean comprobarEntradas(){
        if(cp.getCarrito().isEmpty()){
            Alertas.mensajeAdvertencia(this, "La lista de selección no debe estar vacía");
            return false;
        }
        JTextField temp = ValidacionEntradas.getJTextFieldEmpty(
                txtNumeroControl, txtNombreAutor,txtApellidoPAutor,txtApellidoMAutor);    
        if(temp != null){ 
             temp.requestFocus();
             Alertas.mensajeAdvertencia(this, "¡Hay un campo vacío!");
             return false;
        }
        if(cmbStatus.getSelectedIndex() < 1){
            Alertas.mensajeAdvertencia(this, "¡Seleccione el status del alumno!");
             return false;
        }
        Estudiante tempEstudiante = getEstudiante(); 
        if(tempEstudiante != null){
            return true;                      
        }
        return false;
    } 
    private void btnVerInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerInfoActionPerformed
        ControllerTesis.dialogViewTesis(tablaTesisDisponibles, this);
    }//GEN-LAST:event_btnVerInfoActionPerformed

    private void tablaTesisDisponiblesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTesisDisponiblesMousePressed
        if(evt.getButton() == 1 && evt.getClickCount() == 1){
             btnVerInfo.setEnabled(true);
        }else if (evt.getButton() == 1 && evt.getClickCount() > 1) {
            cp.dialogoAddCarrito(this,tablaTesisDisponibles,listaTesis);             
        }
    }//GEN-LAST:event_tablaTesisDisponiblesMousePressed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int row = listaTesis.getSelectedIndex();
        if(row >= 0)
            cp.deleteCarrito(row,listaTesis);
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void txtNumeroControlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroControlFocusLost
       comprobarNumeroControl();       
    }//GEN-LAST:event_txtNumeroControlFocusLost
    
    
    private void btnRealizarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarPrestamoActionPerformed
       if(comprobarEntradas()){
           Estudiante estudianteAux = getEstudiante();
           actualizarLista(estudianteAux);
       }
    }//GEN-LAST:event_btnRealizarPrestamoActionPerformed

    private void listaTesisValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaTesisValueChanged
        // TODO add your handling code here:
        if(!listaTesis.isSelectionEmpty()){
            btnQuitar.setEnabled(true);
            btnVerInfo.setEnabled(false);
        }else{
            btnQuitar.setEnabled(false);
        }
    }//GEN-LAST:event_listaTesisValueChanged

    private void txtNumeroControlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroControlKeyReleased
        comprobarNumeroControl();       
    }//GEN-LAST:event_txtNumeroControlKeyReleased

    private void txtNumeroControlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroControlKeyTyped
        String numero_control = txtNumeroControl.getText();
        if(numero_control.length() == 9){           
           evt.consume();
       }
    }//GEN-LAST:event_txtNumeroControlKeyTyped

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        buscar();
         
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnMostrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodoActionPerformed
        // TODO add your handling code here:
        mostrarTodo();
        
    }//GEN-LAST:event_btnMostrarTodoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaAutor;
    private javax.swing.JPanel areaBotones;
    private javax.swing.JPanel areaFormulario;
    private javax.swing.JPanel areaTesisSeleccionadas;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnMostrarTodo;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRealizarPrestamo;
    private javax.swing.JButton btnVerInfo;
    private javax.swing.JComboBox<String> cmbBuscarPor;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel letreroBuscarPor;
    private javax.swing.JLabel letreroError;
    private javax.swing.JLabel letreroInstrucciones;
    private javax.swing.JLabel letreroSubtitulo;
    private javax.swing.JLabel letreroTabla;
    private javax.swing.JList<String> listaTesis;
    private javax.swing.JPanel panelAreaTabla;
    private javax.swing.JPanel panelBarra;
    private javax.swing.JPanel panelBarraHerramientas;
    private javax.swing.JPanel panelBusqueda;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelTesis;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaTesisDisponibles;
    private javax.swing.JTextField txtApellidoMAutor;
    private javax.swing.JTextField txtApellidoPAutor;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtGeneracion;
    private javax.swing.JTextField txtNombreAutor;
    private javax.swing.JTextField txtNumeroControl;
    // End of variables declaration//GEN-END:variables
}
