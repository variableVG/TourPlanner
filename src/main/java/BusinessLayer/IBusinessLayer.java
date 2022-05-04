package BusinessLayer;

import Models.Tour;

import java.util.List;

public interface IBusinessLayer {
    void addTour(Tour tourName);

    List<Tour> getAllTours();

    void deleteTour(String tourName);

    Tour getTourByName(String tourName);
    Tour getTourById(int id);//**

    void updateTour(Tour tour);
}
