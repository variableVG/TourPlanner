package BusinessLayer;

import DataAccessLayer.DataAccessLayer;
import DataAccessLayer.IDataAccessLayer;
import BusinessLayer.Map.ApiDirections;
import BusinessLayer.Map.MapRequest;
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
        getMap(newTour);
        newTour.setChildFriendlinessFromOwnData();
        newTour.setPopularityFromNumberOfLogs();
        int id = dataAccessLayer.addTour(newTour);
        //return back the new Id so it can be assigned in the frontend
        return id;

    }

    /*@Override
    public List<Tour> getAllTours() {
        return dataAccessLayer.getTours();
    }*/
    @Override
    public List<Tour> getAllTours(String search) {
        return dataAccessLayer.getTours(search);
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
            tour.setChildFriendlinessFromOwnData();
            tour.setPopularityFromNumberOfLogs();
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
    public boolean updateLog(Log log, int tourId) throws Exception {
        if(log.getRating() > 5 || log.getRating() < 0) {
            throw new Exception("Rating score is not a valid value. Valid values between 0 and 5");
        }
        else if(log.getDifficulty() > 3 || log.getDifficulty() < 0) {
            throw new Exception("Difficulty store is not a valid value. Valid values between 0 and 3");
        }

        if(dataAccessLayer.updateLog(log, tourId)) {
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteLog(int logId) throws Exception {
        //Check first if log is present in the DB:
        Log checkLog = dataAccessLayer.getLogById(logId);
        if(checkLog == null) {
            throw new Exception("Log is not present in the database");
        }
        return dataAccessLayer.deleteLog(logId);
    }

    @Override
    public void getMap(Tour tour) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        /** this function makes a REST request for a given tour (which contains origin and destination) using the
         * MapQuest Directions and Static BusinessLayer.Map APIs.
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
                    System.out.println("I do not enter here. I am waiting for directions. ");
                    try {
                        System.out.println("futureDirection in getMap/Business is ");
                        System.out.println(futureDirections);
                        tour.setTime(futureDirections.getFormattedTime());
                        tour.setDistance(futureDirections.getDistance());
                        System.out.println("Now I am going to call the static map ");
                        tour.setFutureImageMap(mapRequest.getStaticMap(futureDirections));
                        //return apiMap;

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    //return apiMap;
                    return tour;
                }
                );
    }

    @Override
    public Log getLogById(int logId) {
        Log log = null;
        log = dataAccessLayer.getLogById(logId);

        return log;
    }

    @Override
    public List<Tour> searchText(String text) {
        List<Tour> foundTours = dataAccessLayer.searchText(text);
        for (Tour t : foundTours) {
            try {
                getMap(t);
            } catch (URISyntaxException | IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return foundTours;
    }


}
