/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Class.InfoFalla;
import Class.PlanVisita;
import Class.Reclamo;
import Class.Tecnico;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class AlgoritmoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Tecnico> tblTecnicos;
    @FXML private TableColumn<Tecnico, Integer> colId;
    @FXML private TableColumn<Tecnico, String> colNombre;
    @FXML private TableColumn<Tecnico, String> collApellidos;
    @FXML private TableColumn<Tecnico, String> colTipo;
    @FXML private TableColumn<Tecnico, String> colTurno;
    
    @FXML private TreeTableView<Reclamo> tblReclamos;
    @FXML private TreeTableColumn<Reclamo, Integer> colId2;
    @FXML private TreeTableColumn<Reclamo, Date> colFecha;
    @FXML private TreeTableColumn<Reclamo, String> colDoc;
    @FXML private TreeTableColumn<Reclamo, String> colEstado;
    @FXML private TreeTableColumn<Reclamo, String> colNombre2;
    @FXML private TreeTableColumn<Reclamo, String> colFalla;
    
    List<Tecnico> tecnicos = new ArrayList<>();
    List<Reclamo> reclamos = new ArrayList<>();
    List<PlanVisita> planesVisita = new ArrayList<>();
    
    TreeItem<Reclamo> rootNode;
    @FXML
    DatePicker dpFechaInicio;
    @FXML
    DatePicker dpFechaFin;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
        dpFechaInicio.setValue(LocalDate.now());
        dpFechaFin.setValue(LocalDate.now());
        FillTable();
        LoadTableData();
    }    
    
    private void FillTable() {
        try {
            String sql = "SELECT * FROM tecnico"; 
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Tecnico tecnico = new Tecnico(resultSet.getInt("id"),
                        resultSet.getInt("TipoTurno_id"),
                        resultSet.getInt("TipoTecnico_id"), 
                        resultSet.getInt("Persona_id"));
                tecnicos.add(tecnico);
                
            };
            
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getNombres()));
            collApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getPersona().getApPaterno() + " " + cellData.getValue().getPersona().getApMaterno()));
            colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTecnico().getNombre()));
            colTurno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTurno().getNombre()));
           
            tblTecnicos.getItems().setAll(tecnicos);
        } catch (SQLException ex) {
            Logger.getLogger(ListarTecnicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void LoadTableData() {
        try {
            // TODO
            String sql = "SELECT * FROM reclamo where estadoId = 1 order by fecha";
            preparedStatement = con.prepareStatement(sql); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Reclamo reclamo = new Reclamo(resultSet.getInt("id"), 
                        resultSet.getInt("clienteId"), 
                        resultSet.getInt("reclamoId"), 
                        resultSet.getInt("estadoId"), 
                        resultSet.getDate("fecha"));
                reclamos.add(reclamo);
            };
            
            rootNode = new TreeItem<>(reclamos.get(0));
            for (Reclamo r: reclamos) {
                TreeItem<Reclamo> node = new TreeItem<>(r);
                rootNode.getChildren().add(node);
                
                if (r.getProductos().size() == 1)
                {
                    r.setNombreProducto(r.getProductos().get(0).getProducto());
                    r.setNombreFalla(r.getProductos().get(0).getFalla().getTipoFalla().getNombre());
                }
                else
                {
                    for (InfoFalla i: r.getProductos())
                    {
                        Reclamo rec = new Reclamo(r.getId(),r.getCliente().getId(),r.getReclamoId(),i.getEstadoId(),r.getFecha());
                        rec.setNombreFalla(i.getFalla().getTipoFalla().getNombre());
                        rec.setNombreProducto(i.getProducto());
                        TreeItem<Reclamo> node2 = new TreeItem<>(rec);
                        node.getChildren().add(node2);
                    }
                }
                
            }
            tblReclamos.setShowRoot(false);
            tblReclamos.setRoot(rootNode);
            colId2.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colFecha.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha"));
            colDoc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCliente().getPersona().getId())));
            colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEstado().getNombre()));
            colNombre2.setCellValueFactory(new TreeItemPropertyValueFactory<>("NombreProducto"));
            colFalla.setCellValueFactory(new TreeItemPropertyValueFactory<>("NombreFalla"));
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void btnRemoveTecnicoClick(ActionEvent event){
        Tecnico selectedItem = tblTecnicos.getSelectionModel().getSelectedItem();
        tblTecnicos.getItems().remove(selectedItem);
    }
    @FXML
    private void btnRemoveReclamoClick(ActionEvent event){
        TreeItem<Reclamo> r = tblReclamos.getSelectionModel().getSelectedItem();
        if(r == null || r.getParent().getParent() != null)
        {
            JOptionPane.showMessageDialog(null,"Seleccionar un reclamo");
            return;
        }
        r.getParent().getChildren().remove(r);
    }
    @FXML
    private void btnPlanClick(ActionEvent event){
        //Algoritmo
        try
        {
            List<Integer> ListaCandidatos;
            long MaxDays = 3;
            //Obtencion de planes
            long days = DAYS.between(dpFechaInicio.getValue(), dpFechaFin.getValue());
            days++;
            for (int i = 0; i < days; i++) {
                for (int j = 0; j < tblTecnicos.getItems().size() ; j++) {
                    String sql = "SELECT * FROM planvisita where fecha = ? and tecnicoId = ?";
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setDate(1, Date.valueOf(dpFechaInicio.getValue().plusDays(i)));
                    preparedStatement.setInt(2, tblTecnicos.getItems().get(j).getId());
                    resultSet = preparedStatement.executeQuery();
                    
                    int PlanId = -1;
                    while(resultSet.next()){
                        PlanId = resultSet.getInt("id");
                    }
                    PlanVisita plan = new PlanVisita(PlanId, dpFechaInicio.getValue().plusDays(i), tblTecnicos.getItems().get(j));
                    if(plan.getHoras() < 9)
                        planesVisita.add(plan);
                }
            }
            //elegir tecino para cada reclamo
            for (int i = 0; i < rootNode.getChildren().size(); i++) {
                Reclamo r = rootNode.getChildren().get(i).getValue();
                Integer DistritoId = r.getCliente().getDistrito().getId();
                Integer EspecialidadId = r.getProductos().get(0).getFalla().getEspecialidad_id();
                Integer Tiempo = r.getProductos().get(0).getCantidad()*r.getProductos().get(0).getFalla().getTiempoResolucion();
                if (r.getProductos().size() == 1)
                {
                    ListaCandidatos = GetListaCandidatos(DistritoId,EspecialidadId,Tiempo);
                }
                else
                {
                    boolean sameFalla = true;
                    for (int j = 1; j < r.getProductos().size(); j++) {
                        Tiempo += r.getProductos().get(j).getCantidad()*r.getProductos().get(j).getFalla().getTiempoResolucion();
                        if(EspecialidadId != r.getProductos().get(j).getFalla().getEspecialidad_id())
                            sameFalla = false;
                    }
                    if(sameFalla)
                        ListaCandidatos = GetListaCandidatos(DistritoId,EspecialidadId,Tiempo);
                    else
                        ListaCandidatos = GetListaCandidatos(DistritoId,-1,Tiempo);
                }
                if(ListaCandidatos.size() == 0) break;
                int index = SelectCandidato(ListaCandidatos);
                planesVisita.get(index).getReclamos().add(r);
                int horas = 0;
                for (int j = 0; j < r.getProductos().size(); j++) {
                   horas += r.getProductos().get(j).getCantidad()*r.getProductos().get(j).getFalla().getTiempoResolucion();
                }
                planesVisita.get(index).setHoras(planesVisita.get(index).getHoras()+horas);
            }
            
            //GUARDAR EN BD
            for (int i = 0; i < planesVisita.size(); i++) {
                if (planesVisita.get(i).getReclamos().size() != 0) {
                    Integer PlanId = planesVisita.get(i).getId();
                    String sql;
                    if (planesVisita.get(i).getId() == -1) {
                        sql = "INSERT INTO planvisita (fecha, tecnicoId) VALUES (?,?)";
                        preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        preparedStatement.setDate(1,Date.valueOf(planesVisita.get(i).getFecha()));
                        preparedStatement.setInt(2,planesVisita.get(i).getTecnico().getId());
                        preparedStatement.executeUpdate();

                        resultSet = preparedStatement.getGeneratedKeys();
                        while(resultSet.next())
                        {
                           planesVisita.get(i).setId(resultSet.getInt(1));
                        }
                    }
                    for (int j = 0; j < planesVisita.get(i).getReclamos().size(); j++) {
                        sql = "SELECT * FROM planvisita_has_reclamo where reclamoId = ? and planVisitaId = ?";
                        preparedStatement = con.prepareStatement(sql); 
                        preparedStatement.setInt(1, planesVisita.get(i).getReclamos().get(j).getId());
                        preparedStatement.setInt(2, planesVisita.get(i).getId());
                        resultSet = preparedStatement.executeQuery();

                        Boolean found = false;
                        while(resultSet.next()){
                            found = true;
                        };
                        if(!found)
                        {
                            sql = "INSERT INTO planvisita_has_reclamo (reclamoId, planVisitaId, estadoId) VALUES (?,?,1)";
                            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            preparedStatement.setInt(1,planesVisita.get(i).getReclamos().get(j).getId());
                            preparedStatement.setInt(2,planesVisita.get(i).getId());
                            preparedStatement.executeUpdate();

                            sql = "UPDATE reclamo SET estadoId = 2 WHERE  (id = ?)";
                            preparedStatement = con.prepareStatement(sql);
                            preparedStatement.setInt(1,planesVisita.get(i).getReclamos().get(j).getId());
                            preparedStatement.executeUpdate();
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null,"Reclamos asignados");
        } catch (SQLException ex) {
            Logger.getLogger(ListaClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private List<Integer> GetListaCandidatos(Integer DistritoId, Integer EspecialidadId, Integer Tiempo) {
        List<Integer> Candidatos = new ArrayList<Integer>();
        for (int i = 0; i < planesVisita.size(); i++) {
            //LocalDate PlanFecha = planesVisita.get(i).getFecha();
            Integer Horas = planesVisita.get(i).getHoras();
            if(planesVisita.get(i).getHoras() > 9) continue;
            if (Horas + Tiempo > 9)
                continue;
            if(EspecialidadId != planesVisita.get(i).getTecnico().getEspecialidadId())
                continue;
            boolean match = false;
            if(planesVisita.get(i).getReclamos().size() == 0) match = true;
            for (Reclamo reclamo : planesVisita.get(i).getReclamos()) {
                if(reclamo.getCliente().getDistrito().getId() == DistritoId)
                    match = true;
            }
            if(!match) continue;
            Candidatos.add(i);
        }
        if(Candidatos.size() != 0)
            return Candidatos;
        
        for (int i = 0; i < planesVisita.size(); i++) {
            Integer Horas = planesVisita.get(i).getHoras();
            if(planesVisita.get(i).getHoras() > 9) continue;
            if(EspecialidadId != planesVisita.get(i).getTecnico().getEspecialidadId())
                continue;
            boolean match = false;
            if(planesVisita.get(i).getReclamos().size() == 0) match = true;
            for (Reclamo reclamo : planesVisita.get(i).getReclamos()) {
                if(reclamo.getCliente().getDistrito().getId() == DistritoId)
                    match = true;
            }
            if(!match) continue;
            Candidatos.add(i);
        }
        if(Candidatos.size() != 0)
            return Candidatos;
        
        for (int i = 0; i < planesVisita.size(); i++) {
            Integer Horas = planesVisita.get(i).getHoras();
            if(planesVisita.get(i).getHoras() > 9) continue;
            if(planesVisita.get(i).getTecnico().getEspecialidadId() != -1)
                continue;
            boolean match = false;
            if(planesVisita.get(i).getReclamos().size() == 0) match = true;
            for (Reclamo reclamo : planesVisita.get(i).getReclamos()) {
                if(reclamo.getCliente().getDistrito().getId() == DistritoId)
                    match = true;
            }
            if(!match) continue;
            Candidatos.add(i);
        }
        if(Candidatos.size() != 0)
            return Candidatos;
        
        for (int i = 0; i < planesVisita.size(); i++) {
            Integer Horas = planesVisita.get(i).getHoras();
            if(planesVisita.get(i).getHoras() > 9) continue;
            boolean match = false;
            if(planesVisita.get(i).getReclamos().size() == 0) match = true;
            for (Reclamo reclamo : planesVisita.get(i).getReclamos()) {
                if(reclamo.getCliente().getDistrito().getId() == DistritoId)
                    match = true;
            }
            if(!match) continue;
            Candidatos.add(i);
        }
        if(Candidatos.size() != 0)
            return Candidatos;
        
        for (int i = 0; i < planesVisita.size(); i++) {
            Integer Horas = planesVisita.get(i).getHoras();
            if(planesVisita.get(i).getHoras() > 9) continue;
            Candidatos.add(i);
        }
        
        return Candidatos;
    }

    private int SelectCandidato(List<Integer> ListaCandidatos) {
        Random rand = null;
        int randomNum = (int) (Math.random()*ListaCandidatos.size()/5);
        System.out.println(ListaCandidatos.size());
        System.out.println(randomNum);
        return ListaCandidatos.get(randomNum);
    }
}
