package PresentationLayer.ReportGenerator;

import BusinessLayer.Map.ApiDirections;
import BusinessLayer.Map.ApiMap;
import BusinessLayer.Map.MapRequest;
import PresentationLayer.Models.Log;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import PresentationLayer.Models.Tour;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ReportGenerator implements IReportGenerator{

    public ReportGenerator(){}

    @Override
    public void generateSummarizeReport(List<Tour> tours) throws IOException{
        String filename = "Summarize-Report.pdf";
        PdfWriter writer = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Summarize-Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK));

        for (Tour tour: tours){
            //name
            document.add(new Paragraph("Tour: " + tour.getName()).setBold());
            //distance
            document.add(new Paragraph("Distance: " + tour.getDistance() + " km"));
            //total logs:
            document.add(new Paragraph("Number of logs: " + tour.getLogs().size()));
            //rating & time
            int rating = 0;
            int ratingCount = 0;
            int timeCount = 0;
            Duration totalTime = Duration.ZERO;
            for(Log log: tour.getLogs()){
                if(log.getRating() != null) { rating += log.getRating();  ratingCount++;}
                if(log.getTotaltime() != null) {
                    //Parse String totalTime to get Hours and Minutes
                    String timeString = log.getTotaltime();
                    String[] arrOfStr = timeString.split(":");
                    int hours = Integer.parseInt(arrOfStr[0]);
                    int minutes = Integer.parseInt(arrOfStr[1]);

                    //add result to total time
                    totalTime = totalTime.plusHours(hours);
                    totalTime = totalTime.plusMinutes(minutes);

                    timeCount++;
                }
            }

            if(ratingCount > 0) {
                double ratingFinal = (double)rating/(double)ratingCount;
                document.add(new Paragraph("Average Rating: " + ratingFinal));
            }

            //add total Time:
            // I have to check first, if timeCount is 0, since it cannot perform division by 0.
            // If timeCount = 0, then no totalTime has been registered for the logs.
            if(timeCount > 0) {
                totalTime = totalTime.dividedBy(timeCount);
                String totalTimeString = String.valueOf(totalTime.toHoursPart()) + ":" +
                        String.valueOf(totalTime.toMinutesPart()) + ":" +
                        String.valueOf(totalTime.toSecondsPart());

                document.add(new Paragraph("Average Total Time: " + totalTimeString));
            }
            else { document.add(new Paragraph("Average Total Time: NaN")); }

        }

        document.close();

    }

    @Override
    public void generateReport(Tour tour) throws IOException {
        String filename = tour.getName() + ".pdf";
        PdfWriter writer = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph(tour.getName())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK));

        document.add(new Paragraph("From: " + tour.getOrigin() + "\tTo: " + tour.getDestination()));
        document.add(new Paragraph("Distance: " + tour.getDistance() + "\tTime: " + tour.getTime()));
        document.add(new Paragraph("TransportType: " + tour.getTransportType() + "\t(some more attributes: ...) "));
        document.add(new Paragraph("Description: " + tour.getDescription()));

        //get Image Map
        tour.getFutureImageMap().thenApply(
                futureImage -> {
                    //convert bufferedimage to bytearray
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(futureImage, "png", baos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] bytes = baos.toByteArray();
                    ImageData tourImage = ImageDataFactory.create(bytes);
                    document.add(new Image(tourImage));
                    return null;
                }
        );


        document.add(new Paragraph("Logs")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK));

        Table logsTable = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
            logsTable.addHeaderCell(getHeaderCell("Date"));
            logsTable.addHeaderCell(getHeaderCell("Time"));
            logsTable.addHeaderCell(getHeaderCell("Total Time"));
            logsTable.addHeaderCell(getHeaderCell("Rating"));
            logsTable.addHeaderCell(getHeaderCell("Difficulty"));
            logsTable.addHeaderCell(getHeaderCell("Comment"));

        for(Log log : tour.getLogs()){
            //check date and time
            String date = ""; String time = ""; String totalTime = ""; String rating = ""; String difficulty ="";
            String comment ="";

            if(log.getDate() != null) { date = log.getDate().toString(); }
            if(log.getTime() != null) { time = log.getTime().toString(); }
            if(log.getTotaltime() != null) { totalTime = log.getTotaltime(); }
            if(log.getRating() != null) { rating = log.getRating().toString(); }
            if(log.getDifficulty() != null) {difficulty = log.getDifficulty().toString(); }
            if(log.getComment() != null) {comment = log.getComment(); }

            logsTable.addCell(date);
            logsTable.addCell(time);
            logsTable.addCell(totalTime);
            logsTable.addCell(rating);
            logsTable.addCell(difficulty);
            logsTable.addCell(comment);
        }
        document.add(logsTable);

        document.close();
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }

}
