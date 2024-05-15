package github.alfonsojaen.view;


import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAssignPaso  {
    private Cuadrilla cuadrilla;
    private List<Paso> previas = new ArrayList<>();
    private ObservableList<Paso> pasos;

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
    @FXML
    private void switchToMenuCuadrilla() throws IOException {
        Scenes.setRoot("pantallaMenuCuadrilla",null,null);
    }
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
        this.previas=pdao.findByCuadrilla(cuadrilla);
        for(Paso paso : this.pasos){
            if(this.previas.contains(paso)){
                paso.setSelected(true);
            }else{
                paso.setSelected(false);
            }
        }

        tableview.setItems(this.pasos);
    }


    public void setCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
        start();
    }
    public void assing() {
        try {
            CuadrillaDAO.build().setPaso(this.cuadrilla);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
