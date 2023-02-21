package dk.easv.presentation.controller;

import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BudgetMother implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    protected ScrollBar getVerticalScrollbar(ScrollPane scrollPane) {
        ScrollBar result = null;
        for (Node n : scrollPane.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
        return result;
    }
}
