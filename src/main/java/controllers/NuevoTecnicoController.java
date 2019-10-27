/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Distrito;
import Class.GamaProducto;
import Class.TipoProducto;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class NuevoTecnicoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField txtDni;
    @FXML
    TextField txtNombre;
    @FXML
    TextField txtApPaterno;
    @FXML
    TextField txtApMaterno;
    @FXML
    TextField txtTelefono;
    @FXML
    ComboBox cboTipoProducto;
    @FXML
    ComboBox cboGamaProducto;
    @FXML
    RadioButton rbtnMorning;
    @FXML
    RadioButton rbtnAfternoon;
    @FXML
    RadioButton rbtnEspecialista;
    @FXML
    RadioButton rbtnGenerico;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<TipoProducto> tiposProductos = new ArrayList<>();
    ArrayList<GamaProducto> gamasProductos = new ArrayList<>();
    int indexTipoProducto = -1;
    int indexGamaProducto = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        rbtnMorning.selectedProperty().setValue(true);
        rbtnGenerico.selectedProperty().setValue(true);
        cboGamaProducto.setDisable(true);
        cboTipoProducto.setDisable(true);
        FillCombos();
    }
    
    @FXML
    void btnRegistrarAction(ActionEvent event){
        if(!validations()) return;
        CreateTecnico();
    }
    
    @FXML
    void cboTipoProductoAction(ActionEvent event){
        indexTipoProducto = cboTipoProducto.getSelectionModel().getSelectedIndex();
        indexTipoProducto++;
    }
    
    @FXML
    void cboGamaProductoAction(ActionEvent event){
        indexGamaProducto = cboGamaProducto.getSelectionModel().getSelectedIndex();
        indexGamaProducto++;
    }

    @FXML
    void rbtnMorningAction(ActionEvent event){
        rbtnAfternoon.selectedProperty().setValue(!rbtnAfternoon.selectedProperty().getValue());
    }
    
    @FXML
    void rbtnAfternoonAction(ActionEvent event){
        rbtnMorning.selectedProperty().setValue(!rbtnMorning.selectedProperty().getValue());
    }
    
    @FXML
    void rbtnEspecialistaAction(ActionEvent event){
        rbtnGenerico.selectedProperty().setValue(!rbtnGenerico.selectedProperty().getValue());
        if (!rbtnEspecialista.selectedProperty().getValue())
        {
            cboGamaProducto.setDisable(true);
            cboTipoProducto.setDisable(true);
        }
        else
        {
            cboGamaProducto.setDisable(false);
            cboTipoProducto.setDisable(false);
        }
    }
    
    @FXML
    void rbtnGenericoAction(ActionEvent event){
        rbtnEspecialista.selectedProperty().setValue(!rbtnEspecialista.selectedProperty().getValue());
        if (rbtnGenerico.selectedProperty().getValue())
        {
            cboGamaProducto.setDisable(true);
            cboTipoProducto.setDisable(true);
        }
        else
        {
            cboGamaProducto.setDisable(false);
            cboTipoProducto.setDisable(false);
        }
    }

    private void FillCombos() {
        try {
            //gama
            String sql = "SELECT * FROM gamaproducto"; 
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                GamaProducto gamaProducto = new GamaProducto(resultSet.getInt("id"), resultSet.getString("Nombre"));
                gamasProductos.add(gamaProducto);
                cboGamaProducto.getItems().add(gamaProducto.getNombre());
            };
            
            //tipo
            sql = "SELECT * FROM tipoproducto"; 
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                TipoProducto tipoProducto = new TipoProducto(resultSet.getInt("id"), resultSet.getString("Nombre"));
                tiposProductos.add(tipoProducto);
                cboTipoProducto.getItems().add(tipoProducto.getNombre());
            };
        } catch (SQLException ex) {
            Logger.getLogger(NuevoTecnicoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean validations() {
        if (txtDni.getText()== "" ||
                txtNombre.getText() == "" ||
                txtApPaterno.getText() == "" ||
                txtApMaterno.getText() == "")
        {
            JOptionPane.showMessageDialog(null,"completar todos los campos");
            return false;
        }
        if (rbtnEspecialista.selectedProperty().getValue())
        {
            if(indexGamaProducto == -1 || indexTipoProducto == -1)
            {
                JOptionPane.showMessageDialog(null,"completar todos los campos");
                return false;
            }
        }
        return true;
    }

    private void CreateTecnico() {
        try {
            int personaId = -1;
            String sql = "INSERT INTO persona (id, Nombres, ApPaterno, ApMaterno, Telefono) VALUES (?,?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,Integer.parseInt(txtDni.getText()));
            preparedStatement.setString(2,txtNombre.getText());
            preparedStatement.setString(3,txtApPaterno.getText());
            preparedStatement.setString(4,txtApMaterno.getText());
            preparedStatement.setString(5,txtTelefono.getText());
            preparedStatement.executeUpdate();
            
            resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next())
            {
                personaId = resultSet.getInt(1);
            }
            
            sql = "INSERT INTO tecnico (TipoTurno_id, TipoTecnico_id, Persona_id) VALUES (?, ?, ?);";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (rbtnMorning.selectedProperty().getValue())
                preparedStatement.setInt(1,1);
            else
                preparedStatement.setInt(1,2);
            if (rbtnEspecialista.selectedProperty().getValue())
                preparedStatement.setInt(2,2);
            else
                preparedStatement.setInt(2,1);
            preparedStatement.setInt(3,personaId);
            preparedStatement.executeUpdate();
           
            int tecnicoid = -1;
            resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next())
            {
                tecnicoid = resultSet.getInt(1);
            }
            
            if (rbtnEspecialista.selectedProperty().getValue())
            {
                sql = "SELECT * FROM especialidad WHERE TipoProducto_id = ? AND Gama_id = ?";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,indexTipoProducto);
                preparedStatement.setInt(2,indexGamaProducto);
                resultSet = preparedStatement.executeQuery();
                int especialidadId = -1;
                while(resultSet.next())
                {
                    especialidadId = resultSet.getInt("Id");
                }
                if(especialidadId == -1)
                {
                    sql = "INSERT INTO especialidad (TipoProducto_id, Gama_id) VALUES (?, ?)";
                    preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1,indexTipoProducto);
                    preparedStatement.setInt(2,indexGamaProducto);
                    preparedStatement.executeUpdate();
                    resultSet = preparedStatement.getGeneratedKeys();

                    while(resultSet.next())
                    {
                        especialidadId = resultSet.getInt(1);
                    }
                }
                sql = "INSERT INTO tecnico_has_especialidad (especialidadId, tecnicoId) VALUES (?, ?)";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,especialidadId);
                preparedStatement.setInt(2,tecnicoid);
                preparedStatement.executeUpdate();
            }
            
            
            
            JOptionPane.showMessageDialog(null,"Tecnico creado");
        } catch (SQLException ex) {
            Logger.getLogger(NuevoTecnicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
