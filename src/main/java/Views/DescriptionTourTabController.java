package Views;

import Models.Tour;
import Models.TourPlannerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Data;

@Data
public class DescriptionTourTabController {

    @FXML public Label tourDescriptionLabel;
    @FXML public Label originTourLabel;
    @FXML public Label destinationTourLabel;
    @FXML public Label tourDistanceLabel;
    @FXML public Label estimatedTimeLabel;
    @FXML public Label transportTypeLabel;
    @FXML public Label tourPopularityLabel;
    @FXML public Label childFriendlinessLabel;
    Tour tour;

    public DescriptionTourTabController() {
        tour = new Tour("Tour X");
    }
    //description tour tab model

    @FXML
    public void initialize() {
        this.tourDescriptionLabel.textProperty().bindBidirectional(tour.getDescription());
        this.originTourLabel.textProperty().bindBidirectional(tour.getOrigin());
        this.destinationTourLabel.textProperty().bindBidirectional(tour.getDestination());
        this.tourDistanceLabel.textProperty().bindBidirectional(tour.getDistance());
        this.estimatedTimeLabel.textProperty().bindBidirectional(tour.getTime());
        this.transportTypeLabel.textProperty().bindBidirectional(tour.getTransportType());
        this.tourPopularityLabel.textProperty().bindBidirectional(tour.getPopularity());
        this.childFriendlinessLabel.textProperty().bindBidirectional(tour.getChildFriendliness());
    }

    public void updateTourTab(Tour tour) {
        this.tour = tour;
        this.tourDescriptionLabel.setText(tour.getDescription().getValue());
        this.originTourLabel.setText(tour.getOrigin().getValue());
        this.destinationTourLabel.setText(tour.getDestination().getValue());
        this.tourDistanceLabel.setText(tour.getDistance().getValue());
        this.estimatedTimeLabel.setText(tour.getTime().getValue());
        this.transportTypeLabel.setText(tour.getTransportType().getValue());
        this.tourPopularityLabel.setText(tour.getPopularity().getValue());
        this.childFriendlinessLabel.setText(tour.getChildFriendliness().getValue());
    }


}
