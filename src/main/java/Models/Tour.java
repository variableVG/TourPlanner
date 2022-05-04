package Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;


@Data
public class Tour {
    private int id;
    private StringProperty name;
    private StringProperty description;
    private StringProperty origin;
    private StringProperty destination;
    private StringProperty transportType;
    private StringProperty distance;
    private StringProperty time;
    private StringProperty popularity;
    private StringProperty childFriendliness;

    private ArrayList<Log> logs;

    /*public Tour (String name) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty("no tour selected...");//data from database
        this.origin = new SimpleStringProperty("no tour selected...");
        this.destination = new SimpleStringProperty("no tour selected...");
        this.transportType = new SimpleStringProperty("no tour selected...");
        this.distance = new SimpleStringProperty("no tour selected...");
        this.time = new SimpleStringProperty("no tour selected...");
        this.popularity = new SimpleStringProperty("no tour selected...");
        this.childFriendliness = new SimpleStringProperty("no tour selected...");
    }*/

    //used when we create a tour but we don't know the id yet
    public Tour (String name, String description, String origin,
                 String destination, String transportType, String distance,
                 String time) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);//data from database
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.transportType = new SimpleStringProperty(transportType);
        this.distance = new SimpleStringProperty(distance);
        this.time = new SimpleStringProperty(time);
        this.popularity = new SimpleStringProperty("...");
        this.childFriendliness = new SimpleStringProperty("...");
    }

    //used when we know the id (from database)
    public Tour (int id, String name, String description, String origin,
                 String destination, String transportType, String distance,
                 String time) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);//data from database
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.transportType = new SimpleStringProperty(transportType);
        this.distance = new SimpleStringProperty(distance);
        this.time = new SimpleStringProperty(time);
        this.popularity = new SimpleStringProperty("...");
        this.childFriendliness = new SimpleStringProperty("...");
    }
}
