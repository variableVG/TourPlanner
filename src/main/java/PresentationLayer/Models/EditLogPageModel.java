package PresentationLayer.Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractMap;

@Data
public class EditLogPageModel {

    private IntegerProperty id;
    private Property<LocalDate> date;
    private StringProperty time;
    private StringProperty comment;
    private IntegerProperty difficulty ;
    private StringProperty totalTime;
    private IntegerProperty rating;
    private StringProperty info;
    Log log;
    Tour tour;
    IBusinessLayer business = new BusinessLayer();

    public EditLogPageModel(int logId, String tourName) {
        this.tour = business.getTourByName(tourName);
        this.log = business.getLogById(logId);
        this.id = new SimpleIntegerProperty(log.getId());
        this.date = new SimpleObjectProperty<>(log.getDate());
        if(null == log.getTime()) {
            this.time = new SimpleStringProperty();
        } else {
            this.time = new SimpleStringProperty(log.getTime().toString());
        }
        this.comment = new SimpleStringProperty(log.getComment());
        this.difficulty = new SimpleIntegerProperty(log.getDifficulty());
        this.totalTime = new SimpleStringProperty(log.getTotaltime());
        this.rating = new SimpleIntegerProperty(log.getRating());
        this.info = new SimpleStringProperty();

    }

    public boolean editLog() {
        this.log.setDate(this.date.getValue());
        this.log.setComment(this.comment.getValue());
        this.log.setDifficulty(this.difficulty.getValue());
        this.log.setTotaltime(this.totalTime.getValue());
        this.log.setRating(this.rating.getValue());

        //Validate and set the time
        LocalTime timeLog = null;
        if(this.time.getValue() != null) {
            timeLog = validateTime();
            if (timeLog == null) {
                return false;
            }
        }
        this.log.setTime(timeLog);

        if(validateFields()) {
            try {
                return business.updateLog(log, tour.getId());
            } catch (Exception e) {
                e.printStackTrace();
                this.info.setValue(e.toString());
            }
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
