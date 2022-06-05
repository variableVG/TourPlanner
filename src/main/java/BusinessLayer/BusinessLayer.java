package BusinessLayer;

import BusinessLayer.Logger.ILoggerWrapper;
import BusinessLayer.Logger.LoggerFactory;
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

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public BusinessLayer() {
        dataAccessLayer = DataAccessLayer.getInstance();
        mapRequest = new MapRequest();
    }

    @Override
    public int addTour(Tour newTour) {
        /** This function add a tour to the database and gives back the in the database generated Id for that tour.
         * */

        //Check if the tour has set name, destination and origin
        if(newTour.getName().isEmpty() | newTour.getDestination().isEmpty() | newTour.getOrigin().isEmpty()) {
            logger.error("Class BusinessLayer, addTour() - Tour Name or Destination or Origin are empty");
            return -1;
        }

        //We ask for the map when the Tour is created, so we can store it already and it can upload faster.
        try {
            getMap(newTour);
        } catch (URISyntaxException | InterruptedException | ExecutionException | IOException e) {
            logger.error("Class BusinessLayer, addTour() - Exception when requesting map for tour " + newTour.getName());
            logger.error("Exception: " + e);
            //e.printStackTrace();
        }

        //Set Childfriendliness and Popularity
        newTour.setChildFriendlinessFromOwnData();
        newTour.setPopularityFromNumberOfLogs();


        int id = 0;
        try {
            logger.debug("Class BusinessLayer, addTour() - Adding Tour " + newTour.getName());
            id = dataAccessLayer.addTour(newTour);
        } catch (Exception e) {
            logger.error("Class BusinessLayer, addTour() - Exception " + e);
            e.printStackTrace();
        }

        //return back the new Id so it can be assigned in the frontend
        logger.debug("Class BusinessLayer, addTour() - Tour " + newTour.getName() + " successfully added and id " + id + " assigned.");
        return id;

    }

    @Override
    public List<Tour> getAllTours(String search) {
        logger.debug("Class BusinessLayer, getAllTours() - All tours requested.");
        return dataAccessLayer.getTours(search);
    }

    @Override
    public void deleteTour(String tourName) {
        dataAccessLayer.deleteTour(tourName);
        logger.debug("Class BusinessLayer, deleteTour() - Tour " + tourName + " was deleted.");
    }

    @Override
    public Tour getTourByName(String tourName) {
        return dataAccessLayer.getTourByName(tourName);
    }

    @Override
    public Tour getTourById(int id) {
        return dataAccessLayer.getTourById(id);
    }

    @Override
    public void updateTour(Tour tour) {
        logger.debug("Class BusinessLayer, updateTour() - Tour " + tour.getName() + " with id " + tour.getId() + " will be updated.");
        dataAccessLayer.updateTour(tour);
        try {
            getMap(tour);
            tour.setChildFriendlinessFromOwnData();
            tour.setPopularityFromNumberOfLogs();
        } catch (URISyntaxException | IOException | ExecutionException | InterruptedException e) {
            logger.error("Class BusinessLayer, updateTour() - Exception: " + e);
            //e.printStackTrace();
        }

        logger.debug("Class BusinessLayer, updateTour() - Tour " + tour.getName() + " with id " + tour.getId() + " has been updated.");

    }

    @Override
    public int addLog(Tour tour, Log log) {
        //check if tourId exists in DB
        Tour tourCheck = dataAccessLayer.getTourById(tour.getId());
        if(tourCheck.getId() != tour.getId()) {
            logger.warn("Class BusinessLayer, addLog() - Tour " + tour.getName() + " with id " + tour.getId() +
                    " does not match with tour in DB.");
        }

        //check Log data
        //Check rating: Rating should be between 0 and 5
        if(log.getRating() < 0 | log.getRating() > 6) {
            logger.warn("Class BusinessLayer, addLog() - Tour " + tour.getName() + " with id " + tour.getId() +
                    " cannot add log because Log-Rating has not a valid value.");
        }
        //Check difficulty: Difficulty should have a value between 0 and 3
        if(log.getDifficulty() < 0 | log.getDifficulty() > 4) {
            logger.warn("Class BusinessLayer, addLog() - Tour " + tour.getName() + " with id " + tour.getId() +
                    " cannot add log because Log-Rating has not a valid difficulty.");
        }

        int logId = -1;
        logger.debug("Class BusinessLayer, addLog() - Adding Log " + log.getId() + " to tour " + tour.getId());
        logId = dataAccessLayer.addLog(tour.getId(), log);
        logger.debug("Class BusinessLayer, addLog() - Log " +logId + " successfully added  to tour " + tour.getId());

        return logId;

    }

    @Override
    public List<Log> getLogs(int tourId) {
        List<Log> logs = dataAccessLayer.getLogs(tourId);
        return logs;
    }

    @Override
    public boolean updateLog(Log log, int tourId) {
        logger.debug("Class BusinessLayer, updateLog() - Log " + log.getId() + " will be updated.");

        if(log.getRating() > 5 || log.getRating() < 0) {
            logger.warn("Class BusinessLayer, updateLog() - Log " + log.getId() + " has an invalid rating-score value.");
        }
        else if(log.getDifficulty() > 3 || log.getDifficulty() < 0) {
            logger.warn("Class BusinessLayer, updateLog() - Log " + log.getId() + " has an invalid difficulty-score value.");
        }

        if(dataAccessLayer.updateLog(log, tourId)) {
            logger.debug("Class BusinessLayer, updateLog() - Log " + log.getId() + " successfully updated.");
            return true;
        }

        logger.debug("Class BusinessLayer, updateLog() - Log " + log.getId() + " could not be updated.");
        return false;
    }

    @Override
    public boolean deleteLog(int logId) {
        //Check first if log is present in the DB:
        Log checkLog = dataAccessLayer.getLogById(logId);
        if(checkLog == null) {
            logger.debug("Class BusinessLayer, deleteLog() - Log " + logId+ " could not be deleted because it is not present in the database.");
        }
        logger.debug("Class BusinessLayer, deleteLog() - Log " + logId + " will be deleted.");
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
        logger.debug("Class BusinessLayer, getMap() - Map for tour " + tour.getName() + " requested.");

        directions.thenApply(
                futureDirections -> {
                    try {
                        logger.debug("Class BusinessLayer, getMap() - Directions for tour " + tour.getName() + " received: " +
                                futureDirections);

                        tour.setTime(futureDirections.getFormattedTime());
                        tour.setDistance(futureDirections.getDistance());

                        logger.debug("Class BusinessLayer, getMap() - Static Map for tour " + tour.getName() + " requested.");
                        tour.setFutureImageMap(mapRequest.getStaticMap(futureDirections));

                    } catch (URISyntaxException e) {
                        logger.error("Class BusinessLayer, getMap() - Tour " + tour.getName() + " Exception: " + e);
                        //e.printStackTrace();
                    }
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
                logger.error("Class BusinessLayer, searchText() - Exception: " + e);
                //e.printStackTrace();
            }
        }
        return foundTours;
    }


}
