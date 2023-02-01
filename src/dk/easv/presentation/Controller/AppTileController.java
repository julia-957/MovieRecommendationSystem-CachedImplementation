package dk.easv.presentation.Controller;

import dk.easv.entities.Movie;
import dk.easv.entities.TopMovie;
import dk.easv.entities.User;
import dk.easv.entities.UserSimilarity;
import dk.easv.presentation.Model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AppTileController implements Initializable {

    @FXML
    private TableView<User> tblUsers;
    @FXML
    private TableColumn<User, Integer> clmUserId;
    @FXML
    private TableColumn<User, String> clmUserName;
    @FXML
    private TableColumn<User, Double> clmUserRatings;

    @FXML
    private TableView<Movie> tblAvgTop;
    @FXML
    private TableColumn<Movie, String> clmTopAvgTitle;
    @FXML
    private TableColumn<Movie, Integer> clmTopAvgYear;
    @FXML
    private TableColumn<Movie, Integer> clmTopAvgRatings;
    @FXML
    private TableColumn<Movie, Double> clmTopAvgRating;

    @FXML
    private TableView<Movie> tblAvgTopNotSeen;
    @FXML
    private TableColumn<Movie, String> clmNotSeeTitle;
    @FXML
    private TableColumn<Movie, Integer> clmNotSeeYear;
    @FXML
    private TableColumn<Movie, Integer> clmNotSeeRatings;
    @FXML
    private TableColumn<Movie, Double> clmNotSeeAvgRating;

    @FXML
    private TableView<UserSimilarity> tblTopSimilarUsers;
    @FXML
    private TableColumn<UserSimilarity, Integer>clmTopSimUsersId;
    @FXML
    private TableColumn<UserSimilarity, String> clmTopSimUsersName;
    @FXML
    private TableColumn<UserSimilarity, String> clmTopSimUsersSimilarity;

    @FXML
    private TableView<TopMovie> tblTopMoviesSimilarUsers;
    @FXML
    private TableColumn<TopMovie, String> clmTopSimMovieTitle;
    @FXML
    private TableColumn<TopMovie, Integer>  clmTopSimMovieYear;
    @FXML
    private TableColumn<TopMovie, Double>  clmTopSimMovieAvgRating;

    private AppModel model = new AppModel();
    private long timerStartMillis = 0;
    private String timerMsg = "";

    private void startTimer(String message){
        timerStartMillis = System.currentTimeMillis();
        timerMsg = message;
    }

    private void stopTimer(){
        System.out.println(timerMsg + " took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
    }

    private void initTables(){
        clmUserId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        clmUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmUserRatings.setCellValueFactory(new PropertyValueFactory<>("ratingsSize"));

        clmTopAvgTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmTopAvgYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        clmTopAvgRating.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        clmTopAvgRatings.setCellValueFactory(new PropertyValueFactory<>("ratingsSize"));

        clmNotSeeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmNotSeeYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        clmNotSeeAvgRating.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        clmNotSeeRatings.setCellValueFactory(new PropertyValueFactory<>("ratingsSize"));

        clmTopSimUsersId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmTopSimUsersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmTopSimUsersSimilarity.setCellValueFactory(new PropertyValueFactory<>("similarityPercent"));

        clmTopSimMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmTopSimMovieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        clmTopSimMovieAvgRating.setCellValueFactory(new PropertyValueFactory<>("averageRating"));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTables();
        tblUsers.setItems(model.getObsUsers());
        tblAvgTop.setItems(model.getObsTopMovieSeen());
        tblAvgTopNotSeen.setItems(model.getObsTopMovieNotSeen());
        tblTopSimilarUsers.setItems(model.getObsSimilarUsers());
        tblTopMoviesSimilarUsers.setItems(model.getObsTopMoviesSimilarUsers());

        startTimer("Load users");
        model.loadUsers();
        stopTimer();

        tblUsers.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldUser, selectedUser) -> {
                    startTimer("Loading all data for user: " + selectedUser);
                    model.loadData(selectedUser);
                    stopTimer();
                });
    }


}
