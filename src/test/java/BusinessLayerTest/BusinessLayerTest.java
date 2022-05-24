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
    @BeforeEach
    public void setUp(){
        //Simulate user input
        //Source: https://www.logicbig.com/how-to/junit/java-test-user-command-line-input.html
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);


        tour = new Tour("tour4", "ABC", "Wien", "Graz", "Auto", "1000Km", "2 Stunden");
        business = new BusinessLayer();

    }
    @Test
    public void addTourTest() throws Exception {
        int answer = business.addTour(tour);
        assertTrue(answer >= 0, "Tour id is not a vaild number");

    }
    @Test
    public void deleteTourTest() throws Exception{
        business.deleteTour("tour4");
        assertEquals("tour4","tour4","Tour wurde nicht gelöscht");

    }

    @DisplayName("Log testing")
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

        // Add log
        try {
            business.addLog(tour, log);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Modify log
        log.setComment("This is another comment");
        business.updateLog(log, tour.getId());

        //assert
        assertEquals("This is another comment", log.getComment(), "Comment was not updated");
    }


}
