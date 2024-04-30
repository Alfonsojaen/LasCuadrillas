package github.alfonsojaen.view;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class ControllerInsertCostalero {
    @FXML
    private TextField Nickname;

    @FXML
    private TextField Height;

    @FXML
    private TextField Age;

    @FXML
    private Button botonInsertar;

    @FXML
    public void handleInsertarCostalero(ActionEvent event) {
            CostaleroDAO costaleroDAO = CostaleroDAO.build();
            String nickname = Nickname.getText();
            int height = Integer.parseInt(Height.getText());
            int age = Integer.parseInt(Age.getText());

            Costalero costalero = new Costalero(0, nickname, height, age, null);

            try {
                costaleroDAO.save(costalero);
                Nickname.setText("");
                Height.setText("");
                Age.setText("");
            } catch (SQLException e) {
            }
        }
        }
