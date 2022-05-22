package PresentationLayer.Views;

import PresentationLayer.Models.AddLogPageModel;
import PresentationLayer.Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class AddLogPageController {

    @FXML public DatePicker date;
    @FXML public TextField time;
    @FXML public TextArea comment;
    @FXML public TextField totalTime;
    @FXML public Rating rating;
    @FXML public Button addButton;
    @FXML public Label info;
    @FXML public ToggleGroup difficulty;
    @FXML public RadioButton easyDiff;
    @FXML public RadioButton mediumDiff;
    @FXML public RadioButton hardDiff;

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
        this.totalTime.textProperty().bindBidirectional(model.getTotalTime());
        this.rating.ratingProperty().bindBidirectional(model.getRating());
        this.info.textProperty().bindBidirectional(model.getInfo());
    }


    public void addLogOnClick(ActionEvent actionEvent) {
        //Get difficulty
        if(easyDiff.isSelected()) {
            model.getDifficulty().setValue(1);
        }
        else if(mediumDiff.isSelected()) {
            model.getDifficulty().setValue(2);
        }
        else if(hardDiff.isSelected()) {
            model.getDifficulty().setValue(3);
        }
        else {
            model.getDifficulty().setValue(0);
        }


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
