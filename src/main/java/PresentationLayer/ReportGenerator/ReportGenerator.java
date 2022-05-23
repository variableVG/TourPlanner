package PresentationLayer.ReportGenerator;

//import com.itextpdf.io.font.constants.StandardFonts;
//import com.itextpdf.io.image.ImageData;
//import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import com.itextpdf.layout.Document;

import java.io.FileNotFoundException;

public class ReportGenerator {
    private String filename;
    PdfWriter writer = new PdfWriter(filename);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    public ReportGenerator() throws FileNotFoundException {
    }
}
