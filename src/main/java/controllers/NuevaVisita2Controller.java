/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.InfoFalla;
import Class.Reclamo;
import Class.Tecnico;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class NuevaVisita2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    DatePicker dpFecha;
    
    @FXML private TreeTableView<Reclamo> tblReclamos1;
    @FXML private TreeTableColumn<Reclamo, Integer> colId1;
    @FXML private TreeTableColumn<Reclamo, Date> colFecha1;
    @FXML private TreeTableColumn<Reclamo, String> colNombre1;
    @FXML private TreeTableColumn<Reclamo, Integer> colCantidad1;
    @FXML private TreeTableColumn<Reclamo, Integer> colTiempo1;
    
    @FXML private TreeTableView<Reclamo> tblReclamos2;
    @FXML private TreeTableColumn<Reclamo, Integer> colId2;
    @FXML private TreeTableColumn<Reclamo, Date> colFecha2;
    @FXML private TreeTableColumn<Reclamo, String> colNombre2;
    @FXML private TreeTableColumn<Reclamo, Integer> colCantidad2;
    @FXML private TreeTableColumn<Reclamo, Integer> colTiempo2;
    
    //DB
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    List<Reclamo> reclamos1 = new ArrayList<>();
    List<Reclamo> reclamos2 = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        FillTable1();
        FillTable2();
    }    

    void setData(Tecnico t, LocalDate value) {
        dpFecha.setValue(value);
    }

    private void FillTable1() {
        try {
            // TODO
            String sql = "SELECT * FROM reclamo where estadoId = 1";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Reclamo reclamo = new Reclamo(resultSet.getInt("id"), 
                        resultSet.getInt("clienteId"), 
                        resultSet.getInt("reclamoId"), 
                        resultSet.getInt("estadoId"), 
                        resultSet.getDate("fecha"));
                reclamos1.add(reclamo);
            };
            
            TreeItem<Reclamo> rootNode = new TreeItem<>(reclamos1.get(0));
            for (Reclamo r: reclamos1) {
                TreeItem<Reclamo> node = new TreeItem<>(r);
                rootNode.getChildren().add(node);
                
                if (r.getProductos().size() == 1)
                {
                    r.setNombreProducto(r.getProductos().get(0).getProducto());
                    r.setNombreFalla(r.getProductos().get(0).getFalla().getTipoFalla().getNombre());
                    r.setCantidad(r.getProductos().get(0).getCantidad());
                    r.setTiempo(r.getCantidad()*r.getProductos().get(0).getFalla().getTiempoResolucion());
                }
                else
                {
                    int CantidadTotal=0;
                    int TimepoTotal=0;
                    for (InfoFalla i: r.getProductos())
                    {
                        Reclamo rec = new Reclamo(r.getId(),r.getCliente().getId(),r.getReclamoId(),i.getEstadoId(),r.getFecha());
                        rec.setNombreFalla(i.getFalla().getTipoFalla().getNombre());
                        rec.setNombreProducto(i.getProducto());
                        rec.setCantidad(i.getCantidad());
                        rec.setTiempo(rec.getCantidad()*i.getFalla().getTiempoResolucion());
                        TreeItem<Reclamo> node2 = new TreeItem<>(rec);
                        node.getChildren().add(node2);
                        CantidadTotal += i.getCantidad();
                        TimepoTotal += rec.getTiempo();
                    }
                    r.setTiempo(TimepoTotal);
                    r.setCantidad(CantidadTotal);
                }
                
            }
            tblReclamos1.setShowRoot(false);
            tblReclamos1.setRoot(rootNode);
            colId1.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colFecha1.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha"));
            colNombre1.setCellValueFactory(new TreeItemPropertyValueFactory<>("NombreProducto"));
            colCantidad1.setCellValueFactory(new TreeItemPropertyValueFactory<>("cantidad"));
            colTiempo1.setCellValueFactory(new TreeItemPropertyValueFactory<>("tiempo"));
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void FillTable2() {

    }
    
}
