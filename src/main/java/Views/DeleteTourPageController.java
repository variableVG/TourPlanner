package Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteTourPageController {

    @FXML public Button cancelButton;
    //@FXML public Button confirmDeleteButton;

    public DeleteTourPageController() throws IOException {
    }

    public void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
