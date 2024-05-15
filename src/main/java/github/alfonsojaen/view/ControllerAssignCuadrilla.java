package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

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

public class ControllerAssignCuadrilla {

    private Costalero costalero;
    private List<Cuadrilla> previas = new ArrayList<>();
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
    @FXML
    private void switchToMenuCostalero() throws IOException {
        Scenes.setRoot("pantallaMenuCostalero",null,null);
    }

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
        this.previas=cdao.findByCostalero(costalero);
        for(Cuadrilla cuadrilla : this.cuadrillas){
            if(this.previas.contains(cuadrilla)){
                cuadrilla.setSelected(true);
            }else{
                cuadrilla.setSelected(false);
            }
        }
        tableview.setItems(this.cuadrillas);
    }

    public void setCostalero(Costalero costalero) {
        this.costalero = costalero;
        start();


    }
    public void assign() {
        try {
            CostaleroDAO.build().setCuadrilla(this.costalero);
       } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

