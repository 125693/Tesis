/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class MenuReclamoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public ImageView imgBack;
    
    @FXML
    public Button btnListado;
    
    @FXML
    public Button btnNuevo;
    
    @FXML
    Button selected;
    
    @FXML 
    AnchorPane apWindow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
        apWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/views/RegistrarReclamo1.fxml"));
        AnchorPane window = loader.load();
        RegistrarReclamo1Controller RRC = loader.getController();
        RRC.setapWindow(this.apWindow,this.apMenu);
        apWindow.getChildren().add(window);
        selected = this.btnNuevo;
        this.btnNuevo.setStyle("-fx-background-color: #729bb3;");
    }
    
    @FXML
    private void btnClickBack(ActionEvent event){  
        try {
            apMenu.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/Menu.fxml"));
            AnchorPane menu = loader.load();
            MenuController MC = loader.getController();
            MC.setapWindow(this.apWindow,this.apMenu);
            apMenu.getChildren().add(menu);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnListadoClick(ActionEvent event){  
        try {
            if (selected == btnListado) return;
            ChangeSelectedButton(btnListado,btnNuevo);
            apWindow.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/ListaReclamos.fxml"));
            AnchorPane window = loader.load();
            ListaReclamosController Controller = loader.getController();
            Controller.setapWindow(this.apWindow,this.apMenu);
            apWindow.getChildren().add(window);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnNuevoClick(ActionEvent event){  
        nuevo();
    }

    private void ChangeSelectedButton(Button newselected, Button oldselected) {
        newselected.setStyle("-fx-background-color: #729bb3;");
        oldselected.setStyle("-fx-background-color: #FDFEFE;");
        selected = newselected;       
    }

    private void nuevo() {
        try {
            if (selected == btnNuevo) return;
            ChangeSelectedButton(btnNuevo,btnListado);
            apWindow.getChildren().clear();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/RegistrarReclamo1.fxml"));
            AnchorPane window = loader.load();
            RegistrarReclamo1Controller RRC = loader.getController();
            RRC.setapWindow(this.apWindow,this.apMenu);
            apWindow.getChildren().add(window);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
