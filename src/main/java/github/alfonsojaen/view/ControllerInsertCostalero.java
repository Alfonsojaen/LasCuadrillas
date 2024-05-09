package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
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
            int height = Integer.parseInt(Height.getText());
            int age = Integer.parseInt(Age.getText());

        if (nickname.equals("") || height == 0 || age == 0 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Falta algún campo por introducir");
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
            Scenes.setRoot("pantallaMenuCostalero", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}
