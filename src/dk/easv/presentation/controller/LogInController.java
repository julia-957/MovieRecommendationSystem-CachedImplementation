package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML private PasswordField passwordField;
    @FXML private TextField userId;
    private AppModel model;
    private Main mainApp;
    private AppController appController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new AppModel();
    }

    public void logIn(ActionEvent actionEvent) {
        model.loadUsers();
        model.loginUserFromUsername(userId.getText());
        if(model.getObsLoggedInUser()!=null){
            //Close current window
            mainApp.initRootLayout();

            Button b = (Button) actionEvent.getSource();
            Stage thisStage = (Stage) b.getScene().getWindow();
            thisStage.close();
        /*
            try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/MenuRootView.fxml"));
            Parent root = loader.load();

            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth()-400;
            int height = gd.getDisplayMode().getHeight()-350;

            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Budgetflix 2.1");
            stage.centerOnScreen();
            stage.show();

            AppController appController = loader.getController();
            mainApp.setAppController(appController);
            appController.setMainApp(mainApp);
            appController.setModel(model);

            //Close current window
            Button b = (Button) actionEvent.getSource();
            Stage thisStage = (Stage) b.getScene().getWindow();
            thisStage.hide();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load App.fxml");
            alert.showAndWait();
        }

         */

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
            alert.showAndWait();
        }
    }

    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setAppController(AppController controller) {
        this.appController = controller;
    }
}
