package PresentationLayer.Models;

import BusinessLayer.IBusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

@Data
public class LogTourTabModel {
    Tour tour;
    IBusinessLayer business;

    private ObservableList<Tour> logs =
            FXCollections.observableArrayList();

    public LogTourTabModel(Tour tour){
        this.tour = tour;
    }


}
