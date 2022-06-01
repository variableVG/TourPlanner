package BusinessLayerTest;

import BusinessLayer.BusinessLayer;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;
import javafx.util.converter.LocalDateStringConverter;
import org.bouncycastle.asn1.cms.Time;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


public class BusinessLayerTest {
    static private Tour tour;
    static private BusinessLayer business;
    @BeforeAll
    public static void setUpAll(){
        //Simulate user input
        //Source: https://www.logicbig.com/how-to/junit/java-test-user-command-line-input.html
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        business = new BusinessLayer();

    }
    @BeforeEach
    public void setUp() {
        tour = new Tour(-1, "tour4", "Wien", "Graz", "ABC", "Auto", 0, null);

    }

    @Test
    public void addTourTest(){
        int answer = 0;
        try {
            answer = business.addTour(tour);
            assertTrue(false, "Exception does not work in addTour");

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(e instanceof Exception, "Exception works in addTour");

        }
        assertTrue(answer >= 0, "Tour id is not a vaild number");

    }

    @Test
    public void TourOriginTest() {
        business.deleteTour(tour.getName());

        Tour tourEmptyOrigin = new Tour(-1, "Name", "", "Damaskus", "ABC", "Auto", 0, null);

        int answer2 = -1;
        try {
            answer2 = business.addTour(tourEmptyOrigin);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(e instanceof Exception, "Exception works in TourOriginTest");

        }
        assertTrue(answer2 < 0, "Tour was added in the DB with an empty Origin");

    }
    @Test
    public void TourDestinationTest() {
        business.deleteTour(tour.getName());

        Tour tourEmptyDestination = new Tour(-1, "Name", "Damaskus", "", "ABC", "Auto", 0, null);

        int answer2 = -1;
        try {
            answer2 = business.addTour(tourEmptyDestination);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(e instanceof Exception, "Exception works in TourDestinationTest");

        }
        assertTrue(answer2 < 0, "Tour was added in the DB with an empty Destination");

    }

    @Test
    public void deleteTourTest(){
        business.deleteTour("tour4");
        assertNull(business.getTourByName("tour4"), "Tour was not deleted");

    }

    @DisplayName("Log testing")
    @Test
    public void getLogTest(){
        //TODO
    }
    @Test
    public void addLogTest(){

        //get Tour:
        Tour checkTour = business.getTourByName(tour.getName());
        //Define log
        LocalDate date = LocalDate.parse("2022-02-01");
        LocalTime time = LocalTime.parse("12:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "1h";
        int rating = 4;
        Log log = new Log(-2, date, time, comment, difficulty, totalTime, rating);
        try {
            business.addLog(checkTour, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(business.getLogs(-1),tour.getLogs(), "tour has no logs");


    }
    @Test
    public void editLogTest() {

        //Define log
        LocalDate date = LocalDate.parse("2019-09-04");
        LocalTime time = LocalTime.parse("21:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "3h";
        int rating = 5;
        Log log = new Log(-1, date, time, comment, difficulty, totalTime, rating );

        //get Tour:
        Tour checkTour = business.getTourByName(tour.getName());

        // Add log
        try {
            int id = business.addLog(checkTour, log);
            log.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Modify log
        log.setComment("This is another comment");
        assertEquals("This is another comment", log.getComment(), "There is a problem with setters in Log Class. Variable comment is not set");

        try {
            business.updateLog(log, checkTour.getId());
        }catch (Exception e) {
            System.out.println(e);
        }

        Log checkLog = business.getLogById(log.getId());


        //assert
        assertEquals(checkLog.getComment(), log.getComment(), "Comment was not updated");
    }

    @Test
    public void deleteLogTest() {
        //Define log
        LocalDate date = LocalDate.parse("2019-09-04");
        LocalTime time = LocalTime.parse("21:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "3h";
        int rating = 5;
        Log log = new Log(-1, date, time, comment, difficulty, totalTime, rating );

        //get Tour:
        Tour checkTour = business.getTourByName(tour.getName());

        // Add log
        try {
            int id = business.addLog(checkTour, log);
            log.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // asserts
        try {
            assertEquals(true, business.deleteLog(log.getId()), "business.DeleteLog() has failed");
            assertFalse(business.deleteLog(log.getId()), "The test gives back true but the log is not present in the DB");
            // In the last assert we expect an Exception, we will also test for the exception:
            assertTrue(false); // this line of code should not be run
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(e instanceof Exception);
        }

    }

}
