/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import Operaciones.Operaciones;
import gui.Panel;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
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
    private float total,iva=21;
    
    ArrayList<Ticket_Producto> lineasfactura;

    public Ticket() throws SQLException {
        Calendar aux= Calendar.getInstance();
        this.id = getNextId();
        lineasfactura=new ArrayList<>();
        String dia = String.valueOf(aux.get(Calendar.DAY_OF_MONTH)), 
                mes=String.valueOf(aux.get(Calendar.MONTH)+1), año = String.valueOf(aux.get(Calendar.YEAR)),
                minuto = String.valueOf(aux.get(Calendar.MINUTE)), _hora = String.valueOf(aux.get(Calendar.HOUR_OF_DAY));
        
        if(dia.length()<2) dia='0'+dia;
        if(mes.length()<2) mes='0'+mes;
        if(minuto.length()<2) minuto='0'+minuto;
        if(_hora.length()<2) _hora='0'+_hora;
        
        fecha = dia + "/" + mes + "/" + año;
        hora = _hora + ":" + minuto;
         
    }
    
    public Ticket(int id) throws SQLException{
        
        Operaciones db= new Operaciones("..\\Base de Datos\\TPV");
        
        ResultSet resultados=db.consultar("SELECT * FROM ticket WHERE id="+id);
        
        if(!resultados.isClosed()){
            
            this.id=resultados.getInt("id");
            String aux=resultados.getString("fecha");
            this.fecha = aux.charAt(6)+aux.charAt(7)+"/"+aux.charAt(4)+aux.charAt(5)+"/"+aux.charAt(0)+aux.charAt(1)+aux.charAt(2)+aux.charAt(3);
            this.hora=resultados.getString("hora");
            this.total=resultados.getFloat("total");
            this.iva=resultados.getFloat("iva");
            
            lineasfactura=new ArrayList<>();
            
            resultados = db.consultar("SELECT * FROM ticket_producto WHERE idticket=" + id);            
            resultados.next();
                
            while(!resultados.isAfterLast()) {
                    
                lineasfactura.add(new Ticket_Producto(id, resultados.getInt("isproducto"), 
                        resultados.getInt("cantidad"), resultados.getInt("precio")));
                        
                resultados.next();


            }
            
            
        }
        else{
            Panel.error("ERROR", "No existe el producto.");
            this.id = -1;
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
    public Ticket(List<Ticket_Producto> list){
        
        for(int i = 0 ; i < list.size() ; i++){
            list.get(i).setIdticket(id);
        }
        
        lineasfactura=new ArrayList<>(list);
        
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
    
    public static int getNextId() throws SQLException{
        
        Operaciones db= new Operaciones("..\\Base de Datos\\TPV");
        
        ResultSet resultados;
        
        resultados=db.consultar("SELECT id FROM ticket WHERE id=(SELECT MAX(id) FROM ticket)");
        
        int aux=resultados.getInt("id");
        
        return aux + 1;
    }
    
        public void insertar() throws SQLException {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");

        ResultSet resultados = db.consultar("SELECT id FROM productos WHERE id=" + id);

        if (!resultados.isClosed()) {
            Panel.error("ERROR", "El ticket ya existe en la Base de Datos.");
        } else {
            
            String aux="";
            aux+=fecha.charAt(6);
            aux+=fecha.charAt(7);
            aux+=fecha.charAt(8);
            aux+=fecha.charAt(9);
            aux+=fecha.charAt(3);
            aux+=fecha.charAt(4);
            aux+=fecha.charAt(0);
            aux+=fecha.charAt(1);
            
            db.insertar("INSERT INTO ticket VALUES (" + id + ",'" + aux + "','"
                    + hora + "'," + total + "," + iva + ")");
            
            for (int i = 0; i < lineasfactura.size(); i++) {
                
                db.insertar("INSERT INTO ticket_producto VALUES (" + lineasfactura.get(i).getIdproducto()+ 
                        "," + lineasfactura.get(i).getIdticket() + "," + lineasfactura.get(i).getPrecio()+
                        "," + lineasfactura.get(i).getCantidad() + ")");
                
            }
        }
    }
        
    public static ArrayList<Ticket> consultarTodo() throws SQLException{
        
        ArrayList<Ticket> lista=new ArrayList<>();
        ArrayList<Integer> ids=new ArrayList<>();
        
        Operaciones db= new Operaciones("..\\Base de Datos\\TPV");
        
        ResultSet resultados;
        
        resultados=db.consultar("SELECT id FROM ticket");
        
        resultados.next();
        
        while(!resultados.isAfterLast()){
            ids.add(resultados.getInt("id"));
            resultados.next();
        }
        
        for (int i = 0; i < ids.size(); i++) {
            
            lista.add(new Ticket(ids.get(i)));
            
        }
        
        return lista;
    }    
    
    public static ArrayList<Ticket> consultarPorFecha(String fecha1,String fecha2) throws SQLException{
        
        ArrayList<Ticket> lista=new ArrayList<>();
        ArrayList<Integer> ids=new ArrayList<>();
              
        Operaciones db= new Operaciones("..\\Base de Datos\\TPV"); 
        ResultSet resultados;
        
        String aux1="";
        aux1+=fecha1.charAt(6);
        aux1+=fecha1.charAt(7);
        aux1+=fecha1.charAt(8);
        aux1+=fecha1.charAt(9);
        aux1+=fecha1.charAt(3);
        aux1+=fecha1.charAt(4);
        aux1+=fecha1.charAt(0);
        aux1+=fecha1.charAt(1);
        
        String aux2="";
        aux2+=fecha2.charAt(6);
        aux2+=fecha2.charAt(7);
        aux2+=fecha2.charAt(8);
        aux2+=fecha2.charAt(9);
        aux2+=fecha2.charAt(3);
        aux2+=fecha2.charAt(4);
        aux2+=fecha2.charAt(0);
        aux2+=fecha2.charAt(1);
        
        resultados=db.consultar("SELECT id FROM ticket WHERE fecha>=" + fecha1 +" AND fecha<=" + fecha2);
        
        resultados.next();
        
        while(!resultados.isAfterLast()){
            ids.add(resultados.getInt("id"));
            resultados.next();
        }
        
        for (int i = 0; i < ids.size(); i++) {
            
            lista.add(new Ticket(ids.get(i)));
            
        }
        
        return lista;
    }        
    
    public Ticket_Producto getLineaFactura(int i){
        return lineasfactura.get(i);
    }
    
}
