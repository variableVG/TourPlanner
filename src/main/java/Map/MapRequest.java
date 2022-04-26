package Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MapRequest {
    static private String consumerKey = "qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06";
    static private String apiBasicMapRequest = "https://www.mapquestapi.com/staticmap/v5/map?key="+ consumerKey + "&center=Boston,MA&size=600,400@2x";

    public static final String HTTPS_API_MAP = apiBasicMapRequest;

    public void getMap() throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        CompletableFuture<ApiMap> yieldFact = getStaticMap();
        System.out.println("Waiting for fact");
        while(!yieldFact.isDone()) {
            System.out.print(".");
            Thread.sleep(250);
        }

    }

    private static CompletableFuture<ApiMap> getStaticMap() throws URISyntaxException {
        String url = HTTPS_API_MAP;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(
                        stringHttpResponse -> {
                            try {
                                return parseResponse(stringHttpResponse.body());
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
    }

    private static ApiMap parseResponse(String toParse) throws JsonProcessingException {
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
        System.out.println("hello?");
        System.out.println("toParse is ");
        System.out.println(toParse);
        System.out.println(objectMapper.readValue(toParse, ApiMap.class));
        return null;
        //return objectMapper.readValue(toParse, ApiMap.class);
    }
}
