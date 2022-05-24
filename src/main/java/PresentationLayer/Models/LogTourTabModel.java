package PresentationLayer.Models;

import BusinessLayer.IBusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

@Data
public class LogTourTabModel {
    Tour tour;
    IBusinessLayer business;

    private ObservableList<Log> logs =
            FXCollections.observableArrayList();

    public LogTourTabModel(Tour tour){
        this.tour = tour;
    }


    public void updateLogs() {
        this.logs.clear();
        for (Log l : tour.getLogs()) {
            logs.add(l);
        }
    }
}
