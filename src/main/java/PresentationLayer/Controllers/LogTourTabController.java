package PresentationLayer.Controllers;

import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LogTourTabController {

    public javafx.scene.layout.VBox VBox;
    /*@FXML public TableView<Log> tableViewLogs;
        @FXML public TableColumn comment;
        @FXML public TableColumn difficulty;
        @FXML public TableColumn total_time;
        @FXML public TableColumn rating;
        @FXML public TableColumn log_id;
        @FXML public TableColumn date;
        @FXML public TableColumn time;*/
    @FXML private TourPlannerApplication tpa = new TourPlannerApplication();
    Tour tour;



    //constructor for controller factory

    LogTourTabController(Tour tour) {
        this.tour = tour;
    }

    @FXML
    public void initialize(){
    }

    public void updateLogs(Tour tour) {
        //SET THE NEW TOUR
        this.tour = tour;

        //DISPLAY TOURS
        VBox.getChildren().clear();

        String labelTitleStyle = "-fx-font-weight: bold;";
        String cssLayout = "-fx-border-color: grey;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: dashed;\n";


        for (Log l : tour.getLogs()) {
            VBox logBox = new VBox();
            logBox.setPadding(new Insets(10));

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

            logBox.setStyle(cssLayout);
            VBox.getChildren().addAll(logBox);
        }


     }

    public void addLogOnButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        if(tour != null) {
            tpa.addLog(stage, tour.getName());
        }
        else {
            System.out.println("You cannot add a log to an empty tour");
        }


    }
}
