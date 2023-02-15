package dk.easv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    private Stage primaryStage;
    private int width, height;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth() - 400;
        height = gd.getDisplayMode().getHeight() - 350;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Budgetflix 2.1");
        this.primaryStage.getIcons().add(new Image("file:resources/icons/budgetflixIcon.png"));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/menuViews/MenuRootView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        this.primaryStage.centerOnScreen();
        this.primaryStage.setScene(scene);
        this.primaryStage.setMaximized(true);
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
