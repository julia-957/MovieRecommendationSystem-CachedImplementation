package dk.easv;

import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInController logInController = fxmlLoader.getController();
        logInController.setMainApp(this);

        this.primaryStage.centerOnScreen();
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }


    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("presentation/view/MenuRootView.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            shownScene = new Scene(rootLayout, width, height);
            primaryStage.setScene(shownScene);

            // Give the controller access to the main app.
            appController = loader.getController();
            appController.setMainApp(this);

            primaryStage.centerOnScreen();
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
       primaryStage = currentStage;
       primaryStage.centerOnScreen();
       primaryStage.setScene(shownScene);
   }

    public void openIntroScreen(Stage currentStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/IntroScreen.fxml"));
        VBox introScreen = fxmlLoader.load();

        rootLayout.setCenter(introScreen);
        primaryStage = currentStage;
        primaryStage.centerOnScreen();
        primaryStage.setScene(shownScene);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
