package github.alfonsojaen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

       /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pantallaInsertCuadrilla.fxml"));
        Parent root = fxmlLoader.load();
        ControllerInsertCuadrilla controller = fxmlLoader.getController();

        // Configurar cualquier otra cosa en el controlador si es necesario

        // Crear la escena y establecerla en la ventana
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();*/
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pantallaLoginUser.fxml"));
        Parent root = fxmlLoader.load();

        // Configurar cualquier otra cosa en el controlador si es necesario

        // Crear la escena y establecerla en la ventana
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();

    }


    static void setRoot(String fxml) throws IOException {
        //scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
      //  FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
       //return fxmlLoader.load();
        return null;
    }

    public static void main(String[] args) {
        //launch();
        launch(args);
    }

}