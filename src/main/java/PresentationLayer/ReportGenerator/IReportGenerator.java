package PresentationLayer.ReportGenerator;

import java.io.IOException;

public interface IReportGenerator {
    void generateReport() throws IOException;
    void generateSummarizeReport() throws IOException;
}
