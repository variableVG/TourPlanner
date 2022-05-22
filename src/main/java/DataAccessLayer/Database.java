package DataAccessLayer;

import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;

import java.sql.Connection;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Database {

    public static void initDb() {
        try (Connection connection = DatabaseConnection.getInstance().connect("")) {
            if(reloadDatabase()) {
                DatabaseConnection.executeSql(connection, "DROP DATABASE IF EXISTS tourplanner", false);
                DatabaseConnection.executeSql(connection, "CREATE DATABASE tourplanner", false);
            }
        } catch (SQLException throwables) {
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
                        distance VARCHAR(50),
                        estimated_time VARCHAR(50)
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
            throwables.printStackTrace();
        }
    }

    private static boolean reloadDatabase() {
        System.out.println("Should we reload the database? Press 1 for yes, 0 for no");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();
        if(answer == 1) {
            return true;
        }
        return false;
    }

    public static Tour getTour(String tourName) {
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
                        resultSet.getString("description"),
                        resultSet.getString("origin"),
                        resultSet.getString("destination"),
                        resultSet.getString("transport_type"),
                        resultSet.getString("distance"),
                        resultSet.getString("estimated_time")
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void addTour(Tour newTour) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO tour
                (name, description, origin, destination, transport_type, distance, estimated_time)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """ )
        ) {
            statement.setString(1, newTour.getName());
            statement.setString(2, newTour.getDescription());
            statement.setString(3, newTour.getOrigin());
            statement.setString(4, newTour.getDestination());
            statement.setString(5, newTour.getTransportType());
            statement.setString(6, newTour.getDistance());
            statement.setString(7, newTour.getTime());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
                                resultSet.getString("description"),
                                resultSet.getString("origin"),
                                resultSet.getString("destination"),
                                resultSet.getString("transport_type"),
                                resultSet.getString("distance"),
                                resultSet.getString("estimated_time")
                        )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tours;
    }

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
            throwables.printStackTrace();
        }
    }

    public static void updateTour(Tour newTour) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE tour 
                SET name = ?, description = ?, origin = ?, destination = ?, transport_type = ?, 
                distance = ?, estimated_time = ?
                WHERE id = ?;
                """ )
        ) {
            //statement.setString(1, newTour.getId().get());
            statement.setString(1, newTour.getName());
            statement.setString(2, newTour.getDescription());
            statement.setString(3, newTour.getOrigin());
            statement.setString(4, newTour.getDestination());
            statement.setString(5, newTour.getTransportType());
            statement.setString(6, newTour.getDistance());
            statement.setString(7, newTour.getTime());
            statement.setInt(8, newTour.getId());
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //**
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
                        resultSet.getString("description"),
                        resultSet.getString("origin"),
                        resultSet.getString("destination"),
                        resultSet.getString("transport_type"),
                        resultSet.getString("distance"),
                        resultSet.getString("estimated_time")
                );
            }

        } catch (SQLException throwables) {
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
            if(resultSet.next() ) {
                id = resultSet.getInt("id");
            }
        }
        catch (SQLException throwables) {
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
            throwables.printStackTrace();
        }
        return answer;
    }

    ////comment
}
/*
package at.fhtw.bif3vz.swe.mtcg.if19b101.database;

import java.sql.Connection;
import java.sql.*;
import java.util.*;

public class Database {

    public static void initDb() {
        try (Connection connection = DatabaseConnection.getInstance().connect("")) {
            DatabaseConnection.executeSql(connection, "DROP DATABASE monstertradingcardsgame", true);
            DatabaseConnection.executeSql(connection, "CREATE DATABASE monstertradingcardsgame", true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS Users (
                        Username VARCHAR(50) NOT NULL PRIMARY KEY,
                        Password VARCHAR(50) NOT NULL,
                        Token VARCHAR(50) NOT NULL,
                        Coins INT,
                        ELO INT,
                        Bio VARCHAR(50),
                        Image VARCHAR(50)
                    )
                    """);
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS Cards (
                        Id VARCHAR(100) NOT NULL PRIMARY KEY,
                        Name VARCHAR(50) NOT NULL,
                        Damage NUMERIC(4,2) NOT NULL,
                        User_Token VARCHAR(50),
                        inDeck BOOLEAN
                    )
                    """);
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS Packages (
                        PackageId SERIAL UNIQUE PRIMARY KEY,
                        CardId1 VARCHAR(100),
                        CardId2 VARCHAR(100),
                        CardId3 VARCHAR(100),
                        CardId4 VARCHAR(100),
                        CardId5 VARCHAR(100)
                    )
                    """);
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS Battles (
                        BattleId SERIAL UNIQUE PRIMARY KEY,
                        Token1 VARCHAR(50),
                        Token2 VARCHAR(50)
                    )
                    """);
            DatabaseConnection.getInstance().executeSql("""
                        CREATE TABLE IF NOT EXISTS Trades (
                        Token VARCHAR(50),
                        TradeId VARCHAR(100) UNIQUE PRIMARY KEY,
                        CardToTrade VARCHAR(50),
                        Type VARCHAR(50),
                        MinimumDamage NUMERIC(4,2)
                    )
                    """);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //trade
    public void saveTrade(String token, TradeRecord trade){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO trades
                (token, tradeid, cardtotrade, type, minimumdamage)
                VALUES (?, ?, ?, ?, ?);
                """ )
        ) {
            statement.setString(1, token);
            statement.setString(2, trade.id());
            statement.setString(3, trade.cardToTrade());
            statement.setString(4, trade.type());
            statement.setFloat(5, trade.minimumDamage());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public TradeRecord getTrades(String token){
        TradeRecord trade = new TradeRecord("-1","-1","-1","-1",-1);//record problem
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT token, tradeid, cardtotrade, type, minimumdamage
                FROM trades
                WHERE NOT token=?
                """ )
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return new TradeRecord(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getFloat(5)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trade;
    }

    public TradeRecord getTradeById(String id){
        TradeRecord trade = new TradeRecord("-1","-1","-1" ,"-1",-1);
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT token, tradeid, cardtotrade, type, minimumdamage
                FROM trades
                WHERE tradeid=?
                """ )
        ) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return new TradeRecord(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getFloat(5)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trade;
    }

    public void deleteTrade(String token, String id){//token immer übergeben -> bessere lösung ?
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                 DELETE FROM trades
                WHERE tradeid=? AND token=?;
                """ )
        ) {
            statement.setString(1, id);
            statement.setString(2, token);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //user
    public void saveUser(UserDB user) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO users
                (username, password, token, coins, elo)
                VALUES (?, ?, ?, ?, ?);
                """ )
        ) {
            statement.setString(1, user.getUsername() );
            statement.setString(2, user.getPassword() );
            statement.setString(3, user.getToken() );
            statement.setInt(4, 20 );
            statement.setInt(5, 100 );
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UserDB getUserDataByName(String name){//besser mit username and password
        UserDB user = new UserDB();
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT username, password, token
                FROM users
                WHERE username=?
                """)
        ){
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                user.setUsername(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setToken(resultSet.getString(3));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return user;
    }

    public List<String> getUserProfile(String token){
        List<String> data = new ArrayList<>();
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT username, bio, image
                FROM users
                WHERE token = ?
                """)
        ){
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                data.add(resultSet.getString(1));
                data.add(resultSet.getString(2));
                data.add(resultSet.getString(3));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return data;
    }

    public void updateUserProfile(String token, List<String> data){
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE users
                SET username = ?, bio = ?, image = ?
                WHERE token = ?;
                """)
        ){
            statement.setString(1, data.get(0));
            statement.setString(2, data.get(1));
            statement.setString(3, data.get(2));
            statement.setString(4, token);
            statement.execute();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public String readUsernameByToken(String token){
        String username = null;
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT username
                FROM users
                WHERE token = ?
                """)
        ){
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                username = resultSet.getString(1);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return username;
    }

    public int getUserStats(String token){
        int elo = -1;
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT elo FROM users
                WHERE token = ?
                """ )
        ) {
            statement.setString(1, token );
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                elo = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return elo;
    }

    public void updateUserStats(String token, int elo){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE users
                SET elo = ?
                WHERE token = ?;
                """ )
        ) {
            statement.setInt(1, elo );
            statement.setString(2, token );
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getCoinsByToken(String token){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT coins FROM users
                WHERE token = ?;
                """ )
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void updateCoinsByToken(String token){
        int coins = getCoinsByToken(token);
        coins -= 5;
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE users
                SET coins = ?
                WHERE token = ?;
                """ )
        ) {
            statement.setInt(1, coins);
            statement.setString(2, token);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //card
    public void saveCard(CardDB card) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO cards
                (id, name, damage)
                VALUES (?, ?, ?);
                """ )
        ) {
            statement.setString(1, card.getId() );
            statement.setString(2, card.getName() );
            statement.setFloat(3, card.getDamage() );
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateCard(String cardID, String token) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE cards
                SET user_token = ?
                WHERE id = ?;
                """ )
        ) {
            statement.setString(1, token );
            statement.setString(2, cardID );
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<CardDB> getCards(String token){
        List<CardDB> cards = new ArrayList<>();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT id, name, damage FROM cards
                WHERE user_token = ?;
                """ )
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                cards.add(new CardDB(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getFloat(3)
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    public CardDB getCardById(String id){
        CardDB card = new CardDB();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT id, name, damage FROM cards
                WHERE id = ?;
                """ )
        ) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                card.setId(resultSet.getString(1));
                card.setName(resultSet.getString(2));
                card.setDamage(resultSet.getFloat(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

    public List<String> getUserCardIds(String token){
        List<String> cards = new ArrayList<>();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT id FROM cards
                WHERE user_token = ?;
                """ )
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                cards.add(
                        resultSet.getString(1)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    //deck
    public void addCardToDeck(String cardID, String token) {
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE cards
                SET indeck = true
                WHERE id = ? AND user_token = ?;
                """ )
        ) {
            statement.setString(1, cardID );
            statement.setString(2, token );
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void excludeAllCardsFromDeck(String token){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE cards
                SET indeck = false
                WHERE user_token = ?;
                """ )
        ) {
            statement.setString(1, token);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<CardDB> getDeckCards(String token){
        List<CardDB> cards = new ArrayList<>();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT id, name, damage FROM cards
                WHERE user_token = ? AND indeck = true;
                """ )
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                cards.add(new CardDB(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getFloat(3)
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    //package
    public void savePackage(List<CardDB> cards){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO packages
                (cardid1, cardid2, cardid3, cardid4, cardid5)
                VALUES (?, ?, ?, ?, ?);
                """ )
        ) {
            statement.setString(1, cards.get(0).getId() );
            statement.setString(2, cards.get(1).getId() );
            statement.setString(3, cards.get(2).getId() );
            statement.setString(4, cards.get(3).getId() );
            statement.setString(5, cards.get(4).getId() );
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deletePackage(Integer packID){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                DELETE FROM packages
                WHERE packageid = ?;
                """)
        ) {
            statement.setInt( 1, packID);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> getIdsOfPackage(){
        List<String> ids = new ArrayList<>();
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT * FROM packages LIMIT 1;
                """)
        ){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ids.add(resultSet.getString(2));
                ids.add(resultSet.getString(3));
                ids.add(resultSet.getString(4));
                ids.add(resultSet.getString(5));
                ids.add(resultSet.getString(6));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return ids;
    }

    public int getIdOfPackage(){
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT packageid FROM packages LIMIT 1;
                """)
        ){
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return -1;
    }

    //battle
    public int getLastBattleId(){
        int battleId = -1;
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT battleid FROM battles ORDER BY battleid DESC LIMIT 1;
                """ )
        ) {
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                battleId = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return battleId;
    }

    public List<String> getBattleTokens(int battleId){//pair besser?
        List<String> tokens = new ArrayList<>();
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT token1, token2 FROM battles
                WHERE battleid = ?;
                """ )
        ) {
            statement.setInt(1, battleId);
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                tokens.add(resultSet.getString(1));
                tokens.add(resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tokens;
    }

    public void createNewBattle(String token){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                INSERT INTO battles
                (token1)
                VALUES (?);
                """ )
        ) {
            statement.setString(1, token);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void joinBattle(String token, int battleId){
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                UPDATE battles
                SET token2 = ?
                WHERE battleid = ?;
                """ )
        ) {
            statement.setString(1, token);
            statement.setInt(2, battleId);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //scoreboard
    public TreeMap<String, Integer> getScoreboard(){
        TreeMap<String, Integer> scoreboard = new TreeMap<>(Comparator.reverseOrder());
        try ( PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement("""
                SELECT username, elo FROM users;
                """ )
        ) {
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                scoreboard.put(
                        resultSet.getString(1),
                        resultSet.getInt(2)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return scoreboard;
    }
}

 */
