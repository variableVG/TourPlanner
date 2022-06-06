package BusinessLayerTest;

import BusinessLayer.BusinessLayer;
import DataAccessLayer.*;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;
import javafx.util.converter.LocalDateStringConverter;
import org.bouncycastle.asn1.cms.Time;
import org.bouncycastle.operator.bc.BcSignerOutputStream;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
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
    public void addTourTestWithMock() {
        IDataAccessLayer d = EasyMock.createMock(IDataAccessLayer.class);
        business = new BusinessLayer(d);

        try {
            EasyMock.expect(d.addTour(tour)).andReturn(1);
            EasyMock.replay(d);
            assertEquals(business.addTour(tour), 1, "Tour has not be added correctly");
            EasyMock.verify(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTourByNameTestWithMock() {
        IDataAccessLayer d = EasyMock.createMock(IDataAccessLayer.class);
        business = new BusinessLayer(d);

        try {
            //Check if it gets a tour when a tourName is given.
            EasyMock.expect(d.getTourByName(tour.getName())).andReturn(tour);
            EasyMock.replay(d);
            assertEquals(business.getTourByName("tour4"), tour,
                    "getTourByName does not return the correct tour");
            EasyMock.verify(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTourByIdTestWithMock() {
        IDataAccessLayer d = EasyMock.createMock(IDataAccessLayer.class);
        business = new BusinessLayer(d);

        Tour t = new Tour(10, "getIdTour", "Wien", "Graz", "ABC", "Auto", 0, null);

        try {
            EasyMock.expect(d.getTourById(t.getId())).andReturn(t);
            EasyMock.replay(d);
            assertEquals(business.getTourById(t.getId()), t,
                    "getTourById does not return the correct tour");
            EasyMock.verify(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateOriginTourTest() {
        Tour t = new Tour(-1, "updateOrigin", "Wien", "Graz", "ABC", "Auto", 0, null);

        int id = business.addTour(t);
        t.setId(id);
        //change origin
        t.setOrigin("Linz");
        business.updateTour(t);

        Tour checkTour = business.getTourById(id);

        assertEquals(checkTour.getOrigin(), "Linz", "Origin has not been updated correctly");
        assertNotEquals(checkTour.getOrigin(), "Wien", "Origin has not been updated correctly");

    }

    @Test
    public void updateDestinationTourTest() {
        Tour t = new Tour(-1, "updateDestination", "Wien", "Graz", "ABC", "Auto", 0, null);

        int id = business.addTour(t);
        t.setId(id);
        //change origin
        t.setDestination("Linz");
        business.updateTour(t);

        Tour checkTour = business.getTourById(id);

        assertEquals(checkTour.getDestination(), "Linz", "Origin has not been updated correctly");
        assertNotEquals(checkTour.getDestination(), "Graz", "Origin has not been updated correctly");

    }

    

    @Test
    public void addTourTest(){
        int answer = 0;
        try {
            answer = business.addTour(tour);

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
    public void addLogTest(){
        //System.out.println(tour.getLogs());
        //Define log
        LocalDate date = LocalDate.parse("2022-02-01");
        LocalTime time = LocalTime.parse("12:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "1h";
        int rating = 4;
        Log log = new Log(-2, date, time, comment, difficulty, totalTime, rating);
        try {
           business.addLog(tour, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(tour.getLogs());
        assertNotNull(tour.getLogs());

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
    public void getLogByIdTest(){
        //Define log
        LocalDate date = LocalDate.parse("2022-02-01");
        LocalTime time = LocalTime.parse("12:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "1h";
        int rating = 4;
        Log log = new Log(-2, date, time, comment, difficulty, totalTime, rating);

        business.getLogById(-2);
        assertEquals(-2, log.getId(),"Id is not correct");
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
