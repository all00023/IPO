/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import Operaciones.Operaciones;
import gui.Panel;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian
 */
public class Ticket {
    
    private static String CIF = "A12345678";
    private static String NombreCompañia="Alibel";
    private static String Direccion="C\\ Falsa 123";
    private static String Ciudad="Huelva 21001 ";
    private static String Provincia="Huelva";
    
    private int id;
    private String fecha, hora;
    float total,iva=21;
    
    ArrayList<Ticket_Producto> lineasfactura;

    public Ticket(int id) {
        Calendar aux= Calendar.getInstance();
        this.id = id;
        lineasfactura=new ArrayList<>();
        fecha = aux.get(Calendar.DAY_OF_MONTH) + "/" + (aux.get(Calendar.MONTH)+1) + "/" + aux.get(Calendar.YEAR);
        hora = aux.get(Calendar.HOUR_OF_DAY) + ":" + aux.get(Calendar.MINUTE);
         
    }

    
    private void calcularTotal(){
        
        total=0;
                
        for(int i = 0 ; i < lineasfactura.size(); i++ ){
            
            total+=lineasfactura.get(i).getPrecio();
            
        }
        
    }
    
    
    public boolean insertarLinea(int idProducto,int cantidad,float precio){
        
        boolean encontrado=false;
        
        for(int i = 0 ; i < lineasfactura.size(); i++ ){
            
            if(lineasfactura.get(i).getIdproducto() == idProducto){
                
                encontrado=true;
                
            }
            
        }
        
        if(!encontrado){
        
            lineasfactura.add(new Ticket_Producto(id, idProducto, cantidad, precio));

            total+=precio*cantidad;
            
        }
        
        return !encontrado;
        
    }
    
    public boolean eliminarLinea(int idproducto){
        
        boolean encontrado=false;
        
        for(int i = 0 ; i < lineasfactura.size(); i++ ){
            
            if(lineasfactura.get(i).getIdproducto() == idproducto){
                
                total-=lineasfactura.get(i).getPrecio() * lineasfactura.get(i).getCantidad();
                
                
            }
            
        }
        
        return encontrado;
                
    } 
    
    @Override
    public String toString(){
        
         if(lineasfactura.size()>0){
            String nombre="";
            String aux;

            Collections.sort(lineasfactura);
            
            
            Operaciones db= new Operaciones("..\\Base de Datos\\TPV");
            String bw="";

            bw.concat(NombreCompañia);
            bw.concat("\n");
            bw.concat(CIF);
            bw.concat("\n");
            bw.concat("\n");
            bw.concat(Direccion);
            bw.concat("\n");;
            bw.concat(Ciudad);
            bw.concat("\n");
            bw.concat(Provincia);
            bw.concat("\n");
            bw.concat("\n");
            bw.concat(fecha + "  " + hora);
            bw.concat("\n");
            bw.concat("\n");
            bw.concat("=====================================");
            bw.concat("\n");
            bw.concat("Art  Cant  Producto          Precio");
            bw.concat("\n");

            aux = "SELECT nombre FROM productos WHERE cod_barras=" + lineasfactura.get(0).getIdproducto();
            for (int i = 1; i < lineasfactura.size(); i++) {

                aux += " OR cod_barras=" + lineasfactura.get(i).getIdproducto();


            }
            aux+=" ORDER BY cod_barras";
            
            ResultSet resultados = db.consultar(aux);
            try {
                resultados.next();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            for (int i = 0; i < lineasfactura.size(); i++) {
                try {
                    nombre = resultados.getString(1);
                } catch (SQLException ex) {
                    Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    resultados.next();
                } catch (SQLException ex) {
                    Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
                }

                bw.concat("\n");
                bw.concat(lineasfactura.get(i).getIdproducto() + "  "
                        + lineasfactura.get(i).getCantidad() + "    "
                        + nombre.substring(0, 15) + "   "
                        + lineasfactura.get(i).getPrecio() + " €");
                bw.concat("\n");

            }

            bw.concat("=====================================");
            bw.concat("\n");
            bw.concat("\n");
            bw.concat("                     Total:  " + total + " €");
            bw.concat("\n");
            bw.concat("\n");
            bw.concat("---------21% I.V.A. Incluido---------");


            return bw; 
            

        } else {
            Panel.error("ERROR", "Ticket Vacio.");
        }
        
         return "";
         
    } 
    
    public void imprimir_ticket() throws IOException, SQLException{
        
        if(lineasfactura.size()>0){
            String nombre;
            String aux;

            Collections.sort(lineasfactura);
            
            
            Operaciones db= new Operaciones("..\\Base de Datos\\TPV");

            BufferedWriter bw = new BufferedWriter(new FileWriter("tickets" + File.separatorChar + "ticket"+id+".txt"));

            bw.write(NombreCompañia);
            bw.newLine();
            bw.write(CIF);
            bw.newLine();
            bw.newLine();
            bw.write(Direccion);
            bw.newLine();
            bw.write(Ciudad);
            bw.newLine();
            bw.write(Provincia);
            bw.newLine();
            bw.newLine();
            bw.write(fecha + "  " + hora);
            bw.newLine();
            bw.newLine();
            bw.write("=====================================");
            bw.newLine();
            bw.write("Art  Cant  Producto          Precio");
            bw.newLine();

            aux = "SELECT nombre FROM productos WHERE cod_barras=" + lineasfactura.get(0).getIdproducto();
            for (int i = 1; i < lineasfactura.size(); i++) {

                aux += " OR cod_barras=" + lineasfactura.get(i).getIdproducto();


            }
            aux+=" ORDER BY cod_barras";
            
            ResultSet resultados = db.consultar(aux);
            
            resultados.next();
                    
            for (int i = 0; i < lineasfactura.size(); i++) {

                nombre = resultados.getString(1);
                resultados.next();

                bw.newLine();
                bw.write(lineasfactura.get(i).getIdproducto() + "  "
                        + lineasfactura.get(i).getCantidad() + "    "
                        + nombre.substring(0, 15) + "   "
                        + lineasfactura.get(i).getPrecio() + " €");
                bw.newLine();

            }

            bw.write("=====================================");
            bw.newLine();
            bw.newLine();
            bw.write("                     Total:  " + total + " €");
            bw.newLine();
            bw.newLine();
            bw.write("---------21% I.V.A. Incluido---------");


            bw.close();

            Desktop.getDesktop().open(new File("tickets" + File.separatorChar + "ticket" + id + ".txt"));

        } else {
            Panel.error("ERROR", "Ticket Vacio.");
        }
    }
}
