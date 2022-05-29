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
import java.util.List;

public class ReportGenerator implements IReportGenerator{
    private Tour tour;
    private List<Tour> tours;
    private String filename;
    private PdfWriter writer;
    private PdfDocument pdf;
    private Document document;


    public ReportGenerator(Tour tour) throws FileNotFoundException {
        this.tour = tour;
        this.filename = tour.getName() + ".pdf";
        this.writer = new PdfWriter(filename);
        this.pdf = new PdfDocument(writer);
        this.document = new Document(pdf);
    }

    public ReportGenerator(List<Tour> tours) throws FileNotFoundException {
        this.tours = tours;
        this.filename = "Summarize-Report.pdf";
        this.writer = new PdfWriter(filename);
        this.pdf = new PdfDocument(writer);
        this.document = new Document(pdf);
    }

    public void generateSummarizeReport() throws IOException{
        document.add(new Paragraph("Summarize-Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK));

        for (Tour tour: tours){
            //name
            document.add(new Paragraph("Tour: " + tour.getName()).setBold());
            //distance
            document.add(new Paragraph("Distance: " + tour.getDistance()));
            //rating & time
            var rating = 0;
            int count = 0;
            var time = 0;
            for(Log log: tour.getLogs()){
                rating += log.getRating();
                time += Integer.parseInt(log.getTotaltime());
                count++;
            }
            double rating2 = (double)rating/(double)count;
            document.add(new Paragraph("Rating: " + rating2));
            double time2 = (double)time/(double)count;
            document.add(new Paragraph("Time: " + time2));
        }

        document.close();

    }

    public void generateReport() throws IOException {
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

        Table logsTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
            logsTable.addHeaderCell(getHeaderCell("Date"));
            //logsTable.addHeaderCell(getHeaderCell("Time"));
            logsTable.addHeaderCell(getHeaderCell("Total Time"));
            logsTable.addHeaderCell(getHeaderCell("Rating"));
            //logsTable.addHeaderCell(getHeaderCell("Difficulty"));
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
