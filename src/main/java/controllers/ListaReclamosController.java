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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class ListaReclamosController implements Initializable {

    @FXML
    public AnchorPane apMenu;
    
    @FXML
    public AnchorPane apWindow;
    
    public void setapWindow(AnchorPane apWindow,AnchorPane apMenu) throws IOException
    {
        this.apWindow=apWindow;
        this.apMenu=apMenu;
    }
    
    @FXML
    private void btnEditarClick(ActionEvent event){  
        try {
            apWindow.getChildren().clear();
            AnchorPane window = FXMLLoader.load(getClass().getResource("/views/EditarReclamo.fxml"));
            apWindow.getChildren().add(window);
        } catch (Exception ex) {
            String error = ex.getMessage();
            Logger.getLogger(MenuReclamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
