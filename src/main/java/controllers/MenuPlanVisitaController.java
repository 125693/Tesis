/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private void btnClickBack(ActionEvent event) throws IOException{  
        apMenu.getChildren().clear();
        AnchorPane nuevo = FXMLLoader.load(getClass().getResource("/views/Menu.fxml"));
        apMenu.getChildren().add(nuevo); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
