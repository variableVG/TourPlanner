package Models;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TourPlannerModel {

    ////////////////////////////////////////////////
    // In this part we should connect with the DB
    public ObservableList<String> tourNames =
            FXCollections.observableArrayList();

    public TourPlannerModel () {
        tourNames.add("Tour 1");
        tourNames.add("Tour 2");
        tourNames.add("Tour 3");
        tourNames.add("Tour 4");

    }

    public ObservableList<String> getTourNames() {
        return tourNames;
    }
}
