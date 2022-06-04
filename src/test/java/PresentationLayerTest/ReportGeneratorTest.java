package PresentationLayerTest;

import PresentationLayer.Models.Log;
import PresentationLayer.Models.Tour;
import PresentationLayer.ReportGenerator.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReportGeneratorTest {

    private static ReportGenerator reportGenerator;
    private static List<Tour> tours;

    @BeforeAll
    public static void setUp() {
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);

        reportGenerator = new ReportGenerator();
        tours = new ArrayList<>();
        Tour tour1 = new Tour(-1, "tour1", "Wien", "Graz", "Macarena", "Auto", 0, null);
        Tour tour2 = new Tour(-2, "tour2", "Wien", "Linz", "Asereje", "Auto", 0, null);

        LocalDate date = LocalDate.parse("2022-02-01");
        LocalTime time = LocalTime.parse("12:00");
        String comment = "This is a comment";
        int difficulty = 1;
        String totalTime = "10:00";
        int rating = 4;
        Log log11 = new Log(1, date, time, comment, difficulty, totalTime, rating);
        tour1.getLogs().add(log11);

        LocalDate date2 = LocalDate.parse("2022-02-01");
        LocalTime time2 = LocalTime.parse("12:00");
        String comment2 = "This is a comment";
        int difficulty2 = 1;
        String totalTime2 = "09:21";
        int rating2 = 4;
        Log log12 = new Log(1, date2, time2, comment2, difficulty2, totalTime2, rating2);
        tour1.getLogs().add(log12);

        tours.add(tour1);
        tours.add(tour2);

    }

    @Test
    public void generateSummarizeReportTest() {
        try {
            reportGenerator.generateSummarizeReport(tours);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
