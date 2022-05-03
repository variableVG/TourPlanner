package Views;

import Models.EditTourPageModel;
import Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    public EditTourPageController(String tourName) {
        this.model = new EditTourPageModel(tourName);
        this.tour = model.getTour();

    }

    @FXML
    public void initialize(){
        tourName.textProperty().bindBidirectional(tour.getName());
        origin.textProperty().bindBidirectional(tour.getOrigin());
        destination.textProperty().bindBidirectional(tour.getName());
        transportType.setText(this.tour.getTransportType().getValue());
        distance.setText(this.tour.getDistance().getValue());
        time.setText(this.tour.getTime().getValue());
        description.setText(this.tour.getDescription().getValue());
    }


    public void editTourOnClick(ActionEvent actionEvent) {
        model.updateTour();
        System.out.println("Edit Button has been clicked");
    }
}
