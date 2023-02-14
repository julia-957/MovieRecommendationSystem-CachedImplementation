package dk.easv.presentation.controller;

import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController {
    @FXML
    public GridPane gridPane;
    @FXML private PasswordField passwordField;
    @FXML private TextField userId;
    private AppModel model;
    private AppController appController;

    public void logIn(ActionEvent actionEvent) throws IOException {
        model.loadUsers();
        model.loginUserFromUsername(userId.getText());
        if(model.getObsLoggedInUser()!=null){
            appController.openMenu();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
            alert.showAndWait();
        }
    }
    public void setAppController(AppController appController){ this.appController = appController; }

    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }

    public void setModel(AppModel model) {
        this.model = model;
    }
}
