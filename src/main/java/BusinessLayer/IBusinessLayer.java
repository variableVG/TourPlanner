package BusinessLayer;

import Models.Log;
import Models.Tour;

import java.util.List;

public interface IBusinessLayer {
    void addTour(Tour tourName);

    List<Tour> getAllTours();

    void deleteTour(String tourName);

    Tour getTourByName(String tourName);
    Tour getTourById(int id);//**

    void updateTour(Tour tour);

    void addLog(Tour tour, Log log);

    List<Log> getLogs(int tourId);
}
