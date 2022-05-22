package PresentationLayer.Controllers;

import PresentationLayer.Models.DescriptionTourTabModel;
import PresentationLayer.Models.Tour;
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

    DescriptionTourTabModel model;

    public DescriptionTourTabController(Tour tour) {
        //we need this, because otherwise tour would be null when we inizialize() -> error
        model = new DescriptionTourTabModel();

    }


    @FXML
    public void initialize() {
        this.tourDescriptionLabel.textProperty().bindBidirectional(model.getDescription());
        this.originTourLabel.textProperty().bindBidirectional(model.getOrigin());
        this.destinationTourLabel.textProperty().bindBidirectional(model.getDestination());
        this.tourDistanceLabel.textProperty().bindBidirectional(model.getDistance());
        this.estimatedTimeLabel.textProperty().bindBidirectional(model.getTime());
        this.transportTypeLabel.textProperty().bindBidirectional(model.getTransportType());

    }

    public void updateTourTab(Tour tour) {
        model.updateTour(tour);

    }


}
