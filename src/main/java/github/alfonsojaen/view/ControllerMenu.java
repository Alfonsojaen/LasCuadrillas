package github.alfonsojaen.view;

import github.alfonsojaen.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu {
   // VBox contenedorImagen;
    //ImageView imagen;

    @FXML
    private void MenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla", null);
                }
    @FXML
    private void MenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero", null);
    }
    @FXML
    private void MenuPaso() throws IOException {
        Scenes.setRoot("pantallaMenuPaso", null);
    }
    @FXML
    private void MenuLogin() throws IOException {
        Scenes.setRoot("pantallaLoginUser", null);
    }

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imagen.preserveRatioProperty().set(false);
        contenedorImagen.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(newVal);
            imagen.setFitWidth(newVal.doubleValue());
            // Do whatever you want
        });
        contenedorImagen.heightProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(newVal);
            imagen.setFitHeight(newVal.doubleValue());
            // Do whatever you want
        });
    }*/
}


