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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flash
 */
public class Reclamo {
    
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Connection con  = utils.ConnectionUtil.conDB();
    
    int id;
    //int clienteId;
    int reclamoId;
    //int estadoId;
    Date fecha;
    Cliente cliente;
    Estado estado;
    List<ProductoReclamo> productos = new ArrayList<>();
    
    public Reclamo(){};
    
    public Reclamo(int id,int clienteId, int reclamoId, int estadoId, Date fecha) {
        try {
            this.id = id;
            //this.clienteId = clienteId;
            String sql = "";
            sql = "SELECT * FROM cliente where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, clienteId);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.cliente = new Cliente(resultSet.getInt("id"), 
                        resultSet.getString("Direccion"), 
                        resultSet.getInt("Distrito_id"), 
                        resultSet.getInt("TipoCliente_id"), 
                        resultSet.getInt("Persona_id"));
            }
            this.reclamoId = reclamoId;
            sql = "SELECT * FROM estado where id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, estadoId);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.estado = new Estado(resultSet.getInt("id"), 
                        resultSet.getString("Nombre"));
            }
            
            sql = "SELECT * FROM reclamo_has_producto where reclamoId = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                this.estado = new Estado(resultSet.getInt("id"), 
                        resultSet.getString("Nombre"));
            }
            
            this.fecha = fecha;
            
        } catch (SQLException ex) {
            Logger.getLogger(Reclamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReclamoId() {
        return reclamoId;
    }

    public void setReclamoId(int reclamoId) {
        this.reclamoId = reclamoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
