package Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class TourPlannerController {

    //Could that be correct?
    TourPlannerApplication tourPlannerApplication = new TourPlannerApplication();

    @FXML
    private Label testTextObjectPlaceholder;

    public void onRouteButtonClick(ActionEvent actionEvent) {
        testTextObjectPlaceholder.setText("here you can see the route aka map, Yeah!");
    }

    public void onDescriptionButtonClick(ActionEvent actionEvent) {
        testTextObjectPlaceholder.setText("here you can see the description of the route, Yippie!\nfrom:\nto:\ntransport type:\ntour distance:\nestimated time:\n");
    }

    public void onLogsButtonClick(ActionEvent actionEvent) {
        testTextObjectPlaceholder.setText("here you can see your logs and logs of other users, Yesssa! (in a table)\n\tdate:\n\ttime:\n\tdistance:");
    }

    public void addTour(ActionEvent actionEvent) throws IOException {
        System.out.println("Hi");
        tourPlannerApplication.addTourWindow();
    }
}