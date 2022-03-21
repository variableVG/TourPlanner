package Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import lombok.*;


@Data
public class Tour {
    private String name;
    private String description;
    private String origin;
    private String destination;
    private String transportType;
    private double distance;
    private double time;
    private String information;

    private ArrayList<Log> logs;

    

}
