/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author flash
 */
public class ProductoReclamo {
    String Nombre;
    Color color;
    Modelo modelo;
    TipoProducto tipoProducto;
    GamaProducto gama;
    TipoFalla tipoFalla;
    int cantidad;
    String comentario;

    public ProductoReclamo(String Nombre, Color color, Modelo modelo, 
            TipoProducto tipoProducto, GamaProducto gama, TipoFalla tipoFalla, 
            int cantidad, String comentario) {
        this.Nombre = Nombre;
        this.color = color;
        this.modelo = modelo;
        this.tipoProducto = tipoProducto;
        this.gama = gama;
        this.tipoFalla = tipoFalla;
        this.cantidad = cantidad;
        this.comentario = comentario;
    }

    public TipoFalla getTipoFalla() {
        return tipoFalla;
    }

    public void setTipoFalla(TipoFalla tipoFalla) {
        this.tipoFalla = tipoFalla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public GamaProducto getGama() {
        return gama;
    }

    public void setGama(GamaProducto gama) {
        this.gama = gama;
    }
    
    
}
