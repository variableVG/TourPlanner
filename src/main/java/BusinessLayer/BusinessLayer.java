package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.Database;
import DataAccessLayer.IDataAccessLayer;

public class BusinessLayer implements IBusinessLayer {

    IDataAccessLayer dataAccessLayer = null;

    public BusinessLayer() {
        dataAccessLayer = DataAccessLayer.getInstance();
    }

    @Override
    public void addTour(String tourName) {
        System.out.println(tourName);
    }

    @Override
    public void getAllTours() {

    }
}
