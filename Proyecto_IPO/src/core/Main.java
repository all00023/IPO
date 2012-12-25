/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import gui.PPal;
import gui.Panel;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

/**
 *
 * @author Adrian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {

        ServerSocket SERVER_SOCKET;
        try {
            SERVER_SOCKET = new ServerSocket(9582);
        } catch (IOException ex) {
            Panel.error("ERROR", "Ya hay una instacia del programa abierta.");       
        }
        
         
        
        /*
         * Prueba insertar producto en base de datos 
         */
        
        
//        producto pr=new producto(999, "producto auxiliar", (float)0.0, 5, 2, "vacio");
        
//        producto.borrarDB(999);
        
//        pr.insertar();
        
        
        /*
         * Prueba constructor que carga de la base de datos
         */
//        producto p = new producto(999);
        
//        System.out.println(p.getCod_barras() + " " + p.getNombre() + " " + p.getFormato() + " " + p.getStock() + " " + p.getStock_minimo() + " " + p.getPrecio());
        
        /*
         * Prueba eliminar un dato de la base de datos
         */
        
//        producto.borrarDB(999);
        
        /*
         *      Test Imprimir Ticket
         */
//        ticket y = new ticket(1);
//        y.insertarLinea(1005, 1, 1);        
//        y.insertarLinea(1001, 4, 2);        
//        y.insertarLinea(1004, 7, 4);        
//        y.imprimir_ticket();
//               
        
//        EstadoStock estado = new EstadoStock();
//        estado.setLocation(100, 100);
//        estado.setEnabled(true);
//        estado.setVisible(true);
        PPal principal=new PPal();
        principal.setEnabled(true);
        principal.setVisible(true);
       
        
    
    }
}
