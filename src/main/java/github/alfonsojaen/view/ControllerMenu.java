package github.alfonsojaen.view;

import javafx.fxml.FXML;

import java.io.IOException;


public class ControllerMenu {

    @FXML
    private void MenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla", null, null);
                }
    @FXML
    private void MenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero", null, null);
    }
    @FXML
    private void MenuPaso() throws IOException {
        Scenes.setRoot("pantallaMenuPaso", null, null);
    }
    @FXML
    private void MenuLogin() throws IOException {
        Scenes.setRoot("pantallaLoginUser", null, null);
    }

}


