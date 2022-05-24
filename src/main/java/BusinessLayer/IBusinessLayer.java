package BusinessLayer;

import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IBusinessLayer {
    int addTour(Tour tourName) throws Exception;

    List<Tour> getAllTours();

    void deleteTour(String tourName);

    Tour getTourByName(String tourName);
    Tour getTourById(int id);//**

    void updateTour(Tour tour);

    int addLog(Tour tour, Log log) throws Exception;

    List<Log> getLogs(int tourId);

    void updateLog(Log log, int tourId);

    boolean deleteLog(Log log) throws Exception;

    void getMap(Tour tour) throws IOException, URISyntaxException, ExecutionException, InterruptedException;
}
