package dk.easv.presentation.controller.menuControllers;

import dk.easv.entities.User;
import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.model.AppModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML private GridPane gridPane;
    @FXML private PasswordField passwordField;
    @FXML private TextField userId;
    private final AppModel model = AppModel.getInstance();
    private AppController appController;

    public void logIn(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();

        Thread thread = new Thread(() -> {
            // show progress bar and status label
            Platform.runLater(() -> {
                VBox root = new VBox();
                root.getChildren().add(new ImageView("https://i.gifer.com/YCZH.gif"));
                Scene scene = new Scene(root, 414, 233);
                stage.setScene(scene);
                stage.show();
            });

            // perform long running operation
            model.loadUsers();
            User user = loginUserFromUsername(userId.getText());
            System.out.println(userId.getText());

            var a = model.getLogic().getTopAverageRatedMovies(user);
            var b = model.getLogic().getTopAverageRatedMoviesUserDidNotSee(user);
            var c = model.getLogic().getTopSimilarUsers(user);
            var d = model.getLogic().getTopMoviesFromSimilarPeople(user);

            // update UI with result
            Platform.runLater(() -> {
                if (user != null) {
                    model.loadData(a,b,c,d);
                    appController.openMenu();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
                    alert.showAndWait();
                }

                // hide progress bar and status label
                stage.close();
            });
        });
        thread.start();
    }

    public void setAppController(AppController appController) { this.appController = appController; }

    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }

    private User loginUserFromUsername(String username) {
        return model.loginUserFromUsername(username);
    }
}

