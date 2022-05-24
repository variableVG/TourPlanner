package PresentationLayer.Controllers;

import PresentationLayer.Models.EditLogPageModel;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class EditLogPageController {

    @FXML public Label info;
    @FXML public DatePicker date;
    @FXML public TextField time;
    @FXML public TextField totalTime;
    @FXML public ToggleGroup difficulty;
    @FXML public RadioButton easyDiff;
    @FXML public RadioButton mediumDiff;
    @FXML public RadioButton hardDiff;
    @FXML public Rating rating;
    @FXML public TextArea comment;
    @FXML public Button editButton;

    EditLogPageModel model;
    Tour tour;
    Log log;

    public EditLogPageController(String tourName, int logId) {
        model = new EditLogPageModel(logId, tourName);
    }

    @FXML
    public void initialize(){
        info.textProperty().bindBidirectional(model.getInfo());
        this.date.valueProperty().bindBidirectional(model.getDate());
        this.time.textProperty().bindBidirectional(model.getTime());
        this.comment.textProperty().bindBidirectional(model.getComment());
        this.totalTime.textProperty().bindBidirectional(model.getTotalTime());
        this.rating.ratingProperty().bindBidirectional(model.getRating());

        int difficultyInt = model.getDifficulty().getValue();
        if(difficultyInt == 1) {
            easyDiff.setSelected(true);
            mediumDiff.setSelected(false);
            hardDiff.setSelected(false);
        }
        else if(difficultyInt == 2) {
            mediumDiff.setSelected(true);
            easyDiff.setSelected(false);
            hardDiff.setSelected(false);
        }
        else if(difficultyInt == 3) {
            hardDiff.setSelected(true);
            mediumDiff.setSelected(false);
            easyDiff.setSelected(false);
        }





    }

    public void editLogOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) editButton.getScene().getWindow();
        model.editLog();
        stage.close();

    }
}
