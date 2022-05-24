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

    public void editLog() {
        this.log.setDate(this.date.getValue());
        //TODO time
        this.log.setComment(this.comment.getValue());
        this.log.setDifficulty(this.difficulty.getValue());
        //TODO set correct difficulty value
        this.log.setTotaltime(this.totalTime.getValue());
        this.log.setRating(this.rating.getValue());

        business.updateLog(log, tour.getId());

    }



}
