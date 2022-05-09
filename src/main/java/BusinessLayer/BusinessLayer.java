package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.Database;
import DataAccessLayer.IDataAccessLayer;
import Models.Log;
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
    public Tour getTourById(int id) {
        return dataAccessLayer.getTourById(id);
    }//**

    @Override
    public void updateTour(Tour tour) {
        dataAccessLayer.updateTour(tour);
    }

    @Override
    public void addLog(Tour tour, Log log) {
        //TODO check if tourId exists in DB
        //TODO check Log data
        dataAccessLayer.addLog(tour.getId(), log);

    }

    @Override
    public List<Log> getLogs(int tourId) {
        List<Log> logs = dataAccessLayer.getLogs(tourId);
        return logs;
    }
}
