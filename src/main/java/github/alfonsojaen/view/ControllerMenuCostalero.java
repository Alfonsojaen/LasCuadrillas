package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for managing the Costalero menu.
 */
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

    /**
     * Switches to the main menu screen.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void switchToMenu() throws IOException {
        Scenes.setRoot("pantallaMenu", null, null);
    }

    /**
     * Switches to the screen for inserting a new Costalero.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void switchToInsertCostalero() throws IOException {
        Scenes.setRoot("pantallaInsertCostalero", null,null);
    }

    /**
     * Switches to the screen for deleting a Costalero.
     *
     * @throws IOException If an error occurs while loading the screen
     */
    @FXML
    private void switchToDeleteCostalero() throws IOException {
        Scenes.setRoot("pantallaDeleteCostalero", null,null);
    }

    /**
     * Initializes the controller by retrieving Costalero data from the database,
     * setting up TableView with Costalero data, defining custom behavior for double-clicking rows,
     * setting cell value factories for displaying Costalero attributes,
     * and defining behavior for editing Costalero attributes in the TableView.
     *
     */
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
                        Scenes.setRoot("pantallaAssignCuadrilla", rowCostalero, null);
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
            if (event.getNewValue().length() <= 20) {
                Costalero costalero = event.getRowValue();
                costalero.setNickname(event.getNewValue());
                CostaleroDAO.build().update(costalero);
            } else {
                Utils.ShowAlert("Te has pasado del limtite de caracteres!");
            }
        });
        height.setCellFactory(TextFieldTableCell.forTableColumn());
        height.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 3) {
                Costalero costalero = event.getRowValue();
                costalero.setHeight(Integer.parseInt(event.getNewValue()));
                CostaleroDAO.build().update(costalero);
            } else {
                Utils.ShowAlert("Te has pasado del limtite de números!");
            }
        });
        age.setCellFactory(TextFieldTableCell.forTableColumn());
        age.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }
            if (event.getNewValue().length() <= 3) {
                Costalero costalero = event.getRowValue();
                costalero.setAge(Integer.parseInt(event.getNewValue()));
                CostaleroDAO.build().update(costalero);
            } else {
                Utils.ShowAlert("Te has pasado del limtite de números!");
            }
        });
    }


}

