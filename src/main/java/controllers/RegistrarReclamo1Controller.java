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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    TextField txtReclamo;
    @FXML
    ComboBox cboDistrito;
    @FXML
    RadioButton rbntNatural;
    @FXML
    RadioButton rbtnJuridica;
    @FXML
    DatePicker dpFecha;
    @FXML
    Button btnAsignar;
    @FXML
    Button btnRegistrar;
    @FXML
    Button btnEditar;
    
    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public AnchorPane apWindow;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<Distrito> distritos = new ArrayList<Distrito>();
    int index = -1;
    int clienteId = -1;

    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
    }
    
    @FXML
    private void btnAsignarClick(ActionEvent event){
        try
        {
            if ("".equals(txtId.getText()) ||
                    "".equals(txtNombre.getText()) ||
                    "".equals(txtDireccion.getText()) ||
                    "".equals(txtTelefono.getText()) ||
                index == -1 || dpFecha.getValue() == null)
            {
                JOptionPane.showMessageDialog(null,"completar todos los campos");
            }
            else
            {
                if(rbntNatural.selectedProperty().getValue() && 
                        ("".equals(txtApPaterno.getText()) || "".equals(txtApMaterno.getText())))
                {
                    JOptionPane.showMessageDialog(null,"completar todos los campos");
                }
                else
                {
//                    apWindow.getChildren().clear();
//                    AnchorPane window = FXMLLoader.load(getClass().getResource("/views/RegistrarReclamo2.fxml"));
//                    apWindow.getChildren().add(window);
                    apWindow.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("/views/RegistrarReclamo2.fxml"));
                    AnchorPane window = loader.load();
                    RegistrarReclamo2Controller controller = loader.getController();
                    controller.setData(Integer.parseInt(txtId.getText()),dpFecha.getValue(),txtReclamo.getText(),clienteId);
                    apWindow.getChildren().add(window);
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(RegistrarReclamo1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnRegistrarClick(ActionEvent event){  
        if(!validations()) return;
        SaveCliente();
        btnEditar.setDisable(true);
        btnRegistrar.setDisable(true);
        btnAsignar.setDisable(false);
    }
    
    @FXML
    private void btnEditarClick(ActionEvent event){  
        if(!validations()) return;
        UpdateCliente();
        btnEditar.setDisable(true);
    }
    
    @FXML
    private void searchAction(ActionEvent event){  
        
        try {
            String sql = "SELECT * FROM cliente WHERE Persona_id = ?";
            preparedStatement = con.prepareStatement(sql); 
            preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
            resultSet = preparedStatement.executeQuery();
            int distritoId = -1;
            int tipoClienteId = -1;
            
            while(resultSet.next()){
                clienteId = resultSet.getInt("id");
                distritoId = resultSet.getInt("Distrito_id");
                tipoClienteId = resultSet.getInt("TipoCliente_id");
                txtDireccion.setText(resultSet.getString("Direccion"));
            }
            
            if(clienteId == -1)
            {
                JOptionPane.showMessageDialog(null,"Cliente no registrado");
                return;
            }
            
            sql = "SELECT * FROM persona WHERE id = ?";
            preparedStatement = con.prepareStatement(sql); 
            preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                txtNombre.setText(resultSet.getString("Nombres"));
                txtApMaterno.setText(resultSet.getString("ApMaterno"));
                txtApPaterno.setText(resultSet.getString("ApPaterno"));
                txtTelefono.setText(resultSet.getString("Telefono"));
            }
            
            final int disId = distritoId;
            distritos.forEach(e -> {
                if (e.getId() == 1)
                    cboDistrito.getSelectionModel().selectFirst();
                if(disId - e.getId() > 0)
                {
                    index = disId;
                    cboDistrito.getSelectionModel().selectNext();
                }
            });
            
            if(tipoClienteId == 1)
            {
                rbntNatural.selectedProperty().setValue(true);
                rbtnJuridica.selectedProperty().setValue(false);
            }
            else
            {
                rbtnJuridica.selectedProperty().setValue(true);
                txtApPaterno.setDisable(true);
                txtApMaterno.setDisable(true);
                rbntNatural.selectedProperty().setValue(false);
            }
            btnEditar.disableProperty().set(false);
            btnAsignar.disableProperty().set(false);
            btnRegistrar.disableProperty().set(true);
            txtId.setDisable(true);
                
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        btnEditar.disableProperty().set(true);
        btnAsignar.disableProperty().set(true);
        rbntNatural.selectedProperty().setValue(true);
        dpFecha.setValue(LocalDate.now());
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
            }
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
        index++;
    }
    
    private void UpdateCliente() {
        try {
            //update persona
            String sql = "UPDATE persona SET id = ?, Nombres = ?, ApPaterno = ?, ApMaterno = ?, Telefono = ? WHERE (id = ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
            preparedStatement.setString(2,txtNombre.getText());
            preparedStatement.setString(3,txtApPaterno.getText());
            preparedStatement.setString(4,txtApMaterno.getText());
            preparedStatement.setString(5,txtTelefono.getText());
            preparedStatement.setInt(6,Integer.parseInt(txtId.getText()));
            preparedStatement.executeUpdate();
            //update cliente
            sql = "UPDATE cliente SET Direccion = ?, Distrito_id = ?, TipoCliente_id = ?, Persona_id = ? WHERE (id = ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,txtDireccion.getText());
            preparedStatement.setInt(2,index);
            if (rbntNatural.selectedProperty().getValue())
                preparedStatement.setInt(3,1);
            else
                preparedStatement.setInt(3,2);
            preparedStatement.setInt(4,Integer.parseInt(txtId.getText()));
            preparedStatement.setInt(5,clienteId);
            preparedStatement.executeUpdate();
            
            btnEditar.setDisable(true);
            JOptionPane.showMessageDialog(null,"Cliente Actualizado");
        } catch (SQLException ex) {
            Logger.getLogger(EditarClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validations() {
        if (index == -1 ||
                "".equals(txtTelefono.getText()) ||
                "".equals(txtId.getText()) ||
                "".equals(txtNombre.getText()) ||
                "".equals(txtDireccion.getText()))
        {
            System.out.println("1");
            JOptionPane.showMessageDialog(null,"completar todos los campos");
            return false;
        }
        else
        {
            if(rbntNatural.selectedProperty().getValue() && 
                    ("".equals(txtApPaterno.getText()) || "".equals(txtApMaterno.getText())))
            {
                System.out.println("2");
                JOptionPane.showMessageDialog(null,"completar todos los campos");
                return false; 
            }
            else
            {
                return true;
            }
        }
    }

    private void SaveCliente() {
        try {
            String sql = "INSERT INTO persona (id, Nombres, ApPaterno, ApMaterno, Telefono) VALUES (?,?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,Integer.parseInt(txtId.getText()));
            preparedStatement.setString(2,txtNombre.getText());
            preparedStatement.setString(3,txtApPaterno.getText());
            preparedStatement.setString(4,txtApMaterno.getText());
            preparedStatement.setString(5,txtTelefono.getText());
            preparedStatement.executeUpdate();
            
            sql = "INSERT INTO cliente (Direccion, Distrito_id, TipoCliente_id, Persona_id) VALUES (?, ?, ?, ?)";
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
            Logger.getLogger(RegistrarReclamo1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
