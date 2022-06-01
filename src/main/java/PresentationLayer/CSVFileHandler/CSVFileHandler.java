package PresentationLayer.CSVFileHandler;

import PresentationLayer.Models.Tour;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVFileHandler implements ICSVFileHandler{

    public CSVFileHandler(){}

    private String loadCSVFilePathnameConfiguration(){
        try {
            Properties appProperties = new Properties();
            appProperties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("app.properties"));

            return appProperties.getProperty("csvfilepathname");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void exportTours(List<Tour> tours){
        //nicht JFileChooser aber JavaFX FileChooser
        //how can we define a new file and path (window to select destination and filename)
        File file = new File(loadCSVFilePathnameConfiguration());
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            //all attributes here except childfriendliness and popularity, bacause we can't insert into db and we also calculate it, no id too, not shure about distance and time
            String[] header = {"Name","Description","Origin","Destination","TransportType","Distance","EstimatedTime"};
            writer.writeNext(header);

            //insert data
            for(Tour tour: tours){
                writer.writeNext(
                        new String[]{
                                tour.getName(),
                                tour.getDescription(),
                                tour.getOrigin(),
                                tour.getDestination(),
                                tour.getTransportType(),
                                String.valueOf(tour.getDistance()),//typecasting
                                tour.getTime()
                        }
                );
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tour> importTours() {
        File file = new File(loadCSVFilePathnameConfiguration());
        List<Tour> tours = new ArrayList<>();

        try{
            FileReader inputfile = new FileReader(file);
            CSVReader reader = new CSVReader(inputfile);
            String[] nextLine;

            //skip first line
            nextLine = reader.readNext();
            //loop other lines
            while((nextLine = reader.readNext()) != null){
                List<String> singleTourElements = new ArrayList<>(Arrays.asList(nextLine));

                tours.add(
                        new Tour(
                                0,//id is garbage
                                singleTourElements.get(0),
                                singleTourElements.get(1),
                                singleTourElements.get(2),
                                singleTourElements.get(3),
                                singleTourElements.get(4),
                                Float.parseFloat(singleTourElements.get(5)),
                                singleTourElements.get(6)
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tours;
    }
}
