package Views;

import Models.RouterTourTabModel;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class RouteTourTabController {

    RouterTourTabModel model;
    @FXML
    public ImageView apiMapImageView;

    public RouteTourTabController() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        model = new RouterTourTabModel();
    }

    @FXML
    public void initialize() {
        this.apiMapImageView.imageProperty().bindBidirectional(model.getApiMap());
        // I could not bring bindBiderectional to work
        //apiMapImageView.setImage(model.getMapImage());

    }

}
