package BusinessLayer;

import Map.ApiMap;
import Models.Log;
import Models.Tour;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IBusinessLayer {
    int addTour(Tour tourName) throws Exception;

    List<Tour> getAllTours();

    void deleteTour(String tourName);

    Tour getTourByName(String tourName);
    Tour getTourById(int id);//**

    void updateTour(Tour tour);

    void addLog(Tour tour, Log log);

    List<Log> getLogs(int tourId);

    CompletableFuture<ApiMap> getMap(Tour tour) throws IOException, URISyntaxException, ExecutionException, InterruptedException;
}
