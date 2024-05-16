package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

import github.alfonsojaen.utils.Utils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for assigning cuadrillas to a costalero in a JavaFX application.
 */
public class ControllerAssignCuadrilla {

    private Costalero costalero;
    private List<Cuadrilla> selected = new ArrayList<>();
    private ObservableList<Cuadrilla> cuadrillas;
    private List<Cuadrilla> Selected = new ArrayList<>(selected);


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

    /**
     * Method to switch to the costalero menu screen.
     *
     * @throws IOException If an error occurs while loading the screen.
     */
    @FXML
    private void switchToMenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero",null,null);
    }

    /**
     * Initializes the table and configures the columns.
     */
    public void start() {
        idColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getId())));

        nameColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getName())));

        overseerColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getOverseer())));

        descriptionColumn.setCellValueFactory(cuadrilla -> new SimpleStringProperty(String.valueOf(cuadrilla.getValue().getDescription())));
        selectionColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer i) ->{
                    SimpleBooleanProperty selectedProperty= new SimpleBooleanProperty(cuadrillas.get(i).isSelected());
                    selectedProperty.addListener((obs, oldValue, newValue) -> {
                        Cuadrilla cuadrilla = cuadrillas.get(i);
                        cuadrilla.setSelected(newValue); // Actualiza el valor de setSelected de la cuadrículaa
                        if(newValue){
                            this.costalero.addCuadrilla(cuadrilla);
                        }else{
                            this.costalero.removeCuadrilla(cuadrilla);
                        }
                    });
                    return selectedProperty;
                } ));


        selectionColumn.setEditable(true);
        this.cuadrillas = FXCollections.observableArrayList(CuadrillaDAO.build().findAll());
        CuadrillaDAO cdao = new CuadrillaDAO();
        this.selected=cdao.findByCostalero(costalero);
        for(Cuadrilla cuadrilla : this.cuadrillas){
            if(this.selected.contains(cuadrilla)){
                cuadrilla.setSelected(true);
            }else{
                cuadrilla.setSelected(false);
            }
        }
        tableview.setItems(this.cuadrillas);
    }

    /**
     * Sets the costalero and calls the start() method.
     *
     * @param costalero The costalero to assign.
     */
    public void setCostalero(Costalero costalero) {
        this.costalero = costalero;
        start();
    }

    /**
     * Assigns the selected cuadrillas to the costalero and updates the database.
     */
    public void assign() {
        try {
            List<Cuadrilla> cuadrillasSelected = new ArrayList<>();
            for (Cuadrilla cuadrilla : cuadrillas) {
                if (cuadrilla.isSelected()) {
                    cuadrillasSelected.add(cuadrilla);
                }
            }

            for (Cuadrilla cuadrilla : Selected) {
                if (!cuadrillasSelected.contains(cuadrilla)) {
                    costalero.removeCuadrilla(cuadrilla);
                }
            }

            for (Cuadrilla cuadrilla : cuadrillasSelected) {
                if (!Selected.contains(cuadrilla)) {
                    costalero.addCuadrilla(cuadrilla);
                }
            }
            CostaleroDAO.build().setCuadrilla(this.costalero);
            Utils.ShowAlert("Se ha asignado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

