/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flash
 */
public class Tecnico {
    
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Connection con  = utils.ConnectionUtil.conDB();
    
    int id;
    TipoTurno tipoTurno;
    TipoTecnico tipoTecnico;
    Persona persona;

    public Tecnico(int id, int TipoTurnoId, int TipoTecnicoId, int PersonaId) {
        this.id = id;
        SetTipoTurno(TipoTurnoId);
        SetTipoTecnico(TipoTecnicoId);
        SetPersonaId(PersonaId);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoTurno getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(TipoTurno tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public TipoTecnico getTipoTecnico() {
        return tipoTecnico;
    }

    public void setTipoTecnico(TipoTecnico tipoTecnico) {
        this.tipoTecnico = tipoTecnico;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    private void SetTipoTurno(int TipoTurnoId) {
        try {
            String sql = "SELECT * FROM tipoturno where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, TipoTurnoId);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.tipoTurno = new TipoTurno(TipoTurnoId,
                        resultSet.getString("Nombre"),
                        resultSet.getInt("HoraInicio"),
                        resultSet.getInt("HoraFin"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetTipoTecnico(int TipoTecnicoId) {
        try {
            String sql = "SELECT * FROM tipotecnico where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, TipoTecnicoId);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.tipoTecnico = new TipoTecnico(TipoTecnicoId,
                        resultSet.getString("Nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetPersonaId(int PersonaId) {
        try {
            String sql = "SELECT * FROM persona where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, PersonaId);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.persona = new Persona(PersonaId,
                        resultSet.getString("Nombres"),
                        resultSet.getString("ApPaterno"),
                        resultSet.getString("ApMaterno"),
                        resultSet.getString("Telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
