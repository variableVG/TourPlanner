package Views;

import Models.AddLogPageModel;
import Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.time.ZoneId;

public class AddLogPageController {

    @FXML public DatePicker date;
    @FXML public TextField time;
    @FXML public TextField comment;
    @FXML public TextField difficulty;
    @FXML public TextField totalTime;
    @FXML public Rating rating;
    @FXML public Button addButton;
    @FXML public Label info;
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
        this.date.valueProperty().bindBidirectional(model.getDate());
        this.time.textProperty().bindBidirectional(model.getTime());
        this.comment.textProperty().bindBidirectional(model.getComment());
        this.difficulty.textProperty().bindBidirectional(model.getDifficulty());
        this.totalTime.textProperty().bindBidirectional(model.getTotalTime());
        this.rating.ratingProperty().bindBidirectional(model.getRating());
        this.info.textProperty().bindBidirectional(model.getInfo());
    }


    public void addLogOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) addButton.getScene().getWindow();
        try {
            if(model.addLog()) {
                stage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
