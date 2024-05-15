package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerInsertCostalero {
    @FXML
    private TextField Nickname;

    @FXML
    private TextField Height;

    @FXML
    private TextField Age;

    @FXML
    public void handleInsertarCostalero(ActionEvent event) throws SQLException {
        CostaleroDAO costaleroDAO = CostaleroDAO.build();
        String nickname = Nickname.getText();
        int height = 0;
        int age = 0;

        try {
            height = Integer.parseInt(Height.getText());
            age = Integer.parseInt(Age.getText());
        } catch (NumberFormatException e) {
            // Handle the case where height or age input is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("La altura y la edad deben ser números enteros");
            alert.showAndWait();
            return;
        }

        if (nickname.isEmpty() || height == 0 || age == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Falta algún campo por introducir");
            alert.showAndWait();
        } else if (costaleroDAO.findByName(nickname) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("El nombre del costalero ya existe");
            alert.showAndWait();
        } else {
            Costalero costalero = new Costalero(0, nickname, height, age, null);
            costaleroDAO.save(costalero);
            Nickname.setText("");
            Height.setText("");
            Age.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Costalero creado");
            alert.setHeaderText("Costalero creado exitosamente");
            alert.setContentText("El costalero ha sido creado correctamente.");
            alert.showAndWait();
        }
    }

    @FXML
    private void switchToMenuCostalero() {
        try {
            Scenes.setRoot("pantallaMenuCostalero", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}
