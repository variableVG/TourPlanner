package PresentationLayer.Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Data;

@Data
public class LogTourTabModel {
    Tour tour;
    IBusinessLayer business;
    public ObservableList<VBox> logs;


    public LogTourTabModel(Tour tour){
        this.tour = tour;
        business = new BusinessLayer();
        logs = FXCollections.observableArrayList();

    }


    public void updateLogs(Log log) {
        System.out.println("Update in LogTourTabModel with log " + log.getId());
        System.out.println("Log size is " + logs.size());
        for(int j = 0; j < logs.size(); j++) {
            System.out.println("Inside log loop");
            if(logs.get(j).getId().equals(String.valueOf(log.getId()))) {
                System.out.println("Inside if condition");
                //HBox for Date and Time
                System.out.println("Print HBOX?");
                System.out.println(logs.get(j).getChildren().get(1));

            }
        }

    }



    public void deleteLog(int logId) {
        try {
            business.deleteLog(logId);
            //delete Log in the frontend
            TourPlannerModel.getInstance().deleteLog(tour, logId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateVBoxIn(Log l, VBox box) {
        String labelTitleStyle = "-fx-font-weight: bold;";

        //ID BOX
        HBox idBox = new HBox();
        Label idLabel = new Label();
        idLabel.setText("Log id:");
        idLabel.setStyle(labelTitleStyle);
        Label idLabelContent = new Label();
        idLabelContent.setText("  " + l.getId());
        idBox.getChildren().addAll(idLabel, idLabelContent);
        box.getChildren().addAll(idBox);

        //DATE AND TIME
        HBox dateBox = new HBox();
        Label dateLabel = new Label();
        dateLabel.setText("Date: ");
        dateLabel.setStyle(labelTitleStyle);
        Label dateLabelContent = new Label();
        dateLabelContent.setText(" " + l.getDate());
        Label timeLabel = new Label();
        timeLabel.setText(" at " + l.getTime() + " hours ");
        dateBox.getChildren().addAll(dateLabel, dateLabelContent, timeLabel);
        box.getChildren().addAll(dateBox);

        //DIFFICULTY
        HBox difficultyBox = new HBox();
        Label difficultyLabel = new Label();
        difficultyLabel.setText("Difficulty: ");
        difficultyLabel.setStyle(labelTitleStyle);
        Label difficultyLabelContent = new Label();
        difficultyLabelContent.setText("  " + l.getDifficulty());
        difficultyBox.getChildren().addAll(difficultyLabel, difficultyLabelContent);
        box.getChildren().addAll(difficultyBox);

        //TOTAL TIME
        HBox totalTimeBox = new HBox();
        Label totalTimeLabel = new Label();
        totalTimeLabel.setText("Total time: ");
        totalTimeLabel.setStyle(labelTitleStyle);
        Label totalTimeLabelContent = new Label();
        totalTimeLabelContent.setText("  " + String.valueOf(l.getTotaltime()));
        totalTimeBox.getChildren().addAll(totalTimeLabel, totalTimeLabelContent);
        box.getChildren().addAll(totalTimeBox);

        //RATING
        HBox ratingBox = new HBox();
        Label ratingLabel = new Label();
        ratingLabel.setText("Rating: ");
        ratingLabel.setStyle(labelTitleStyle);
        Label ratingLabelContent = new Label();
        ratingLabelContent.setText("  " + String.valueOf(l.getRating()));
        ratingBox.getChildren().addAll(ratingLabel, ratingLabelContent);
        box.getChildren().addAll(ratingBox);

        // COMMENTS
        HBox commentBox = new HBox();
        Label commentLabel = new Label();
        commentLabel.setText("Comments: ");
        commentLabel.setStyle(labelTitleStyle);
        Label commentLabelContent = new Label();
        commentLabelContent.setText("  " + String.valueOf(l.getComment()));
        commentBox.getChildren().addAll(commentLabel, commentLabelContent);
        box.getChildren().addAll(commentBox);



    }



}
