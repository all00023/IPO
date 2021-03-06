/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author Adrian
 */
public class Ticket_Producto implements Comparable<Ticket_Producto> {
    
    private int idticket,idproducto,cantidad;
    private float precio;

    public Ticket_Producto(int idticket, int idproducto, int cantidad, float precio) {
        this.idticket = idticket;
        this.idproducto = idproducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Ticket_Producto() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public int getIdticket() {
        return idticket;
    }

    public float getPrecio() {
        return precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public void setIdticket(int idticket) {
        this.idticket = idticket;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public int compareTo(Ticket_Producto o) {
        
        if(this.getIdproducto()<o.getIdproducto())
            return -1;
        else
            if(this.getIdproducto()>o.getIdproducto())
                return 1;
            else
                return 0;
        
    }

        
}
