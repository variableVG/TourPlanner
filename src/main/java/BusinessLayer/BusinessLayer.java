package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.IDataAccessLayer;
import Map.ApiDirections;
import Map.MapRequest;
import Models.Log;
import Models.Tour;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.spec.ECField;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BusinessLayer implements IBusinessLayer {

    IDataAccessLayer dataAccessLayer = null;
    MapRequest mapRequest;

    public BusinessLayer() {
        dataAccessLayer = DataAccessLayer.getInstance();
        mapRequest = new MapRequest();
    }

    @Override
    public int addTour(Tour newTour) throws Exception {
        //Check if the tour has set name, destination and origin
        if(newTour.getName().isEmpty() | newTour.getDestination().isEmpty() | newTour.getOrigin().isEmpty()) {
            throw new Exception("Tour Name or Destination or Origin are empty");
        }

        //We ask for the map when the Tour is created, so we can store it already and it can upload faster.

        try { getMap(newTour);}
        catch (Exception e){
            System.out.println("Is it printed?");
            System.out.println(e);
        }

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
    public void getMap(Tour tour) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        CompletableFuture<ApiDirections> directions = mapRequest.getMapDirections(tour);
        directions.thenApply(
                futureDirections -> {
                    try {
                        //TODO Check if the answer to the request directions has a valid address:
                        if(futureDirections.getStatuscode() != 0) {
                            throw new Exception( "Directions could not be sent " + futureDirections.getMessages().toString());
                        } else {
                            System.out.println("Map for tour" + tour.getName() + " is about to be set");
                            tour.setStaticMap(mapRequest.getStaticMap(futureDirections));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );

    }
}
