package PresentationLayer.Models;

import BusinessLayer.BusinessLayer;
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
    public ObservableList<VBox> logs;


    public LogTourTabModel(Tour tour){
        this.tour = tour;
        business = new BusinessLayer();
        logs = FXCollections.observableArrayList();

    }


    public void updateLogs(Log log) {
        System.out.println("Update in LogTourTabModel with log " + log.getId());
        System.out.println("Log size is " + logs.size());
        for(int j = 0; j < logs.size(); j++) {
            System.out.println("Inside log loop");
            if(logs.get(j).getId().equals(String.valueOf(log.getId()))) {
                System.out.println("Inside if condition");
                //HBox for Date and Time
                System.out.println("Print HBOX?");
                System.out.println(logs.get(j).getChildren().get(1));

            }
        }
    }


    public void deleteLog(int logId) {
        try {
            business.deleteLog(logId);
            //delete Log in the frontend
            TourPlannerModel.getInstance().deleteLog(tour, logId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
