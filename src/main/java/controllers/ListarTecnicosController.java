/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Cliente;
import Class.Tecnico;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class ListarTecnicosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField txtId;
    @FXML private TableView<Tecnico> tblTecnicos;
    @FXML private TableColumn<Tecnico, Integer> colId;
    @FXML private TableColumn<Tecnico, String> colNombre;
    @FXML private TableColumn<Tecnico, String> collApellidos;
    @FXML private TableColumn<Tecnico, String> colTipo;
    @FXML private TableColumn<Tecnico, String> colTurno;
    
    List<Tecnico> tecnicos = new ArrayList<>();
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        FillTable();
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
    
    @FXML
    private void txtIdAction(ActionEvent event){
        if (txtId.getText().equals(""))
        {
            tblTecnicos.getItems().setAll(tecnicos);
            return;
        }
        List<Tecnico> result = new ArrayList<>();
        tecnicos.forEach(e -> {
            if(e.getId() == Integer.parseInt(txtId.getText()))
                result.add(e);
        });
        tblTecnicos.getItems().setAll(result);
    }
    
}
