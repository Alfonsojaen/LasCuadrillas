package github.alfonsojaen.view;


import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
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
 * Controller for assigning pasos to a cuadrilla in a JavaFX application.
 */
public class ControllerAssignPaso  {
    private Cuadrilla cuadrilla;
    private List<Paso> selected = new ArrayList<>();
    private ObservableList<Paso> pasos;
    private List<Paso> Selected = new ArrayList<>(selected);

    @FXML
    private TableView<Paso> tableview;

    @FXML
    private TableColumn<Paso, String> idColumn;

    @FXML
    private TableColumn<Paso, String> brotherhoodColumn;

    @FXML
    private TableColumn<Paso, String> capacityColumn;

    @FXML
    private TableColumn<Paso, Boolean> selectionColumn;

    /**
     * Method to switch to the cuadrilla menu screen.
     *
     * @throws IOException If an error occurs while loading the screen.
     */
    @FXML
    private void switchToMenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla",null,null);
    }

    /**
     * Initializes the table and configures the columns.
     */
    public void start () {
        idColumn.setCellValueFactory(paso -> new SimpleStringProperty(String.valueOf(paso.getValue().getId())));

        brotherhoodColumn.setCellValueFactory(paso -> new SimpleStringProperty(String.valueOf(paso.getValue().getBrotherhood())));

        capacityColumn.setCellValueFactory(paso -> new SimpleStringProperty(String.valueOf(paso.getValue().getCapacity())));

        selectionColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer i) ->{
                    SimpleBooleanProperty selectedProperty= new SimpleBooleanProperty(pasos.get(i).isSelected());
                    selectedProperty.addListener((obs, oldValue, newValue) -> {
                        Paso paso = pasos.get(i);
                        paso.setSelected(newValue); // Actualiza el valor de setSelected de la cuadrículaa
                        if(newValue){
                            this.cuadrilla.addPaso(paso);
                        }else{
                            this.cuadrilla.removePaso(paso);
                        }
                    });
                    return selectedProperty;
                } ));


        selectionColumn.setEditable(true);
        this.pasos = FXCollections.observableArrayList(PasoDAO.build().findAll());

        PasoDAO pdao = new PasoDAO();
        this.selected=pdao.findByCuadrilla(cuadrilla);
        for(Paso paso : this.pasos){
            if(this.selected.contains(paso)){
                paso.setSelected(true);
            }else{
                paso.setSelected(false);
            }
        }

        tableview.setItems(this.pasos);
    }

    /**
     * Sets the cuadrilla and calls the start() method.
     *
     * @param cuadrilla The cuadrilla to assign.
     */
    public void setCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
        start();
    }

    /**
     * Assigns the selected pasos to the cuadrilla and updates the database.
     */
    public void assing() {
        try {
            List<Paso> pasosSelected = new ArrayList<>();
            for (Paso paso : pasos) {
                if (paso.isSelected()) {
                    pasosSelected.add(paso);
                }
            }

            for (Paso paso : Selected) {
                if (!pasosSelected.contains(paso)) {
                    cuadrilla.removePaso(paso);
                }
            }

            for (Paso paso : pasosSelected) {
                if (!Selected.contains(paso)) {
                    cuadrilla.addPaso(paso);
                }
            }
            CuadrillaDAO.build().setPaso(this.cuadrilla);
            Utils.ShowAlert("Se ha asignado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
