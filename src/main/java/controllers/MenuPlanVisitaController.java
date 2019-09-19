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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class MenuPlanVisitaController implements Initializable {

    @FXML
    public AnchorPane apMenu;
    
    @FXML 
    AnchorPane apWindow;
    
    @FXML
    Button selected;
    
    @FXML
    public Button btnListado;
    
    @FXML
    public Button btnNuevo;
    
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
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu = apMenu;
        apWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/views/NuevaVisita1.fxml"));
        AnchorPane window = loader.load();
        NuevaVisita1Controller NV1C = loader.getController();
        NV1C.setapWindow(this.apWindow,this.apMenu);
        apWindow.getChildren().add(window);
        selected = this.btnNuevo;
        this.btnNuevo.setStyle("-fx-background-color: #729bb3;");
    }
    
    @FXML
    private void btnListadoClick(ActionEvent event){  
        try {
            if (selected == btnListado) return;
            ChangeSelectedButton(btnListado,btnNuevo);
            apWindow.getChildren().clear();
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/ListaVisitas.fxml"));
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
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("/views/NuevaVisita1.fxml"));
            AnchorPane window = loader.load();
            NuevaVisita1Controller NV1C = loader.getController();
            NV1C.setapWindow(this.apWindow,this.apMenu);
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
