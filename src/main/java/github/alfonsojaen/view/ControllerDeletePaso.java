package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ControllerDeletePaso {
    @FXML
    private TextField Brotherhood;

    @FXML
    public void handleDeletePaso(ActionEvent event) {
        PasoDAO pasoDAO = PasoDAO.build();
        String nameBrotherhood = Brotherhood.getText();

        if (nameBrotherhood.isEmpty()) {
            Alert("Error", "Por favor ingrese el nombre del paso.");
            return;
        }
        Paso pasoDelete = pasoDAO.findByName(nameBrotherhood);

        if (pasoDelete != null) {
            pasoDAO.delete(pasoDelete);
            Alert("Borrado exitoso", "El paso ha sido borrado correctamente.");
        } else {
            Alert("Error", "No se encontró ningún paso con el nombre proporcionado.");
        }

        Brotherhood.setText("");
    }
    @FXML
    private void MenuPaso() throws IOException {
        Scenes.setRoot("pantallaMenuPaso", null,null);
    }
    private void Alert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

