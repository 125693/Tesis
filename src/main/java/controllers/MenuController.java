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
    void btnClickReclamos(ActionEvent event){
        ap1.getChildren().clear();
        AnchorPane nuevo = null;
        try {
            nuevo = FXMLLoader.load(getClass().getResource("/views/MenuReclamo.fxml"));
        } catch (IOException ex) {
            String error = ex.getMessage();
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ap1.getChildren().add(nuevo); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
