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
public class InfoFalla {
    String producto;
    Falla falla;
    int estadoId;
    int cantidad;
    
    public InfoFalla(){};

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Falla getFalla() {
        return falla;
    }

    public void setFalla(Falla falla) {
        this.falla = falla;
    }

    
}
