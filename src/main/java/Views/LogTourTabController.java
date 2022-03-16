package Views;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogTourTabController {
    private TourPlannerApplication tpa = new TourPlannerApplication();

    public void addLogOnButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        tpa.addLog(stage);
    }
}
