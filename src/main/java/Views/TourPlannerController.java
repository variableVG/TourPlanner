package Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TourPlannerController {

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
}