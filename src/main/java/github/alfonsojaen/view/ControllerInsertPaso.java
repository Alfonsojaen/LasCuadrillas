package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;


public class ControllerInsertPaso {
    @FXML
    private TextField Brotherhood;

    @FXML
    private TextField Capacity;

    @FXML
    private ImageView boton;

    @FXML
    private Button botonInsertar;

    @FXML
    public void handleInsertarPaso(ActionEvent event) throws SQLException {
        PasoDAO pasoDAO = PasoDAO.build();
        String brotherhood = Brotherhood.getText();
        int capacity = Integer.parseInt(Capacity.getText());

        Paso paso = new Paso(0, brotherhood, capacity, null);

        pasoDAO.save(paso);
        Brotherhood.setText("");
        Capacity.setText("");
    }
    @FXML
    private void switchToMenuPaso() {
        try {
            Scenes.setRoot("pantallaMenuPaso", null,null);
    } catch (IOException e) {
        e.printStackTrace();
    }
        }

    }


