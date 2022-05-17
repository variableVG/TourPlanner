package Map;


import lombok.Data;

@Data
public class ApiDirections {
    float distance;
    Object boundingBox;
    String sessionId;
    Object routeError;
    String legs;
    Object messages;
    int statuscode;
}
