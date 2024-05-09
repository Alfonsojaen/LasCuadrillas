package github.alfonsojaen.view;

import github.alfonsojaen.App;
import github.alfonsojaen.model.dao.UserDAO;
import github.alfonsojaen.model.singleton.UserSession;
import github.alfonsojaen.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ControllerUserLogin  {

    @FXML
    private TextField tGmail;
    @FXML
    private PasswordField tPass;
    @FXML
    private Hyperlink hyperlink;

    @FXML
    private void login() throws SQLException, IOException {

        String gmail = tGmail.getText().trim();
        String password = tPass.getText().trim();
        password = Utils.encryptSHA256(password);


        if(gmail.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Falta algun campo por introducir");
            alert.showAndWait();
        }else {
            UserDAO mDAO = new UserDAO();
            String nameUser;
            if((nameUser=mDAO.checkLogin(gmail, password))!=null) {
                UserSession.login(gmail, password);
                //Logged
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Login");
                alerta.setHeaderText("Login exitoso");
                alerta.setContentText("Se ha logeago el Matchmaker correctamente.");
                alerta.showAndWait();
                switchToUserPage();
            }else {
                UserSession.logout();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Login");
                alerta.setHeaderText("No se ha podido logear");
                alerta.setContentText("Intentelo de nuevo.");
                alerta.showAndWait();
            }

        }

    }

    @FXML
    private void switchToUserPage() throws IOException {
       Scenes.setRoot("pantallaMenu",null);
    }
    @FXML
    private void switchToRegister() throws IOException {
        Scenes.setRoot("pantallaRegisterUser", null);
    }
}


