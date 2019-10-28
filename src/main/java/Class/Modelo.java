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
public class Modelo {
    int id;
    String Nombre;
    int TipoProductoId;

    public Modelo(int id, String Nombre, int TipoProductoId) {
        this.id = id;
        this.Nombre = Nombre;
        this.TipoProductoId = TipoProductoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getTipoProductoId() {
        return TipoProductoId;
    }

    public void setTipoProductoId(int TipoProductoId) {
        this.TipoProductoId = TipoProductoId;
    }
    
    
}
