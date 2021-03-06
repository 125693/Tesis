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
public class Cliente {
    
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Connection con  = utils.ConnectionUtil.conDB();
    
    int id;
    String Direccion;
    int Distrito_id;
    public int TipoCliente_id;
    int Persona_id;
    
    Distrito distrito;
    TipoCliente tipoCliente;
    public Persona persona;
    
    public Cliente(int id, String Direccion, int Distrito_id, int TipoCliente_id, int Persona_id) {
        try {
            this.id = id;
            this.Direccion = Direccion;
            this.Distrito_id = Distrito_id;
            this.TipoCliente_id = TipoCliente_id;
            this.Persona_id = Persona_id;
            
            String sql = "SELECT * FROM distrito where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Distrito_id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.distrito = new Distrito(Distrito_id,
                        resultSet.getString("Nombre"));
            }
            
            sql = "SELECT * FROM tipocliente where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, TipoCliente_id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.tipoCliente = new TipoCliente(TipoCliente_id,
                        resultSet.getString("Nombre"));
            }
            
            sql = "SELECT * FROM persona where id = ?";
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
            
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getPersona_id() {
        return Persona_id;
    }

    public void setPersona_id(int Persona_id) {
        this.Persona_id = Persona_id;
    }
    
}
