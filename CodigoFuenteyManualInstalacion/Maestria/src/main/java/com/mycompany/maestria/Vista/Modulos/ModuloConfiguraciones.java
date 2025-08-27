/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.maestria.Vista.Modulos;

import com.mycompany.maestria.Modelo.BaseDeDatos;
import com.mycompany.maestria.Modelo.Usuario;
import com.mycompany.maestria.Validaciones.ValidacionEntradas;
import com.mycompany.maestria.Vista.Componentes.AlertaConfirmacion;
import com.mycompany.maestria.Vista.Componentes.Alertas;
import com.mycompany.maestria.Vista.Componentes.NavBar;
import com.mycompany.maestria.Vista.Colores.Colores;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JTextField;
/**
 *
 * @author omarsilverio
 */
public class ModuloConfiguraciones extends javax.swing.JDialog {
    private Usuario usuario = new Usuario();
    private BaseDeDatos bdd = new BaseDeDatos();
    /**
     * Creates new form CrearLogin
     */
    public ModuloConfiguraciones() {
        //super(parent);  
        this.setJMenuBar(new NavBar(this));
        initComponents();        
        this.setTitle("Configuraciones");
        rellenarEntradasBDD();
        txtNombreUsuario.setText(usuario.getUsuario());
        txtContrasena.setEnabled(false);
        txtConfirmarContrasena.setEnabled(false);
        ValidacionEntradas.addValidatorNumber(txtPuertoBDD);
        btnGuardarConfigBDD.setEnabled(false);
       
    }   
   private boolean guardarCambiosBDD(){
       boolean band = alerta();
        if(band){
            return bdd.guardarCambios();
        }
        return false;
   }
    
    private void ComprobarConexionBDD(int selec){
        Connection conn = null; 
        if(!txtNombreBDD.getText().isEmpty()){    
            if(!txtHostBDD.getText().isEmpty()){
                cambiarConfigBDD();
                try {
                    Class.forName("org.postgresql.Driver");
                    conn = DriverManager.getConnection(bdd.getURLDATABASE(),  bdd.getUSUARIO(), bdd.getPASSWORD());
                    if(conn != null){
                        conn.close();
                        if(selec == 1){
                            Alertas.mensajeExito(this,"Conexión exitosa");
                            btnGuardarConfigBDD.setEnabled(true);                            
                        }else{
                            if(guardarCambiosBDD()){   
                                Alertas.mensajeExito(this,"Cambios Guardados Correctamente");
                                this.dispose();
                            }else{
                                Alertas.mensajeError(this,"No se Pudierón almacenar los cambios");
                                btnGuardarConfigBDD.setEnabled(false);
                            }
                        }                        
                    }else{
                        Alertas.mensajeError(this,"No se pudo conectar con la BDD");
                        btnGuardarConfigBDD.setEnabled(false);                        
                    }
                } catch (Exception e) {
                    Alertas.mensajeError(this,"No se pudo conectar con la BDD \nError: " + e.toString());
                    btnGuardarConfigBDD.setEnabled(false);
                }
            }else{
                Alertas.mensajeError(this,"No se pudo conectar con la BDD \n agregue un HOST");
                btnGuardarConfigBDD.setEnabled(false);                
            }
        }else{
            Alertas.mensajeError(this,"No se pudo conectar con la BDD \n agregue un nombre");
            btnGuardarConfigBDD.setEnabled(false);            
        }
    }
    
    private void rellenarEntradasBDD(){
        txtNombreBDD.setText(bdd.getNAMEDATABASE());
        txtHostBDD.setText(bdd.getHOST());
        txtUsuarioBDD.setText(bdd.getUSUARIO());
        txtPuertoBDD.setText(bdd.getPORT() + "");
        txtPasswordBDD.setText(bdd.getPASSWORD());
        
    }
    /**
     * Comprueba si los datos pueden agregarse
     */
    private boolean comprobarContrasenias(){         
        String contraseniaTemp = new String(txtContrasena.getPassword());
         String contraseniaTemp2 = new String(txtConfirmarContrasena.getPassword());         
         return contraseniaTemp.equals(contraseniaTemp2);
    }
     /**
     * Comprueba si los datos pueden agregarse
     */
    private boolean comprobarEntradas(){         
        JTextField temp = ValidacionEntradas.getJTextFieldEmpty(
                txtNombreUsuario,txtContrasena,txtConfirmarContrasena);        
        if(temp != null){ 
             temp.requestFocus();
             Alertas.mensajeAdvertencia(this, "¡Campo vacío!");
             return false;
        }        
        return true;
    }
    /**
     * Abre la confirmación con contraseña para aplicar los cambios
     */
    private boolean alerta(){
        AlertaConfirmacion form = new AlertaConfirmacion(this);
        form.pack();
        form.setModal(true);
        form.setLocationRelativeTo(null);
        form.setVisible(true); 
        return form.getConfirmacion();
    }
    
     /**
     * Cambia El nombre del usuario
     */
    private void actualizarUsuario(String usuarioCad){
        if(usuario.setUsuario(usuarioCad))
                    Alertas.mensajeExito(this, "Usuario Actualizado"); 
                   else
                       Alertas.mensajeError(this, "No se pudo actualizar el nombre de usuario"); 
    }
    private void cambiarUsuario(){
        String usuarioCad = txtNombreUsuario.getText();
        if(usuarioCad.isEmpty()){
             Alertas.mensajeAdvertencia(this, "El nombre del usuario no puede estar vacío");
             return;
        }         
        boolean band = alerta(); 
        if(band){       
        if(!usuarioCad.isEmpty()){                
            if(checkContrasenia.isSelected()){
                cambiarContrasenia(usuarioCad);
            }else{
                actualizarUsuario(usuarioCad);
            }                    
            this.dispose();  
            }else{
                Alertas.mensajeAdvertencia(this, "El nombre del usuario no puede estar vacío");
            }      
        }
        
    }
    /**
     * Cambia la contraseña
     */
    private void cambiarContrasenia(String cadUsuario){
        String contraseniaNueva = new String(txtContrasena.getPassword());
        String confirContraseniaNueva = new String(txtConfirmarContrasena.getPassword());
        if(contraseniaNueva.isEmpty() || confirContraseniaNueva.isEmpty()){
            Alertas.mensajeAdvertencia(this, "La contraseña no puede estar vacía");
            return;
        }
               
        if(comprobarContrasenias()){            
            String passwordTemp = new String(txtContrasena.getPassword());
            if(usuario.setPassword(passwordTemp)){
                actualizarUsuario(cadUsuario);
                Alertas.mensajeExito(this, "¡La contraseña se cambio!");                
            }            
        }else{
            Alertas.mensajeError(this, "¡Las contraseñas no coinciden!");
            txtConfirmarContrasena.setFocusable(true);
            txtConfirmarContrasena.selectAll();
         }
    }
    
    private void cambiarConfigBDD(){        
        bdd.setNAMEDATABASE(txtNombreBDD.getText());
        bdd.setHOST(txtHostBDD.getText());
        bdd.setPASSWORD(new String(txtPasswordBDD.getPassword()));
        bdd.setPORT(Integer.parseInt(txtPuertoBDD.getText()));
        bdd.setUSUARIO(txtUsuarioBDD.getText());
        bdd.setURLDATABASE();
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
        panelBarHerramientas = new javax.swing.JPanel();
        barraHerramientas = new javax.swing.JToolBar();
        btnHome = new javax.swing.JButton();
        panelFormularioUsuario = new javax.swing.JPanel();
        letreroNombreUsuario = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        letreroContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        letreroConfirmarContrasena = new javax.swing.JLabel();
        txtConfirmarContrasena = new javax.swing.JPasswordField();
        checkContrasenia = new javax.swing.JCheckBox();
        btnConfirmar = new javax.swing.JButton();
        panelFormularioBDD = new javax.swing.JPanel();
        letreroNombreUsuario1 = new javax.swing.JLabel();
        txtUsuarioBDD = new javax.swing.JTextField();
        letreroContrasena1 = new javax.swing.JLabel();
        letreroConfirmarContrasena1 = new javax.swing.JLabel();
        txtPasswordBDD = new javax.swing.JPasswordField();
        txtNombreBDD = new javax.swing.JTextField();
        letreroContrasena2 = new javax.swing.JLabel();
        txtPuertoBDD = new javax.swing.JTextField();
        letreroContrasena3 = new javax.swing.JLabel();
        txtHostBDD = new javax.swing.JTextField();
        btnComprobarBDD = new javax.swing.JButton();
        mostraContraseñaBDD = new javax.swing.JCheckBox();
        btnGuardarConfigBDD = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(680, 800));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        panelTitulo.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        panelTitulo.setMinimumSize(new java.awt.Dimension(188, 30));
        panelTitulo.setPreferredSize(new java.awt.Dimension(1103, 30));
        panelTitulo.setLayout(new java.awt.BorderLayout());

        letreroConsulta.setBackground(Colores.colorPrimary);
        letreroConsulta.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        letreroConsulta.setForeground(new java.awt.Color(255, 255, 255));
        letreroConsulta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letreroConsulta.setText("Configuraciones");
        letreroConsulta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        letreroConsulta.setMaximumSize(new java.awt.Dimension(18888, 22));
        letreroConsulta.setOpaque(true);
        panelTitulo.add(letreroConsulta, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelTitulo);

        panelBarHerramientas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBarHerramientas.setMaximumSize(new java.awt.Dimension(11033, 46));
        panelBarHerramientas.setMinimumSize(new java.awt.Dimension(1103, 46));
        panelBarHerramientas.setPreferredSize(new java.awt.Dimension(271, 46));
        panelBarHerramientas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        barraHerramientas.setFloatable(false);
        barraHerramientas.setRollover(true);

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home.png"))); // NOI18N
        btnHome.setToolTipText("Menú");
        btnHome.setFocusable(false);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHome.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        barraHerramientas.add(btnHome);

        panelBarHerramientas.add(barraHerramientas);

        getContentPane().add(panelBarHerramientas);

        panelFormularioUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configuración Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        panelFormularioUsuario.setMaximumSize(new java.awt.Dimension(198298, 300));
        panelFormularioUsuario.setMinimumSize(new java.awt.Dimension(750, 300));
        panelFormularioUsuario.setPreferredSize(new java.awt.Dimension(750, 250));
        panelFormularioUsuario.setLayout(new java.awt.GridBagLayout());

        letreroNombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroNombreUsuario.setText("Nombre de Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 115, 0, 0);
        panelFormularioUsuario.add(letreroNombreUsuario, gridBagConstraints);

        txtNombreUsuario.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 102);
        panelFormularioUsuario.add(txtNombreUsuario, gridBagConstraints);

        letreroContrasena.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroContrasena.setText("Nueva Contraseña:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 122, 0, 0);
        panelFormularioUsuario.add(letreroContrasena, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 102);
        panelFormularioUsuario.add(txtContrasena, gridBagConstraints);

        letreroConfirmarContrasena.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroConfirmarContrasena.setText("Confirmar Contraseña:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 98, 0, 0);
        panelFormularioUsuario.add(letreroConfirmarContrasena, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 102);
        panelFormularioUsuario.add(txtConfirmarContrasena, gridBagConstraints);

        checkContrasenia.setText("Cambiar Contraseña");
        checkContrasenia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkContraseniaItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        panelFormularioUsuario.add(checkContrasenia, gridBagConstraints);

        btnConfirmar.setText("Guardar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 31, 0);
        panelFormularioUsuario.add(btnConfirmar, gridBagConstraints);

        getContentPane().add(panelFormularioUsuario);

        panelFormularioBDD.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configuracion Base De Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        panelFormularioBDD.setMaximumSize(new java.awt.Dimension(261767221, 400));
        panelFormularioBDD.setMinimumSize(new java.awt.Dimension(690, 400));
        panelFormularioBDD.setPreferredSize(new java.awt.Dimension(690, 400));
        panelFormularioBDD.setLayout(new java.awt.GridBagLayout());

        letreroNombreUsuario1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroNombreUsuario1.setText("Nombre de la bdd:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 116, 0, 0);
        panelFormularioBDD.add(letreroNombreUsuario1, gridBagConstraints);

        txtUsuarioBDD.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 110);
        panelFormularioBDD.add(txtUsuarioBDD, gridBagConstraints);

        letreroContrasena1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroContrasena1.setText("Usuario de la bdd:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 118, 0, 0);
        panelFormularioBDD.add(letreroContrasena1, gridBagConstraints);

        letreroConfirmarContrasena1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroConfirmarContrasena1.setText("Contraseña de la bdd:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 95, 0, 0);
        panelFormularioBDD.add(letreroConfirmarContrasena1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 110);
        panelFormularioBDD.add(txtPasswordBDD, gridBagConstraints);

        txtNombreBDD.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 110);
        panelFormularioBDD.add(txtNombreBDD, gridBagConstraints);

        letreroContrasena2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroContrasena2.setText("Puerto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 188, 0, 0);
        panelFormularioBDD.add(letreroContrasena2, gridBagConstraints);

        txtPuertoBDD.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 110);
        panelFormularioBDD.add(txtPuertoBDD, gridBagConstraints);

        letreroContrasena3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        letreroContrasena3.setText("Host:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 198, 0, 0);
        panelFormularioBDD.add(letreroContrasena3, gridBagConstraints);

        txtHostBDD.setMaximumSize(new java.awt.Dimension(11, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 110);
        panelFormularioBDD.add(txtHostBDD, gridBagConstraints);

        btnComprobarBDD.setText("Comprobar Conexión");
        btnComprobarBDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarBDDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 92, 0);
        panelFormularioBDD.add(btnComprobarBDD, gridBagConstraints);

        mostraContraseñaBDD.setText("Mostrar Contraseña");
        mostraContraseñaBDD.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mostraContraseñaBDDStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        panelFormularioBDD.add(mostraContraseñaBDD, gridBagConstraints);

        btnGuardarConfigBDD.setText("Guardar");
        btnGuardarConfigBDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarConfigBDDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 92, 0);
        panelFormularioBDD.add(btnGuardarConfigBDD, gridBagConstraints);

        getContentPane().add(panelFormularioBDD);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:      
        cambiarUsuario();            
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void checkContraseniaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkContraseniaItemStateChanged
        // TODO add your handling code here:
        if(checkContrasenia.isSelected()){
            txtContrasena.setEnabled(true);
            txtConfirmarContrasena.setEnabled(true);
        }else{
            txtContrasena.setEnabled(false);
            txtConfirmarContrasena.setEnabled(false);
        }
    }//GEN-LAST:event_checkContraseniaItemStateChanged

    private void mostraContraseñaBDDStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mostraContraseñaBDDStateChanged
        // TODO add your handling code here:
        if (mostraContraseñaBDD.isSelected()) {  // a es una variable boolean en true
            txtPasswordBDD.setEchoChar((char)0); // este método es el que hace visible el texto del jPasswordField
   
        } else {
            txtPasswordBDD.setEchoChar('*'); // i es el char
    
         }
    }//GEN-LAST:event_mostraContraseñaBDDStateChanged

    private void btnComprobarBDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarBDDActionPerformed
        // TODO add your handling code here:
        ComprobarConexionBDD(1);
    }//GEN-LAST:event_btnComprobarBDDActionPerformed

    private void btnGuardarConfigBDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarConfigBDDActionPerformed
        // TODO add your handling code here:
        ComprobarConexionBDD(0);
    }//GEN-LAST:event_btnGuardarConfigBDDActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnHomeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JButton btnComprobarBDD;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnGuardarConfigBDD;
    private javax.swing.JButton btnHome;
    private javax.swing.JCheckBox checkContrasenia;
    private javax.swing.JLabel letreroConfirmarContrasena;
    private javax.swing.JLabel letreroConfirmarContrasena1;
    private javax.swing.JLabel letreroConsulta;
    private javax.swing.JLabel letreroContrasena;
    private javax.swing.JLabel letreroContrasena1;
    private javax.swing.JLabel letreroContrasena2;
    private javax.swing.JLabel letreroContrasena3;
    private javax.swing.JLabel letreroNombreUsuario;
    private javax.swing.JLabel letreroNombreUsuario1;
    private javax.swing.JCheckBox mostraContraseñaBDD;
    private javax.swing.JPanel panelBarHerramientas;
    private javax.swing.JPanel panelFormularioBDD;
    private javax.swing.JPanel panelFormularioUsuario;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JPasswordField txtConfirmarContrasena;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtHostBDD;
    private javax.swing.JTextField txtNombreBDD;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JPasswordField txtPasswordBDD;
    private javax.swing.JTextField txtPuertoBDD;
    private javax.swing.JTextField txtUsuarioBDD;
    // End of variables declaration//GEN-END:variables
}
