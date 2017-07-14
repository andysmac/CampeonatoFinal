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
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sebastian
 */
public class Arbitros extends javax.swing.JFrame {

    DefaultTableModel modelo;

    /**
     * Creates new form Arbitros
     */
    public Arbitros() {
        initComponents();
        cargarpartidos();
        tblArbitro.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = tblArbitro.getSelectedRow();
                if (tblArbitro.getSelectedRow() != -1) {

                    txtCedula.setText(tblArbitro.getValueAt(fila, 0).toString());
                    txtNombre.setText(tblArbitro.getValueAt(fila, 1).toString());
                    txtApellido.setText(tblArbitro.getValueAt(fila, 2).toString());
                    txtClase.setText(tblArbitro.getValueAt(fila, 3).toString());
                }

                //botonEliminar();
                //   btnActualizar.setEnabled(true);
            }
        });
    }

    public void cargarpartidos() {
        String[] titulos = {"CEDULA", "NOMBRE", "APELLIDO", "CLASE FIFA"};
        modelo = new DefaultTableModel(null, titulos);
        tblArbitro.setModel(modelo);
        String[] datos = new String[4];
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "SELECT * FROM ARBITROS ORDER BY APE_ARB ASC";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("ID_ARB");
                datos[1] = rs.getString("NOM_ARB");
                datos[2] = rs.getString("APE_ARB");
                datos[3] = rs.getString("CLASF_FIFA");

                modelo.addRow(datos);
            }
            tblArbitro.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void guardarDatos() {

        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar la Cedula");
            txtCedula.requestFocus();
        } else {
            if (txtApellido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe Ingresar el Apellido");
                txtApellido.requestFocus();
            } else {
                if (txtNombre.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe Ingresar el Nombre");
                    txtNombre.requestFocus();

                } else {
                    if (txtClase.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe Ingresar la Clase FIFA ");
                        txtClase.requestFocus();

                    }
                    conexion cn = new conexion();
                    Connection cc = cn.conectar();
                    String ID_ARB, NOM_ARB, APE_ARB;
                    int CLASF_FIFA;
                    String sql = "";
                    sql = "INSERT INTO ARBITROS(ID_ARB,NOM_ARB,APE_ARB,CLASF_FIFA) VALUES(?,?,?,?)";
                    ID_ARB = txtCedula.getText();
                    NOM_ARB = txtNombre.getText();
                    APE_ARB = txtApellido.getText();
                    CLASF_FIFA = Integer.valueOf(txtClase.getText());

                    try {
                        PreparedStatement psd = cc.prepareStatement(sql);
                        psd.setString(1, ID_ARB);
                        psd.setString(2, NOM_ARB);
                        psd.setString(3, APE_ARB);
                        psd.setInt(4, CLASF_FIFA);

                        int n = psd.executeUpdate();
                        if (n > 0) {
                            JOptionPane.showMessageDialog(null, "Se a insertado una fila");
                            cargarpartidos();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "El dato no se inserto");
                    }
                }
            }

        }

    }

    public void modificar() {

        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Se debe ingresar Goles del Local");
        } else {
            if (txtApellido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se debe ingresar Goles del Visitante");

            } else {
                if (txtClase.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Se debe ingresar Goles del Visitante");
                } else {

                    conexion cc = new conexion();
                    Connection cn = cc.conectar();
                    String sql = "";
                    sql = "UPDATE ARBITROS SET NOM_ARB='" + txtNombre.getText() + "',"
                            + "APE_ARB='" + txtApellido.getText() + "',"
                            + "CLASF_FIFA='" + Integer.valueOf(txtClase.getText()) + "'"
                            + "WHERE ID_ARB='" + txtCedula.getText().toString() + "'";
                    try {
                        PreparedStatement psd = cn.prepareStatement(sql);
                        int n = psd.executeUpdate();
                        if (n > 0) {
                            JOptionPane.showMessageDialog(null, "Se actualiso Correctamente");
                            cargarpartidos();
                        // botonesInicio();
                            //limpiar();

                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        JOptionPane.showMessageDialog(null , "No se actualizo");
                    }

                }
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

        txtApellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtClase = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArbitro = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cédula");

        jLabel2.setText("Nombre");

        jLabel4.setText("Apellido");

        jLabel5.setText("Clase FIFA");

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
        btnSalir.setActionCommand("");

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel3.setText("NUEVO ARBITRO");

        tblArbitro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblArbitro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(txtCedula)))
                    .addComponent(txtClase)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnNuevo)
                                    .addComponent(btnGuardar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))))
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarDatos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        modificar();
    }//GEN-LAST:event_btnActualizarActionPerformed

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
            java.util.logging.Logger.getLogger(Arbitros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Arbitros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Arbitros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Arbitros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Arbitros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblArbitro;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtClase;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
