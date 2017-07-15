/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juank
 */
public class Partidos extends javax.swing.JInternalFrame {

    DefaultTableModel modelo;

    /**
     * Creates new form Partidos
     */
    public Partidos() {
        initComponents();
        cargarpartidos("");
//       cargarEquiposC();
        cargarEquipos();
        llenarEstadio();
        botonesInicio();
    }

    public void cargarpartidos(String busqueda) {
        String[] titulos = {"NUMERO PARTIDO", "FECHA", "ESTADIO", "EQUIPO1", "EQUIPO2"};
        modelo = new DefaultTableModel(null, titulos);
        tblPartidos.setModel(modelo);
        String[] datos = new String[5];
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "SELECT * FROM PARTIDOS ORDER BY NUM_PAR ASC";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("NUM_PAR");
                datos[1] = rs.getString("FEC_PAR");
                datos[2] = rs.getString("ESTADIO");
                datos[3] = (rs.getString("EQ1"));
                datos[4] = (rs.getString("EQ2"));
                modelo.addRow(datos);
            }
            tblPartidos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void cargarEquiposC() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "SELECT COD_EQU FROM EQUIPOS";

        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cmbEquiLocal.addItem(rs.getString("COD_EQU"));
                cmbEquiVistante.addItem(rs.getString("COD_EQU"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarEquipos() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "SELECT NOM_EQU FROM EQUIPOS";

        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cmbEquiLocal.addItem(rs.getString("NOM_EQU"));
                cmbEquiVistante.addItem(rs.getString("NOM_EQU"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void limpiar() {
        txtNumeroPar.setText("");
        cmbEquiLocal.setSelectedIndex(0);
        cmbEquiVistante.setSelectedIndex(0);
        cmbEstadio.setSelectedIndex(0);
    }

    public void guardarDatos() {
        if (txtNumeroPar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar un numero de partido");
            txtNumeroPar.requestFocus();
        } else {
            if (cmbEquiLocal.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Debe Escojer un Equipo Local");
                cmbEquiLocal.requestFocus();
            } else {
                if (cmbEquiVistante.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Debe Escojer un Equipo Visitante");
                    cmbEquiVistante.requestFocus();

                } else {
                    if (cmbEstadio.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Debe Escojer un Estadio ");
                        cmbEstadio.requestFocus();

                    }
                    conexion cn = new conexion();
                    Connection cc = cn.conectar();
                    Calendar cal = Calendar.getInstance();

                    Date fec = jdtFecha.getDate();
                    int aniov = Integer.valueOf(fec.getYear()) + 1900;
                    String mesv = String.valueOf(fec.getMonth() + 1);
                    String fechav = String.valueOf(fec.getDate());
                    String FEC_PAR, ESTADIO, EQ1, EQ2;
                    int NUM_PAR;
                    String sql = "";
                    sql = "INSERT INTO PARTIDOS(NUM_PAR,FEC_PAR,ESTADIO,EQ1,EQ2) VALUES(?,?,?,?,?)";
                    NUM_PAR = Integer.valueOf(txtNumeroPar.getText());
                    FEC_PAR = fechav + "/" + mesv + "/" + aniov;
//        JOptionPane.showMessageDialog(null, FEC_PAR);
                    ESTADIO = cmbEstadio.getSelectedItem().toString();
                    EQ1 = verCodigoEquipo(cmbEquiLocal.getSelectedItem().toString());
                    EQ2 = verCodigoEquipo(cmbEquiVistante.getSelectedItem().toString());
                    try {
                        PreparedStatement psd = cc.prepareStatement(sql);
                        psd.setInt(1, NUM_PAR);
                        psd.setString(2, FEC_PAR);
                        psd.setString(3, ESTADIO);
                        psd.setString(4, EQ1);
                        psd.setString(5, EQ2);

                        int n = psd.executeUpdate();
                        if (n > 0) {
                            JOptionPane.showMessageDialog(null, "Se a insertado una fila");
                            cargarpartidos("");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "El dato no se inserto");
                    }
                }
            }

        }

    }

    public String verCodigoEquipo(String equipo) {
        String codigo = "";
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select COD_EQU from equipos where nom_equ='" + equipo + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codigo = rs.getString("COD_EQU");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede consultar la codigo");
        }

        return codigo;
    }

    public String verEquipo(String codigo_equipo) {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String equipo = "";
        String sql = "";
        sql = "select nom_equ from equipos where cod_equ='" + codigo_equipo + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                equipo = rs.getString("nom_equ");
            }
        } catch (Exception e) {

        }
        return equipo;
    }

    public void llenarEstadio() {
        cmbEstadio.removeAllItems();
        cmbEstadio.addItem("Seleccione uno");
        cmbEstadio.addItem("CAPWEL");
        cmbEstadio.addItem("BELLAVISTA");

    }

    public void Cancelar() {
        txtNumeroPar.setText("");
        //  txtBuscar.setText((""));
        cmbEquiLocal.setSelectedIndex(0);
        cmbEquiVistante.setSelectedIndex(0);
        cmbEstadio.setSelectedIndex(0);
    }

    public void botonesInicio() {
        txtNumeroPar.requestFocus();
        txtNumeroPar.setEnabled(false);
        cmbEquiLocal.setEnabled(false);
        cmbEquiVistante.setEnabled(false);
        cmbEstadio.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(true);
        btnCancelar.setEnabled(false);

    }

    public void nuevo() {
        txtNumeroPar.requestFocus();
        txtNumeroPar.setEnabled(true);
        cmbEquiLocal.setEnabled(true);
        cmbEquiVistante.setEnabled(true);
        cmbEstadio.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnSalir.setEnabled(true);
        btnCancelar.setEnabled(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPartidos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNumeroPar = new javax.swing.JTextField();
        cmbEstadio = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jdtFecha = new com.toedter.calendar.JDateChooser();
        btnLocal = new javax.swing.JButton();
        btnVisitante = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnVisitante1 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblPartidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblPartidos);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jButton1.setText("Fin Partido");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(99, 99, 99))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("PARTIDOS");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Equipo Local:");

        jLabel2.setText("Fecha :");

        jLabel3.setText("Estadio:");

        jLabel1.setText("Número Partido:");

        jLabel5.setText("Equipo Visitante:");

        jdtFecha.setPreferredSize(new java.awt.Dimension(135, 26));

        btnLocal.setText("Local");

        btnVisitante.setText("Visitante");

        jLabel7.setText("Terna Arbitral");

        btnVisitante1.setText("Arbitraje");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumeroPar)
                    .addComponent(jdtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(cmbEstadio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVisitante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVisitante1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumeroPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jdtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnLocal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnVisitante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnVisitante1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        nuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarDatos();
        botonesInicio();
        limpiar();
        cargarpartidos("");

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        Cancelar();
        botonesInicio();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Partidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Partidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Partidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Partidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Partidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLocal;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVisitante;
    private javax.swing.JButton btnVisitante1;
    private javax.swing.JComboBox cmbEstadio;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdtFecha;
    private javax.swing.JTable tblPartidos;
    private javax.swing.JTextField txtNumeroPar;
    // End of variables declaration//GEN-END:variables
}
