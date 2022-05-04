package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.Database;
import DataAccessLayer.IDataAccessLayer;
import Models.Tour;

import java.util.List;

public class BusinessLayer implements IBusinessLayer {

    IDataAccessLayer dataAccessLayer = null;

    public BusinessLayer() {
        dataAccessLayer = DataAccessLayer.getInstance();
    }

    @Override
    public void addTour(Tour newTour) {
        dataAccessLayer.addTour(newTour);
        System.out.println(newTour.getName());
    }

    @Override
    public List<Tour> getAllTours() {
        return dataAccessLayer.getTours();
    }

    @Override
    public void deleteTour(String tourName) {
        dataAccessLayer.deleteTour(tourName);
    }

    @Override
    public Tour getTourByName(String tourName) {
        return dataAccessLayer.getTourByName(tourName);
    }

    @Override
    public void updateTour(Tour tour) {
        dataAccessLayer.updateTour(tour);
    }
}
