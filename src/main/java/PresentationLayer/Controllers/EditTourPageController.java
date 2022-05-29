package PresentationLayer.Controllers;

import PresentationLayer.Models.EditTourPageModel;
import PresentationLayer.Models.Tour;
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
    //@FXML TextField distance;
    //@FXML TextField time;
    @FXML TextArea description;
    @FXML public Button editButton;
    DescriptionTourTabController descriptionTourTabController;

    public EditTourPageController(String tourName, DescriptionTourTabController descriptionTourTabController) {
        this.model = new EditTourPageModel(tourName);
        //**this.model = new EditTourPageModel(tourId);
        this.tour = model.getTour();
        this.descriptionTourTabController = descriptionTourTabController;

    }

    @FXML
    public void initialize(){
        tourName.textProperty().bindBidirectional(model.getTourName());
        origin.textProperty().bindBidirectional(model.getOrigin());
        destination.textProperty().bindBidirectional(model.getDestination());
        transportType.textProperty().bindBidirectional(model.getTransportType());
        //Distance and Time are requested by API
        //distance.textProperty().bindBidirectional(model.getDistance());
        //time.textProperty().bindBidirectional(model.getTime());;
        description.textProperty().bindBidirectional(model.getDescription());
    }


    public void editTourOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) editButton.getScene().getWindow();
        if(model.updateTour()){
            stage.close();
            descriptionTourTabController.updateTourTab(tour);


        }

    }
}
