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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class MainController implements Initializable {

    public AnchorPane apMenu, apWindow;
    
    @FXML
    void inicia() throws IOException {
        apMenu.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/Menu.fxml"));
        apMenu.getChildren().add(nuevo); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                        
        apWindow.getChildren().clear();
        AnchorPane window = null;
        try {
            window = FXMLLoader.load(getClass().getResource("/views/WindowIntro.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        apWindow.getChildren().add(window);
        
        
        apMenu.getChildren().clear();
        AnchorPane menu = null;
        try {
            menu = FXMLLoader.load(getClass().getResource("/views/Menu.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (menu != null)
            apMenu.getChildren().add(menu); 
        
    }    
    
}
