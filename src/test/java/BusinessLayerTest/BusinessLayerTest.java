package BusinessLayerTest;

import BusinessLayer.BusinessLayer;
import PresentationLayer.Models.Tour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class BusinessLayerTest {
    static private Tour tour;
    static private BusinessLayer business;
    @BeforeAll
    static public void setUp(){
        tour = new Tour("tour1", "ABC", "Wien", "Graz", "Auto", "1000Km", "2 Stunden");
        business = new BusinessLayer();

    }
    @Test
    public void addTourTest() throws Exception {
        int answer = business.addTour(tour);
        assertTrue(answer >= 0, "Tour id is not a vaild number");


    }
}
