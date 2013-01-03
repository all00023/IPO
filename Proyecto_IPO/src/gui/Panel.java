/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JOptionPane;

/**
 *
 * @author JOSE
 */
public class Panel {
     public static int confirmacion_si_no (String titulo, String mensaje){
        int x=0;
        x=javax.swing.JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return x;
    }
    
    public static int confirmacion_cancelar_aceptar (String titulo, String mensaje){
        int x=0;
        x=javax.swing.JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return x;
    }
    
    public static void error (String titulo, String mensaje){
        try {
            javax.swing.JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        
    }
}
