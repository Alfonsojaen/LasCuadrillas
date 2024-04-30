package github.alfonsojaen;

import github.alfonsojaen.view.AppController;
import github.alfonsojaen.view.Scenes;
import github.alfonsojaen.view.View;
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
    public static Stage stage;
    public static AppController currentController;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("pantallaLoginUser"), 640, 460);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
    private static Scene createScene(String fxml, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, width, height);
        return scene;
    }
    public static void setRoot(String fxml) throws IOException {
        Parent p = loadFXML(fxml);
        Scene newScene;

        if (fxml.equals("pantallaLoginUser")) {
            newScene = createScene(fxml, 640, 460);
            primaryStage.setResizable(false);

        }else if(fxml.equals("pantallaEditCostaleros")){
                newScene = createScene(fxml, 640, 460);
            primaryStage.setResizable(false);

        }else if(fxml.equals("pantallaInsertCostalero")){
            newScene = createScene(fxml,    640, 460);
            primaryStage.setResizable(false);

        }else if(fxml.equals("pantallaInsertCuadrilla")){
            newScene = createScene(fxml, 640, 460);
            primaryStage.setResizable(false);

        }else if(fxml.equals("pantallaRegisterUser")){
            newScene = createScene(fxml, 640, 460);
            primaryStage.setResizable(false);

        }else if(fxml.equals("pantallaMenu")){
            newScene = createScene(fxml, 640, 460);
            primaryStage.setResizable(false);

        }else if(fxml.equals("pantallaMenuCuadrilla")){
            newScene = createScene(fxml, 640, 460);
            primaryStage.setResizable(false);

        }else {
            newScene = createScene(fxml, 640, 480);
            primaryStage.setResizable(true);
        }
        primaryStage.setScene(newScene);
        App.scene.setRoot(p);


    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}