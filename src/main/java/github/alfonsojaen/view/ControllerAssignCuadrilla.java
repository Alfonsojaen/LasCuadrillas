package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAssignCuadrilla implements Initializable {

    private Costalero costalero;
    private List<Cuadrilla> seleccionados = new ArrayList<>();
    private ObservableList<Cuadrilla> cuadrillas;

    @FXML
    private TableView<Cuadrilla> tableview;

    @FXML
    TableColumn<Cuadrilla, String> idColumn;

    @FXML
    TableColumn<Cuadrilla, String> nameColumn;

    @FXML
    TableColumn<Cuadrilla, String> overseerColumn;

    @FXML
    TableColumn<Cuadrilla, String> descriptionColumn;

    @FXML
    TableColumn<Cuadrilla, Boolean> selectionColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableView<Cuadrilla> tableView2 = tableview;
        idColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getId())));

        nameColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getName())));

        overseerColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getOverseer())));

        descriptionColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getDescription())));

        selectionColumn.setCellValueFactory(cellData -> {
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(cellData.getValue().isSelected());
            selectedProperty.addListener((obs, oldValue, newValue) -> {
                System.out.println("Selected state changed to: " + newValue);
            });
            return selectedProperty;
        });

        selectionColumn.setCellFactory(column -> {
            CheckBoxTableCell<Cuadrilla, Boolean> cell = new CheckBoxTableCell<>();
            /*
            final BooleanProperty selected = new SimpleBooleanProperty();
            cell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {

                @Override
                public ObservableValue<Boolean> call(Integer index) {
                    return selected ;
                }
            });
            selected.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                    int rowIndex = cell.getIndex();
                    System.out.println("Índice de la fila: " + rowIndex);
                    System.out.println(isSelected);
                    //actuas sobre tu datos
                }
            });
            */

            return cell;
        });

        selectionColumn.setOnEditCommit((TableColumn.CellEditEvent<Cuadrilla, Boolean> event) -> {
            Cuadrilla item = event.getRowValue();

            Boolean nuevoValor = event.getNewValue();

            System.out.println("Nuevo valor: " + nuevoValor);


        });
        selectionColumn.setEditable(true);
        this.cuadrillas = FXCollections.observableArrayList(CuadrillaDAO.build().findAll());
        tableview.setItems(this.cuadrillas);

    }

    public void setCostalero(Costalero costalero) {
        for (Cuadrilla cuadrilla : cuadrillas) {
            cuadrilla.setSelected(true);
        }
        System.out.println("Cuadrillas"+cuadrillas);
        System.out.println("Costalero: "+costalero);
        this.costalero = costalero;


    }
}

