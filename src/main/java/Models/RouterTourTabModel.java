package Models;

import Map.ApiMap;
import Map.MapRequest;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Data
public class RouterTourTabModel {

    MapRequest mapRequest;

    private Property<Image> apiMap;

    public RouterTourTabModel() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        mapRequest = new MapRequest();
        CompletableFuture<ApiMap> futureApiMap =  mapRequest.getMap();
        BufferedImage mapImage = futureApiMap.get().getMapImage();
        

    }
}
