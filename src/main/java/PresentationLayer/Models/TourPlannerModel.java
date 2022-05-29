package PresentationLayer.Models;

import BusinessLayer.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TourPlannerModel {

    private static TourPlannerModel tourPlannerModel = null;
    private static Object mutex = new Object();
    private ObservableList<Tour> tours = FXCollections.observableArrayList();
    private ObservableList<String> tourNames = FXCollections.observableArrayList();
    private IBusinessLayer business;

    private  TourPlannerModel () {
        business = new BusinessLayer();
        getAllTours();

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
        if (tourPlannerModel == null) {
            synchronized (mutex) {
                if (tourPlannerModel == null)
                    tourPlannerModel = new TourPlannerModel();
            }
        }
        return tourPlannerModel;
    }

    public void getAllTours(){
        tours.clear();
        tourNames.clear();
        List<Tour> toursDb = business.getAllTours();
        // When starting the application for first time, we want to call all the Maps for each tour
        for(Tour t: toursDb){
            try {
                business.getMap(t);
            } catch (IOException | URISyntaxException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            tours.add(t);
            tourNames.add(t.getName());
        }
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public ObservableList<String> getTourNames() {
       /* MISTAKE was here!
       for(Tour tour : tours) {
            tourNames.add(tour.getName().getValue());
        }*/
        return tourNames;
    }

    // I think this 2 functions should be in the logic ...

    public Tour getTourByName(String name) {
        Tour tour = null;
        for (Tour t : tours) {
            if(t.getName().equals(name)) {
                tour = t;
            }
        }
        return tour;
    }

    public void addTour(Tour tour) {
        tours.add(tour);
        tourNames.add(tour.getName());
    }

    public void deleteTour(String tourName) {
        business.deleteTour(tourName);
        tourNames.remove(tourName);
        tours.remove(getTourByName(tourName));
    }

    public void updateTour(Tour tour) {
        // We have a lot of problems with exceptions here.
        //Solution to exception:
        //https://rollbar.com/blog/java-concurrentmodificationexception/#
        for(int i = 0; i < tours.size(); i++) {
            if(tours.get(i).getId() == tour.getId()) {
                //This approach is creating some Exceptions and problems with the threads (completableFuture)
                tours.remove(tours.get(i));
                tours.add(tour);
            }
        }

    }

    public void updateLogs(Tour tour, Log log) {
        for(int i = 0; i < tours.size(); i++) {
            if (tours.get(i).getId() == tour.getId()) {
                for(int j = 0; j < tours.get(i).getLogs().size(); j++) {
                    if(tours.get(i).getLogs().get(j).getId() == log.getId()) {
                        tours.get(i).getLogs().remove(tours.get(i).getLogs().get(j));
                        tours.get(i).getLogs().add(log);
                    }
                }
            }
        }

    }


    public void deleteLog(Tour tour, int logId) {
        for(int i = 0; i < tours.size(); i++) {
            if (tours.get(i).getId() == tour.getId()) {
                for(int j = 0; j < tours.get(i).getLogs().size(); j++) {
                    if(tours.get(i).getLogs().get(j).getId() == logId) {
                        tours.get(i).getLogs().remove(tours.get(i).getLogs().get(j));
                    }
                }
            }
        }
    }

    public void searchText(String text) {
        List<Tour> foundTours = business.searchText(text);
        tours.clear();
        tourNames.clear();
        for(Tour t: foundTours){
            tours.add(t);
            tourNames.add(t.getName());
            System.out.println(t.getName());
        }

        //If the search returns something, then set the ObservableList tours and toursName with the new values.
    }
}
