package Views;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogTourTabController {
    private TourPlannerApplication tpa = new TourPlannerApplication();

    //constructor for controller factory

    public void addLogOnButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        tpa.addLog(stage);
    }
}
