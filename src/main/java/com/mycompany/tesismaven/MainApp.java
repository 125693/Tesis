package com.mycompany.tesismaven;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Parent rootNode;
    
    @Override
    public void init() throws Exception {
       // URL url = getClass().getResource("../views/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Scene.fxml"));
        rootNode = fxmlLoader.load();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("SST");
        stage.setScene(new Scene(rootNode));
        stage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
