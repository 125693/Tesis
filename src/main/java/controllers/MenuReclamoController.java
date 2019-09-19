/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void btnClickBack(ActionEvent event) throws IOException{  
        apMenu.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/Menu.fxml"));
        apMenu.getChildren().add(nuevo); 
    }
    
}
