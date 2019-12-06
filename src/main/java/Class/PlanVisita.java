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
public class PlanVisita {
    
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement2 = null;
    private ResultSet resultSet2 = null;
    private Connection con  = utils.ConnectionUtil.conDB();
    
    int id;
    LocalDate fecha;
    Tecnico tecnico;
    int horas = 0;
    List<Reclamo> reclamos = new ArrayList<>();
    
    public PlanVisita(int id, LocalDate fecha, Tecnico tecnicoId) {
        this.id = id;
        this.fecha = fecha;
        this.tecnico = tecnicoId;
        SetReclamos(id);
    }

    public List<Reclamo> getReclamos() {
        return reclamos;
    }

    public void setReclamos(List<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }


    private void SetReclamos(int id) {
        try {
            if(id != -1)
            {
                String sql = "SELECT * FROM planvisita_has_reclamo where planVisitaId = ?";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                
                while(resultSet.next()){
                    String sql2 = "SELECT * FROM reclamo where id = ?";
                    preparedStatement2 = con.prepareStatement(sql2);
                    preparedStatement2.setInt(1, resultSet.getInt("reclamoId"));
                    resultSet2 = preparedStatement2.executeQuery();
                    while(resultSet2.next()){
                        Reclamo r = new Reclamo(resultSet2.getInt("id"), 
                                                resultSet2.getInt("clienteId"),
                                                resultSet2.getInt("reclamoId"),
                                                resultSet2.getInt("estadoId"),
                                                resultSet2.getDate("fecha"));
                        r.getProductos().forEach((p) -> {
                            this.horas += p.getCantidad()*p.getFalla().getTiempoResolucion();
                        });
                        this.reclamos.add(r);
                    }
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Tecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
}
