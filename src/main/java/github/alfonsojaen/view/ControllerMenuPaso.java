package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    @FXML
    private void switchToMenu() throws IOException {
        Scenes.setRoot("pantallaMenu",null, null);
    }
    @FXML
    private void switchToInsertPaso() throws IOException {
        Scenes.setRoot("pantallaInsertPaso",null, null);
    }
    @FXML
    private void switchToDeletePaso() throws IOException {
        Scenes.setRoot("pantallaDeletePaso",null, null);
    }
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
            if (event.getNewValue().length() <= 30) {
                Paso paso = event.getRowValue();
                paso.setBrotherhood(event.getNewValue());
                PasoDAO.build().update(paso);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
        capacity.setCellFactory(TextFieldTableCell.forTableColumn());
        capacity.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 20) {
                Paso paso = event.getRowValue();
                paso.setCapacity(Integer.parseInt(event.getNewValue()));
                PasoDAO.build().update(paso);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
    }


}

