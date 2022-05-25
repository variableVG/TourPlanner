package PresentationLayer.Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.*;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;


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


    public boolean addLog() {
        if(validateFields()) {

            //Validate and set the time
            LocalTime timeLog = null;
            if(this.time.getValue() != null) {
                timeLog = validateTime();
                if (timeLog == null) {
                    return false;
                }
            }

            Log log = new Log(-1, date.getValue(), timeLog , this.comment.getValue(),
                    this.difficulty.getValue(), this.totalTime.getValue(),
                    this.rating.getValue());

            int logId = 0;
            try {
                logId = business.addLog(this.tour, log);
            } catch (Exception e) {
                e.printStackTrace();
                this.info.setValue(e.toString());
            }
            log.setId(logId);
            this.tour.getLogs().add(log);
            TourPlannerModel.getInstance().updateTour(tour);
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
            & this.difficulty.getValue() == 0 & this.rating.getValue() == 0 & this.totalTime.getValue() == null) {
            message = "All the fields are empty. At least one field must contain information.";
            hasPassedValidation = false;
        }

        if(!hasPassedValidation) {
            this.info.set(message);
        }
        return hasPassedValidation;
    }

    private LocalTime validateTime() {
        LocalTime time = null;

        //If time has been entered, control that it has a valid format.
        try {
            time = LocalTime.parse(this.time.getValue());
        } catch (Exception e) {
            this.info.set(e.getMessage());
        }

        return time;
    }





}
