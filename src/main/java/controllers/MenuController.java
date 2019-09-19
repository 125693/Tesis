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
    AnchorPane apWindow;
    
    @FXML 
    AnchorPane apMenu;
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu = apMenu;
        apWindow.getChildren().clear();
        AnchorPane window = FXMLLoader.load(getClass().getResource("/views/WindowIntro.fxml"));
        apWindow.getChildren().add(window);
    }
    
    @FXML
    void btnClickReclamos(ActionEvent event){
        try {
            apMenu.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/MenuReclamo.fxml"));
            AnchorPane menu = loader.load();
            MenuReclamoController MRC = loader.getController();
            MRC.setapWindow(this.apWindow,this.apMenu);
            apMenu.getChildren().add(menu);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void btnClickClientes(ActionEvent event) throws IOException{
        try {
            apMenu.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/MenuCliente.fxml"));
            AnchorPane menu = loader.load();
            MenuClienteController MCC = loader.getController();
            MCC.setapWindow(this.apWindow,this.apMenu);
            apMenu.getChildren().add(menu);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void btnClickAlamacen(ActionEvent event) throws IOException{
//        ap1.getChildren().clear();
//        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/MenuReclamo.fxml"));
//        ap1.getChildren().add(nuevo); 
    }
    
    @FXML
    void btnClickTecnicos(ActionEvent event) throws IOException{
        apMenu.getChildren().clear();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/views/MenuTecnicos.fxml"));
        AnchorPane menu = loader.load();
        MenuTecnicosController MTC = loader.getController();
        MTC.setapWindow(this.apWindow,this.apMenu);
        apMenu.getChildren().add(menu);
    }
    
    @FXML
    void btnClickVisitas(ActionEvent event){
        try {
            apMenu.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/MenuPlanVisita.fxml"));
            AnchorPane menu = loader.load();
            MenuPlanVisitaController MVC = loader.getController();
            MVC.setapWindow(this.apWindow,this.apMenu);
            apMenu.getChildren().add(menu);
        } catch (IOException ex) {
            String error = ex.getMessage();
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
