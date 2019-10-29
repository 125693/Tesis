/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.Cliente;
import Class.Color;
import Class.ProductoReclamo;
import Class.GamaProducto;
import Class.Modelo;
import Class.TipoFalla;
import Class.TipoProducto;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class RegistrarReclamo2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label txtDni;
    @FXML
    DatePicker dpFecha;
    @FXML
    ComboBox cboTipoProducto;
    @FXML
    ComboBox cboGama;
    @FXML
    ComboBox cboFalla;
    @FXML
    ComboBox cboColor;
    @FXML
    ComboBox cboModelo;
    @FXML
    TextField txtCantidad;
    @FXML
    TextField txtName;
    @FXML
    TextArea taComentario;
  
    @FXML private TableView<ProductoReclamo> tbllProducto;
    @FXML private TableColumn<ProductoReclamo, String> colFalla;
    @FXML private TableColumn<ProductoReclamo, String> colNombre;
    @FXML private TableColumn<ProductoReclamo, String> colTipo;
    @FXML private TableColumn<ProductoReclamo, String> colGama;
    @FXML private TableColumn<ProductoReclamo, Integer> ColCantidad;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<TipoProducto> tipos = new ArrayList<>();
    ArrayList<GamaProducto> gamas = new ArrayList<>();
    ArrayList<TipoFalla> fallas = new ArrayList<>();
    ArrayList<Color> colors = new ArrayList<>();
    ArrayList<Modelo> modelos = new ArrayList<>();
    ArrayList<ProductoReclamo> productos = new ArrayList<>();
    ArrayList<TipoFalla> tiposfalla = new ArrayList<>();
    
    int IndexTipoProducto = -1;
    int IndexModeloProducto = -1;
    int IndexGamaProducto = -1;
    int IndexTipoFalla = -1;
    int IndexColor = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        FillCombos();
        colFalla.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoFalla().getNombre()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoProducto().getNombre()));
        colGama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGama().getNombre()));
        ColCantidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCantidad()));
    }    

    void setData(int clienteId, LocalDate value, String text) {
        txtDni.setText(String.valueOf(clienteId));
        dpFecha.setValue(value);
    }

    private void FillCombos() {
        try {
            // TODO
            String sql = "SELECT * FROM tipoproducto";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                TipoProducto producto = new TipoProducto(resultSet.getInt("id"), resultSet.getString("Nombre"));
                tipos.add(producto);
                cboTipoProducto.getItems().add(producto.getNombre());
            }
            
            sql = "SELECT * FROM gamaproducto";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                GamaProducto gama = new GamaProducto(resultSet.getInt("id"), resultSet.getString("Nombre"));
                gamas.add(gama);
                cboGama.getItems().add(gama.getNombre());
            }
            
            sql = "SELECT * FROM tipofalla";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                TipoFalla falla = new TipoFalla(resultSet.getInt("id"), resultSet.getString("Nombre"));
                fallas.add(falla);
                cboFalla.getItems().add(falla.getNombre());
            }
            
            sql = "SELECT * FROM color";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Color color = new Color(resultSet.getInt("id"), resultSet.getString("Nombre"));
                colors.add(color);
                cboColor.getItems().add(color.getNombre());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NuevoClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void cboGamaAction(ActionEvent event){  
        IndexGamaProducto = cboGama.getSelectionModel().getSelectedIndex();
        IndexGamaProducto++;
    }
    
    @FXML
    private void cboTipoProductoAction(ActionEvent event){  
        try {
            IndexTipoProducto = cboTipoProducto.getSelectionModel().getSelectedIndex();
            IndexTipoProducto++;
            
            cboModelo.getItems().clear();
            modelos.clear();
            IndexModeloProducto = -1;
            
            String sql = "SELECT * FROM modeloproducto WHERE TipoProducto_id = ?"; 
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,IndexTipoProducto);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Modelo modelo = new Modelo(resultSet.getInt("id"), resultSet.getString("Nombre"),resultSet.getInt("TipoProducto_id"));
                modelos.add(modelo);
                cboModelo.getItems().add(modelo.getNombre());
            }
            cboModelo.setDisable(false);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void cboFallaAction(ActionEvent event){  
        IndexTipoFalla = cboFalla.getSelectionModel().getSelectedIndex();
        IndexTipoFalla++;
    }
    
    @FXML
    private void btnAgregarAction(ActionEvent event){  
        if (!validationProduct()) return;
        ProductoReclamo producto = new ProductoReclamo(txtName.getText(), 
                colors.get(IndexColor-1), 
                modelos.get(IndexModeloProducto-1), 
                tipos.get(IndexTipoProducto-1), 
                gamas.get(IndexGamaProducto-1), 
                fallas.get(IndexTipoFalla-1),
                Integer.parseInt(txtCantidad.getText()),
                taComentario.getText());
        productos.add(producto);
        
//        colFalla.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoFalla().getNombre()));
//        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
//        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoProducto().getNombre()));
//        colGama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGama().getNombre()));
//        ColCantidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCantidad()));
        
        tbllProducto.getItems().setAll(productos);
    }
    
    @FXML
    private void cboColorAction(ActionEvent event){  
        IndexColor = cboColor.getSelectionModel().getSelectedIndex();
        IndexColor++;
        
    }
    
    @FXML
    private void cboModeloAction(ActionEvent event){  
        IndexModeloProducto = cboModelo.getSelectionModel().getSelectedIndex();
        IndexModeloProducto++;
    }

    private boolean validationProduct() {
        if(IndexColor == -1 ||
                IndexGamaProducto == -1 ||
                IndexModeloProducto == -1 ||
                IndexTipoProducto == -1 ||
                IndexTipoFalla == -1 ||
                "".equals(txtCantidad.getText()))
        {
            JOptionPane.showMessageDialog(null,"Completar todos los campos");
            return false;
        }
        return true;
    }
    
    
    
}
