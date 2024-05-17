package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Controller class for inserting a Cuadrilla.
 */
public class ControllerInsertCuadrilla {
    @FXML
    private TextField Name;

    @FXML
    private TextField Overseer;

    @FXML
    private TextField Description;

    @FXML
    private ImageView boton;

    /**
     * Handles the insertion of a Cuadrilla when the insert button is clicked.
     *
     * @param event Action event triggered by the insert button
     */
    @FXML
    public void handleInsertarCuadrilla(ActionEvent event) {
         CuadrillaDAO cuadrillaDAO = CuadrillaDAO.build();
        String name = Name.getText();
        String overseer = Overseer.getText();
        String description = Description.getText();

        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Se encontraron los siguientes errores:\n");

        if (name.isEmpty() ) {
            errorMessage.append("- El campo Nombre es obligatario\n");
            isValid = false;
        }
        if (name.length() > 30 || overseer.length() > 20) {
            errorMessage.append("- El campo Nombre no puede tener más de 30 caracteres y el campo Capataz no puede tener más de 20 caracteres.\n");
            isValid = false;
        }

        if (!name.matches("[a-zA-Z\\s]+") || !overseer.matches("[a-zA-Z\\s]+") || !description.matches("[a-zA-Z\\s]+")) {
            errorMessage.append("- Los campos Nombre, Capataz y Descripción no pueden contener números.\n");
            isValid = false;
        }
        if (name.isBlank() || overseer.isBlank() || description.isBlank()) {
            errorMessage.append("- Los campos no pueden contener solo espacios en blanco.\n");
            isValid = false;
        }

        if (cuadrillaExists(name)) {
            errorMessage.append("- Ya existe una cuadrilla con ese nombre.\n");
            isValid = false;
        }

        if (!isValid) {
            Utils.ShowAlert(errorMessage.toString());
            return;
        }


        Cuadrilla cuadrilla = new Cuadrilla(0, name, overseer, description, null, null);
        cuadrillaDAO.save(cuadrilla);
        Name.setText("");
        Overseer.setText("");
        Description.setText("");

    }

    /**
     * Checks if a cuadrilla with the given name already exists.
     *
     * @param name The name of the cuadrilla
     * @return True if the cuadrilla exists, otherwise false
     */
    private boolean cuadrillaExists(String name) {
        Cuadrilla existingCuadrilla = CuadrillaDAO.build().findByName(name);
        return existingCuadrilla.getId() != 0;
    }

    /**
     * Switches to the Cuadrilla menu screen.
     */
    @FXML
    private void switchToMenuCuadrilla() {
        try {
            Scenes.setRoot("pantallaMenuCuadrilla", null, null);
    } catch (IOException e) {
        e.printStackTrace();
    }
        }

    }


