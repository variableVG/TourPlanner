package DataLayerTest;

import DataAccessLayer.Database;
import PresentationLayer.Models.Log;
import PresentationLayer.Models.LogTourTabModel;
import PresentationLayer.Models.Tour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class DatabaseTest {
    static private Tour tour;

    @BeforeAll
    public static void setUpAll() {
        //Simulate user input
        //Source: https://www.logicbig.com/how-to/junit/java-test-user-command-line-input.html
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);

    }

    @BeforeEach
    public void setUp(){

        tour = new Tour(-1, "tour4", "Wien", "Graz","ABC", "Auto", 0, null);

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
    public void addTourTest() {
        Database.deleteTour(tour.getName());
        int id = Database.addTour(tour);
        assertTrue(id >= 0, "addTourTest() returns an invalid tour-id");
    }

    @Test
    public void searchTextTest() {
        Tour tour1 = new Tour(-1, "tour1", "Wien", "Graz","ABC", "Auto", 0, null);
        Tour tour2 = new Tour(-1, "tour2", "Graz", "Salzburg","Macarena", "Walking", 0, null);
        Tour tour3 = new Tour(-1, "tour3", "Salzburg", "Linz","Asereje", "Bicycle", 0, null);

        Database.deleteTour(tour1.getName()); Database.deleteTour(tour2.getName()); Database.deleteTour(tour3.getName());

        int id1 = Database.addTour(tour1); tour1.setId(id1);
        int id2 = Database.addTour(tour2); tour2.setId(id2);
        int id3 = Database.addTour(tour3); tour3.setId(id3);

        List<Tour> foundTours = Database.searchText("tour1");
        assertEquals(foundTours.get(0).getName(), tour1.getName());

        foundTours.clear(); foundTours = Database.searchText("Graz");
        assertEquals(foundTours.get(0).getName(), tour1.getName());
        assertEquals(foundTours.get(1).getName(), tour2.getName());

        foundTours.clear(); foundTours = Database.searchText("Asereje");
        assertEquals(foundTours.get(0).getName(), tour3.getName());

        //Test Search in Logs

        Log log1 = new Log(-1, null, null, "Despacito", 2, null, 0);
        Database.addLog(tour1.getId(), log1);
        Log log2 = new Log(-1, null, null, "Macarena", 1, null, 0);
        Database.addLog(tour2.getId(), log2);

        foundTours.clear(); foundTours = Database.searchText("Despacito");
        assertEquals(tour1.getId(), foundTours.get(0).getId());

        foundTours.clear(); foundTours = Database.searchText("easy");
        assertEquals(tour2.getId(), foundTours.get(0).getId());



    }



}
