package github.alfonsojaen;

import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static Scene scene;
    public static Stage primaryStage;

    /**
     * Start method of the application.
     * @param stage the primary stage of the application
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("pantallaLoginUser"), 640, 460);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Static method to create a scene with specific size and optionally passing additional data.
     * @param fxml the name of the FXML file
     * @param width the width of the scene
     * @param height the height of the scene
     * @param costalero the Costalero object to pass to the scene (can be null)
     * @param cuadrilla the Cuadrilla object to pass to the scene (can be null)
     * @return the created scene
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public static Scene createScene(String fxml, double width, double height,Costalero costalero, Cuadrilla cuadrilla) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        if(fxml.equals("pantallaAssignCuadrilla")){
            ControllerAssignCuadrilla controller=fxmlLoader.getController();
            if (controller.getClass().equals(ControllerAssignCuadrilla.class)) {
                System.out.println(controller.getClass());
            }
            controller.setCostalero(costalero);
        }
        if(fxml.equals("pantallaAssignPaso")){
            ControllerAssignPaso controller=fxmlLoader.getController();
            if (controller.getClass().equals(ControllerAssignPaso.class)) {
                controller.getClass();
            }
            controller.setCuadrilla(cuadrilla);
        }
        Scene scene = new Scene(root, width, height);
        return scene;
    }

    /**
     * Static method to load an FXML file and return the root node.
     * @param fxml the name of the FXML file
     * @return the root node of the FXML file
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Main method that starts the application.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}