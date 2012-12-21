/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Adrian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        URL urlpagina = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String linea="";
        StringBuffer buffer = new StringBuffer();
        boolean encontrado=false;

        try {
            urlpagina = new URL("http://www.logista.es/LogistaWeb_v2/tarifario/catalogoCompleto.asp");
            isr = new InputStreamReader(urlpagina.openStream());
            br = new BufferedReader(isr);
            while (((linea = br.readLine()) != null) && encontrado==false) {
                if(linea.indexOf(">4764<") != -1){
                    buffer.append(linea);   
                    encontrado=true;
                }
            }
            if(encontrado==true){
                linea="";
                br.readLine();
                br.readLine();
                linea=br.readLine();
            }
                
                
            br.close();
            isr.close();
        } catch (MalformedURLException e) {
            System.out.println("Error en la url, ejemplo http://www.java-elrincondetucasa.blogspot.com");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }
        int corte=linea.indexOf(">");
        int corte2=linea.indexOf(" ",corte);
        if(linea!=null){
            linea=linea.substring(corte+1, corte2);
        }
        
        
        System.out.println(linea);

    }
}
