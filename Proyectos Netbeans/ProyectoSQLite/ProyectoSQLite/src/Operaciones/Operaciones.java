/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Operaciones extends Conexion {

    /**
     * Constructor for objects of class Operaciones
     */
    public Operaciones(String ruta) {
        super(ruta);
    }

    public Operaciones() {
    }
    

    public boolean insertar(String sql) {
        boolean valor = true;
        conectar();
        try {
            consulta.executeUpdate(sql);
        } catch (SQLException e) {
            valor = false;
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                consulta.close();
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valor;
    }

    public ResultSet consultar(String sql) {
        conectar();
        ResultSet resultado = null;
        try {
            resultado = consulta.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println("Mensaje:" + e.getMessage());
            System.out.println("Estado:" + e.getSQLState());
            System.out.println("Codigo del error:" + e.getErrorCode());
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
        }
        return resultado;
    }

    
}