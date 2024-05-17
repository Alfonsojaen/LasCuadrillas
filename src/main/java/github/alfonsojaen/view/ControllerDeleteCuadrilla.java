package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class for deleting a Cuadrilla.
 */
public class ControllerDeleteCuadrilla {
    @FXML
    private TextField Name;

    /**
     * Handles the deletion of a Cuadrilla when the delete button is clicked.
     *
     * @param event Action event triggered by the delete button
     */
    @FXML
    public void handleDeleteCuadrilla(ActionEvent event) {
        CuadrillaDAO cuadrillaDAO = CuadrillaDAO.build();
        String nameCuadrilla = Name.getText();

        if (nameCuadrilla.isEmpty()) {
            Utils.ShowAlert("Por favor ingrese el nombre de la cuadrilla.");
            return;
        }
        Cuadrilla cuadrillaDelete = cuadrillaDAO.findByName(nameCuadrilla);

        if (cuadrillaDelete != null) {
            cuadrillaDAO.delete(cuadrillaDelete);
            Utils.ShowAlert("La cuadrilla ha sido borrado correctamente.");
        } else {
            Utils.ShowAlert("No se encontró ninguna cuadrilla con el nombre proporcionado.");
        }

        Name.setText("");
    }

    /**
     * Switches to the Cuadrilla menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void MenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla", null, null);




    }
}
