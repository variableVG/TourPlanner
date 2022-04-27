package Models;

import Map.ApiMap;
import Map.MapRequest;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;


@Data
public class RouterTourTabModel {

    MapRequest mapRequest;

    private Property<Image> propertyApiMap;

    private CompletableFuture<ApiMap> completableFutureApiMap;
    private Image mapImage;

    public RouterTourTabModel() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        //this.setInitialMapImage();
        mapRequest = new MapRequest();
        completableFutureApiMap =  mapRequest.getMap();
        System.out.println("In the model the completable FutureApiMap is ");
        System.out.println(completableFutureApiMap);

    }

    private void setInitialMapImage() throws IOException {
        //Ge the file to read

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\programming\\swen2\\TourPlanner\\src\\main\\java\\Models\\Blank.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapImage = SwingFXUtils.toFXImage(img, null);

        //propertyApiMap.setValue(mapImage);

    }


}
