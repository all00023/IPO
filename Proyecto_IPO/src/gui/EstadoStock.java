/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import core.Producto;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adrian
 */
public class EstadoStock extends javax.swing.JFrame {

    private ArrayList<Producto> lista = new ArrayList<>();
     /*
     * Creates new form EstadoStock
     */
    public EstadoStock() {
        initComponents();   
        jSeparator2.setVisible(false);
        
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
        Tabla = new javax.swing.JTable();
        TTitulo = new javax.swing.JLabel();
        BTodo = new javax.swing.JButton();
        BBajo = new javax.swing.JButton();
        BCerrar = new javax.swing.JButton();
        TBusqueda = new javax.swing.JTextField();
        BRapida = new javax.swing.JButton();
        BBuscar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        BAplicar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrar Stock");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                cerrar(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Barras", "Nombre", "Stock Mínimo", "Stock Actual"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TablaFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla);
        Tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        Tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        Tabla.getColumnModel().getColumn(2).setPreferredWidth(25);
        Tabla.getColumnModel().getColumn(3).setPreferredWidth(25);

        TTitulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        TTitulo.setText("Estado Stock (Vacio)");

        BTodo.setText("Mostrar Todo");
        BTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTodoActionPerformed(evt);
            }
        });

        BBajo.setText("Mostrar Stock Bajo");
        BBajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBajoActionPerformed(evt);
            }
        });

        BCerrar.setText("Cerrar");
        BCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCerrarActionPerformed(evt);
            }
        });

        TBusqueda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TBusqueda.setForeground(new java.awt.Color(153, 153, 153));
        TBusqueda.setText("Búsqueda");
        TBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TBusquedaActionPerformed(evt);
            }
        });
        TBusqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TBusquedaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TBusquedaFocusLost(evt);
            }
        });
        TBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBusquedaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TBusquedaKeyTyped(evt);
            }
        });

        BRapida.setText("Modificar Artículo");
        BRapida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRapidaActionPerformed(evt);
            }
        });

        BBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/16px/search.png"))); // NOI18N
        BBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBuscarActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        BAplicar.setText("Aplicar Cambios");
        BAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAplicarActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setText("Añadir Artículo");

        jButton2.setText("Eliminar Artículo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BBajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BRapida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BAplicar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(BCerrar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TTitulo)
                    .addComponent(TBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(BCerrar))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BTodo)
                        .addGap(18, 18, 18)
                        .addComponent(BBajo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BRapida)
                        .addGap(18, 18, 18)
                        .addComponent(BAplicar))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBusquedaActionPerformed

    private void TBusquedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TBusquedaFocusGained

        if ("Búsqueda".equals(TBusqueda.getText())) {
            TBusqueda.setText("");
            TBusqueda.setForeground(Color.black);
        }
    }//GEN-LAST:event_TBusquedaFocusGained

    private void BBajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBajoActionPerformed

        DefaultTableModel temp = (DefaultTableModel) Tabla.getModel();
        Object[] fila = new Object[4];
        int filas = Tabla.getRowCount();

        for (int i = 0; i < filas; i++) {

            temp.removeRow(0);

        }

        lista.clear();
        
        try {
            lista = Producto.consultarStock();
        } catch (SQLException ex) {
            Logger.getLogger(EstadoStock.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getCod_barras();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getStock_minimo();
            fila[3] = lista.get(i).getStock();

            temp.addRow(fila);
        }

        TTitulo.setText("Estado Stock (Bajo Mínimos)");

    }//GEN-LAST:event_BBajoActionPerformed

    private void BTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTodoActionPerformed
Toolkit.getDefaultToolkit().beep();
        DefaultTableModel temp = (DefaultTableModel) Tabla.getModel();
        Object[] fila = new Object[4];
        int filas = Tabla.getRowCount();

        for (int i = 0; i < filas; i++) {

            temp.removeRow(0);

        }

        lista.clear();
              
        try {
            lista = Producto.consultarTodoStock();
        } catch (SQLException ex) {
            Logger.getLogger(EstadoStock.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getCod_barras();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getStock_minimo();
            fila[3] = lista.get(i).getStock();

            temp.addRow(fila);
        }

        TTitulo.setText("Estado Stock (Todo)");

    }//GEN-LAST:event_BTodoActionPerformed

    private void BBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBuscarActionPerformed

        if (!TBusqueda.getText().equals("") || TBusqueda.getText().equals("Búsqueda")) {

            DefaultTableModel temp = (DefaultTableModel) Tabla.getModel();
            Object[] fila = new Object[4];
            int filas = Tabla.getRowCount();

            for (int i = 0; i < filas; i++) {

                temp.removeRow(0);

            }

            lista.clear();
            
            try {
                lista = Producto.busquedaStock(TBusqueda.getText());
            } catch (SQLException ex) {
                Logger.getLogger(EstadoStock.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i).getCod_barras();
                fila[1] = lista.get(i).getNombre();
                fila[2] = lista.get(i).getStock_minimo();
                fila[3] = lista.get(i).getStock();

                temp.addRow(fila);
            }

            TTitulo.setText("Estado Stock (Búsqueda)");
            // TODO add your handling code here:
        }
    }//GEN-LAST:event_BBuscarActionPerformed

    private void BCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCerrarActionPerformed
        cerrar(null);
        

    }//GEN-LAST:event_BCerrarActionPerformed

    
    private void TBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBusquedaKeyTyped
        
    }//GEN-LAST:event_TBusquedaKeyTyped

    private void TBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBusquedaKeyPressed

        if(evt.getKeyCode()== evt.VK_ENTER){
            
            BBuscar.doClick();
            
        }
    }//GEN-LAST:event_TBusquedaKeyPressed
   
    private void BAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAplicarActionPerformed
        
        int filas = Tabla.getRowCount();
        if (filas > 0) {

            ArrayList<Integer> mod = new ArrayList<>();
            Producto p, paux = new Producto();

            for (int i = 0; i < filas; i++) {
                paux.setCod_barras((int) Tabla.getValueAt(i, 0));
                p = lista.get(lista.indexOf(paux));

                if (p.getStock_minimo() != (int) Tabla.getValueAt(i, 2) || p.getStock() != (int) Tabla.getValueAt(i, 3)) {

                    p.setStock_minimo((int) Tabla.getValueAt(i, 2));
                    p.setStock((int) Tabla.getValueAt(i, 3));
                    p.modificar_cambios();

                }

            }


        }

    }//GEN-LAST:event_BAplicarActionPerformed

    private void BRapidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRapidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BRapidaActionPerformed

    private void TablaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TablaFocusLost
        Tabla.editCellAt(-1, -1);
    }//GEN-LAST:event_TablaFocusLost

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if(MAXIMIZED_BOTH==getExtendedState())
            jSeparator2.setVisible(true);
        else
            jSeparator2.setVisible(false);
            
    }//GEN-LAST:event_formWindowStateChanged

    private void TBusquedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TBusquedaFocusLost

    }//GEN-LAST:event_TBusquedaFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        EliminarArticulo dialogo_eliminacion=null;
        try {
            dialogo_eliminacion = new EliminarArticulo(null,true);
        } catch (IOException ex) {
            Logger.getLogger(EstadoStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    
            dialogo_eliminacion.setLocation(500,250);            
            dialogo_eliminacion.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cerrar(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_cerrar
        this.setVisible(false);
        this.setEnabled(false);
        this.dispose();
    }//GEN-LAST:event_cerrar

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EstadoStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EstadoStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EstadoStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EstadoStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new EstadoStock().setVisible(true);
            }
        });
    }   
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BAplicar;
    private javax.swing.JButton BBajo;
    private javax.swing.JButton BBuscar;
    private javax.swing.JButton BCerrar;
    private javax.swing.JButton BRapida;
    private javax.swing.JButton BTodo;
    private javax.swing.JTextField TBusqueda;
    private javax.swing.JLabel TTitulo;
    private javax.swing.JTable Tabla;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables
}