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
import javax.print.DocFlavor;

/**
 *
 * @author Adrian
 */
public class Ticket {

    private int id;
    private String fecha, hora;
    private float total, iva = 21;
    ArrayList<Ticket_Producto> lineasfactura;
    private static String RUTA_BBDD = "BBDD\\TPV";

    public Ticket() throws SQLException {

        Calendar aux = Calendar.getInstance();
        this.id = getNextId();
        lineasfactura = new ArrayList<>();
        String dia = String.valueOf(aux.get(Calendar.DAY_OF_MONTH)),
                mes = String.valueOf(aux.get(Calendar.MONTH) + 1), año = String.valueOf(aux.get(Calendar.YEAR)),
                minuto = String.valueOf(aux.get(Calendar.MINUTE)), _hora = String.valueOf(aux.get(Calendar.HOUR_OF_DAY));

        if (dia.length() < 2) {
            dia = '0' + dia;
        }
        if (mes.length() < 2) {
            mes = '0' + mes;
        }
        if (minuto.length() < 2) {
            minuto = '0' + minuto;
        }
        if (_hora.length() < 2) {
            _hora = '0' + _hora;
        }

        fecha = dia + "/" + mes + "/" + año;
        hora = _hora + ":" + minuto;

        calcularTotal();

    }

    public Ticket(int id) throws SQLException {

        Operaciones db = new Operaciones(RUTA_BBDD);

        ResultSet resultados = db.consultar("SELECT * FROM ticket WHERE id=" + id);

        if (!resultados.isClosed()) {

            this.id = resultados.getInt("id");
            String aux = resultados.getString("fecha");
            this.fecha = aux.charAt(6) + aux.charAt(7) + "/" + aux.charAt(4) + aux.charAt(5) + "/" + aux.charAt(0) + aux.charAt(1) + aux.charAt(2) + aux.charAt(3);
            this.hora = resultados.getString("hora");
            this.total = resultados.getFloat("total");
            this.iva = resultados.getFloat("iva");

            lineasfactura = new ArrayList<>();

            resultados.close();

            resultados = db.consultar("SELECT * FROM ticket_producto WHERE idticket=" + id);
            resultados.next();

            while (!resultados.isAfterLast()) {

                lineasfactura.add(new Ticket_Producto(id, resultados.getInt("idproducto"),
                        resultados.getInt("cantidad"), resultados.getFloat("precio")));

                resultados.next();


            }


        } else {
            Panel.error("ERROR", "No existe el producto.");
            this.id = -1;
            Toolkit.getDefaultToolkit().beep();
        }

        resultados.close();

    }

    public Ticket(List<Ticket_Producto> list) throws SQLException {

        Calendar aux = Calendar.getInstance();
        this.id = getNextId();

        String dia = String.valueOf(aux.get(Calendar.DAY_OF_MONTH)),
                mes = String.valueOf(aux.get(Calendar.MONTH) + 1), año = String.valueOf(aux.get(Calendar.YEAR)),
                minuto = String.valueOf(aux.get(Calendar.MINUTE)), _hora = String.valueOf(aux.get(Calendar.HOUR_OF_DAY));

        if (dia.length() < 2) {
            dia = '0' + dia;
        }
        if (mes.length() < 2) {
            mes = '0' + mes;
        }
        if (minuto.length() < 2) {
            minuto = '0' + minuto;
        }
        if (_hora.length() < 2) {
            _hora = '0' + _hora;
        }

        fecha = dia + "/" + mes + "/" + año;
        hora = _hora + ":" + minuto;

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIdticket(id);
        }

        lineasfactura = new ArrayList<>(list);

        calcularTotal();

    }

    private void calcularTotal() {

        total = 0;

        for (int i = 0; i < lineasfactura.size(); i++) {

            total += lineasfactura.get(i).getPrecio() * lineasfactura.get(i).getCantidad();

        }

    }

    public boolean insertarLinea(int idProducto, int cantidad, float precio) {

        boolean encontrado = false;

        for (int i = 0; i < lineasfactura.size(); i++) {

            if (lineasfactura.get(i).getIdproducto() == idProducto) {

                encontrado = true;

            }

        }

        if (!encontrado) {

            lineasfactura.add(new Ticket_Producto(id, idProducto, cantidad, precio));

            total += precio * cantidad;

        }

        calcularTotal();

        return !encontrado;

    }

    public boolean eliminarLinea(int idproducto) {

        boolean encontrado = false;

        for (int i = 0; i < lineasfactura.size(); i++) {

            if (lineasfactura.get(i).getIdproducto() == idproducto) {

                total -= lineasfactura.get(i).getPrecio() * lineasfactura.get(i).getCantidad();


            }

        }

        calcularTotal();

        return encontrado;

    }

    @Override
    public String toString() {

        ArrayList<String> datos = getConfiguracion();

        if (lineasfactura.size() > 0) {
            String nombre = "";
            String aux;

            Collections.sort(lineasfactura);


            Operaciones db = new Operaciones(RUTA_BBDD);
            String bw = "";

            bw += datos.get(0);
            bw += "\n";
            bw += datos.get(1);
            bw += "\n";
            bw += "\n";
            bw += datos.get(2);
            bw += "\n";
            bw += datos.get(3) + " " + (datos.get(4) == null ? "" : datos.get(4)); //en la base de datos este valor puede ser nulo
            bw += "\n";
            bw += datos.get(5);
            bw += "\n";
            bw += "\n";
            bw += fecha + "  " + hora;
            bw += "\n";
            bw += "\n";
            bw += "=====================================";
            bw += "\n";
            bw += "Art  Cant  Producto          Precio";
            bw += "\n";

            aux = "SELECT nombre FROM productos WHERE cod_barras=" + lineasfactura.get(0).getIdproducto();
            for (int i = 1; i < lineasfactura.size(); i++) {

                aux += " OR cod_barras=" + lineasfactura.get(i).getIdproducto();


            }
            aux += " ORDER BY cod_barras";

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

                //rellenamos con espacios si no llegamos a los 15 caracteres
                for (int j = nombre.length(); j <= 15; j++) {

                    nombre += " ";

                }

                String auxString = String.valueOf(lineasfactura.get(i).getPrecio());

                int indice = auxString.indexOf(".");

                if (indice > 0 && indice < auxString.length() - 3) {
                    auxString = auxString.substring(0, indice + 3);
                }

                bw += "\n";
                bw += lineasfactura.get(i).getIdproducto() + "  "
                        + lineasfactura.get(i).getCantidad() + "    "
                        + nombre.substring(0, 15) + "   "
                        + auxString + " €";
                bw += "\n";

            }
            try {
                resultados.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }

            String auxString = String.valueOf(total);

            int indice = auxString.indexOf(".");

            if (indice > 0 && indice < auxString.length() - 3) {
                auxString = auxString.substring(0, indice + 3);
            }

            bw += "=====================================";
            bw += "\n";
            bw += "\n";
            bw += "                     Total:  " + auxString + " €";
            bw += "\n";
            bw += "\n";
            bw += "---------" + iva + "% I.V.A. Incluido---------";


            return bw;


        } else {
            Panel.error("ERROR", "Ticket Vacio.");
        }

        return "";

    }

    public void imprimir_ticket() throws IOException, SQLException {

        ArrayList<String> datos = getConfiguracion();

        if (lineasfactura.size() > 0) {
            String nombre;
            String aux;

            Collections.sort(lineasfactura);


            Operaciones db = new Operaciones(RUTA_BBDD);

            BufferedWriter bw = new BufferedWriter(new FileWriter("tickets" + File.separatorChar + "ticket" + id + ".txt"));

            bw.write(datos.get(0));
            bw.newLine();
            bw.write(datos.get(1));
            bw.newLine();
            bw.newLine();
            bw.write(datos.get(2));
            bw.newLine();
            bw.write(datos.get(3) + " " + (datos.get(4) == null ? "" : datos.get(4))); //en la base de datos este valor puede ser nulo
            bw.newLine();
            bw.write(datos.get(5));
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
            aux += " ORDER BY cod_barras";

            ResultSet resultados = db.consultar(aux);

            resultados.next();

            for (int i = 0; i < lineasfactura.size(); i++) {

                nombre = resultados.getString(1);
                resultados.next();

                //rellenamos con espacios si no llegamos a los 15 caracteres
                for (int j = nombre.length(); j <= 15; j++) {

                    nombre += " ";

                }

                String auxString = String.valueOf(lineasfactura.get(i).getPrecio());

                int indice = auxString.indexOf(".");

                if (indice > 0 && indice < auxString.length() - 3) {
                    auxString = auxString.substring(0, indice + 3);
                }

                bw.newLine();
                bw.write(lineasfactura.get(i).getIdproducto() + "  "
                        + lineasfactura.get(i).getCantidad() + "    "
                        + nombre.substring(0, 15) + "   "
                        + auxString + " €");
                bw.newLine();

            }

            resultados.close();

            String auxString = String.valueOf(total);

            int indice = auxString.indexOf(".");

            if (indice > 0 && indice < auxString.length() - 3) {
                auxString = auxString.substring(0, indice + 3);
            }

            bw.write("=====================================");
            bw.newLine();
            bw.newLine();
            bw.write("                     Total:  " + auxString + " €");
            bw.newLine();
            bw.newLine();
            bw.write("---------" + 21 + "% I.V.A. Incluido---------");


            bw.close();

//            Desktop.getDesktop().open(new File("tickets" + File.separatorChar + "ticket" + id + ".txt"));

        } else {
            Panel.error("ERROR", "Ticket Vacio.");
        }
    }

    public void abrirTicket() {
        try {
            Desktop.getDesktop().open(new File("tickets" + File.separatorChar + "ticket" + id + ".txt"));
        } catch (IOException ex) {
            Panel.error("No se encuentra el ticket", "El fichero no se encuentra en la carpeta ticket o esta siendo usado por otra aplicación");
        }
    }

    public static int getNextId() throws SQLException {

        Operaciones db = new Operaciones(RUTA_BBDD);

        ResultSet resultados;

        resultados = db.consultar("SELECT id FROM ticket WHERE id=(SELECT MAX(id) FROM ticket)");

        int aux = resultados.getInt("id");
        resultados.close();
        return aux + 1;
    }

    public void insertar() throws SQLException {

        Operaciones db = new Operaciones(RUTA_BBDD);

        ResultSet resultados = db.consultar("SELECT id FROM ticket WHERE id=" + id);

        if (!resultados.isClosed()) {
            Panel.error("ERROR", "El ticket ya existe en la Base de Datos.");
        } else {

            String aux = "";
            aux += fecha.charAt(6);
            aux += fecha.charAt(7);
            aux += fecha.charAt(8);
            aux += fecha.charAt(9);
            aux += fecha.charAt(3);
            aux += fecha.charAt(4);
            aux += fecha.charAt(0);
            aux += fecha.charAt(1);

            db.insertar("INSERT INTO ticket VALUES (" + id + ",'" + aux + "','"
                    + hora + "'," + total + "," + iva + ")");

            for (int i = 0; i < lineasfactura.size(); i++) {

                db.insertar("INSERT INTO ticket_producto VALUES (" + lineasfactura.get(i).getIdproducto()
                        + "," + lineasfactura.get(i).getIdticket() + "," + lineasfactura.get(i).getPrecio()
                        + "," + lineasfactura.get(i).getCantidad() + ")");

                if (lineasfactura.get(i).getCantidad() > 0) {

                    db.insertar("UPDATE productos SET nventas=nventas+" + lineasfactura.get(i).getCantidad() + ",stock=stock-"
                            + lineasfactura.get(i).getCantidad() + " WHERE cod_barras=" + lineasfactura.get(i).getIdproducto());

                } else {

                    int auxInt=lineasfactura.get(i).getCantidad();
                    auxInt=Math.abs(auxInt);
                    
                    db.insertar("UPDATE productos SET nventas=nventas-" + auxInt + ",stock=stock+"
                            + auxInt + " WHERE cod_barras=" + lineasfactura.get(i).getIdproducto());

                }
            }
        }
        resultados.close();
    }

    public static ArrayList<ArrayList<String>> consultarIds() throws SQLException {

        ArrayList<ArrayList<String>> lista = new ArrayList<>();

        Operaciones db = new Operaciones(RUTA_BBDD);

        ResultSet resultados;
        resultados = db.consultar("SELECT * FROM ticket");

        resultados.next();

        while (!resultados.isAfterLast()) {

            ArrayList<String> listaAux = new ArrayList<>();

            listaAux.add(String.valueOf(resultados.getInt("id")));
            String aux = resultados.getString("fecha");
            String aux2 = "";
            //esto es por problemas de char(convierte algunos enteros en binario)
            aux2 += aux.charAt(6);
            aux2 += (char) aux.charAt(7);
            aux = aux2 + "/" + aux.charAt(4) + aux.charAt(5) + "/" + aux.charAt(0) + aux.charAt(1) + aux.charAt(2) + aux.charAt(3);
            listaAux.add(aux);
            listaAux.add(resultados.getString("hora"));

            lista.add(listaAux);

            resultados.next();
        }

        resultados.close();

        return lista;
    }

    public static ArrayList<ArrayList<String>> consultarIdsPorFecha(String fecha1, String fecha2) throws SQLException {

        ArrayList<ArrayList<String>> lista = new ArrayList<>();

        Operaciones db = new Operaciones(RUTA_BBDD);
        ResultSet resultados;

        String aux1 = "";
        aux1 += fecha1.charAt(6);
        aux1 += fecha1.charAt(7);
        aux1 += fecha1.charAt(8);
        aux1 += fecha1.charAt(9);
        aux1 += fecha1.charAt(3);
        aux1 += fecha1.charAt(4);
        aux1 += fecha1.charAt(0);
        aux1 += fecha1.charAt(1);

        String aux2 = "";
        aux2 += fecha2.charAt(6);
        aux2 += fecha2.charAt(7);
        aux2 += fecha2.charAt(8);
        aux2 += fecha2.charAt(9);
        aux2 += fecha2.charAt(3);
        aux2 += fecha2.charAt(4);
        aux2 += fecha2.charAt(0);
        aux2 += fecha2.charAt(1);

        resultados = db.consultar("SELECT * FROM ticket");

        resultados.next();

        while (!resultados.isAfterLast()) {
            ArrayList<String> listaAux = new ArrayList<>();

            listaAux.add(String.valueOf(resultados.getInt("id")));
            String aux = resultados.getString("fecha");

            //Comprobamos las fechas, estan en formato YYYYMMDD para poder comparaar directamente
            if (Integer.valueOf(aux1) <= Integer.valueOf(aux) && Integer.valueOf(aux) <= Integer.valueOf(aux2)) {

                String aux3 = "";

                //esto es por problemas de char(convierte algunos enteros(char) en binario)
                aux3 += aux.charAt(6);
                aux3 += (char) aux.charAt(7);
                aux = aux3 + "/" + aux.charAt(4) + aux.charAt(5) + "/" + aux.charAt(0) + aux.charAt(1) + aux.charAt(2) + aux.charAt(3);
                listaAux.add(aux);
                listaAux.add(resultados.getString("hora"));

                lista.add(listaAux);

            }
            resultados.next();
        }

        resultados.close();

        return lista;
    }

    public Ticket_Producto getLineaFactura(int i) {
        return lineasfactura.get(i);
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public static void setConfiguracion(ArrayList<String> conf) {

        String nombre = conf.get(0);
        String cif = conf.get(1);
        String direccion = conf.get(2);
        String ciudad = conf.get(3);
        String cp = conf.get(4);
        String provincia = conf.get(5);

        Operaciones db = new Operaciones(RUTA_BBDD);

        String aux = "UPDATE configuracion SET nombre='" + nombre + "',cif='" + cif
                + "',direccion='" + direccion + "',ciudad='" + ciudad + "',provincia='" + provincia
                + "',cp='" + cp + "'";

        db.insertar(aux);

    }

    public static ArrayList<String> getConfiguracion() {

        ArrayList<String> config = new ArrayList<>();

        try {

            Operaciones db = new Operaciones(RUTA_BBDD);
            ResultSet resultados = db.consultar("SELECT * FROM configuracion");

            if (!resultados.isClosed()) {

                config.add(resultados.getString("nombre"));
                config.add(resultados.getString("cif"));
                config.add(resultados.getString("direccion"));
                config.add(resultados.getString("ciudad"));
                config.add(resultados.getString("cp"));
                config.add(resultados.getString("provincia"));

            }

            resultados.close();

        } catch (SQLException ex) {
            Panel.error("Error SQL", "Compruebe que ningun programa este usando la base de datos");
        }


        return config;

    }
}
