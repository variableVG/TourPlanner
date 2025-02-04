package PresentationLayer.Controllers;

import PresentationLayer.Models.EditTourPageModel;
import PresentationLayer.Models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTourPageController {


    EditTourPageModel model;
    Tour tour;
    @FXML TextField tourName;
    @FXML TextField origin;
    @FXML TextField destination;
    @FXML public RadioButton AutoFastest;
    @FXML public RadioButton AutoShortest;
    @FXML public RadioButton Bicycle;
    @FXML public RadioButton Pedestrian;
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
        description.textProperty().bindBidirectional(model.getDescription());

        //If selected:
        String transportType = model.getTransportType().getValue();
        if(transportType.equals("shortest")) {
            this.AutoShortest.setSelected(true);
            this.AutoFastest.setSelected(false);
            this.Pedestrian.setSelected(false);
            this.Bicycle.setSelected(false);
        }
        else if(transportType.equals("bicycle")) {
            this.AutoShortest.setSelected(false);
            this.AutoFastest.setSelected(false);
            this.Pedestrian.setSelected(false);
            this.Bicycle.setSelected(true);
        }
        else if (transportType.equals("pedestrian")) {
            this.AutoShortest.setSelected(false);
            this.AutoFastest.setSelected(false);
            this.Pedestrian.setSelected(true);
            this.Bicycle.setSelected(false);

        }
        else {
            this.AutoShortest.setSelected(false);
            this.AutoFastest.setSelected(true);
            this.Pedestrian.setSelected(false);
            this.Bicycle.setSelected(false);

        }

    }


    public void editTourOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) editButton.getScene().getWindow();

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

        if(model.updateTour()){
            stage.close();
            descriptionTourTabController.updateTourTab(tour);

        }

    }
}
