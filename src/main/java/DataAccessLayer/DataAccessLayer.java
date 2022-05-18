package DataAccessLayer;

import Models.Log;
import Models.Tour;

import java.util.List;

public class DataAccessLayer implements IDataAccessLayer {
    //TODO thread safe

    //private Database database;
    private static DataAccessLayer dataAccessLayer = null;
    //
    private static Object mutex = new Object();

    private DataAccessLayer() {
        Database.initDb();
    }

    public static IDataAccessLayer getInstance() {
        /** When I need the class I access to it through getInstance. With the lazy
         * method it delays the creation of an object until the first time is needed.
         * For that I check if the singleton Instance has been already created, if not I
         * will create the instance, otherwise I give back the already created instance.
         *
         * This implementation can create problems with threads.
         * Each thread might create its own instance, one per thread. That is not the purpose
         * of using Singletons. To avoid that I used the keyword "synchronized", like that if
         * a thread enters the getInstance() function, the rest of the threads would have to wait
         * if they want to run the function.
         * */
        if (dataAccessLayer == null) {
            synchronized (mutex) {

                if (dataAccessLayer == null)
                    dataAccessLayer = new DataAccessLayer();
            }

        }
        return dataAccessLayer;
    }


    @Override
    public void addTour(Tour newTour) throws Exception {
        //Check if TourName is already present:
        Tour dbTour = Database.getTour(newTour.getName());
        if(dbTour == null) {
            Database.addTour(newTour);
        }
        else {
            throw new Exception("The tour cannot be added because a tour with the same name is already present in the DB.");
        }
    }

    @Override
    public List<Tour> getTours() {
        return Database.getTours();
    }

    @Override
    public void deleteTour(String tourName) {
        Database.deleteTour(tourName);
    }

    @Override
    public void updateTour(Tour tour) {
        Database.updateTour(tour);
    }

    @Override
    public Tour getTourByName(String tourName) {
        return Database.getTour(tourName);
    }
    @Override
    public Tour getTourById(int id) {
        return Database.getTourById(id);
    }//**

    @Override
    public int addLog(int id, Log log) {
        return Database.addLog(id, log);
    }

    @Override
    public List<Log> getLogs(int tourId) {
        List<Log> logs = Database.getLogs(tourId);
        return logs;
    }

    @Override
    public int getTourIdByName(String name) {
        //If returns -1, it could not find the tour.
        return Database.getTourIdByName(name);
    }
}
