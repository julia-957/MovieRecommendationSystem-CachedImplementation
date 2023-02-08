package dk.easv;

import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.controller.LogInController;
import dk.easv.presentation.controller.SearchController;
import dk.easv.presentation.model.AppModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene shownScene;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Budgetflix 2.1");
        this.primaryStage.getIcons().add(new Image("file:resources/icons/budgetflixIcon.png"));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInController logInController = fxmlLoader.getController();
        logInController.setMainApp(this);

        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        this.primaryStage.setOnHidden(event -> initRootLayout());
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("presentation/view/MenuRootView.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            shownScene = new Scene(rootLayout);
            primaryStage.setScene(shownScene);

            // Give the controller access to the main app.
            AppController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

   public void openSearchWindow(Stage currentStage) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/SearchView.fxml"));
       BorderPane mainView = fxmlLoader.load();
       rootLayout.setCenter(mainView);
       //TODO center, autosize rootLayout.getCenter().autosize();
       primaryStage = currentStage;
       primaryStage.setScene(shownScene);
   }
}
