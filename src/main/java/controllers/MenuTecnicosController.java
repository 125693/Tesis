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
public class MenuTecnicosController implements Initializable {

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
    private void btnClickBack(ActionEvent event) throws IOException{  
        apMenu.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/Menu.fxml"));
        apMenu.getChildren().add(nuevo); 
    }
    
    public void setapWindow(AnchorPane apWindow) throws IOException
    {
        this.apWindow=apWindow;
        apWindow.getChildren().clear();
        AnchorPane window = FXMLLoader.load(getClass().getResource("/views/NuevoTecnico.fxml"));
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
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/ListarTecnicos.fxml"));
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
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/NuevoTecnico.fxml"));
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
