package PresentationLayer.ReportGenerator;

import PresentationLayer.Models.Tour;

import java.io.IOException;
import java.util.List;

public interface IReportGenerator {
    void generateReport(Tour tour) throws IOException;
    void generateSummarizeReport(List<Tour> tours) throws IOException;
}
