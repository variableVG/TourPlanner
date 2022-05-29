package PresentationLayer.Controllers;

import PresentationLayer.Models.DescriptionTourTabModel;
import PresentationLayer.Models.Tour;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

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

    //In order to be able to change information, the model has to be static here (and public)
    static public DescriptionTourTabModel model;

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
        this.tourPopularityLabel.textProperty().bindBidirectional(model.getPopularity());

    }

    public void updateTourTab(Tour tour) {
        model.updateTour(tour);
    }


}
