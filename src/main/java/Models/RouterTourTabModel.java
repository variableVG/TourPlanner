package Models;

import Map.ApiMap;
import Map.MapRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class RouterTourTabModel {

    MapRequest map;

    public RouterTourTabModel() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        map = new MapRequest();
        map.getMap();
    }
}
