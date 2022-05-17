package Models;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.*;


@Data
public class Tour {
    private IBusinessLayer business = new BusinessLayer();
    private int id;
    private String name;
    private String description;
    private String origin;
    private String destination;
    private String transportType;
    private String distance;
    private String time;
    private String popularity;
    private String childFriendliness;
    private Image imageMap;
    //private CompletableFuture<BufferedImage> futureMap;

    private boolean isAPIRequested;

    private ObservableList<Log> logs =  FXCollections.observableArrayList();

    //used when we create a tour but we don't know the id yet
    public Tour (String name, String description, String origin,
                 String destination, String transportType, String distance,
                 String time) {
        this.name = name;
        this.description = description;//data from database
        this.origin = origin;
        this.destination = destination;
        this.transportType = transportType;
        this.distance = distance;
        this.time = time;
        this.isAPIRequested = false;
    }

    //used when we know the id (from database)
    public Tour (int id, String name, String description, String origin,
                 String destination, String transportType, String distance,
                 String time) {
        this.id = id;
        this.name = name;
        this.description = description;//data from database
        this.origin = origin;
        this.destination = destination;
        this.transportType = transportType;
        this.distance = distance;
        this.time = time;
        this.isAPIRequested = false;
        getLogsFromDb();
    }

    public void getLogsFromDb() {
        for (Log l : business.getLogs(this.id)) {
            logs.add(l);
        }
    }


    public boolean getIsAPIrequested() {
        return this.isAPIRequested;
    }

    public void setIsAPIrequested(boolean b) {
        this.isAPIRequested =b;
    }


}
