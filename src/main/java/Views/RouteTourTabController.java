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
    public ImageView apiMap;

    public RouteTourTabController() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        model = new RouterTourTabModel();
    }

    @FXML
    public void initialize() {
        this.apiMap.imageProperty().bindBidirectional(model.getApiMap());
    }

}
