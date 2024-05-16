package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class for deleting a costalero.
 */
public class ControllerDeleteCostalero {
    @FXML
    private TextField Nickname;

    /**
     * Handles the deletion of a costalero when the delete button is clicked.
     *
     * @param event Action event triggered by the delete button
     */
    @FXML
    public void handleDeleteCostalero(ActionEvent event) {
        CostaleroDAO costaleroDAO = CostaleroDAO.build();
        String nickNameCostalero = Nickname.getText();
        if (nickNameCostalero.isEmpty()) {
            Utils.ShowAlert("Por favor ingrese el nombre del costalero.");
            return;
        }

        Costalero costaleroDelete = costaleroDAO.findByNickname(nickNameCostalero);

        if (costaleroDelete != null) {
            costaleroDAO.delete(costaleroDelete);
            Utils.ShowAlert("El costalero ha sido borrado correctamente.");
        } else {
            Utils.ShowAlert("No se encontró ningún costalero con el nombre proporcionado.");
        }

        Nickname.setText("");
    }

    /**
     * Switches to the costalero menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void MenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero", null, null);
    }


}

