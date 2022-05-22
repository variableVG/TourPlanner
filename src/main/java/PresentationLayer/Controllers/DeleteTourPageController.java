package PresentationLayer.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

@Data
public class DeleteTourPageController {

    @FXML public Button cancelButton;
    @FXML public Button confirmDeleteButton;

    private String tourName;


    public DeleteTourPageController() throws IOException {
    }

    public void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void onDeleteButtonClick(ActionEvent event) {
        System.out.println("you are in onDelteButtonBlick");

    }
}
