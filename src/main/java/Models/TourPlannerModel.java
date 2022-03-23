package Models;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TourPlannerModel {

    // I have converted the TourPlannerModel in a single ton class, since by now we just want to keep one instance of
    // the tour planner model.

    private static TourPlannerModel tourPlannerModel = null;

    ////////////////////////////////////////////////
    // In this part we should connect with the DB
    ObservableList<Tour> tours =
            FXCollections.observableArrayList();
    ObservableList<String> tourNames =
            FXCollections.observableArrayList();

    private  TourPlannerModel () {
        // The constructor is private, to prevent other objects from using the new operator within the Singleton.
        //Check if Instance already exists.
        if(tourPlannerModel != null) {
            throw new RuntimeException("Cannot create. Please use getInstance8)\n");
        }

        tours.add(new Tour("Tour 1"));
        tours.add(new Tour("Tour 2"));
        tours.add(new Tour("Tour 3"));
        tours.add(new Tour("Tour 4"));

    }

    public static synchronized TourPlannerModel getInstance() {
        /** When I need the class I access to it through getInstance. With the lazy
         * method it delays the creation of an object until the first time is needed.
         * For that I check if the singleton Instance has been already created, if not I
         * will create the instance, otherwise I give back the already created instance.
         *
         * This implementation can create problems with threads.
         * Each thread might create its own instance, one per thread. That is not the purpose
         * of using Singletons. To avoid that I used the keyword "synchronized", like that if
         * a thread enters the getInstance() function, the rest of the threads would have to wait
         * if they want to run the function.
         * */
        if(tourPlannerModel == null) {
            tourPlannerModel = new TourPlannerModel();
        }
        return tourPlannerModel;
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public ObservableList<String> getTourNames() {

        for(Tour tour : tours) {
            tourNames.add(tour.getName().getValue());
        }
        return tourNames;
    }

    // I think this 2 functions should be in the logic ...

    public Tour getTourByName(String name) {
        Tour tour = null;

        for (Tour t : tours) {
            if(t.getName().getValue().equals(name)) {
                tour = t;
            }
        }
        return tour;
    }

    public void addTour(Tour tour) {
        tours.add(tour);
        tourNames.add(tour.getName().getValue());

        //check it has been added:
        for (Tour t : tours) {
            System.out.println(t.getName());
        }
    }




}
