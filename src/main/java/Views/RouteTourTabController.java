package Views;

import Map.ApiMap;
import Models.RouterTourTabModel;
import Models.Tour;
import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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

        // I could not bring bindBiderectional to work


    }

    public void update(Tour tour)  {
        this.tour = tour;
        if(tour == null) {
            System.out.println("please select a Tour");
            return;
        }
        if(!tour.getIsAPIrequested() && tour.getStaticMap() == null) {
            System.out.println("I am in update - RouteTourController, getStaticMap() is null");
            model.requestRouteAPI(tour);
        }
        else {
            tour.getStaticMap().thenApply(
                    futureMap -> {
                        BufferedImage mapBufferedImage = futureMap;
                        Image mapImage = SwingFXUtils.toFXImage(mapBufferedImage, null);
                        this.apiMapImageView.setImage(mapImage);
                        return null;
                    });
        }

    }




}
