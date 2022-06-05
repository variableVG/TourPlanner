package DataAccessLayer;

import BusinessLayer.Logger.ILoggerWrapper;
import BusinessLayer.Logger.LoggerFactory;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;

public class Database {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public static void initDb() {
        logger.debug("Class Database, initDb() - Starting Database.");
        try (Connection connection = DatabaseConnection.getInstance().connect("")) {
            if(reloadDatabase()) {
                DatabaseConnection.executeSql(connection, "DROP DATABASE IF EXISTS tourplanner", false);
                DatabaseConnection.executeSql(connection, "CREATE DATABASE tourplanner", false);
            }
        } catch (SQLException throwables) {
            logger.fatal("Class Database, initDb(). " + throwables);
            throwables.printStackTrace();
        }

        try {
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS tour (
                        id SERIAL PRIMARY KEY,                        
                        name VARCHAR(50) NOT NULL UNIQUE,
                        description VARCHAR(500),
                        origin VARCHAR(50),
                        destination VARCHAR(50),
                        transport_type VARCHAR(50),
                        distance NUMERIC,
                        estimated_time TIME
                    )
                    """);
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS log (
                        id SERIAL PRIMARY KEY,
                        tour_id INT REFERENCES tour (id) ON UPDATE CASCADE ON DELETE CASCADE,
                        date DATE,
                        time TIME,
                        total_time VARCHAR(50), 
                        difficulty NUMERIC,
                        rating NUMERIC,
                        comment VARCHAR(500)
                        
                    )
                    """);
        }
        catch (SQLException throwables) {
            logger.fatal("Class Database, initDb(). " + throwables);
            throwables.printStackTrace();
        }
    }

    private static boolean reloadDatabase() {
        System.out.println("Should we reload the database? Press 1 for yes, 0 for no");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();
        if(answer == 1) {
            logger.debug("Class Database, reloadDatabase() - The user has press 1 to delete the all database and create new tables.");
            return true;
        }
        return false;
    }

    public static Tour getTourByName(String tourName) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT * FROM tour WHERE name = ?;
                """)
        ) {
            statement.setString(1, tourName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next() ) {
                return new Tour(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("origin"),
                        resultSet.getString("destination"),
                        resultSet.getString("description"),
                        resultSet.getString("transport_type"),
                        resultSet.getFloat("distance"),
                        resultSet.getString("estimated_time")
                );
            }

        } catch (SQLException throwables) {
            logger.error("Class Database, getTourByName. " + throwables);
            throwables.printStackTrace();
        }
        return null;
    }

    public static int addTour(Tour newTour) {
        int id = -1;
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO tour
                (name, description, origin, destination, transport_type, distance, estimated_time)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id;
                """ )
        ) {
            Time time = null;
            if(newTour.getTime() != null) { time = Time.valueOf(newTour.getTime().toString()); }

            statement.setString(1, newTour.getName());
            statement.setString(2, newTour.getDescription());
            statement.setString(3, newTour.getOrigin());
            statement.setString(4, newTour.getDestination());
            statement.setString(5, newTour.getTransportType());
            statement.setFloat(6, newTour.getDistance());
            statement.setTime(7, time);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next() ) { id = resultSet.getInt("id"); }
        } catch (SQLException throwables) {
            logger.error("Class Database, addTour(). " + throwables);
            throwables.printStackTrace();
        }
        return id;
    }

    public static List<Tour> getTours(){
        List<Tour> tours = new ArrayList<>();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT *
                FROM tour
                """ )
        ) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                tours.add(
                        new Tour(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("origin"),
                                resultSet.getString("destination"),
                                resultSet.getString("description"),
                                resultSet.getString("transport_type"),
                                resultSet.getFloat("distance"),
                                resultSet.getString("estimated_time")
                        )
                );
            }
        } catch (SQLException throwables) {
            logger.error("Class Database, getTours(). " + throwables);
            throwables.printStackTrace();
        }
        return tours;
    }

    /*TEST public static List<Tour> getTours(String search){
        List<Tour> tours = new ArrayList<>();
        if(search.equals("")){
            try (
                    PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT *
                FROM tour
                """ )
            ) {
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    tours.add(
                            new Tour(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("origin"),
                                    resultSet.getString("destination"),
                                    resultSet.getString("description"),
                                    resultSet.getString("transport_type"),
                                    resultSet.getFloat("distance"),
                                    resultSet.getString("estimated_time")
                            )
                    );
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            try (
                    PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT *
                FROM tour
                WHERE name = ?
                """ )
            ) {
                statement.setString(1, search);
                //statement.execute();
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    tours.add(
                            new Tour(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("origin"),
                                    resultSet.getString("destination"),
                                    resultSet.getString("description"),
                                    resultSet.getString("transport_type"),
                                    resultSet.getFloat("distance"),
                                    resultSet.getString("estimated_time")
                            )
                    );
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return tours;
    }*/

    public static void deleteTour(String tourName) {
        //return type boolean -> delete succ || err ?
        //parameter of type Tour? more specific ?
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                DELETE 
                FROM tour 
                WHERE name=?;
                """ )
        ) {
            statement.setString(1, tourName);
            statement.execute();
        } catch (SQLException throwables) {
            logger.error("Class Database, deleteTour(). " + throwables);
            throwables.printStackTrace();
        }
    }

    public static void updateTour(Tour newTour) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE tour 
                SET name = ?, description = ?, origin = ?, destination = ?, transport_type = ?, 
                distance = ?, estimated_time = ?
                WHERE id = ?
                RETURNING id;
                """ )
        ) {
            Time time = null;
            if(newTour.getTime() != null) { time = Time.valueOf(newTour.getTime().toString()); }

            statement.setString(1, newTour.getName());
            statement.setString(2, newTour.getDescription());
            statement.setString(3, newTour.getOrigin());
            statement.setString(4, newTour.getDestination());
            statement.setString(5, newTour.getTransportType());
            statement.setFloat(6, newTour.getDistance());
            statement.setTime(7, time);
            statement.setInt(8, newTour.getId());
            statement.execute();

        } catch (SQLException throwables) {
            logger.error("Class Database, updateTour(). " + throwables);
            throwables.printStackTrace();
        }

    }

    public static Tour getTourById(int id) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT * FROM tour WHERE id = ?;
                """)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next() ) {

                return new Tour(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("origin"),
                        resultSet.getString("destination"),
                        resultSet.getString("description"),
                        resultSet.getString("transport_type"),
                        resultSet.getFloat("distance"),
                        resultSet.getString("estimated_time")
                );
            }

        } catch (SQLException throwables) {
            logger.error("Class Database, getTourById(). " + throwables);
            throwables.printStackTrace();
        }
        return null;
    }

    public static int addLog(int tourId, Log log) {
        int id = -1;
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO log
                (tour_id, date, time, total_time, difficulty, rating, comment)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id;
                """ )
        ) {
            //Check for date and Time:
            Date date = null;
            if(log.getDate() != null) { date = Date.valueOf(log.getDate()); }
            Time time = null;
            if(log.getTime() != null) { time = Time.valueOf(log.getTime()); }

            statement.setInt(1, tourId);
            statement.setDate(2, date);
            statement.setTime(3, time);
            statement.setString(4, log.getTotaltime());
            statement.setInt(5, log.getDifficulty());
            statement.setInt(6, log.getRating());
            statement.setString(7, log.getComment());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next() ) { id = resultSet.getInt("id"); }
        }
        catch (SQLException throwables) {
            logger.error("Class Database, addLog(). " + throwables);
            throwables.printStackTrace();
        }
        return id;
    }

    public static List<Log> getLogs(int tourId){
        List<Log> logs = new ArrayList<>();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT *
                FROM log WHERE tour_id = ?
                """ )
        ) {
            statement.setInt(1, tourId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //validate Data
                int id = resultSet.getInt("id");
                LocalDate date = null;
                if(resultSet.getDate("date")!= null) {
                    date = resultSet.getDate("date").toLocalDate();
                }
                LocalTime time = null;
                if(resultSet.getTime("time") != null) {
                    time = resultSet.getTime("time").toLocalTime();
                }

                //create log
                logs.add(
                        new Log(
                                resultSet.getInt("id"), date, time,
                                resultSet.getString("comment"),
                                resultSet.getInt("difficulty"),
                                resultSet.getString("total_time"),
                                resultSet.getInt("rating")

                        )
                );
            }
        } catch (SQLException throwables) {
            logger.error("Class Database, getLogs(). " + throwables);
            throwables.printStackTrace();
        }
        return logs;
    }

    public static int getTourIdByName(String name) {
        int answer = -1;
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT id FROM tour WHERE name = ?;
                """)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next() ) {
                answer = resultSet.getInt("id");
            }

        } catch (SQLException throwables) {
            logger.error("Class Database, getTourIdByName(). " + throwables);
            throwables.printStackTrace();
        }
        return answer;
    }

    public static boolean updateLog(Log log, int tourId) {
        Date date = null;
        if(log.getDate()!= null) {date = Date.valueOf(log.getDate());}
        Time time = null;
        if(log.getTime() != null) { time = Time.valueOf(log.getTime());}

        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE log 
                SET tour_id = ?, date = ?, time = ?, total_time = ?, difficulty = ?, rating = ?, comment = ?
                WHERE id = ?;
                """ )
        ) {
            //statement.setString(1, newTour.getId().get());
            statement.setInt(1, tourId);
            statement.setDate(2, date);
            statement.setTime(3, time);
            statement.setString(4, log.getTotaltime());
            statement.setInt(5, log.getDifficulty());
            statement.setInt(6, log.getRating());
            statement.setString(7, log.getComment());
            statement.setInt(8, log.getId());
            statement.execute();
            return true;

        } catch (SQLException throwables) {
            logger.error("Class Database, updateLog(). " + throwables);
            throwables.printStackTrace();
        }
        return false;
    }

    public static Log getLogById(int logId) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT * FROM log WHERE id = ?;
                """)
        ) {
            statement.setInt(1, logId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next() ) {
                //validate Data
                int id = resultSet.getInt("id");
                LocalDate date = null;
                if(resultSet.getDate("date")!= null) {
                    date = resultSet.getDate("date").toLocalDate();
                }
                LocalTime time = null;
                if(resultSet.getTime("time") != null) {
                    time = resultSet.getTime("time").toLocalTime();
                }
                return new Log(
                        resultSet.getInt("id"),
                        date,
                        time,
                        resultSet.getString("comment"),
                        resultSet.getInt("difficulty"),
                        resultSet.getString("total_time"),
                        resultSet.getInt("rating")
                );
            }

        } catch (SQLException throwables) {
            logger.error("Class Database, getLogById(). " + throwables);
            throwables.printStackTrace();
        }

        return null;
    }

    public static boolean deleteLog(int logId) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                DELETE FROM log WHERE id=?;
                """ )
        ) {
            statement.setInt(1, logId);
            statement.execute();
            return true;
        } catch (SQLException throwables) {
            logger.error("Class Database, deleteLog(). " + throwables);
            throwables.printStackTrace();
        }
        return false;
    }

    public static List<Tour> searchText(String text) {
        //Source: https://www.compose.com/articles/mastering-postgresql-tools-full-text-search-and-phrase-search/
        List<Tour> foundTours = new ArrayList<>();
        String pattern = "%" + text + "%";

        int difficulty = 6;
        if (text.equalsIgnoreCase("easy")) { difficulty = 1; }
        else if (text.equalsIgnoreCase("medium")) { difficulty = 2; }
        else if (text.equalsIgnoreCase("hard")) { difficulty = 3; }

        //First we search the tours
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT DISTINCT tour.* FROM tour FULL OUTER JOIN log ON tour.id = log.tour_id
                WHERE
                name LIKE ? OR description LIKE ? OR origin LIKE ?
                OR destination LIKE ? OR transport_type LIKE ?
                OR log.comment LIKE ? OR log.difficulty = ?;
                """ )
        ) {
            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);
            statement.setString(4, pattern);
            statement.setString(5, pattern);
            statement.setString(6, pattern);
            statement.setInt(7, difficulty);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                foundTours.add(
                        new Tour(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("origin"),
                                resultSet.getString("destination"),
                                resultSet.getString("description"),
                                resultSet.getString("transport_type"),
                                resultSet.getFloat("distance"),
                                resultSet.getString("estimated_time")
                        )
                );
            }
        } catch (SQLException throwables) {
            logger.error("Class Database, searchText(). " + throwables);
            throwables.printStackTrace();
        }
        return foundTours;
    }
}