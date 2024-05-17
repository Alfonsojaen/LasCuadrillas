package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Paso;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Controller class for inserting a Paso.
 */
public class ControllerInsertPaso {
    @FXML
    private TextField Brotherhood;

    @FXML
    private TextField Capacity;

    @FXML
    private ImageView boton;

    @FXML
    private Button botonInsertar;

    /**
     * Handles the insertion of a Paso when the insert button is clicked.
     *
     * @param event Action event triggered by the insert button
     * @throws SQLException If an SQL exception occurs during insertion
     */
    @FXML
    public void handleInsertarPaso(ActionEvent event) throws SQLException {
        PasoDAO pasoDAO = PasoDAO.build();
        String brotherhood = Brotherhood.getText();
        String capacity = Capacity.getText();

        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Se encontraron los siguientes errores:\n");

        if (brotherhood.isEmpty() || capacity.isEmpty()) {
            errorMessage.append("- El campo Hermandad y el campo Capacidad son obligatorio\n");
            isValid = false;

        } if (!brotherhood.matches("[a-zA-Z\\s]+")) {
            errorMessage.append("- El campo Hermandad no puede contener números.\n");
            isValid = false;
        }
        if (capacity.replaceAll("[^0-9]", "").length() > 3) {
            errorMessage.append("- El campo capacidad no puede contener más de 3 números.\n");
            isValid = false;
        }
        if (pasoExists(brotherhood)) {
            errorMessage.append("- Ya existe un paso con ese nombre.\n");
            isValid = false;
        }
        if (brotherhood.length() > 25) {
            errorMessage.append("- El campo Hermandad no puede tener más de 25 caracteres .\n");
            isValid = false;
        }
        if (brotherhood.isBlank() || capacity.isBlank()) {
            errorMessage.append("- El campo Hermandad y Capacidad no pueden contener solo espacios en blanco.\n");
            isValid = false;
        }
        if (!isValid) {
            Utils.ShowAlert(errorMessage.toString());
            return;
        }
        int capacityInt = Integer.parseInt(capacity);

        Paso paso = new Paso(0, brotherhood, capacityInt, null);

        pasoDAO.save(paso);
        Brotherhood.setText("");
        Capacity.setText("");
    }

    /**
     * Checks if a paso with the given brotherhood already exists.
     *
     * @param brotherhood The brotherhood of the paso
     * @return True if the paso exists, otherwise false
     */
    private boolean pasoExists(String brotherhood)  {
        Paso existingPaso = PasoDAO.build().findByBrotherhood(brotherhood);
        return existingPaso.getId() != 0;
    }

    /**
     * Switches to the Paso menu screen.
     */
    @FXML
    private void switchToMenuPaso() {
        try {
            Scenes.setRoot("pantallaMenuPaso", null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

