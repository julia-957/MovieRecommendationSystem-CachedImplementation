package dk.easv.presentation.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class SearchController {
    private AppController appController;
    private MenuController menuController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void myListAction(ActionEvent actionEvent) throws IOException {
        appController.openFavouritesScreen();
    }


}
