/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author flash
 */
public class RegistrarReclamo2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label txtDni;
    @FXML
    DatePicker dpFecha;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setData(int clienteId, LocalDate value, String text) {
        txtDni.setText(String.valueOf(clienteId));
        dpFecha.setValue(value);
    }
    
}
