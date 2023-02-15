package dk.easv.presentation.controller.menuControllers;

import dk.easv.Main;
import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button homeButton, searchButton, favouritesButton, accountButton;
    private AppController appController;
    private AppModel model;

    public void setAppController(AppController appController){ this.appController = appController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonIcons();
    }

    private void setButtonIcons(){
        ImageView homeIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/house-bold.png"))));
        homeIconView.setId("homeIconView");
        homeIconView.setFitHeight(50);
        homeIconView.setFitWidth(50);
        homeButton.setText("");
        homeButton.setGraphic(homeIconView);

        ImageView searchIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/magnifying-glass-bold.png"))));
        searchIconView.setId("searchIconView");
        searchIconView.setFitHeight(50);
        searchIconView.setFitWidth(50);
        searchButton.setText("");
        searchButton.setGraphic(searchIconView);

        ImageView favouritesIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/star-bold.png"))));
        favouritesIconView.setId("favouritesIconView");
        favouritesIconView.setFitHeight(50);
        favouritesIconView.setFitWidth(50);
        favouritesButton.setText("");
        favouritesButton.setGraphic(favouritesIconView);

        ImageView accountIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/user-circle-bold.png"))));
        accountIconView.setId("accountIconView");
        accountIconView.setFitHeight(50);
        accountIconView.setFitWidth(50);
        accountButton.setText("");
        accountButton.setGraphic(accountIconView);
    }

    @FXML
    private void handleHomeButton(ActionEvent actionEvent) throws IOException {
        appController.openIntroScreen();
    }

    @FXML
    private void handleSearchButton(ActionEvent actionEvent) throws IOException {
        appController.openSearchScreen();
    }

    @FXML
    private void handleFavouritesButton(ActionEvent actionEvent) throws IOException {
        appController.openFavouritesScreen();
    }

    @FXML
    private void handleAccountButton(ActionEvent actionEvent) throws IOException {
        appController.openLogInScreen();
    }

    public void setFocusOnFavourites() {
        favouritesButton.requestFocus();
    }

    public void setModel(AppModel model) {
        this.model = model;
    }
}
