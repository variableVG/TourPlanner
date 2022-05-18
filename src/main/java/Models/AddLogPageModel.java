package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.*;
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
    private IntegerProperty difficulty = new SimpleIntegerProperty();
    private StringProperty totalTime = new SimpleStringProperty();
    private IntegerProperty rating = new SimpleIntegerProperty();
    private StringProperty info = new SimpleStringProperty();

    public AddLogPageModel(Tour tour) {
        this.tour = tour;
    }


    public boolean addLog() throws Exception {
        if(validateFields()) {
            LocalTime timeLog = validateTime();

            Log log = new Log(-1, date.getValue(), timeLog , this.comment.getValue(),
                    this.difficulty.getValue(), this.totalTime.getValue(),
                    this.rating.getValue());

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


        if(!hasPassedValidation) {
            this.info.set(message);
            return false;
        }
        return true;
    }

    private LocalTime validateTime() {
        LocalTime time = null;
        //Check if time is null, if it is null it must not be validated, since the time is not strictily required.
        if(this.time.getValue() == null){
            return null;
        }

        //If time has been entered, control that it has a valid format.
        try {
            time = LocalTime.parse(this.time.getValue());
        } catch (Exception e) {
            this.info.set(e.getMessage());
        }

        return time;
    }





}
