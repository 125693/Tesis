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
public class TipoTurno {
    int id;
    String Nombre;
    int HoraInicio;
    int HoraFin;

    public TipoTurno(int id, String Nombre, int HoraInicio, int HoraFin) {
        this.id = id;
        this.Nombre = Nombre;
        this.HoraInicio = HoraInicio;
        this.HoraFin = HoraFin;
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

    public int getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(int HoraInicio) {
        this.HoraInicio = HoraInicio;
    }

    public int getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(int HoraFin) {
        this.HoraFin = HoraFin;
    }
    
    
    
}
