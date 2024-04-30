package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class ControllerInsertCuadrilla {
    @FXML
    private TextField Name;

    @FXML
    private TextField Overseer;

    @FXML
    private TextField Description;

    @FXML
    private ImageView boton;

    @FXML
    private Button botonInsertar;

    @FXML
    public void handleInsertarCuadrilla(ActionEvent event) {
         CuadrillaDAO cuadrillaDAO = CuadrillaDAO.build();
        String name = Name.getText();
        String overseer = Overseer.getText();
        String description = Description.getText();

        Cuadrilla cuadrilla = new Cuadrilla(0, name, overseer, description, null, null);

        cuadrillaDAO.save(cuadrilla);
        Name.setText("");
        Overseer.setText("");
        Description.setText("");
    }
    @FXML
    private void overButton(){
        boton.setOpacity(0.5);
    }
    @FXML
    private void offButton(){
        boton.setOpacity(1.0);
    }
    @FXML
    private void switchToMenuCuadrilla() {
        try {
            App.setRoot("pantallaMenuCuadrilla");
    } catch (IOException e) {
        e.printStackTrace();
    }
        }

    }


