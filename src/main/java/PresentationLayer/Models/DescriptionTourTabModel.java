package PresentationLayer.Models;

import javafx.beans.property.*;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DescriptionTourTabModel {

    Tour tour;
    private StringProperty tourName;
    private StringProperty origin;
    private StringProperty destination;
    private StringProperty transportType;
    private StringProperty distance;
    private StringProperty time;
    private StringProperty description;
    private StringProperty popularity;

    public DescriptionTourTabModel() {
        tourName = new SimpleStringProperty("Tour Name");
        origin = new SimpleStringProperty("Origin");
        destination = new SimpleStringProperty("Destination");
        transportType = new SimpleStringProperty("Transport Type");
        distance = new SimpleStringProperty("distance");
        time = new SimpleStringProperty("Time");
        description = new SimpleStringProperty("description");
        popularity = new SimpleStringProperty("♡♡♡♡♡");
    }

    public void updateTour(Tour tour) {
        this.tour = tour;
        this.tourName.setValue(tour.getName());
        this.origin.setValue(tour.getOrigin());
        this.destination.setValue(tour.getDestination());
        this.transportType.setValue(tour.getTransportType());
        String distance = String.format("%.2f", tour.getDistance());
        this.distance.setValue(distance + " km");
        this.time.setValue(tour.getTime() + " hours");
        this.description.setValue(tour.getDescription());
        //this.popularity.setValue(tour.getPopularity());

        //Set popularity:
        if(tour.getPopularity() == 5) {
            this.popularity.setValue("♥♥♥♥♥");
        }
        else if(tour.getPopularity() == 4) {
            this.popularity.setValue("♥♥♥♥♡");
        }
        else if(tour.getPopularity() == 3) {
            this.popularity.setValue("♥♥♥♡♡");
        }
        else if(tour.getPopularity() == 2) {
            this.popularity.setValue("♥♥♡♡♡");
        } else {
            this.popularity.setValue("♥♡♡♡♡");
        }


    }


}
