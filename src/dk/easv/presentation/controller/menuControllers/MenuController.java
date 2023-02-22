package dk.easv.presentation.controller.menuControllers;

import dk.easv.Main;
import dk.easv.presentation.controller.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button homeButton, searchButton, favouritesButton, accountButton;
    private AppController appController;

    public void setAppController(AppController appController){ this.appController = appController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonIcons();
    }

    private void setButtonIcons(){
        ImageView homeIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/house-bold.png"))));
        homeIconView.setId("homeIconView");
        homeIconView.setFitHeight(35);
        homeIconView.setFitWidth(35);
        homeButton.setText("");
        homeButton.setGraphic(homeIconView);

        ImageView searchIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/magnifying-glass-bold.png"))));
        searchIconView.setId("searchIconView");
        searchIconView.setFitHeight(35);
        searchIconView.setFitWidth(35);
        searchButton.setText("");
        searchButton.setGraphic(searchIconView);

        ImageView favouritesIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/star-bold.png"))));
        favouritesIconView.setId("favouritesIconView");
        favouritesIconView.setFitHeight(35);
        favouritesIconView.setFitWidth(35);
        favouritesButton.setText("");
        favouritesButton.setGraphic(favouritesIconView);

        ImageView accountIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/user-circle-bold.png"))));
        accountIconView.setId("accountIconView");
        accountIconView.setFitHeight(35);
        accountIconView.setFitWidth(35);
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

    public void setFocusOnHome() {
        homeButton.setId("homeButtonFocused");
        searchButton.setId("searchButton");
        favouritesButton.setId("favouritesButton");
        accountButton.setId("accountButton");
    }

    public void setFocusOnSearch(){
        homeButton.setId("homeButton");
        searchButton.setId("searchButtonFocused");
        favouritesButton.setId("favouritesButton");
        accountButton.setId("accountButton");
    }

    public void setFocusOnFavourites() {
        homeButton.setId("homeButton");
        searchButton.setId("searchButton");
        favouritesButton.setId("favouritesButtonFocused");
        accountButton.setId("accountButton");
    }

    public void requestFocusOnFavourites(){
        favouritesButton.requestFocus();
    }
}
