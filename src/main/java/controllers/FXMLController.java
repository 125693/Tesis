package controllers;

import Class.Usuario;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
public class FXMLController implements Initializable {
    
    @FXML
    private Button BtnIngresar;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Label msg;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    private Usuario Credenciales(String email, String password)
    {
        Usuario user = null;
        try {
            String encriptMD5=DigestUtils.md5Hex(password);
            
            String sql = "SELECT * FROM usuario where email = ? and password = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, encriptMD5); 
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                user = new Usuario(resultSet.getInt("id"), 
                        resultSet.getString("email"), 
                        resultSet.getInt("Persona_id"), 
                        resultSet.getInt("TipoUsuario_id"));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    @FXML
    void onClickBtnLogin(ActionEvent event) throws IOException {
        Usuario user = Credenciales(email.getText(), password.getText());
        if(user == null) {
            msg.setText("Credenciales Incorrectas");
            return;
        }
        
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/views/Main.fxml"));
         
        
        Stage stage = new Stage();
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("SST");
        Parent root1 = (Parent) loader.load();
        stage.setScene(new Scene(root1));
        MainController MainController = loader.getController();
        MainController.setUser(user);

        
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
