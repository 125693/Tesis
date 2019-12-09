/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.InfoFalla;
import Class.PlanVisita;
import Class.Reclamo;
import Class.Tecnico;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class ListaVisitasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet2 = null;
    
    @FXML private TreeTableView<PlanVisita> tblVisitas;
    @FXML private TreeTableColumn<PlanVisita, Integer> colDni;
    @FXML private TreeTableColumn<PlanVisita, Date> colFecha;
    @FXML private TreeTableColumn<PlanVisita, String> ColDistrito;
    @FXML private TreeTableColumn<PlanVisita, String> colHorario;
    
    List<PlanVisita> planesVisita = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con  = utils.ConnectionUtil.conDB();
        LoadTableData();
    }    
    
    private void LoadTableData() {
        try {
            // TODO
            String sql = "SELECT * FROM planvisita"; 
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                String sql2 = "SELECT * FROM tecnico WHERE id = ?";
                preparedStatement2 = con.prepareStatement(sql2);
                preparedStatement2.setInt(1, resultSet.getInt("tecnicoId"));
                resultSet2 = preparedStatement2.executeQuery();
                Tecnico tecnico = null;
                while(resultSet2.next()){
                    tecnico = new Tecnico(resultSet2.getInt("id"),
                            resultSet2.getInt("TipoTurno_id"),
                            resultSet2.getInt("TipoTecnico_id"), 
                            resultSet2.getInt("Persona_id"));
                    planesVisita.add(new PlanVisita(resultSet.getInt("id"), resultSet.getDate("fecha").toLocalDate(), tecnico));
                };
            }
            if(planesVisita.size() == 0) return;
            TreeItem<PlanVisita> rootNode = new TreeItem<>(planesVisita.get(0));
            for (PlanVisita p: planesVisita) {
                int inicio = p.getTecnico().getTipoTurno().getHoraInicio();
                p.setId(p.getTecnico().getPersona().getId());
                TreeItem<PlanVisita> node = new TreeItem<>(p);
                rootNode.getChildren().add(node);
                for (Reclamo r: p.getReclamos()) {
                    r.setTiempo(r.getCantidad()*r.getProductos().get(0).getFalla().getTiempoResolucion());
                    PlanVisita plan = new PlanVisita();
                    int total = 0;
                    for (InfoFalla i: r.getProductos())
                    {
                        total += i.getCantidad()*i.getFalla().getTiempoResolucion();
                    }
                    int fin = inicio + total;
                    if (fin > p.getTecnico().getTipoTurno().getHoraFin())
                        fin = p.getTecnico().getTipoTurno().getHoraFin();
                    String horario = inicio + "-" + fin;
                    inicio = fin;
                    plan.setHorario(horario);
                    plan.setId(r.getId());
                    plan.setFecha(r.getFecha().toLocalDate());
                    plan.setDistrito(r.getCliente().getDistrito().getNombre());
                    plan.setNombre(r.getNombreProducto());
                    TreeItem<PlanVisita> node2 = new TreeItem<>(plan);
                    node.getChildren().add(node2);
                }
            }
            tblVisitas.setShowRoot(false);
            tblVisitas.setRoot(rootNode);
            colDni.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colFecha.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha"));
            ColDistrito.setCellValueFactory(new TreeItemPropertyValueFactory<>("distrito"));
            colHorario.setCellValueFactory(new TreeItemPropertyValueFactory<>("horario"));
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
