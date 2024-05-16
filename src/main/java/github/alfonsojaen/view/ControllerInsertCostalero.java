package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class for inserting a Costalero.
 */
public class ControllerInsertCostalero {
    @FXML
    private TextField Nickname;

    @FXML
    private TextField Height;

    @FXML
    private TextField Age;

    /**
     * Handles the insertion of a Costalero when the insert button is clicked.
     *
     * @param event Action event triggered by the insert button
     * @throws SQLException If an SQL exception occurs during insertion
     */
    @FXML
    public void handleInsertarCostalero(ActionEvent event) throws SQLException {
        CostaleroDAO costaleroDAO = CostaleroDAO.build();
        String nickname = Nickname.getText();
        String height = Height.getText();
        String age = Age.getText();

        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Se encontraron los siguientes errores:\n");

        if (nickname.isEmpty() || height.isEmpty() || age.isEmpty()) {
            errorMessage.append("- El campo Apodo, el campo Altura y el campo Edad son obligatorio\n");
            isValid = false;

        } if (!nickname.matches("[a-zA-Z\\s]+")) {
            errorMessage.append("- El campo Apodo no puede contener números.\n");
            isValid = false;
        }
        if (height.replaceAll("[^0-9]", "").length() > 3 || age.replaceAll("[^0-9]", "").length() > 3) {
            errorMessage.append("- El campo Altura y Edad no pueden contener más de 3 números.\n");
            isValid = false;
        }
        if (nickname.length() > 20) {
            errorMessage.append("- El campo Apodo no puede tener más de 25 caracteres .\n");
            isValid = false;
        }
        if (costaleroExists(nickname)) {
            errorMessage.append("- Ya existe un costalero con ese nombre.\n");
            isValid = false;
        }
        if (nickname.isBlank() || height.isBlank() || age.isBlank()) {
            errorMessage.append("- El campo Apodo, el campo Altura y el campo Edad no pueden contener solo espacios en blanco.\n");
            isValid = false;
        }
        if (!isValid) {
            Utils.ShowAlert(errorMessage.toString());
            return;
        }


        int heightInt = Integer.parseInt(height);
        int AgeInt = Integer.parseInt(age);

        Costalero costalero = new Costalero(0, nickname, heightInt, AgeInt, null);
            costaleroDAO.save(costalero);
            Nickname.setText("");
            Height.setText("");
            Age.setText("");


        }

    /**
     * Checks if a costalero with the given nickname already exists.
     *
     * @param nickname The nickname of the costalero
     * @return True if the costalero exists, otherwise false
     */
    private boolean costaleroExists(String nickname)  {
        Costalero existingCostalero = CostaleroDAO.build().findByNickname(nickname);
        return existingCostalero.getId() != 0;
    }

    /**
     * Switches to the Costalero menu screen.
     */
    @FXML
    private void switchToMenuCostalero() {
        try {
            Scenes.setRoot("pantallaMenuCostalero", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}
