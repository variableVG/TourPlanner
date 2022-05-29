package PresentationLayer.Models;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.concurrent.CompletableFuture;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private float distance;
    private String time;
    private int popularity;
    private float childFriendliness;
    //private BufferedImage imageMap;

    private boolean isAPIRequested;
    CompletableFuture<BufferedImage> futureImageMap;

    private ObservableList<Log> logs =  FXCollections.observableArrayList();

    //used when we create a tour but we don't know the id yet
    /*public Tour (String name, String description, String origin,
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
    }*/

    //used when we know the id (from database)
    public Tour (int id, String name, String origin, String destination, String description,
                  String transportType, float distance, String time) {
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
        setPopularityFromNumberOfLogs();
        this.childFriendliness = setChildFriendlinessFromOwnData();
    }

    public void setPopularityFromNumberOfLogs() {
        //Popularity can be set from 1 to 5: 1 not popular - 5 very popular
        int numberOfLogs = logs.size();
        if(numberOfLogs > 10) { this.popularity = 5; }
        else if(numberOfLogs > 8) { this.popularity = 4; }
        else if(numberOfLogs > 6) { this.popularity = 3; }
        else if(numberOfLogs > 3) {this.popularity = 2; }
        else {this.popularity = 1; }

    }

    public float setChildFriendlinessFromOwnData() {
        //From difficulty values, total time, distance.
        //Childfriendliness can have a value between 1 and 5: 1 not frienly - 5 very friendly

        //Difficulty score is an average of all difficulties and it should have a value between 0 and 5
        float difficultyScore = 0;
        int counter = 0;
        for(Log log : this.getLogs()) {
            if(log.getDifficulty() != null) {
                difficultyScore += log.getDifficulty();
                counter++;
            }
        }
        difficultyScore = (float) difficultyScore/ counter;

        float totalTimeScore = 0;
        if(this.time != null) {
            System.out.println("Time is " + time);
            String hours = time.substring(0, time.indexOf(":"));
            System.out.println("Hour is " + hours);
            int hoursInt = Integer.parseInt(hours);
            if(hoursInt > 1 & hoursInt <= 2) {
                totalTimeScore = 1;
            }
            else if(hoursInt <= 3) {
                totalTimeScore = 2;
            }
            else if(hoursInt <= 4) {
                totalTimeScore = 3;
            }
            else if(hoursInt <= 5) {
                totalTimeScore = 4;
            }
            else {
                totalTimeScore = 5;
            }
        }


        //set DistanceScore
        int distanceScore = 0;
        if(distance >= 1 & distance < 2) {
            distanceScore = 1;
        }
        else if(distance < 4) {
            distanceScore = 2;
        }
        else if(distance < 8) {
            distanceScore = 3;
        }
        else if(distance < 12) {
            distanceScore = 4;
        }
        else {
            distanceScore = 5;
        }


        float totalScore = (distanceScore + totalTimeScore + difficultyScore) / 3;

        return totalScore;
    }

    public void getLogsFromDb() {
        for (Log l : business.getLogs(this.id)) {
            logs.add(l);
        }
    }

    public void setFutureImageMap(CompletableFuture<BufferedImage> m) {
        this.futureImageMap = m;
        System.out.println("m in Tour setter is ");
        System.out.println(m);
    }

    public boolean getIsAPIrequested() {
        return this.isAPIRequested;
    }

    public void setIsAPIrequested(boolean b) {
        this.isAPIRequested =b;
    }

}
