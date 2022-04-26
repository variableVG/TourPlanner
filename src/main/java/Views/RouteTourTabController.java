package Views;

import Models.RouterTourTabModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class RouteTourTabController {

    RouterTourTabModel model;

    public RouteTourTabController() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        model = new RouterTourTabModel();
    }

}
