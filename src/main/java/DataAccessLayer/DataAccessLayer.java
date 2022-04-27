package DataAccessLayer;

import Models.Tour;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataAccessLayer implements IDataAccessLayer {
    //TODO thread safe

    //private Database database;
    private static DataAccessLayer dataAccessLayer = null;

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
            dataAccessLayer = new DataAccessLayer();
        }
        return dataAccessLayer;
    }


    @Override
    public void addTour(Tour newTour) {
        //Check if TourName is already present:
        Tour dbTour = Database.getTour(newTour.getName().get());
        if(dbTour == null) {
            //.get() is used because newTour.getName() is type StringProperty, we need to cast to string.
            Database.addTour(newTour);
        }
        else {
            //throw exception: THe tour already exist.
            System.out.println("The tour cannot be added because a tour with the same name is already present in the DB.");
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
}
