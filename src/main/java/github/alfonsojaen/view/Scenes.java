package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.entity.Costalero;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scenes {
    public static void setRoot(String fxml, Costalero costalero) throws IOException {
        Parent p = App.loadFXML(fxml);
        Scene newScene;

        if (fxml.equals("pantallaLoginUser")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaMenuCostalero")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaInsertCostalero")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaInsertCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaRegisterUser")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaMenu")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaMenuCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaMenuPaso")) {
            newScene = App.createScene(fxml, 640, 460, null);

        } else if (fxml.equals("pantallaAssignCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, costalero);

        } else if (fxml.equals("pantallaDeleteCuadrilla")) {
            newScene = App.createScene(fxml, 640, 460, costalero);

        } else if (fxml.equals("pantallaAssignPaso")) {
            newScene = App.createScene(fxml, 640, 460, costalero);

        } else {
            newScene = App.createScene(fxml, 640, 480, null);
        }
        App.primaryStage.setScene(newScene);
        App.scene.setRoot(p);


    }
}
