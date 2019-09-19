/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
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
import javafx.scene.layout.Background;

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
    
    public void setapWindow(AnchorPane apWindow) throws IOException
    {
        this.apWindow=apWindow;
        apWindow.getChildren().clear();
        AnchorPane window = FXMLLoader.load(getClass().getResource("/views/RegistrarReclamo1.fxml"));
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
            MC.setapWindow(this.apWindow);
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
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/ListaReclamos.fxml"));
            apWindow.getChildren().add(window);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnNuevoClick(ActionEvent event){  
        try {
            if (selected == btnNuevo) return;
            ChangeSelectedButton(btnNuevo,btnListado);
            apWindow.getChildren().clear();
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/RegistrarReclamo1.fxml"));
            apWindow.getChildren().add(window);
        } catch (IOException ex) {
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ChangeSelectedButton(Button newselected, Button oldselected) {
        newselected.setStyle("-fx-background-color: #729bb3;");
        oldselected.setStyle("-fx-background-color: #FDFEFE;");
        selected = newselected;       
    }
    
}
