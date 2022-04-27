package Map;
;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.Buffer;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import lombok.Data;

@Data
public class ApiMap {
    // https://github.com/hoereth/google-static-map-creator
    // Username: TourPlanner_3
    // Application: TourPlanner
    // Cosumer Key: qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06
    // Consumer Secret: hAY2hNZvov7m7XYW

    private String value;
    BufferedImage bufferedImageMap;
    InputStream inputStream;

    public ApiMap(BufferedImage map) {

        this.bufferedImageMap = map;
        System.out.println("In the ApiMap constructor");
        System.out.println(map);
    }


}
