package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ControllerDeleteCostalero {
    @FXML
    private TextField Nickname;

    @FXML
    public void handleDeleteCostalero(ActionEvent event) {
        CostaleroDAO costaleroDAO = CostaleroDAO.build();
        String nickNameCostalero = Nickname.getText();
        Costalero costaleroDelete = costaleroDAO.findByName(nickNameCostalero);

        if (costaleroDelete != null) {
            costaleroDAO.delete(costaleroDelete);
            Alert("Borrado exitoso", "El costalero ha sido borrado correctamente.");
        } else {
            Alert("Error", "No se encontró ningun costalero con el nombre proporcionado.");
        }

        Nickname.setText("");
    }

    @FXML
    private void MenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero", null);
    }
    private void Alert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

