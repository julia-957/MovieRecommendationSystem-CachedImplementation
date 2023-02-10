package dk.easv;

import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene shownScene;
    int width, height;
    private AppController appController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth()-400;
        height = gd.getDisplayMode().getHeight()-350;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Budgetflix 2.1");
        this.primaryStage.getIcons().add(new Image("file:resources/icons/budgetflixIcon.png"));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/MenuRootView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        this.primaryStage.centerOnScreen();
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
