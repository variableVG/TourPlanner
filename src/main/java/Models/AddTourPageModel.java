package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
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

    public void addTour(){
        Tour newTour = new Tour(
                tourName.getValue(),
                description.getValue(),
                origin.getValue(),
                destination.getValue(),
                transportType.getValue(),
                distance.getValue(),
                time.getValue()
                //popularity.getValue()
                //childFriendliness.getValue()
        );
        // tourPlannerModel.addTour(newTour);
        businessLayer.addTour(newTour);
        TourPlannerModel.getInstance().addTour(newTour);
    }

}
