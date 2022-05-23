package PresentationLayer.ReportGenerator;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import PresentationLayer.Models.Tour;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import com.itextpdf.layout.Document;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReportGenerator implements IReportGenerator{
    private Tour tour;
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

    public void generateReport() throws IOException {
        Paragraph loremIpsumHeader = new Paragraph("Lorem Ipsum header...")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.RED);
        document.add(loremIpsumHeader);
        document.add(new Paragraph("Name: " + tour.getName()));

        document.close();

    }

}
