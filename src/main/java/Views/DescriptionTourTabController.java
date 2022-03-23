package Views;

import Models.Tour;
import Models.TourPlannerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Data;

@Data
public class DescriptionTourTabController {

    Tour tour;

    public DescriptionTourTabController() {
        System.out.println("Controller is called");
        tour = new Tour("Tour X", "XX");

    }

    @FXML
    public Label tourDescriptionLabel;


    @FXML
    public void initialize() {
        System.out.println("I am initializing");
        this.tourDescriptionLabel.textProperty().bindBidirectional(tour.getDescription());
    }

    public void updateTourTab(Tour tour) {
        this.tour = tour;
        //System.out.println("Tour name now is " + tour.getName());
        //System.out.println("And tour description now is " + tour.getDescription().getValue());
        //this.tourDescriptionLabel.setText(tour.getDescription().getValue());
        //System.out.println(tourDescriptionLabel);
        this.tourDescriptionLabel.setText(tour.getDescription().getValue());

    }


}
