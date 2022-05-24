package PresentationLayer.Models;

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

    private ObservableList<VBox> logs =
            FXCollections.observableArrayList();

    public LogTourTabModel(Tour tour){
        this.tour = tour;
    }


    public void updateLogs() {
        this.logs.clear();
        String labelTitleStyle = "-fx-font-weight: bold;";
        String cssLayout = "-fx-border-color: grey;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: dashed;\n";
        for (Log l : tour.getLogs()) {
            VBox logBox = new VBox();
            //ID BOX
            HBox idBox = new HBox();
            Label idLabel = new Label();
            idLabel.setText("Log id:");
            idLabel.setStyle(labelTitleStyle);
            Label idLabelContent = new Label();
            idLabelContent.setText("  " + l.getId());
            idBox.getChildren().addAll(idLabel, idLabelContent);
            logBox.getChildren().addAll(idBox);

            //DATE AND TIME
            HBox dateBox = new HBox();
            Label dateLabel = new Label();
            dateLabel.setText("Date: ");
            dateLabel.setStyle(labelTitleStyle);
            Label dateLabelContent = new Label();
            dateLabelContent.setText(" " + l.getDate());
            Label timeLabel = new Label();
            timeLabel.setText(" at " + l.getTime()+ " hours ");
            dateBox.getChildren().addAll(dateLabel, dateLabelContent, timeLabel);
            logBox.getChildren().addAll(dateBox);

            //DIFFICULTY
            HBox difficultyBox = new HBox();
            Label difficultyLabel = new Label();
            difficultyLabel.setText("Difficulty: ");
            difficultyLabel.setStyle(labelTitleStyle);
            Label difficultyLabelContent = new Label();
            difficultyLabelContent.setText("  " + l.getDifficulty());
            difficultyBox.getChildren().addAll(difficultyLabel, difficultyLabelContent);
            logBox.getChildren().addAll(difficultyBox);

            //TOTAL TIME
            HBox totalTimeBox = new HBox();
            Label totalTimeLabel = new Label();
            totalTimeLabel.setText("Total time: ");
            totalTimeLabel.setStyle(labelTitleStyle);
            Label totalTimeLabelContent = new Label();
            totalTimeLabelContent.setText("  " + String.valueOf(l.getTotaltime()));
            totalTimeBox.getChildren().addAll(totalTimeLabel, totalTimeLabelContent);
            logBox.getChildren().addAll(totalTimeBox);

            //RATING
            HBox ratingBox = new HBox();
            Label ratingLabel = new Label();
            ratingLabel.setText("Rating: ");
            ratingLabel.setStyle(labelTitleStyle);
            Label ratingLabelContent = new Label();
            ratingLabelContent.setText("  " + String.valueOf(l.getRating()));
            ratingBox.getChildren().addAll(ratingLabel, ratingLabelContent);
            logBox.getChildren().addAll(ratingBox);

            // COMMENTS
            HBox commentBox = new HBox();
            Label commentLabel = new Label();
            commentLabel.setText("Comments: ");
            commentLabel.setStyle(labelTitleStyle);
            Label commentLabelContent = new Label();
            commentLabelContent.setText("  " + String.valueOf(l.getComment()));
            commentBox.getChildren().addAll(commentLabel, commentLabelContent);
            logBox.getChildren().addAll(commentBox);

            // EDIT AND DELETE BUTTONS
            HBox buttonBox = new HBox();
            Button editButton = new Button("Edit");
            editButton.setPadding(new Insets(5));
            Button deleteButton = new Button("Delete");
            deleteButton.setPadding(new Insets(5));
            buttonBox.getChildren().addAll(editButton, deleteButton);
            logBox.getChildren().addAll(buttonBox);

            logs.add(logBox);

        }
    }
}
