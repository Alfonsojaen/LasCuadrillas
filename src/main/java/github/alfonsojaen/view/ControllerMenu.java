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
        App.setRoot("pantallaMenuCuadrilla");
                }
    @FXML
    private void MenuLogin() throws IOException {
        App.setRoot("pantallaLoginUser");
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


