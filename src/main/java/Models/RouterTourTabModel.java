package Models;

import Map.ApiMap;
import Map.MapRequest;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.embed.swing.SwingFXUtils;



@Data
public class RouterTourTabModel {

    MapRequest mapRequest;

    private Property<Image> apiMap;
    private BufferedImage mapBufferedImage;
    private Image mapImage;
    private ApiMap futureApiMap;

    public RouterTourTabModel() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        mapRequest = new MapRequest();
        futureApiMap =  mapRequest.getMap();
        mapBufferedImage = futureApiMap.getMapImage();
        mapImage = SwingFXUtils.toFXImage(mapBufferedImage, null);
         // Source: https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image

        apiMap.setValue(mapImage);

    }
}
