/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flash
 */
public class Usuario {
    
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Connection con  = utils.ConnectionUtil.conDB();

    int id;
    String email;
    int Persona_id;
    int TipoUsuario_id;
    Persona persona;
    TipoUsuario tipoUsuario;
    
    public Usuario(int id, String email, int Persona_id, int TipoUsuario_id) {
        try {
            this.id = id;
            this.email = email;
            this.Persona_id = Persona_id;
            this.TipoUsuario_id = TipoUsuario_id;
            
            String sql = "SELECT * FROM persona where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Persona_id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.persona = new Persona(Persona_id, 
                        resultSet.getString("Nombres"), 
                        resultSet.getString("ApPaterno"), 
                        resultSet.getString("ApMaterno"), 
                        resultSet.getString("Telefono"));
            }
            
            sql = "SELECT * FROM tipousuario where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, TipoUsuario_id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.tipoUsuario = new TipoUsuario(TipoUsuario_id, 
                        resultSet.getString("Nombre")); 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPersona_id() {
        return Persona_id;
    }

    public void setPersona_id(int Persona_id) {
        this.Persona_id = Persona_id;
    }

    public int getTipoUsuario_id() {
        return TipoUsuario_id;
    }

    public void setTipoUsuario_id(int TipoUsuario_id) {
        this.TipoUsuario_id = TipoUsuario_id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
}
