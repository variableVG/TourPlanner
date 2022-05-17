package Map;

import Models.Tour;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONObject;


public class MapRequest {
    static private String consumerKey = "qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06";
    static private String apiBasicMapRequest = "https://www.mapquestapi.com/staticmap/v5/map?key="+ consumerKey + "&center=Boston,MA&size=600,400@2x";

    public static final String HTTPS_API_MAP = apiBasicMapRequest;

    public CompletableFuture<ApiDirections> getMapDirections(Tour tour) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        CompletableFuture<ApiDirections> mapDirections = null;
        if(tour != null) {
            String request = "http://www.mapquestapi.com/directions/v2/route?key="
                    + consumerKey
                    + "&from="
                    + tour.getOrigin()
                    + "&to="
                    + tour.getDestination();
            mapDirections = getDirectionsAPIMap(request);
        }
        else {
            System.out.println("Tour has not been selected. Please select a tour");
            //If a tour is not set, a static picture will be sent back.
        }

        return mapDirections;

    }

    private CompletableFuture<ApiDirections> getDirectionsAPIMap(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest;
        try {
            httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        }
        catch(Exception e) {
            System.out.println("Exception has occured in getDirectionsAPIMap");
            System.out.println(e);
            return null;
        }
        //send request
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(
                        stringHttpResponse -> {
                            System.out.println("Directions response is ");
                            System.out.println(stringHttpResponse.body());
                            try {
                                return parseResponseJSON(stringHttpResponse.body());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });

    }

    private ApiDirections parseResponseJSON(String toParse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(
                    DeserializationContext ctxt,
                    JsonParser p,
                    JsonDeserializer<?> deserializer,
                    Object beanOrClass,
                    String propertyName) throws IOException {
                if(beanOrClass.getClass().equals(ApiMap.class)) {
                    p.skipChildren();
                    System.out.println("it returns true");
                    return true;
                } else {
                    System.out.println("It returns false");
                    return false;
                }

            }
        });

        JSONObject o = new JSONObject(toParse);
        JSONObject o2 = new JSONObject(o.get("route").toString());
        JSONObject o3 = new JSONObject(o2.get("boundingBox").toString());
        JSONObject o4 = new JSONObject(o.get("info").toString());

        String jsonString = "{ " +
                "boundingBox : " + o2.get("boundingBox") + ", " +
                "distance : " + o2.get("distance") + ", " +
                "routeError : " + o2.get("routeError") + ", " +
                "sessionId : " + o2.get("sessionId") + ", " +
                "legs : " + o2.get("legs") + " , " +
                "}";

        // objectMapper.readValue(jsonString, ApiMap.class);
        //I did not manage to make Object Mapper work, so I just created the class like this:
        ApiDirections apidirections = new ApiDirections();
        apidirections.setDistance(Float.parseFloat(o2.get("distance").toString()));
        apidirections.setBoundingBox(o2.get("boundingBox"));
        apidirections.setRouteError(o2.get("routeError"));
        apidirections.setSessionId(o2.get("sessionId").toString());
        apidirections.setLegs(o2.get("legs").toString());
        apidirections.setMessages(o4.get("messages"));
        apidirections.setStatuscode(Integer.parseInt(o4.get("statuscode").toString()));
        return apidirections;
    }

    public CompletableFuture<BufferedImage> getStaticMap(ApiDirections apiMap) throws URISyntaxException {
        String url = "https://www.mapquestapi.com/staticmap/v5/map?key="
                + consumerKey
                + "&session="
                + apiMap.getSessionId() +
                "";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest= HttpRequest.newBuilder().uri(new URI(url)).build();

        //send request
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(
                        stringHttpResponse -> {
                            System.out.println("Response for map is ");
                            System.out.println(stringHttpResponse.body().toString());
                            try {
                                System.out.printf("I am in getStaticMap() in MapRequest");
                                return parseResponseImage(stringHttpResponse.body());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });

    }

    private BufferedImage parseResponseImage(byte[] httpBodyResponse) throws IOException {
        // I convert the response in a image. To do so, the read() function just take a file or Stream, so
        // I have to convert first the httpBodyResponse in a stream.
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpBodyResponse);
        BufferedImage map = ImageIO.read(inputStream);
        return map;
    }


   /* OLD getStaticMap() function
   private static CompletableFuture<ApiMap> getStaticMap() throws URISyntaxException {
        String url = HTTPS_API_MAP;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(
                        stringHttpResponse -> {
                            try {
                                //Check if JSON run parseResponse, other wise jpeg
                                System.out.println("Headers are ");
                                System.out.println(stringHttpResponse.headers());
                                List<String> contentType = stringHttpResponse.headers().map().get("content-type");
                                for(String s : contentType) {
                                    if(s.equals("image/jpeg")) {
                                        //TODO it is not necessary this part, we should get always the same
                                        return parseResponseImage(stringHttpResponse.body());
                                    }
                                    else {
                                        //return parseResponseJSON(stringHttpResponse.body().toString());
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
    }*/




}
