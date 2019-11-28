/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Tecnico;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class NuevaVisita1Controller implements Initializable {

    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public AnchorPane apWindow;
    
    @FXML private TableView<Tecnico> tblTecnicos;
    @FXML private TableColumn<Tecnico, Integer> colId;
    @FXML private TableColumn<Tecnico, String> colNombre;
    @FXML private TableColumn<Tecnico, String> colTipo;
    @FXML private TableColumn<Tecnico, Date> colVisita;
    @FXML private TableColumn<Tecnico, Integer> colHoras;
    
    List<Tecnico> tecnicos = new ArrayList<>();
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet2 = null;
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
    }
    
    @FXML
    private void btnAsignarClick(ActionEvent event){  
        try {
            apWindow.getChildren().clear();
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/NuevaVisita2.fxml"));
            apWindow.getChildren().add(window);
        } catch (Exception ex) {
            String error = ex.getMessage();
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        FillTable();
    }    

    private void FillTable() {
        try {
            String sql2 = "";
            String sql = "SELECT * FROM tecnico"; 
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                sql2 = "SELECT * FROM planvisita where tecnicoId = ?"; 
                preparedStatement2 = con.prepareStatement(sql2);
                preparedStatement2.setInt(1, resultSet.getInt("id"));
                resultSet2 = preparedStatement2.executeQuery();
                
                int visitas = 0;
                
                while(resultSet2.next()){
                    visitas++;
                    Tecnico tecnico = new Tecnico(resultSet.getInt("id"),
                        resultSet.getInt("TipoTurno_id"),
                        resultSet.getInt("TipoTecnico_id"), 
                        resultSet.getInt("Persona_id"));
                    tecnico.setFecha(resultSet2.getDate("fecha"));
                }
                if(visitas == 0)
                {
                    Tecnico tecnico = new Tecnico(resultSet.getInt("id"),
                        resultSet.getInt("TipoTurno_id"),
                        resultSet.getInt("TipoTecnico_id"), 
                        resultSet.getInt("Persona_id"));
                    tecnicos.add(tecnico);
                }
            }
            
            
            
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getNombres()));
            colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTecnico().getNombre()));
            colVisita.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            tblTecnicos.getItems().setAll(tecnicos);
        } catch (SQLException ex) {
            Logger.getLogger(ListarTecnicosController.class.getName()).log(Level.SEVERE, null, ex);
    
        }
    }
    
}
