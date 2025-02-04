package BusinessLayer.Map;

import BusinessLayer.Logger.ILoggerWrapper;
import BusinessLayer.Logger.LoggerFactory;
import PresentationLayer.Models.Tour;
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
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;


public class MapRequest {
    //static private String consumerKey = "qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06";
    static private String consumerKey = loadMapQuestConfiguration();
    static private String apiBasicMapRequest = "https://www.mapquestapi.com/staticmap/v5/map?key="+ consumerKey + "&center=Boston,MA&size=600,400@2x";

    public static final String HTTPS_API_MAP = apiBasicMapRequest;

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    static private String loadMapQuestConfiguration(){
        try {
            Properties appProperties = new Properties();
            appProperties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("app.properties"));

            return appProperties.getProperty("mapquestkey");
        } catch (IOException e) {
            logger.error("Class MapRequest, loadMapQuestConfiguration() - " + e);
            //e.printStackTrace();
        }
        return null;
    }

    public CompletableFuture<ApiDirections> getMapDirections(Tour tour) {
        CompletableFuture<ApiDirections> mapDirections = new CompletableFuture<ApiDirections>();
        if(tour != null) {
            String request = "http://www.mapquestapi.com/directions/v2/route?key="
                    + consumerKey
                    + "&from="
                    + tour.getOrigin()
                    + "&to="
                    + tour.getDestination()
                    + "&routeType="
                    + tour.getTransportType()
                    + "&unit=k"; // With this parameter get get back the distance in km (default is miles m)
            mapDirections = sendDirectionsRequest(request);
        }
        else {
            logger.warn("Class MapRequest, getMapDirections() - Map could not be requested because tour is null.");
            //If a tour is not set, a static picture should be sent back.
        }
        return mapDirections;
    }

    private CompletableFuture<ApiDirections> sendDirectionsRequest(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest;
        try {
            logger.debug("Class MapRequest, sendDirectionsRequest() - Send request for map.");
            httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        }
        catch(Exception e) {
            logger.warn("Class MapRequest, sendDirectionsRequest() - " + e);
            return null;
        }
        //send request
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(
                        stringHttpResponse -> {
                            logger.debug("Class MapRequest, sendDirectionsRequest() - Http response received: " + stringHttpResponse.body());
                            System.out.println("Directions response is ");
                            System.out.println(stringHttpResponse.body());
                            try {
                                return parseResponseJSON(stringHttpResponse.body());

                            } catch (IOException e) {
                                logger.warn("Class MapRequest, sendDirectionsRequest() - " + e);
                                //e.printStackTrace();
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
                if(beanOrClass.getClass().equals(ApiDirections.class)) {
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
                "formattedTime: " + o2.get("formattedTime").toString() +
                "}";
        System.out.println("The answer should be " + jsonString);

        // objectMapper.readValue(jsonString, ApiMap.class);
        //I did not manage to make Object Mapper work, so I just created the class like this:
        ApiDirections apidirections = new ApiDirections();
        try {
            apidirections.setDistance(Float.parseFloat(o2.get("distance").toString()));
            apidirections.setBoundingBox(o2.get("boundingBox"));
            apidirections.setRouteError(o2.get("routeError"));
            apidirections.setSessionId(o2.get("sessionId").toString());
            apidirections.setLegs(o2.get("legs").toString());
            apidirections.setMessages(o4.get("messages"));
            apidirections.setStatuscode(Integer.parseInt(o4.get("statuscode").toString()));
            apidirections.setFormattedTime(o2.get("formattedTime").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return apidirections;
    }

    public CompletableFuture<BufferedImage> getStaticMap(ApiDirections apiMap) throws URISyntaxException {
        logger.debug("Class MapRequest, getStaticMap() - Static Map requested for SessionId: " + apiMap.getSessionId());

        //Prepare string for request
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
                            logger.debug("Class MapRequest, getStaticMap() - Response received for SessionId: " + apiMap.getSessionId());
                            logger.debug("Class MapRequest, getStaticMap() - Response: " + stringHttpResponse.toString());
                            try {
                                return parseResponseImage(stringHttpResponse.body());
                            } catch (IOException e) {
                                logger.warn("Class MapRequest, getStaticMap() - " + e);
                                //e.printStackTrace();
                            }
                            return null;
                        });

    }

    private BufferedImage parseResponseImage(byte[] httpBodyResponse) throws IOException {
        // I convert the response in a image. To do so, the read() function just take a file or Stream, so
        // I have to convert first the httpBodyResponse in a stream.
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpBodyResponse);
        BufferedImage map = ImageIO.read(inputStream);
        return  map;
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
