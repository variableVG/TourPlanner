package Views;

import Models.Log;
import Models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ObservableList<String> logs = FXCollections.observableArrayList();
        // Source: https://www.tutorialspoint.com/how-to-add-data-to-a-tableview-in-javafx
        for(Log l : tour.getLogs()) {
            System.out.println("Hi?");
            logs.add(String.valueOf(l.getId()));
            logs.add(l.getDate());
            logs.add(l.getTime());
            logs.add(l.getComment());
            logs.add(String.valueOf(l.getDifficulty()));
            logs.add(l.getTotaltime());
            logs.add(String.valueOf(l.getRating()));
            tableViewLogs.setItems(logs);
            logs.clear();
        }


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
