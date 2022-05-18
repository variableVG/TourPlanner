package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.IDataAccessLayer;
import Map.ApiDirections;
import Map.ApiMap;
import Map.MapRequest;
import Models.Log;
import Models.Tour;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class BusinessLayer implements IBusinessLayer {

    IDataAccessLayer dataAccessLayer = null;
    MapRequest mapRequest;

    public BusinessLayer() {
        dataAccessLayer = DataAccessLayer.getInstance();
        mapRequest = new MapRequest();
    }

    @Override
    public int addTour(Tour newTour) throws Exception {
        /** This function add a tour to the database and gives back the in the database generated Id for that tour.
         * */

        //Check if the tour has set name, destination and origin
        if(newTour.getName().isEmpty() | newTour.getDestination().isEmpty() | newTour.getOrigin().isEmpty()) {
            throw new Exception("Tour Name or Destination or Origin are empty");
        }

        //TODO
        //We ask for the map when the Tour is created, so we can store it already and it can upload faster.
        try { getMap(newTour);}
        catch (Exception e){
            System.out.println(e);
        }
        ////////

        //Add new Tour to the database
        dataAccessLayer.addTour(newTour);

        //return back the new Id so it can be assigned in the frontend
        int id =  dataAccessLayer.getTourIdByName(newTour.getName());
        return id;
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

    @Override
    public CompletableFuture<ApiMap> getMap(Tour tour) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        CompletableFuture<ApiDirections> directions = mapRequest.getMapDirections(tour);
        AtomicReference<CompletableFuture<ApiMap>> apiMap = new AtomicReference<>(new CompletableFuture<>());

        directions.thenApply(
                futureDirections -> {
                    try {
                        apiMap.set(mapRequest.getStaticMap(futureDirections));
                        System.out.println(apiMap);
                        return apiMap;

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    return apiMap;
                }
                );

            return apiMap.get();
        }



}
