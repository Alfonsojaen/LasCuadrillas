package github.alfonsojaen;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerCostalero {
    @FXML
    private TextField Nickname;

    @FXML
    private TextField Height;

    @FXML
    private TextField Age;

    @FXML
    private Button botonInsertar;
    @FXML
    private TableView<Costalero> tableView;
    @FXML
    private TableColumn<Costalero, String> columnNickname;
    @FXML
    private TableColumn<Costalero, String> columnHeight;
    @FXML
    private TableColumn<Costalero, String> columnAge;

    private ObservableList<Costalero> costaleros;
    @FXML
    public void handleInsertarCostalero(ActionEvent event) {
            CostaleroDAO costaleroDAO = CostaleroDAO.build();
            String nickname = Nickname.getText();
            int height = Integer.parseInt(Height.getText());
            int age = Integer.parseInt(Age.getText());

            Costalero costalero = new Costalero(0, nickname, height, age, null);

            // Guardar los datos utilizando CostaleroDAO
            try {
                costaleroDAO.save(costalero);
                Nickname.setText("");
                Height.setText("");
                Age.setText("");
            } catch (SQLException e) {
            }
        }

        @FXML
        public void handleEditarCostalero(URL location, ResourceBundle resources) {
            tableView.setEditable(true);
            columnNickname.setCellValueFactory(costalero -> new SimpleStringProperty(costalero.getValue().getNickname()));
            columnHeight.setCellValueFactory(costalero -> new SimpleStringProperty(String.valueOf(costalero.getValue().getHeight())));
            columnAge.setCellValueFactory(costalero -> new SimpleStringProperty(String.valueOf(costalero.getValue().getAge())));
            columnNickname.setOnEditCommit(event -> {
                if(event.getNewValue()== event.getOldValue()){
                    return;
                }

                if(event.getNewValue().length()<=60){
                    Costalero author = event.getRowValue();
                    author.setNickname(event.getNewValue());
                    try {
                        CostaleroDAO.build().save(author);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Te has pasao!!!!!");
                    alert.show();
                }
                // Lógica de edición de celdas, si es necesaria
            });
        }
    }