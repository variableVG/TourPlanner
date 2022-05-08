package Views;

import Models.Tour;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogTourTabController {
    private TourPlannerApplication tpa = new TourPlannerApplication();
    Tour tour;
    //constructor for controller factory

    LogTourTabController(Tour tour) {
        this.tour = tour;

    }

    public void updateLogs(Tour tour) {
        this.tour = tour;
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
