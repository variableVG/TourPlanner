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

                String labelTitleStyle = "-fx-font-weight: bold;";

                VBox logBox = new VBox();
                logBox.setId(String.valueOf(log.getId()));
                //ID BOX
                HBox idBox = new HBox();
                Label idLabel = new Label();
                idLabel.setText("Log id:");
                idLabel.setStyle(labelTitleStyle);
                Label idLabelContent = new Label();
                idLabelContent.setText("  " + log.getId());
                idBox.getChildren().addAll(idLabel, idLabelContent);
                logBox.getChildren().addAll(idBox);

                //DATE AND TIME
                HBox dateBox = new HBox();
                dateBox.setId("HBoxDateBox"+log.getId());
                Label dateLabel = new Label();
                dateLabel.setText("Date: ");
                dateLabel.setStyle(labelTitleStyle);
                Label dateLabelContent = new Label();
                dateLabelContent.setText(" " + log.getDate());
                dateLabelContent.setId("date" + log.getId());
                Label timeLabel = new Label();
                timeLabel.setText(" at " + log.getTime() + " hours ");
                timeLabel.setId("time" + log.getId());
                dateBox.getChildren().addAll(dateLabel, dateLabelContent, timeLabel);
                logBox.getChildren().addAll(dateBox);

                //DIFFICULTY
                HBox difficultyBox = new HBox();
                Label difficultyLabel = new Label();
                difficultyLabel.setText("Difficulty: ");
                difficultyLabel.setStyle(labelTitleStyle);
                Label difficultyLabelContent = new Label();
                difficultyLabelContent.setText("  " + log.getDifficulty());
                difficultyBox.getChildren().addAll(difficultyLabel, difficultyLabelContent);
                logBox.getChildren().addAll(difficultyBox);

                //TOTAL TIME
                HBox totalTimeBox = new HBox();
                Label totalTimeLabel = new Label();
                totalTimeLabel.setText("Total time: ");
                totalTimeLabel.setStyle(labelTitleStyle);
                Label totalTimeLabelContent = new Label();
                totalTimeLabelContent.setText("  " + String.valueOf(log.getTotaltime()));
                totalTimeBox.getChildren().addAll(totalTimeLabel, totalTimeLabelContent);
                logBox.getChildren().addAll(totalTimeBox);

                //RATING
                HBox ratingBox = new HBox();
                Label ratingLabel = new Label();
                ratingLabel.setText("Rating: ");
                ratingLabel.setStyle(labelTitleStyle);
                Label ratingLabelContent = new Label();
                ratingLabelContent.setText("  " + String.valueOf(log.getRating()));
                ratingBox.getChildren().addAll(ratingLabel, ratingLabelContent);
                logBox.getChildren().addAll(ratingBox);

                // COMMENTS
                HBox commentBox = new HBox();
                Label commentLabel = new Label();
                commentLabel.setText("Comments: ");
                commentLabel.setStyle(labelTitleStyle);
                Label commentLabelContent = new Label();
                commentLabelContent.setText("  " + String.valueOf(log.getComment()));
                commentBox.getChildren().addAll(commentLabel, commentLabelContent);
                logBox.getChildren().addAll(commentBox);

                // EDIT AND DELETE BUTTONS
                HBox buttonBox = new HBox();
                Button editButton = new Button("Edit");
                editButton.setPadding(new Insets(5));
                editButton.setOnAction(event -> logTourTabController.editLogOnButtonClick(event, log.getId()));
                Button deleteButton = new Button("Delete");
                deleteButton.setPadding(new Insets(5));
                deleteButton.setOnAction(event -> logTourTabController.deleteLogOnButtonClick(event, log.getId()));
                buttonBox.getChildren().addAll(editButton, deleteButton);
                logBox.getChildren().addAll(buttonBox);

                logTourTabController.logList.getItems().add(0, logBox);
                break;

            }

            }

    }
}
