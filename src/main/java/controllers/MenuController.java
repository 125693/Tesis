/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
/**
 * FXML Controller class
 *
 * @author flash
 */
public class MenuController implements Initializable {   
    
    @FXML
    private AnchorPane ap1;
    
    @FXML
    void btnClickReclamos(ActionEvent event) throws IOException{
        ap1.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/MenuReclamo.fxml"));
        ap1.getChildren().add(nuevo); 
    }
    
    @FXML
    void btnClickClientes(ActionEvent event) throws IOException{
        ap1.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/MenuCliente.fxml"));
        ap1.getChildren().add(nuevo); 
    }
    
    @FXML
    void btnClickAlamacen(ActionEvent event) throws IOException{
//        ap1.getChildren().clear();
//        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/MenuReclamo.fxml"));
//        ap1.getChildren().add(nuevo); 
    }
    
    @FXML
    void btnClickTecnicos(ActionEvent event) throws IOException{
        ap1.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/MenuTecnicos.fxml"));
        ap1.getChildren().add(nuevo); 
    }
    
    @FXML
    void btnClickVisitas(ActionEvent event) throws IOException{
        ap1.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/MenuPlanVisita.fxml"));
        ap1.getChildren().add(nuevo); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
