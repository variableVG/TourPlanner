package PresentationLayer.CSVFileHandler;

import PresentationLayer.Models.Tour;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.opencsv.CSVWriter;

public class CSVFileHandler implements ICSVFileHandler{

    public CSVFileHandler(){}

    @Override
    public void exportTours(List<Tour> tours){
        //how can we define a new file and path (window to select destination and filename)
        File file = new File("csvFile.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            //all attributes here
            String[] header = {"Tourname","Origin","Destination"};
            writer.writeNext(header);

            //maybe here json jackson??
            String[] data1 = {tours.get(0).getName(), tours.get(0).getOrigin(), tours.get(0).getDestination()};
            writer.writeNext(data1);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
