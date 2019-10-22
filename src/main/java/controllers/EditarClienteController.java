/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Cliente;
import Class.Distrito;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class EditarClienteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField txtId;
    @FXML
    TextField txtNombre;
    @FXML
    TextField txtApPaterno;
    @FXML
    TextField txtDireccion;
    @FXML
    TextField txtTelefono;
    @FXML
    TextField txtApMaterno;
    @FXML
    ComboBox cboDistrito;
    @FXML
    RadioButton rbntNatural;
    @FXML
    RadioButton rbtnJuridica;
    
    Cliente cliente;
    ArrayList<Distrito> distritos = new ArrayList<>();
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int index = -1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            con  = utils.ConnectionUtil.conDB();
            rbntNatural.selectedProperty().setValue(true);
            String sql = "SELECT * FROM mydb.distrito";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Distrito distrito = new Distrito(resultSet.getInt("id"), resultSet.getString("Nombre").substring(1));
                distritos.add(distrito);
                cboDistrito.getItems().add(distrito.getNombre());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EditarClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    void setCliente(Cliente c) {
        
        cliente = c;
        txtId.setText(((Integer)c.getPersona().getId()).toString());
        txtNombre.setText(c.getPersona().getNombres());
        txtApPaterno.setText(c.getPersona().getApPaterno());
        txtApMaterno.setText(c.getPersona().getApMaterno());
        txtDireccion.setText(c.getDireccion());
        txtTelefono.setText(c.getPersona().getTelefono());
        if (c.TipoCliente_id == 1)
            rbntNatural.selectedProperty().setValue(true);
        else if (c.TipoCliente_id == 2)
        {
            rbtnJuridica.selectedProperty().setValue(true);
            txtApPaterno.setDisable(true);
            txtApMaterno.setDisable(true);
            rbntNatural.selectedProperty().setValue(false);
        }
        
        distritos.forEach(e -> {
            if(e.getId() == (c.getDistrito().getId()-1))
                    cboDistrito.getSelectionModel().select(e.getId());
        });
    }
    
    @FXML
    void rbtnJuridicaClick(ActionEvent event){
        rbntNatural.selectedProperty().setValue(!rbntNatural.selectedProperty().getValue());
        if (rbtnJuridica.selectedProperty().getValue())
        {
            txtApPaterno.setText("");
            txtApMaterno.setText("");
            txtApPaterno.setDisable(true);
            txtApMaterno.setDisable(true);
        }
        else
        {
            txtApPaterno.setDisable(false);
            txtApMaterno.setDisable(false);
        }
    }
    
    @FXML
    void rbntNaturalClick(ActionEvent event){
        rbtnJuridica.selectedProperty().setValue(!rbtnJuridica.selectedProperty().getValue());
        if (!rbntNatural.selectedProperty().getValue())
        {
            txtApPaterno.setText("");
            txtApMaterno.setText("");
            txtApPaterno.setDisable(true);
            txtApMaterno.setDisable(true);
        }
        else
        {
            txtApPaterno.setDisable(false);
            txtApMaterno.setDisable(false);
        }
    }
    
}
