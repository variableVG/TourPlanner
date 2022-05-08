package Views;

import Models.EditTourPageModel;
import Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTourPageController {


    EditTourPageModel model;
    Tour tour;
    @FXML TextField tourName;
    @FXML TextField origin;
    @FXML TextField destination;
    @FXML TextField transportType;
    @FXML TextField distance;
    @FXML TextField time;
    @FXML TextArea description;
    @FXML public Button editButton;

    public EditTourPageController(String tourName) {
        this.model = new EditTourPageModel(tourName);
        //**this.model = new EditTourPageModel(tourId);
        this.tour = model.getTour();

    }

    @FXML
    public void initialize(){
        tourName.textProperty().bindBidirectional(tour.getName());
        origin.textProperty().bindBidirectional(tour.getOrigin());
        destination.textProperty().bindBidirectional(tour.getDestination());
        transportType.textProperty().bindBidirectional(tour.getTransportType());
        distance.textProperty().bindBidirectional(tour.getDistance());
        time.textProperty().bindBidirectional(tour.getTime());;
        description.textProperty().bindBidirectional(tour.getDescription());
    }


    public void editTourOnClick(ActionEvent actionEvent) {
        model.updateTour();
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }
}
