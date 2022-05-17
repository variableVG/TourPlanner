package DataAccessLayer;

import Models.Log;
import Models.Tour;

import java.util.List;

public interface IDataAccessLayer {

    void addTour(Tour newTour) throws Exception;

    List<Tour> getTours();

    void deleteTour(String tourName);

    void updateTour(Tour tour);

    Tour getTourByName(String tourName);
    Tour getTourById(int id);//**

    void addLog(int id, Log log);

    List<Log> getLogs(int tourId);

    int getTourIdByName(String name) throws Exception;
}
