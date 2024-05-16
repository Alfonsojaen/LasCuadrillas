package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Paso;
import github.alfonsojaen.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMenuPaso implements Initializable {
    @FXML
    private TableView<Paso> tableview;

    @FXML
    private TableColumn<Paso, String> id;
    @FXML
    private TableColumn<Paso, String> brotherhood;

    @FXML
    private TableColumn<Paso, String> capacity;

    private ObservableList<Paso> pasos;

    /**
     * Handles switching back to the main menu screen.
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void switchToMenu() throws IOException {
        Scenes.setRoot("pantallaMenu",null, null);
    }

    /**
     * Handles switching to the screen for inserting a new Paso.
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void switchToInsertPaso() throws IOException {
        Scenes.setRoot("pantallaInsertPaso",null, null);
    }

    /**
     * Handles switching to the screen for deleting a Paso.
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void switchToDeletePaso() throws IOException {
        Scenes.setRoot("pantallaDeletePaso",null, null);
    }

    /**
     * Initializes the controller by fetching Paso data from the database,
     * configuring the TableView with Paso data, and defining behavior for editing
     * Paso attributes in the TableView.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Paso> pasos = PasoDAO.build().findAll();
        this.pasos = FXCollections.observableArrayList(pasos);
        tableview.setItems(this.pasos);
        tableview.setEditable(true);

        id.setCellValueFactory(paso -> new SimpleStringProperty(String.valueOf(paso.getValue().getId())));
        brotherhood.setCellValueFactory(paso -> new SimpleStringProperty(paso.getValue().getBrotherhood()));
        capacity.setCellValueFactory(paso -> new SimpleStringProperty(String.valueOf(paso.getValue().getCapacity())));


        brotherhood.setCellFactory(TextFieldTableCell.forTableColumn());
        brotherhood.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 25) {
                Paso paso = event.getRowValue();
                paso.setBrotherhood(event.getNewValue());
                PasoDAO.build().update(paso);
            } else {
                Utils.ShowAlert("Te has pasado del limtite de caracteres!");
            }
        });
        capacity.setCellFactory(TextFieldTableCell.forTableColumn());
        capacity.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 3) {
                Paso paso = event.getRowValue();
                paso.setCapacity(Integer.parseInt(event.getNewValue()));
                PasoDAO.build().update(paso);
            } else {
                Utils.ShowAlert("Te has pasado del limtite de números!");
            }
        });
    }
}

