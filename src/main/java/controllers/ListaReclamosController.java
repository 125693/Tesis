/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Cliente;
import Class.Reclamo;
import java.io.IOException;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class ListaReclamosController implements Initializable {

    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public AnchorPane apWindow;
    
    @FXML private TreeTableView<Reclamo> tblReclamos;
    @FXML private TreeTableColumn<Reclamo, Integer> colId;
    @FXML private TreeTableColumn<Reclamo, Date> colFecha;
    @FXML private TreeTableColumn<Reclamo, String> colDoc;
    @FXML private TreeTableColumn<Reclamo, String> colEstado;
    @FXML private TreeTableColumn<Reclamo, String> colNombre;
    @FXML private TreeTableColumn<Reclamo, String> colFalla;
    
    //DB
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    List<Reclamo> reclamos = new ArrayList<>();
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
    }
    
    @FXML
    private void btnEditarClick(ActionEvent event){  
        try {
            apWindow.getChildren().clear();
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/EditarReclamo.fxml"));
            apWindow.getChildren().add(window);
        } catch (Exception ex) {
            String error = ex.getMessage();
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTableData();
    }    

    private void LoadTableData() {
        try {
            // TODO
            con  = utils.ConnectionUtil.conDB();
            String sql = "SELECT * FROM reclamo";
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
            
            TreeItem<Reclamo> rootNode = new TreeItem<>(reclamos.get(0));
            for (Reclamo r: reclamos) {
                TreeItem<Reclamo> node = new TreeItem<>(r);
                rootNode.getChildren().add(node);
            }
            tblReclamos.setShowRoot(false);
            tblReclamos.setRoot(rootNode);
            colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colFecha.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha"));
            colDoc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCliente().getPersona().getId())));
            colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEstado().getNombre()));
           
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
