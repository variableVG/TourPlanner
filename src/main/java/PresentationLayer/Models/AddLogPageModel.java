package PresentationLayer.Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import PresentationLayer.Controllers.DescriptionTourTabController;
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


    public Log addLog() {
        Log log = null;
        if(validateFields()) {

            //Validate and set the time
            LocalTime timeLog = null;
            if(this.time.getValue() != null) {
                timeLog = validateTime();
                if (timeLog == null) {
                    return null;
                }
            }

            //validate totalTime
            if(this.totalTime.getValue() != null) {
                if(!validateTotalTime()) {
                    this.info.set("totalTime must have the format HH:MM");
                    return null;
                }
            }


            log = new Log(-1, date.getValue(), timeLog , this.comment.getValue(),
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
            tour.setPopularityFromNumberOfLogs();
            //TourPlannerModel.getInstance().updateTour(tour);
        }

        return log;

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

    private boolean validateTotalTime(){
        String totalTimeString = this.totalTime.getValue();
        if(totalTimeString != null) {
            //Total time should have the format HH:MM
            String time = this.totalTime.getValue();
            String[] arrOfStr = time.split(":");
            int hours = Integer.parseInt(arrOfStr[0]);
            int minutes = Integer.parseInt(arrOfStr[1]);
            if(hours >= 0 & (minutes >= 0 & minutes < 60)) {
                return true;
            }
        }
        return false;
    }





}
