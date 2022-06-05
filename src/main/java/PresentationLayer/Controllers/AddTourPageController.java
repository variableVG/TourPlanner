package PresentationLayer.Controllers;

import PresentationLayer.Models.AddTourPageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTourPageController {

    @FXML public Button addButton;
    @FXML public TextField tourName;
    @FXML public TextField origin;
    @FXML public TextField destination;
    @FXML public TextField transportType;
    //@FXML public TextField distance;
    //@FXML public TextField time;
    @FXML public TextArea description;
    @FXML public RadioButton AutoFastest;
    @FXML public RadioButton AutoShortest;
    @FXML public RadioButton Bicycle;
    @FXML public RadioButton Pedestrian;
    @FXML private AddTourPageModel model;

    public AddTourPageController() {
        model = new AddTourPageModel();
    }

    @FXML
    public void initialize(){
        this.tourName.textProperty().bindBidirectional(model.getTourName());
        this.origin.textProperty().bindBidirectional(model.getOrigin());
        this.destination.textProperty().bindBidirectional(model.getDestination());
        //Distance and Time are requested by API
        //this.distance.textProperty().bindBidirectional(model.getDistance());
        //this.time.textProperty().bindBidirectional(model.getTime());
        this.description.textProperty().bindBidirectional(model.getDescription());
    }

    public void addTourOnClick(ActionEvent actionEvent){
        Stage stage = (Stage) addButton.getScene().getWindow();

        if(AutoShortest.isSelected()) {
            model.getTransportType().setValue("shortest");
        }
        else if(Bicycle.isSelected()) {
            model.getTransportType().setValue("bicycle");
        }
        else if(Pedestrian.isSelected()) {
            model.getTransportType().setValue("pedestrian");
        }
        else {
            model.getTransportType().setValue("fastest");
        }

        if(model.addTour()) {
            stage.close();
        }
    }

}
