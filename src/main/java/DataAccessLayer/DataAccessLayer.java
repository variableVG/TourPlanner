package DataAccessLayer;

public class DataAccessLayer implements IDataAccessLayer {
    //TODO thread safe 

    private static DataAccessLayer DataAccessLayer = null;

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
        if (DataAccessLayer == null) {
            DataAccessLayer = new DataAccessLayer();
        }
        return DataAccessLayer;
    }
}
