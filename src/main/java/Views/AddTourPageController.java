package Views;

import Models.AddTourPageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddTourPageController {

    @FXML public TextField tourName;
    @FXML public TextField origin;
    @FXML public TextField destination;
    @FXML public TextField transportType;
    @FXML public TextField distance;
    @FXML public TextField time;
    @FXML public TextArea description;
    @FXML private AddTourPageModel model;

    public AddTourPageController(AddTourPageModel addTourPageModel) {
        this.model = addTourPageModel;
    }

    @FXML
    public void initialize(){
        this.tourName.textProperty().bindBidirectional(model.getTourName());
        this.origin.textProperty().bindBidirectional(model.getOrigin());
        this.destination.textProperty().bindBidirectional(model.getDestination());
        this.transportType.textProperty().bindBidirectional(model.getTransportType());
        this.distance.textProperty().bindBidirectional(model.getDistance());
        this.time.textProperty().bindBidirectional(model.getTime());
        this.description.textProperty().bindBidirectional(model.getDescription());
    }

    public void addTourOnClick(ActionEvent actionEvent){
        model.addTour();
    }

}
