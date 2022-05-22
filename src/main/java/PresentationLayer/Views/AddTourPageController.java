package PresentationLayer.Views;

import PresentationLayer.Models.AddTourPageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTourPageController {

    @FXML public Button addButton;

    @FXML public TextField tourName;
    @FXML public TextField origin;
    @FXML public TextField destination;
    @FXML public TextField transportType;
    @FXML public TextField distance;
    @FXML public TextField time;
    @FXML public TextArea description;
    @FXML private AddTourPageModel model;

    public AddTourPageController() {
        model = new AddTourPageModel();
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
        Stage stage = (Stage) addButton.getScene().getWindow();
        if(model.addTour()) {
            stage.close();
        }
    }

}
