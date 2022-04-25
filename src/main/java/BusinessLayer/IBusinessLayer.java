package BusinessLayer;

import Models.Tour;

import java.util.List;

public interface IBusinessLayer {
    void addTour(Tour tourName);

    List<Tour> getAllTours();
}
