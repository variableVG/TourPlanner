package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TourPlannerModel {

    ////////////////////////////////////////////////
    // In this part we should connect with the DB
    public ArrayList<Tour> tours = new ArrayList<>();

    public TourPlannerModel () {
        tours.add(new Tour("Tour 1", "abc"));
        tours.add(new Tour("Tour 2", "This is description for Tour 2"));
        tours.add(new Tour("Tour 3", "tour for Rawan"));
        tours.add(new Tour("Tour 4", "Tour for Manuel"));

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

    public Tour getTourByName(String name) {
        Tour tour = null;

        for (Tour t : tours) {
            if(t.getName().equals(name)) {
                tour = t;
            }
        }
        return tour;
    }




}
