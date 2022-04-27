package Views;

import Map.ApiMap;
import Models.RouterTourTabModel;
import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RouteTourTabController {

    RouterTourTabModel model;
    private final CompletableFuture<ApiMap> completableFutureApiMap;

    @FXML
    public ImageView apiMapImageView;

    public RouteTourTabController() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        model = new RouterTourTabModel();
        completableFutureApiMap = model.getCompletableFutureApiMap();
    }

    @FXML
    public void initialize() {
        //this.apiMapImageView.imageProperty().bindBidirectional(model.getPropertyApiMap());
        // I could not bring bindBiderectional to work
        this.apiMapImageView.setImage(model.getMapImage());

        completableFutureApiMap.thenApply(
                futureApiMap -> {
                    System.out.println("I am in the future");
                    BufferedImage mapBufferedImage = futureApiMap.getBufferedImageMap();
                    System.out.println(mapBufferedImage);
                    Image mapImage = SwingFXUtils.toFXImage(mapBufferedImage, null);
                    // Source: https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
                    this.apiMapImageView.setImage(mapImage);
                    return null;
                }
        );

    }

}
