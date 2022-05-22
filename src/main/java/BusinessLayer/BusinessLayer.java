package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.IDataAccessLayer;
import Map.ApiDirections;
import Map.MapRequest;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;

import java.io.IOException;
import java.net.URISyntaxException;
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
        try {
            getMap(tour);
        } catch (URISyntaxException | IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addLog(Tour tour, Log log) throws Exception {
        //check if tourId exists in DB
        Tour tourCheck = dataAccessLayer.getTourById(tour.getId());
        if(tourCheck.getId() != tour.getId()) {
            throw new Exception("Tour does not match with tour in DB");

        }

        //check Log data
        //Check rating: Rating should be between 0 and 5
        if(log.getRating() < 0 | log.getRating() > 6) {
            throw new Exception("Log-Rating has not valid value");
        }
        //Check difficulty: Difficulty should have a value between 0 and 3
        if(log.getDifficulty() < 0 | log.getDifficulty() > 4) {
            throw new Exception("Difficulty has not valid value");
        }
        int logId = -1;
        logId = dataAccessLayer.addLog(tour.getId(), log);

        return logId;

    }

    @Override
    public List<Log> getLogs(int tourId) {
        List<Log> logs = dataAccessLayer.getLogs(tourId);
        return logs;
    }

    @Override
    public void getMap(Tour tour) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        /** this function makes a REST request for a given tour (which contains origin and destination) using the
         * MapQuest Directions and Static Map APIs.
         *
         * The function is called:
         *      * When starting the main window of the program (at the beginning of the programm) for the tours already
         *          present in the database.
         *      * When adding a new Tour
         *      * When editing Origin or destination of a tour
         * */
        CompletableFuture<ApiDirections> directions = mapRequest.getMapDirections(tour);
        //AtomicReference<CompletableFuture<ApiMap>> apiMap = new AtomicReference<>(new CompletableFuture<>());

        directions.thenApply(
                futureDirections -> {
                    try {
                        System.out.println("futureDirection in getMap/Business is ");
                        System.out.println(futureDirections);
                        tour.setFutureImageMap(mapRequest.getStaticMap(futureDirections));
                        //return apiMap;

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    //return apiMap;
                    return tour;
                }
                );

            //return apiMap.get();
        }



}
