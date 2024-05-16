package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Paso;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class for deleting a Paso.
 */
public class ControllerDeletePaso {
    @FXML
    private TextField Brotherhood;

    /**
     * Handles the deletion of a Paso when the delete button is clicked.
     *
     * @param event Action event triggered by the delete button
     */
    @FXML
    public void handleDeletePaso(ActionEvent event) {
        PasoDAO pasoDAO = PasoDAO.build();
        String nameBrotherhood = Brotherhood.getText();

        if (nameBrotherhood.isEmpty()) {
            Utils.ShowAlert("Por favor ingrese el nombre del paso.");
            return;
        }
        Paso pasoDelete = pasoDAO.findByBrotherhood(nameBrotherhood);

        if (pasoDelete != null) {
            pasoDAO.delete(pasoDelete);
            Utils.ShowAlert( "El paso ha sido borrado correctamente.");
        } else {
            Utils.ShowAlert("No se encontró ningún paso con el nombre proporcionado.");
        }

        Brotherhood.setText("");
    }

    /**
     * Switches to the Paso menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void MenuPaso() throws IOException {
        Scenes.setRoot("pantallaMenuPaso", null,null);
    }

}

