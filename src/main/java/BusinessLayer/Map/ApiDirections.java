package BusinessLayer.Map;


import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

@Data
public class ApiDirections {
    float distance;
    String formattedTime;
    Object boundingBox;
    String sessionId;
    Object routeError;
    String legs;
    Object messages;
    int statuscode;
}
