package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Data;

import java.util.AbstractMap;

@Data
public class AddTourPageModel {

    //private TourPlannerModel tourPlannerModel = TourPlannerModel.getInstance();
    private IBusinessLayer businessLayer = new BusinessLayer();
    private StringProperty tourName = new SimpleStringProperty();
    private StringProperty origin = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();
    private StringProperty transportType = new SimpleStringProperty();
    private StringProperty distance = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    public boolean addTour(){
        if(validateFields()) {
            Tour newTour = new Tour(
                    tourName.getValue(),
                    description.getValue(),
                    origin.getValue(),
                    destination.getValue(),
                    transportType.getValue(),
                    distance.getValue(),
                    time.getValue()
            );
            //TODO: Check if a tour with the same name already exists
            businessLayer.addTour(newTour);
            TourPlannerModel.getInstance().addTour(newTour);
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
