package github.alfonsojaen;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ControllerInsertCuadrilla {
    @FXML
    private TextField Name;

    @FXML
    private TextField Overseer;

    @FXML
    private TextField Description;

    @FXML
    private Button botonInsertar;

    @FXML
    public void handleInsertarCuadrilla(ActionEvent event) {
         CuadrillaDAO cuadrillaDAO = CuadrillaDAO.build();
        // Obtener los valores de los campos de texto
        String name = Name.getText();
        String overseer = Overseer.getText();
        String description = Description.getText();

        // Crear una instancia de Cuadrilla con los valores ingresados
        Cuadrilla cuadrilla = new Cuadrilla(0, name, overseer, description, null, null);

        // Guardar los datos utilizando CuadrillaDAO
        try {
            cuadrillaDAO.save(cuadrilla);
            Name.setText("");
            Overseer.setText("");
            Description.setText("");
        } catch (SQLException e) {
            // Manejar la excepción de SQL
        }
    }

    }


