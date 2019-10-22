/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Distrito;
import Class.Usuario;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class NuevoClienteController implements Initializable {

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
    Label lblDistrito;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<Distrito> distritos = new ArrayList<Distrito>();
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
            };
            
        } catch (SQLException ex) {
            Logger.getLogger(NuevoClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    void cboDistritoAction(ActionEvent event){
        index = cboDistrito.getSelectionModel().getSelectedIndex();
        String dist = distritos.get(index).getNombre();
        index++;
    }
    
    @FXML
    void btnRegistrarClick(ActionEvent event){
        if (txtId.getText()== "" ||
                txtNombre.getText() == "" ||
                txtDireccion.getText() == "" ||
                txtTelefono.getText() == "" ||
                index == -1)
        {
            JOptionPane.showMessageDialog(null,"completar todos los campos");
            return;
        }
        else
        {
            if(rbntNatural.selectedProperty().getValue() && 
                    (txtApPaterno.getText() == "" || txtApMaterno.getText() == ""))
            {
                JOptionPane.showMessageDialog(null,"completar todos los campos");
                return; 
            }
            else
            {
                try {
                    //save in bd en bd
                    String sql = "INSERT INTO mydb.persona (id, Nombres, ApPaterno, ApMaterno, Telefono) VALUES (?,?, ?, ?, ?)";
                    preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
                    preparedStatement.setString(2,txtNombre.getText());
                    preparedStatement.setString(3,txtApPaterno.getText());
                    preparedStatement.setString(4,txtApMaterno.getText());
                    preparedStatement.setString(5,txtTelefono.getText());
                    preparedStatement.executeUpdate();
                    
                    sql = "INSERT INTO mydb.cliente (Direccion, Distrito_id, TipoCliente_id, Persona_id) VALUES (?, ?, ?, ?)";
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1,txtDireccion.getText());
                    preparedStatement.setInt(2,index);
                    if (rbntNatural.selectedProperty().getValue())
                        preparedStatement.setInt(3,1);
                    else
                        preparedStatement.setInt(3,2);
                    preparedStatement.setInt(4,Integer.parseInt(txtId.getText()));
                    preparedStatement.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null,"Cliente creado");
                    
                } catch (SQLException ex) {
                    Logger.getLogger(NuevoClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
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
    
}
