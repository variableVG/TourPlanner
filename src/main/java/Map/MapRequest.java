package Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.scene.effect.ImageInput;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MapRequest {
    static private String consumerKey = "qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06";
    static private String apiBasicMapRequest = "https://www.mapquestapi.com/staticmap/v5/map?key="+ consumerKey + "&center=Boston,MA&size=600,400@2x";

    public static final String HTTPS_API_MAP = apiBasicMapRequest;

    public ApiMap getMap() throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        CompletableFuture<ApiMap> yieldFact = getStaticMap();
        System.out.println("Waiting for picture");
        while(!yieldFact.isDone()) {
            System.out.print(".");
            Thread.sleep(250);
        }
        System.out.println("picture received");
        return yieldFact.get();
    }

    private static CompletableFuture<ApiMap> getStaticMap() throws URISyntaxException {
        String url = HTTPS_API_MAP;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(
                        stringHttpResponse -> {
                            try {
                                //Check if JSON run parseResponse, other wise jpeg
                                System.out.println("Headers are ");
                                System.out.println(stringHttpResponse.headers());
                                List<String> contentType = stringHttpResponse.headers().map().get("content-type");
                                for(String s : contentType) {
                                    if(s.equals("image/jpeg")) {
                                        return parseResponseImage(stringHttpResponse.body());
                                    }
                                    else {
                                        return parseResponseJSON(stringHttpResponse.body());
                                    }
                                }

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
    }

    private static ApiMap parseResponseImage(String httpBodyResponse) {
        try {
            // I convert the response in a image. To do so, the read() function just take a file or Stream, so
            // I have to convert first the httpBodyResponse in a stream.
            InputStream inputStream = new ByteArrayInputStream(httpBodyResponse.getBytes());
            BufferedImage map = ImageIO.read(inputStream);
            return new ApiMap(map);


        } catch (IOException e) {
        }
        return null;
    }

    private static ApiMap parseResponseJSON(String toParse) throws JsonProcessingException {
        ApiMap response;
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
        System.out.println(objectMapper.readValue(toParse, ApiMap.class));
        return null;
        //return objectMapper.readValue(toParse, ApiMap.class);
    }
}
