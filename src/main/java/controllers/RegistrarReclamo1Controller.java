/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Distrito;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class RegistrarReclamo1Controller implements Initializable {

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
    
    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public AnchorPane apWindow;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<Distrito> distritos = new ArrayList<Distrito>();
    int index = -1;
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
    }
    
    @FXML
    private void btnAsignarClick(ActionEvent event){  
        try {
            apWindow.getChildren().clear();
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/RegistrarReclamo2.fxml"));
            apWindow.getChildren().add(window);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void searchAction(ActionEvent event){  
        try {
            String sql = "SELECT * FROM persona WHERE id = ?";
            preparedStatement = con.prepareStatement(sql); 
            preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
            resultSet = preparedStatement.executeQuery();
            
            boolean found = false;
            while(resultSet.next()){
                found = true;
                txtNombre.setText(resultSet.getString("Nombres"));
                txtApMaterno.setText(resultSet.getString("ApMaterno"));
                txtApPaterno.setText(resultSet.getString("ApPaterno"));
                txtTelefono.setText(resultSet.getString("Telefono"));
            };
            if (found)
            {
                sql = "SELECT * FROM cliente WHERE Persona_id = ?";
                preparedStatement = con.prepareStatement(sql); 
                preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
                resultSet = preparedStatement.executeQuery();
            }
            else
                JOptionPane.showMessageDialog(null,"no found");
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        rbntNatural.selectedProperty().setValue(true);
        FillComboDistritos();
    }    

    private void FillComboDistritos() {
        try {
            String sql = "SELECT * FROM distrito";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Distrito distrito = new Distrito(resultSet.getInt("id"), resultSet.getString("Nombre"));
                distritos.add(distrito);
                cboDistrito.getItems().add(distrito.getNombre());
            };
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    
    @FXML
    void cboDistritoAction(ActionEvent event){
        index = cboDistrito.getSelectionModel().getSelectedIndex();
        String dist = distritos.get(index).getNombre();
        index++;
    }
}
