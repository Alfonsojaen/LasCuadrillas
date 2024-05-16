package github.alfonsojaen.view;

import javafx.fxml.FXML;

import java.io.IOException;


public class ControllerMenu {
    /**
     * Switches to the Cuadrilla menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void MenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla", null, null);
                }

    /**
     * Switches to the Costalero menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */


    @FXML
    private void MenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero", null, null);
    }

    /**
     * Switches to the Paso menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void MenuPaso() throws IOException {
        Scenes.setRoot("pantallaMenuPaso", null, null);
    }

    /**
     * Switches to the Login menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void MenuLogin() throws IOException {
        Scenes.setRoot("pantallaLoginUser", null, null);
    }

}


