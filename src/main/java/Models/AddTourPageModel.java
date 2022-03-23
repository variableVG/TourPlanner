package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Data;

import java.util.AbstractMap;

@Data
public class AddTourPageModel {

    /*private StringProperty tourNameInput = new SimpleStringProperty();
    private StringProperty tourNameOutput = new SimpleStringProperty();

    public StringProperty getTourNameInputProperty(){
        return this.tourNameInput;
    }

    public StringProperty getTourNameOutputProperty(){
        return this.tourNameOutput;
    }*/

    private TourPlannerModel tourPlannerModel = TourPlannerModel.getInstance();
    private StringProperty tourName = new SimpleStringProperty();
    private StringProperty origin = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();
    private StringProperty transportType = new SimpleStringProperty();
    private StringProperty distance = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    public void addTour(){
        System.out.println("You have come to add Tour in the model and the name of the tour is: " + tourName);

        //TODO get all properties of the tour and create a Tour

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

        tourPlannerModel.addTour(newTour);


    }

}
