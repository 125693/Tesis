/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Cliente;
import Class.Distrito;
import Class.Persona;
import java.io.IOException;
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
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class ListaClientesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public AnchorPane apWindow;
    
    @FXML TextField txtId;
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> collApellidos;
    @FXML private TableColumn<Cliente, String> colDistrito;
    @FXML private TableColumn<Cliente, String> colTelefono;
    
    //ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    List<Cliente> clientes = new ArrayList<>();
        
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
    }
    
    @FXML
    private void btnEditarClick(ActionEvent event){
        Cliente c = tblClientes.getSelectionModel().getSelectedItem();
        if(c == null)
        {
            JOptionPane.showMessageDialog(null,"Seleccionar un cliente");
            return;
        }
        try {
            apWindow.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/EditarCliente.fxml"));
            AnchorPane window = loader.load();
            EditarClienteController editarClienteController = loader.getController();
            editarClienteController.setCliente(c);
            apWindow.getChildren().add(window);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void txtIdAction(ActionEvent event){
        if (txtId.getText().equals(""))
        {
            tblClientes.getItems().setAll(clientes);
            return;
        }
        List<Cliente> result = new ArrayList<>();
        clientes.forEach(e -> {
            if(e.getId() == Integer.parseInt(txtId.getText()))
                result.add(e);
        });
        tblClientes.getItems().setAll(result);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            con  = utils.ConnectionUtil.conDB();
            String sql = "SELECT * FROM cliente";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Cliente cliente = new Cliente(resultSet.getInt("id"), 
                        resultSet.getString("Direccion"), 
                        resultSet.getInt("Distrito_id"), 
                        resultSet.getInt("TipoCliente_id"), 
                        resultSet.getInt("Persona_id"));
                clientes.add(cliente);
                
            };
            
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getNombres()));
            collApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getPersona().getApPaterno() + " " + cellData.getValue().getPersona().getApMaterno()));
            colDistrito.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDistrito().getNombre()));
            colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getTelefono()));
           
            tblClientes.getItems().setAll(clientes);
            
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
}
