package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TourPlannerModel {

    ////////////////////////////////////////////////
    // In this part we should connect with the DB
    public ArrayList<Tour> tours = new ArrayList<>();

    public TourPlannerModel () {
        tours.add(new Tour("Tour 1"));
        tours.add(new Tour("Tour 2"));
        tours.add(new Tour("Tour 3"));
        tours.add(new Tour("Tour 4"));

    }

    public ArrayList<Tour> getTours() {
        return tours;
    }

    public ObservableList<String> getTourNames() {
        ObservableList<String> tourNames =
                FXCollections.observableArrayList();

        for(Tour tour : tours) {
            tourNames.add(tour.getName());
        }
        return tourNames;
    }


}
