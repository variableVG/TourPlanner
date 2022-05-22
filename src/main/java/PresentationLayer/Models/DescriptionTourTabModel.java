package PresentationLayer.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

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

    public DescriptionTourTabModel() {
        tourName = new SimpleStringProperty("Tour Name");
        origin = new SimpleStringProperty("Origin");
        destination = new SimpleStringProperty("Destination");
        transportType = new SimpleStringProperty("Transport Type");
        distance = new SimpleStringProperty("Distance");
        time = new SimpleStringProperty("Time");
        description = new SimpleStringProperty("description");
    }

    public void updateTour(Tour tour) {
        this.tour = tour;
        this.tourName.setValue(tour.getName());
        this.origin.setValue(tour.getOrigin());
        this.destination.setValue(tour.getDestination());
        this.transportType.setValue(tour.getTransportType());
        this.distance.setValue(tour.getDistance());
        this.time.setValue(tour.getTime());
        this.description.setValue(tour.getDescription());
    }
}
