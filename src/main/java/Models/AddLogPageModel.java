package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import lombok.Data;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;


@Data
public class AddLogPageModel {
    Tour tour;
    private IBusinessLayer business = new BusinessLayer();
    private Property<LocalDate> date = new SimpleObjectProperty<>();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty totalTime = new SimpleStringProperty();
    private StringProperty rating = new SimpleStringProperty();
    private StringProperty info = new SimpleStringProperty();

    public AddLogPageModel(Tour tour) {
        this.tour = tour;
    }


    public boolean addLog() {
        System.out.println("Add log");

        if(validateFields()) {
            LocalTime timeLog = LocalTime.parse(this.time.getValue());
            System.out.println("TIme is " + timeLog.toString());
            Log log = new Log(-1, date.getValue(), this.time.getValue(), this.comment.getValue(),
                    Integer.parseInt(this.difficulty.getValue()), this.totalTime.getValue(),
                    Integer.parseInt(this.rating.getValue()));

            this.tour.getLogs().add(log);
            TourPlannerModel.getInstance().updateTour(tour);
            business.addLog(this.tour, log);
            return true;
        }
        return false;

    }

    private boolean validateFields() {
        /**
         * */
        boolean hasPassedValidation = true;
        String message = "";
        if(this.date.getValue() == null & this.time.getValue() == null & this.comment.getValue() == null
            & this.difficulty.getValue() == null & this.rating.getValue() == null & this.totalTime.getValue() == null) {
            message = "All the fields are empty. At least one field must contain information.";
            hasPassedValidation = false;
        }
        else if(!this.time.getValue().isEmpty()) {
            try {
                LocalTime time = LocalTime.parse(this.time.getValue());
            } catch (Exception e) {
                message = e.getMessage();
            }
            hasPassedValidation = false;
        }

        if(!hasPassedValidation) {
            this.info.set(message);
            return false;
        }
        return true;
    }





}
