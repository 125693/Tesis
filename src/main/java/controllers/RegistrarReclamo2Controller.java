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
import com.sun.javafx.font.PGFont;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.objects.NativeArray;

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
    @FXML
    Button btnRegistrar;
    
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
    
    int reclamoPrevId = -1;
    int clienteId = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnRegistrar.setDisable(true);
        con  = utils.ConnectionUtil.conDB();
        FillCombos();
        colFalla.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoFalla().getNombre()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoProducto().getNombre()));
        colGama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGama().getNombre()));
        ColCantidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCantidad()));
    }    

    void setData(int personaId, LocalDate value, String text, int clienteId) {
        txtDni.setText(String.valueOf(personaId));
        dpFecha.setValue(value);
        if (!("".equals(text)))
            reclamoPrevId = Integer.parseInt(text);
        this.clienteId = clienteId;
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
    private void btnRegistrarClick(ActionEvent event){  
        int reclamoId = CreateReclamo();
        productos.forEach((p) -> {
            int productoId = RetrieveProducto(p.getNombre(),p.getColor().getId(),p.getModelo().getId(),p.getTipoProducto().getId(),p.getGama().getId());
            InsertProducto_Reclamo(productoId,reclamoId,p.getTipoFalla().getId(),p.getCantidad(),p.getComentario(),p.getTipoProducto().getId(),p.getGama().getId());
        });
        JOptionPane.showMessageDialog(null,"Reclamo Registrado");
        System.out.println("buah");
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
        tbllProducto.getItems().setAll(productos);
        btnRegistrar.setDisable(false);
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

    private int CreateReclamo() {
        int reclamoId = -1;
        try {
            String sql = "";
            if (reclamoPrevId != -1)
            {
                sql = "INSERT INTO reclamo (clienteId, reclamoId, estadoId, fecha) VALUES (?, ?, ?, ?)";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,clienteId);
                preparedStatement.setInt(2,reclamoPrevId);
                preparedStatement.setInt(3,1);
                preparedStatement.setDate(4,java.sql.Date.valueOf(dpFecha.getValue()));
                preparedStatement.executeUpdate();
            }
            else
            {
                sql = "INSERT INTO reclamo (clienteId, estadoId, fecha) VALUES (?, ?, ?)";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,clienteId);
                preparedStatement.setInt(2,1);
                preparedStatement.setDate(3,java.sql.Date.valueOf(dpFecha.getValue()));
                preparedStatement.executeUpdate();
            }
            resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next())
            {
                reclamoId = resultSet.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reclamoId;
    }

    private int RetrieveProducto(String nombre,int colorId, int modeloId, int TipoId, int gamaId) {
        int productoId = -1;
        String sql = "";
        try {
            sql = "SELECT * FROM producto WHERE Nombre = ? AND Color_id = ? AND ModeloProducto_id = ? AND ModeloProducto_TipoProducto_id = ? AND Gama_id = ?";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,nombre);
            preparedStatement.setInt(2,colorId);
            preparedStatement.setInt(3,modeloId);
            preparedStatement.setInt(4,TipoId);
            preparedStatement.setInt(5,gamaId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                productoId = resultSet.getInt("Id");
            }
            if(productoId == -1)
            {
                sql = "INSERT INTO .producto (Nombre, Color_id, ModeloProducto_id, ModeloProducto_TipoProducto_id, Gama_id) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,nombre);
                preparedStatement.setInt(2,colorId);
                preparedStatement.setInt(3,modeloId);
                preparedStatement.setInt(4,TipoId);
                preparedStatement.setInt(5,gamaId);
                preparedStatement.executeUpdate();
                
                resultSet = preparedStatement.getGeneratedKeys();
                while(resultSet.next())
                {
                    productoId = resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoId;
    }

    private void InsertProducto_Reclamo(int productoId, int reclamoId, int tipoFallaId, int cantidad, String comentario, int TipoId, int gamaId) {
        int especialidadId = -1;
        int fallaId = -1;
        String sql = "";
        try {
            //buscar especialidad
            sql = "SELECT * FROM especialidad WHERE TipoProducto_id = ? AND Gama_id = ?";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,TipoId);
            preparedStatement.setInt(2,gamaId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                especialidadId = resultSet.getInt("Id");
            }
            if(especialidadId == -1)
            {
                sql = "INSERT INTO especialidad (`TipoProducto_id`, `Gama_id`) VALUES (?, ?)";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,TipoId);
                preparedStatement.setInt(2,gamaId);
                preparedStatement.executeUpdate();
                
                resultSet = preparedStatement.getGeneratedKeys();
                while(resultSet.next())
                {
                    especialidadId = resultSet.getInt(1);
                }
            }
            //Buscar Falla
            sql = "SELECT * FROM falla WHERE TipoFalla_id = ? AND Especialidad_id = ?";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,tipoFallaId);
            preparedStatement.setInt(2,especialidadId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                fallaId = resultSet.getInt("Id");
            }
            if(fallaId == -1)
            {
                sql = "INSERT INTO falla (`TiempoResolucion`,`TipoFalla_id`, `Especialidad_id`) VALUES (?, ?, ?)";
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,2);
                preparedStatement.setInt(2,tipoFallaId);
                preparedStatement.setInt(3,especialidadId);
                preparedStatement.executeUpdate();
                
                resultSet = preparedStatement.getGeneratedKeys();
                while(resultSet.next())
                {
                    fallaId = resultSet.getInt(1);
                }
            }
            //insertar reclamo
            sql = "INSERT INTO reclamo_has_producto (`productoId`, `reclamoId`, `estadoId`, `fallaId`, ` Cantidad`, `CantidadResueltos`, `Comentario`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,productoId);
            preparedStatement.setInt(2,reclamoId);
            preparedStatement.setInt(3,1);
            preparedStatement.setInt(4,fallaId);
            preparedStatement.setInt(5,cantidad);
            preparedStatement.setInt(6,0);
            preparedStatement.setString(7,comentario);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarReclamo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
