package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
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

public class ControllerMenuCostalero  implements Initializable {
    @FXML
    private TableView<Costalero> tableview;

    @FXML
    private TableColumn<Costalero, String> id;
    @FXML
    private TableColumn<Costalero, String> nickname;

    @FXML
    private TableColumn<Costalero, String> height;

    @FXML
    private TableColumn<Costalero, String> age;

    private ObservableList<Costalero> costaleros;

    @FXML
    private void switchToMenu() throws IOException {
        Scenes.setRoot("pantallaMenu", null);
    }
    @FXML
    private void switchToInsertCostalero() throws IOException {
        Scenes.setRoot("pantallaInsertCostalero", null);
    }
    @FXML
    private void switchToDeleteCostalero() throws IOException {
        Scenes.setRoot("pantallaDeleteCostalero", null);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Costalero> costaleros = CostaleroDAO.build().findAll();
        this.costaleros = FXCollections.observableArrayList(costaleros);
        tableview.setItems(this.costaleros);
        tableview.setEditable(true);

        tableview.setRowFactory(tv -> {
            TableRow<Costalero> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 3 && (! row.isEmpty()) ) {
                    Costalero rowCostalero = row.getItem();
                    try {
                        Scenes.setRoot("pantallaAssignCuadrilla", rowCostalero);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            return row ;
        });

        id.setCellValueFactory(costalero -> new SimpleStringProperty(String.valueOf(costalero.getValue().getId())));
        nickname.setCellValueFactory(costalero -> new SimpleStringProperty(costalero.getValue().getNickname()));
        height.setCellValueFactory(costalero -> new SimpleStringProperty(String.valueOf(costalero.getValue().getHeight())));
        age.setCellValueFactory(costalero -> new SimpleStringProperty(String.valueOf(costalero.getValue().getAge())));


        nickname.setCellFactory(TextFieldTableCell.forTableColumn());
        nickname.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 30) {
                Costalero costalero = event.getRowValue();
                costalero.setNickname(event.getNewValue());
                CostaleroDAO.build().update(costalero);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
        height.setCellFactory(TextFieldTableCell.forTableColumn());
        height.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 20) {
                Costalero costalero = event.getRowValue();
                costalero.setHeight(Integer.parseInt(event.getNewValue()));
                CostaleroDAO.build().update(costalero);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
        age.setCellFactory(TextFieldTableCell.forTableColumn());
        age.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 60) {
                Costalero costalero = event.getRowValue();
                costalero.setAge(Integer.parseInt(event.getNewValue()));
                CostaleroDAO.build().update(costalero);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasado del limtite de caracteres!");
                alert.show();
            }
        });
    }


}

