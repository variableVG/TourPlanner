package PresentationLayer.CSVFileHandler;

import PresentationLayer.Models.Tour;

import java.util.List;

public interface ICSVFileHandler {
    void exportTours(List<Tour> tours);
    List<Tour> importTours();
}
