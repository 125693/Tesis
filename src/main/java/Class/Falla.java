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
public class Falla {
    int id;
    int TiempoResolucion;
    TipoFalla tipoFalla;
    int Especialidad_id;

    public Falla(){};
    public Falla(int id, int TiempoResolucion, TipoFalla tipoFalla, int Especialidad_id) {
        this.id = id;
        this.TiempoResolucion = TiempoResolucion;
        this.tipoFalla = tipoFalla;
        this.Especialidad_id = Especialidad_id;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempoResolucion() {
        return TiempoResolucion;
    }

    public void setTiempoResolucion(int TiempoResolucion) {
        this.TiempoResolucion = TiempoResolucion;
    }

    public TipoFalla getTipoFalla() {
        return tipoFalla;
    }

    public void setTipoFalla(TipoFalla tipoFalla) {
        this.tipoFalla = tipoFalla;
    }

    public int getEspecialidad_id() {
        return Especialidad_id;
    }

    public void setEspecialidad_id(int Especialidad_id) {
        this.Especialidad_id = Especialidad_id;
    }
    
    
}
