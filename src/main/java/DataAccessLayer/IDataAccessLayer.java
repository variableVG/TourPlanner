package DataAccessLayer;

import Models.Tour;
import Models.TourPlannerModel;

import java.util.List;

public interface IDataAccessLayer {

    void addTour(Tour newTour);

    List<Tour> getTours();
}
