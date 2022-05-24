package PresentationLayer.Models;

import BusinessLayer.IBusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Data;

@Data
public class LogTourTabModel {
    Tour tour;
    IBusinessLayer business;



    public LogTourTabModel(Tour tour){
        this.tour = tour;
    }


    public void updateLogs() {
    }
}
