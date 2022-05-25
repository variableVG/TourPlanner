package DataLayerTest;

import DataAccessLayer.Database;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.LogTourTabModel;
import PresentationLayer.Models.Tour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;



public class DatabaseTest {
    static private Tour tour;

    @BeforeAll
    public static void setUpAll() {
        //Simulate user input
        //Source: https://www.logicbig.com/how-to/junit/java-test-user-command-line-input.html
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);

    }

    @BeforeEach
    public void setUp(){

        tour = new Tour("tour4", "ABC", "Wien", "Graz", "Auto", "1000Km", "2 Stunden");

    }
    @Test
    public void getTourByIdTest() {
        Tour checkTour = Database.getTourByName(tour.getName());
        System.out.println("Checktour Id is " + checkTour.getId());

        Tour checkTour2 = Database.getTourById(checkTour.getId());
        System.out.println("Chektour Id is " + checkTour2.getId());

        assertEquals(checkTour.getId(), checkTour2.getId(), "getTourById() does not return the correct tour");


    }

    @Test
    public void test() {
        //Define log
        LocalDate date = LocalDate.parse("2019-09-04");
        LocalTime time = LocalTime.parse("21:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "3h";
        int rating = 5;
        Log log = new Log(-1, date, time, comment, difficulty, totalTime, rating );


    }

}
