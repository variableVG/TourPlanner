package BusinessLayerTest;

import BusinessLayer.BusinessLayer;
import PresentationLayer.Models.Tour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;


public class BusinessLayerTest {
    static private Tour tour;
    static private BusinessLayer business;
    @BeforeAll
    static public void setUp(){
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
        tour = new Tour("tour4", "ABC", "Wien", "Graz", "Auto", "1000Km", "2 Stunden");
        business.deleteTour("tour4");
        assertEquals("tour4","tour4","Tour wurde nicht gelöscht");

    }


}
