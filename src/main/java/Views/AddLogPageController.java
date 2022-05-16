package Views;

import Models.AddLogPageModel;
import Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLogPageController {

    @FXML public TextField date;
    @FXML public TextField time;
    @FXML public TextField comment;
    @FXML public TextField difficulty;
    @FXML public TextField totalTime;
    @FXML public TextField rating;
    @FXML public Button addButton;
    //add log page model
    //constructor
    Tour tour;
    AddLogPageModel model;

    AddLogPageController(Tour tour) {
        this.tour = tour;
        model = new AddLogPageModel(tour);

    }
    @FXML
    public void initialize(){
        this.date.textProperty().bindBidirectional(model.getDate());
        this.time.textProperty().bindBidirectional(model.getTime());
        this.comment.textProperty().bindBidirectional(model.getComment());
        this.difficulty.textProperty().bindBidirectional(model.getDifficulty());
        this.totalTime.textProperty().bindBidirectional(model.getTotalTime());
        this.rating.textProperty().bindBidirectional(model.getRating());
    }


    public void addLogOnClick(ActionEvent actionEvent) {
        model.addLog();
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
