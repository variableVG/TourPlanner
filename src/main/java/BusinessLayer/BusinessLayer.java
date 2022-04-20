package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.Database;
import DataAccessLayer.IDataAccessLayer;
import Models.Tour;

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
    public void getAllTours() {

    }
}
