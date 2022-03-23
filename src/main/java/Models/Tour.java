package Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;


@Data
public class Tour {
    private StringProperty name;
    private StringProperty description;
    private StringProperty origin;
    private StringProperty destination;
    private StringProperty transportType;
    private StringProperty distance;
    private StringProperty time;
    //private StringProperty information;
    private StringProperty popularity;
    private StringProperty childFriendliness;

    private ArrayList<Log> logs;

    public Tour (String name) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty("some text...");
        this.origin = new SimpleStringProperty("");
        this.destination = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.distance = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.popularity = new SimpleStringProperty("");
        this.childFriendliness = new SimpleStringProperty("");


    }

}
