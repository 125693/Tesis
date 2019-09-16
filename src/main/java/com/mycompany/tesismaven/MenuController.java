/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tesismaven;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private AnchorPane apWindow;
    
    public void setapWindow(AnchorPane apWindow) throws IOException
    {
        this.apWindow=apWindow;
        apWindow.getChildren().clear();
        AnchorPane window = FXMLLoader.load(getClass().getResource("/views/WindowIntro.fxml"));
        apWindow.getChildren().add(window);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
