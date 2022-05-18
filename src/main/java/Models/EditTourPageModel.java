package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import lombok.Data;

@Data
public class EditTourPageModel {

    Tour tour;
    IBusinessLayer business;

    private StringProperty tourName;
    private StringProperty origin;
    private StringProperty destination;
    private StringProperty transportType;
    private StringProperty distance;
    private StringProperty time;
    private StringProperty description;

    public EditTourPageModel(String tourName) {
        business = new BusinessLayer();
        tour = business.getTourByName(tourName);
        this.tourName = new SimpleStringProperty(tour.getName());
        origin = new SimpleStringProperty(tour.getOrigin());
        destination = new SimpleStringProperty(tour.getDestination());
        transportType = new SimpleStringProperty(tour.getTransportType());
        distance = new SimpleStringProperty(tour.getDistance());
        time = new SimpleStringProperty(tour.getTime());
        description = new SimpleStringProperty(tour.getDescription());

        //**tour = business.getTourById(id);
    }

    public boolean updateTour() {

        if(validateFields()) {

            //set new Values for the tour
            this.tour.setName(tourName.getValue());
            this.tour.setOrigin(origin.getValue());
            this.tour.setDestination(destination.getValue());
            this.tour.setTransportType(transportType.getValue());
            this.tour.setDistance(distance.getValue());
            this.tour.setTime(time.getValue());
            this.tour.setDescription(description.getValue());

            //Update tour in the database
            business.updateTour(this.tour);
            TourPlannerModel.getInstance().updateTour(tour);
            return true;
        }
        return false;
    }

    private boolean validateFields() {
        /** This function validates that the inputs for "TourName", "Origin" and "Destination" are not left empty
         * */
        if((tourName.getValue() == null)| (origin.getValue() == null )| (destination.getValue() == null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setContentText("The fields Tour Name, Origin and Destination cannot be empty. Please" +
                    "fill those fields. ");
            alert.show();
            return false;
        }
        return true;
    }


}
