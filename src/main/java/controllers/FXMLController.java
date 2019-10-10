package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class FXMLController implements Initializable {
    
    @FXML
    private Button BtnIngresar;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    private boolean Credenciales(String email, String password)
    {
        try {
            String sql = "SELECT * FROM color";
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("Nombre"));
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    @FXML
    void onClickBtnLogin(ActionEvent event) throws IOException {
        System.out.println("aber");
        Credenciales("user", "password");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("SST");
        stage.setScene(new Scene(root1));
        stage.show();

        Stage stageOwn = (Stage) BtnIngresar.getScene().getWindow();
        stageOwn.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con  = utils.ConnectionUtil.conDB();
    }    
}
