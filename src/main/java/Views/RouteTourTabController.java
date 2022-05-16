package Views;

import Map.ApiMap;
import Models.RouterTourTabModel;
import Models.Tour;
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
    Tour tour;
   //private CompletableFuture<ApiMap> completableFutureApiMap;

    @FXML
    public ImageView apiMapImageView;

    public RouteTourTabController() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        model = new RouterTourTabModel();
        //completableFutureApiMap = model.getCompletableFutureApiMap();
    }

    @FXML
    public void initialize() {
        //this.apiMapImageView.imageProperty().bindBidirectional(model.getPropertyApiMap());
        // I could not bring bindBiderectional to work


    }

    public void update(Tour tour)  {
        this.tour = tour;
        if(tour.getStaticMap() == null) {
            System.out.println("I am in update - RouteTourController, getStaticMap() is null");
            model.requestRouteAPI(tour);
        }
        else {
            System.out.println("static map was already in update for RouteTourController");
            tour.getStaticMap().thenApply(
                    futureMap -> {
                        BufferedImage mapBufferedImage = futureMap;
                        Image mapImage = SwingFXUtils.toFXImage(mapBufferedImage, null);
                        this.apiMapImageView.setImage(mapImage);
                        return null;
                    }
            );
        }


        /*model.getCompletableFutureApiMap().thenApply(
                futureApiMap -> {
                    BufferedImage mapBufferedImage = futureApiMap.getBufferedImageMap();
                    Image mapImage = SwingFXUtils.toFXImage(mapBufferedImage, null);
                    // Source: https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
                    this.apiMapImageView.setImage(mapImage);
                    return null;
                }
        );*/

    }
}
