package BusinessLayer.Map;


import lombok.Data;

import java.time.LocalTime;

@Data
public class ApiDirections {
    float distance;
    LocalTime formattedTime;
    Object boundingBox;
    String sessionId;
    Object routeError;
    String legs;
    Object messages;
    int statuscode;
}
