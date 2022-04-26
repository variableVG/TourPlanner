package Map;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Map {
    // https://github.com/hoereth/google-static-map-creator
    // Username: TourPlanner_3
    // Application: TourPlanner
    // Cosumer Key: qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06
    // Consumer Secret: hAY2hNZvov7m7XYW

    static private String consumerKey = "qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06";
    static private String apiBasicMapRequest = "https://www.mapquestapi.com/staticmap/v5/map?key="+ consumerKey + "&center=Boston,MA&size=600,400@2x";

    public static final String HTTPS_API_MAP = apiBasicMapRequest;

    public static void getMap() throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        CompletableFuture<Map> yieldFact = getStaticMap();
        System.out.println("Waiting for fact");
        while(!yieldFact.isDone()) {
            System.out.print(".");
            Thread.sleep(250);
        }
        System.out.println("Fact received :" + yieldFact.get().getValue());
    }

    private static CompletableFuture<Map> getStaticMap() throws URISyntaxException {
        String url = HTTPS_API_MAP;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(
                        stringHttpResponse -> {
                            try {
                                simulateLatency();
                                return parseResponse(stringHttpResponse.body());
                            } catch (JsonProcessingException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
    }
}
