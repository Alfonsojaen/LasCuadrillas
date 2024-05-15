package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMenuCuadrilla implements Initializable {
    @FXML
    private TableView<Cuadrilla> tableview;

    @FXML
    private TableColumn<Cuadrilla, String> id;
    @FXML
    private TableColumn<Cuadrilla, String> name;

    @FXML
    private TableColumn<Cuadrilla, String> overseer;

    @FXML
    private TableColumn<Cuadrilla, String> description;

    private ObservableList<Cuadrilla> cuadrillas;

    @FXML
    private void switchToMenu() throws IOException {
        Scenes.setRoot("pantallaMenu",null,null);
    }
    @FXML
    private void switchToInsertCuadrilla() throws IOException {
        Scenes.setRoot("pantallaInsertCuadrilla",null,null);
    }
    @FXML
    private void switchToDeleteCuadrilla() throws IOException {
        Scenes.setRoot("pantallaDeleteCuadrilla",null,null);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Cuadrilla> cuadrillas = CuadrillaDAO.build().findAll();
        this.cuadrillas = FXCollections.observableArrayList(cuadrillas);
        tableview.setItems(this.cuadrillas);
        tableview.setEditable(true);

        tableview.setRowFactory(tv -> {
            TableRow<Cuadrilla> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 3 && (! row.isEmpty()) ) {
                    Cuadrilla rowCuadrilla = row.getItem();
                    try {
                        Scenes.setRoot("pantallaAssignPaso", null, rowCuadrilla);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            return row ;
        });

        id.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getId())));
        name.setCellValueFactory(cuadrilla -> new SimpleStringProperty(cuadrilla.getValue().getName()));
        overseer.setCellValueFactory(cuadrilla -> new SimpleStringProperty(cuadrilla.getValue().getOverseer()));
        description.setCellValueFactory(cuadrilla -> new SimpleStringProperty(cuadrilla.getValue().getDescription()));


        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 30) {
                Cuadrilla cuadrilla = event.getRowValue();
                cuadrilla.setName(event.getNewValue());
                CuadrillaDAO.build().update(cuadrilla);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
        overseer.setCellFactory(TextFieldTableCell.forTableColumn());
        overseer.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 20) {
                Cuadrilla cuadrilla = event.getRowValue();
                cuadrilla.setOverseer(event.getNewValue());
                CuadrillaDAO.build().update(cuadrilla);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 60) {
                Cuadrilla cuadrilla = event.getRowValue();
                cuadrilla.setDescription(event.getNewValue());
                CuadrillaDAO.build().update(cuadrilla);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
    }


}

