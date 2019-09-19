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
    
    public void reinicia() throws IOException {
        apWindow.getChildren().clear();
        AnchorPane window = FXMLLoader.load(getClass().getResource("/views/WindowIntro.fxml"));
        apWindow.getChildren().add(window);

        apMenu.getChildren().clear();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/views/Menu.fxml"));
        AnchorPane menu = loader.load();
        MenuController MC = loader.getController();
        MC.setapWindow(this.apWindow,this.apMenu); 
        apMenu.getChildren().add(menu);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            reinicia();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
