/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import Operaciones.Operaciones;
import gui.Panel;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Adrian
 */
public class producto {

    private int cod_barras, stock, stock_minimo;
    private String nombre, formato;
    private float precio;

    public producto(int cod_barras, String nombre, float precio, int stock,
            int stock_minimo, String formato) {

        this.cod_barras = cod_barras;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.nombre = nombre;
        this.formato = formato;
        this.precio = precio;
    }

    public producto(int cod_barras) throws SQLException {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");

        ResultSet resultados = db.consultar("SELECT * FROM productos WHERE cod_barras=" + cod_barras);
        

        if (!resultados.isClosed()) {
            this.cod_barras = resultados.getInt("cod_barras");
            nombre = resultados.getString("nombre");
            precio = resultados.getFloat("precio");
            stock = resultados.getInt("stock");
            stock_minimo = resultados.getInt("stock_minimo");
            formato = resultados.getString("formato");
        } else {

            Panel.error("ERROR", "No existe el producto.");
            this.cod_barras = -1;
            Toolkit.getDefaultToolkit().beep();
        }

    }
    
    public producto(int _cod_barras, String url) throws SQLException, MalformedURLException, IOException {


        URL urlpagina = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String linea = "";


        urlpagina = new URL("http://www.logista.es/LogistaWeb_v2/tarifario/detalleArticulo.asp?hidCodArt=" + _cod_barras);
        isr = new InputStreamReader(urlpagina.openStream());
        br = new BufferedReader(isr);


        if (isr.ready()) {

            this.cod_barras = _cod_barras;
            int corte;

            while (linea.indexOf("foto_artic") == -1) {
                linea = br.readLine();
            }

            corte = linea.indexOf("\"");
            url = "http://www.logista.es/LogistaWeb_v2/tarifario/" + linea.substring(corte + 1, linea.indexOf("\"", corte + 1));
            url.replaceAll("/../..", "");

            while ((linea = br.readLine()) != null && linea.indexOf("nombrecampo") == -1) {
            }


            while (linea.indexOf("contenidotabla") == -1) {
                linea = br.readLine();
            }
            corte = linea.indexOf(";");
            this.nombre = linea.substring(corte + 1, linea.indexOf("<", corte));

            while ((linea = br.readLine()) != null && linea.indexOf("nombrecampo") == -1) {
            }

            while (linea.indexOf("contenidotabla") == -1) {
                linea = br.readLine();
            }
            corte = linea.indexOf(";");
            this.formato = linea.substring(corte + 1, linea.indexOf("<", corte));

            while ((linea = br.readLine()) != null && linea.indexOf("nombrecampo") == -1) {
            }
            while ((linea = br.readLine()) != null && linea.indexOf("nombrecampo") == -1) {
            }
            while ((linea = br.readLine()) != null && linea.indexOf("nombrecampo") == -1) {
            }

            while (linea.indexOf("contenidotabla") == -1) {
                linea = br.readLine();
            }
            corte = linea.indexOf(">");
            if (linea.substring(corte + 1, linea.indexOf(" ", corte)) == "A") {
                this.precio = Float.parseFloat(linea.substring(corte + 1, linea.indexOf(" ", corte)));
            } else {
                this.precio = (float) 0.0;
            }

            this.stock = 0;
            this.stock_minimo = 0;


        } else {
            cod_barras = -1;
        }
        br.close();
        isr.close();

    }

    public void insertar() throws SQLException {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");

        ResultSet resultados = db.consultar("SELECT cod_barras FROM productos WHERE cod_barras=" + cod_barras);

        if (!resultados.isClosed()) {
            Panel.error("ERROR", "El producto ya existe en la Base de Datos.");
        } else {
            db.insertar("INSERT INTO productos VALUES (" + cod_barras + ",'" + nombre + "',"
                    + precio + "," + stock + "," + stock_minimo + ",'" + formato + "')");
        }
    }

    public void modificar_cambios() {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");

        String aux = "UPDATE productos SET nombre='" + nombre + "',precio=" + precio
                + ",stock=" + stock + ",stock_minimo=" + stock_minimo + ",formato='" + formato
                + "' WHERE cod_barras=" + cod_barras;

        db.insertar(aux);

    }

    public void setCod_barras(int cod_barras) {
        this.cod_barras = cod_barras;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public int getCod_barras() {
        return cod_barras;
    }

    public String getFormato() {
        return formato;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public static void borrarDB(int codibarra) {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");
        db.insertar("DELETE FROM productos WHERE cod_barras=" + codibarra);
        

    }

    public static ArrayList<producto> consultarStock() throws SQLException {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");
        ResultSet resultados = db.consultar("SELECT * "
                + "FROM productos WHERE stock<=stock_minimo");

        ArrayList<producto> lista = new ArrayList<>();

        resultados.next();

        if (!resultados.isClosed()) {

            while (!resultados.isAfterLast()) {
                lista.add(new producto(resultados.getInt("cod_barras"), resultados.getString("nombre"),
                        resultados.getFloat("precio"), resultados.getInt("stock"), resultados.getInt("stock_minimo"),
                        resultados.getString("formato")));
                resultados.next();
            }
        }

        return lista;

    }

    static void actualizarStock(ArrayList<producto> lista) {
    }

    public static ArrayList<producto> consultarTodoStock() throws SQLException {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");
        ResultSet resultados = db.consultar("SELECT * FROM productos");

        ArrayList<producto> lista = new ArrayList<>();

        resultados.next();

        if (!resultados.isClosed()) {

            while (!resultados.isAfterLast()) {
                lista.add(new producto(resultados.getInt("cod_barras"), resultados.getString("nombre"),
                        resultados.getFloat("precio"), resultados.getInt("stock"), resultados.getInt("stock_minimo"),
                        resultados.getString("formato")));
                resultados.next();
            }
        }

        return lista;

    }

    public static ArrayList<producto> busquedaStock(String codigo) throws SQLException {

        Operaciones db = new Operaciones("..\\Base de Datos\\TPV");
        ResultSet resultados = db.consultar("SELECT * FROM productos WHERE cod_barras LIKE '%" + codigo + "%'");

        ArrayList<producto> lista = new ArrayList<>();

        resultados.next();

        if (!resultados.isClosed()) {

            while (!resultados.isAfterLast()) {
                lista.add(new producto(resultados.getInt("cod_barras"), resultados.getString("nombre"),
                        resultados.getFloat("precio"), resultados.getInt("stock"), resultados.getInt("stock_minimo"),
                        resultados.getString("formato")));
                resultados.next();
            }
        }

        return lista;

    }

    public producto() {
    }
    
	    public String getImageUrl() throws MalformedURLException, IOException {



        String url="";
        URL urlpagina = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String linea = "";


        urlpagina = new URL("http://www.logista.es/LogistaWeb_v2/tarifario/detalleArticulo.asp?hidCodArt=" + this.cod_barras);
        isr = new InputStreamReader(urlpagina.openStream());
        br = new BufferedReader(isr);


        if (isr.ready()) {

            int corte;

            while (linea.indexOf("foto_artic") == -1) {
                linea = br.readLine();
            }

            corte = linea.indexOf("\"");
            url = "http://www.logista.es/LogistaWeb_v2/tarifario/" + linea.substring(corte + 1, linea.indexOf("\"", corte + 1));

            
        }
        br.close();
        isr.close();

        return url;
    }
    
    
    @Override
    public boolean equals(Object o){
        
        boolean aux =((producto)o).cod_barras==cod_barras;
        
    return aux;
    }


}
