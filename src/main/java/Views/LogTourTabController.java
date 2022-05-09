package Views;

import Models.Log;
import Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogTourTabController {


    @FXML public TableColumn comment;
    @FXML public TableColumn difficulty;
    @FXML public TableColumn total_time;
    @FXML public TableColumn rating;
    @FXML public TableColumn log_id;
    @FXML public TableColumn date;
    @FXML public TableColumn time;
    @FXML private TourPlannerApplication tpa = new TourPlannerApplication();
    Tour tour;

    @FXML public TableView tableViewLogs;

    //constructor for controller factory

    LogTourTabController(Tour tour) {
        this.tour = tour;


    }

    public void updateLogs(Tour tour) {
        this.tour = tour;

        tableViewLogs.setItems(tour.getLogs());
        tableViewLogs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewLogs.getColumns().addAll(log_id, date, time, comment, difficulty, total_time, rating);


     }

    public void addLogOnButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        if(tour != null) {
            tpa.addLog(stage, tour.getName().getValue());
        }
        else {
            System.out.println("You cannot add a log to an empty tour");
        }


    }
}
