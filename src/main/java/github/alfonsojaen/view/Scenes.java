package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scenes {

    /**
     * Sets the root scene based on the given FXML file and optional data parameters.
     * @param fxml the name of the FXML file
     * @param costalero the Costalero object to pass to the scene (can be null)
     * @param cuadrilla the Cuadrilla object to pass to the scene (can be null)
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public static void setRoot(String fxml, Costalero costalero, Cuadrilla cuadrilla) throws IOException {
        Parent p = App.loadFXML(fxml);
        Scene newScene;

        if (fxml.equals("pantallaLoginUser")) {
            newScene = App.createScene(fxml, 640, 460, null, null);

        } else if (fxml.equals("pantallaMenuCostalero")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaInsertCostalero")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaInsertCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaRegisterUser")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaMenu")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaMenuCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaMenuPaso")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaAssignCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, costalero,null);

        } else if (fxml.equals("pantallaDeleteCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, null,null);

        } else if (fxml.equals("pantallaAssignPaso")) {
            newScene = App.createScene(fxml, 640, 460, null,cuadrilla);

        } else {
            newScene = App.createScene(fxml, 640, 480, null,null);
        }
        App.primaryStage.setScene(newScene);
        App.scene.setRoot(p);


    }
}
