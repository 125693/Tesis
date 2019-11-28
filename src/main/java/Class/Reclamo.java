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
    private PreparedStatement preparedStatement2 = null;
    private ResultSet resultSet2 = null;
    private PreparedStatement preparedStatement3 = null;
    private ResultSet resultSet3 = null;
    private Connection con  = utils.ConnectionUtil.conDB();
    
    int id;
    int reclamoId;
    Date fecha;
    Cliente cliente;
    Estado estado;
    List<InfoFalla> productos = new ArrayList<>();
    String NombreProducto;
    String NombreFalla;
    int cantidad;
    int tiempo;
    
    public Reclamo(){};

    public Reclamo(String NombreProducto, String NombreFalla) {
        this.NombreProducto = NombreProducto;
        this.NombreFalla = NombreFalla;
    }
    
    public Reclamo(int id,int clienteId, int reclamoId, int estadoId, Date fecha) {
        try {
            this.id = id;
            //this.clienteId = clienteId;
            String sql = "";
            String sql2 = "";
            String sql3 = "";
            
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

            this.fecha = fecha;
            //info fallas
            sql = "SELECT * FROM reclamo_has_producto where reclamoId = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                
                InfoFalla infoFalla = new InfoFalla();
                
                infoFalla.setCantidad(resultSet.getInt(" Cantidad")-resultSet.getInt("CantidadResueltos"));
                infoFalla.setEstadoId(resultSet.getInt("estadoId"));
                sql2 = "SELECT * FROM falla where id = ?";
                preparedStatement2 = con.prepareStatement(sql2);
                preparedStatement2.setInt(1, resultSet.getInt("fallaId"));
                resultSet2 = preparedStatement2.executeQuery();
                
                Falla falla = new Falla();
                while(resultSet2.next()){
                    falla.setId(resultSet2.getInt("id"));
                    falla.setTiempoResolucion(resultSet2.getInt("TiempoResolucion"));
                    falla.setEspecialidad_id(resultSet2.getInt("Especialidad_id"));
                    sql3 = "SELECT * FROM tipofalla where id = ?";
                    preparedStatement3 = con.prepareStatement(sql3);
                    preparedStatement3.setInt(1, resultSet2.getInt("TipoFalla_id"));
                    resultSet3 = preparedStatement3.executeQuery();
                    while(resultSet3.next()){
                        TipoFalla tipofalla = new TipoFalla(resultSet3.getInt("id"),resultSet3.getString("Nombre"));
                        falla.setTipoFalla(tipofalla);
                    }
                }
                infoFalla.setFalla(falla);
                
                sql2 = "SELECT * FROM producto where id = ?";
                preparedStatement2 = con.prepareStatement(sql2);
                preparedStatement2.setInt(1, resultSet.getInt("productoId"));
                resultSet2 = preparedStatement2.executeQuery();
                
                while(resultSet2.next()){
                    infoFalla.setProducto(resultSet2.getString("Nombre"));
                }
                productos.add(infoFalla);
            }
            
            
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

    public List<InfoFalla> getProductos() {
        return productos;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }

    public String getNombreFalla() {
        return NombreFalla;
    }

    public void setNombreFalla(String NombreFalla) {
        this.NombreFalla = NombreFalla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    
}
