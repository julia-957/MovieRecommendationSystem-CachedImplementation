package dk.easv.presentation.controller.menuControllers;

import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LogInController {
    @FXML private GridPane gridPane;
    @FXML private PasswordField passwordField;
    @FXML private TextField userId;
    private AppModel model = AppModel.getInstance();
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

    public void setAppController(AppController appController) { this.appController = appController; }

    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }
}
