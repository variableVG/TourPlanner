package PresentationLayer.Controllers;

import PresentationLayer.Models.EditLogPageModel;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    LogTourTabController logTourTabController;
    Tour tour;
    Log log;

    public EditLogPageController(String tourName, int logId, LogTourTabController logTourTabController) {
        model = new EditLogPageModel(logId, tourName);
        log = model.getLog();
        this.logTourTabController = logTourTabController;
    }

    @FXML
    public void initialize(){
        this.info.textProperty().bindBidirectional(model.getInfo());
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
        //get which difficulty has been selected
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

        Stage stage = (Stage) editButton.getScene().getWindow();
        if(model.editLog()) {
            stage.close();
        }

        int size = logTourTabController.logList.getItems().size();
        for(int i = 0; i < size; i++) {
            System.out.println("For-Loop in the controller");
            String vBox = logTourTabController.logList.getItems().get(i).toString();
            Integer id = Integer.parseInt(vBox.substring(8, vBox.lastIndexOf(']')));

            if(id == log.getId()) {
                logTourTabController.logList.getItems().remove(i);
                logTourTabController.setLogCell(log);
                break;
            }

        }
        DescriptionTourTabController.model.updateTour(tour);

    }
}
