package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerDeleteCuadrilla {
    @FXML
    private TextField Name;

    @FXML
    public void handleDeleteCuadrilla(ActionEvent event) {
        CuadrillaDAO cuadrillaDAO = CuadrillaDAO.build();
        String nameCuadrilla = Name.getText();
        Cuadrilla cuadrillaDelete = cuadrillaDAO.findByName(nameCuadrilla);

        if (cuadrillaDelete != null) {
            cuadrillaDAO.delete(cuadrillaDelete);
            Alert("Borrado exitoso", "La cuadrilla ha sido borrado correctamente.");
        } else {
            Alert("Error", "No se encontró ninguna cuadrilla con el nombre proporcionado.");
        }

        Name.setText("");
    }
    @FXML
    private void MenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla", null);
    }
    private void Alert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
