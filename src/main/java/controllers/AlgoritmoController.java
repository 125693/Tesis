/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.InfoFalla;
import Class.Reclamo;
import Class.Tecnico;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class AlgoritmoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Tecnico> tblTecnicos;
    @FXML private TableColumn<Tecnico, Integer> colId;
    @FXML private TableColumn<Tecnico, String> colNombre;
    @FXML private TableColumn<Tecnico, String> collApellidos;
    @FXML private TableColumn<Tecnico, String> colTipo;
    @FXML private TableColumn<Tecnico, String> colTurno;
    
    @FXML private TreeTableView<Reclamo> tblReclamos;
    @FXML private TreeTableColumn<Reclamo, Integer> colId2;
    @FXML private TreeTableColumn<Reclamo, Date> colFecha;
    @FXML private TreeTableColumn<Reclamo, String> colDoc;
    @FXML private TreeTableColumn<Reclamo, String> colEstado;
    @FXML private TreeTableColumn<Reclamo, String> colNombre2;
    @FXML private TreeTableColumn<Reclamo, String> colFalla;
    
    List<Tecnico> tecnicos = new ArrayList<>();
    List<Reclamo> reclamos = new ArrayList<>();
    TreeItem<Reclamo> rootNode;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        FillTable();
        LoadTableData();
    }    
    
    private void FillTable() {
        try {
            String sql = "SELECT * FROM tecnico"; 
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Tecnico tecnico = new Tecnico(resultSet.getInt("id"),
                        resultSet.getInt("TipoTurno_id"),
                        resultSet.getInt("TipoTecnico_id"), 
                        resultSet.getInt("Persona_id"));
                tecnicos.add(tecnico);
                
            };
            
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getNombres()));
            collApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getPersona().getApPaterno() + " " + cellData.getValue().getPersona().getApMaterno()));
            colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTecnico().getNombre()));
            colTurno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTurno().getNombre()));
           
            tblTecnicos.getItems().setAll(tecnicos);
        } catch (SQLException ex) {
            Logger.getLogger(ListarTecnicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void LoadTableData() {
        try {
            // TODO
            String sql = "SELECT * FROM reclamo where estadoId = 1";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Reclamo reclamo = new Reclamo(resultSet.getInt("id"), 
                        resultSet.getInt("clienteId"), 
                        resultSet.getInt("reclamoId"), 
                        resultSet.getInt("estadoId"), 
                        resultSet.getDate("fecha"));
                reclamos.add(reclamo);
            };
            
            rootNode = new TreeItem<>(reclamos.get(0));
            for (Reclamo r: reclamos) {
                TreeItem<Reclamo> node = new TreeItem<>(r);
                rootNode.getChildren().add(node);
                
                if (r.getProductos().size() == 1)
                {
                    r.setNombreProducto(r.getProductos().get(0).getProducto());
                    r.setNombreFalla(r.getProductos().get(0).getFalla().getTipoFalla().getNombre());
                }
                else
                {
                    for (InfoFalla i: r.getProductos())
                    {
                        Reclamo rec = new Reclamo(r.getId(),r.getCliente().getId(),r.getReclamoId(),i.getEstadoId(),r.getFecha());
                        rec.setNombreFalla(i.getFalla().getTipoFalla().getNombre());
                        rec.setNombreProducto(i.getProducto());
                        TreeItem<Reclamo> node2 = new TreeItem<>(rec);
                        node.getChildren().add(node2);
                    }
                }
                
            }
            tblReclamos.setShowRoot(false);
            tblReclamos.setRoot(rootNode);
            colId2.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colFecha.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha"));
            colDoc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCliente().getPersona().getId())));
            colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEstado().getNombre()));
            colNombre2.setCellValueFactory(new TreeItemPropertyValueFactory<>("NombreProducto"));
            colFalla.setCellValueFactory(new TreeItemPropertyValueFactory<>("NombreFalla"));
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void btnRemoveTecnicoClick(ActionEvent event){
        Tecnico selectedItem = tblTecnicos.getSelectionModel().getSelectedItem();
        tblTecnicos.getItems().remove(selectedItem);
    }
    @FXML
    private void btnRemoveReclamoClick(ActionEvent event){
        TreeItem<Reclamo> r = tblReclamos.getSelectionModel().getSelectedItem();
        if(r == null || r.getParent().getParent() != null)
        {
            JOptionPane.showMessageDialog(null,"Seleccionar un reclamo");
            return;
        }
        r.getParent().getChildren().remove(r);
    }
    @FXML
    private void btnPlanClick(ActionEvent event){
        for (int i = 0; i < tblTecnicos.getItems().size() ; i++) {
            System.out.println(tblTecnicos.getItems().get(i).getId());
        }
        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            System.out.println(rootNode.getChildren().get(i).getValue().getId());
        }
    }
}
