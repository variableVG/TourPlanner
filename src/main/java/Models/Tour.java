package Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;


@Data
public class Tour {
    private String name;
    private StringProperty description;
    private String origin;
    private String destination;
    private String transportType;
    private double distance;
    private double time;
    private String information;

    private ArrayList<Log> logs;

    public Tour (String name, String description) {
        this.name = name;
        this.description = new SimpleStringProperty(description);
        //this.description = description;
    }



}
