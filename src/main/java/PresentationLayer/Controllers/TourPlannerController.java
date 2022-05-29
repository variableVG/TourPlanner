package PresentationLayer.Controllers;

import PresentationLayer.CSVFileHandler.CSVFileHandler;
import PresentationLayer.CSVFileHandler.ICSVFileHandler;
import PresentationLayer.Models.TourPlannerModel;
import PresentationLayer.ReportGenerator.IReportGenerator;
import PresentationLayer.ReportGenerator.ReportGenerator;
import PresentationLayer.TourPlannerApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TourPlannerController  {

    @FXML public Label tabTourname;//extends Application
    @FXML public AnchorPane descriptionTourTab;
    @FXML public ListView routeList;
    @FXML public AnchorPane logTourTab;
    @FXML public AnchorPane routeTourTab;
    @FXML private DescriptionTourTabController descriptionTourTabController;
    @FXML private LogTourTabController logTourTabController;
    //*!*@FXML private DeleteTourPageController deleteTourPageController;
    @FXML private RouteTourTabController routeTourTabController;
    //durch @FXML wird hier das DescController Objekt verwendet, dass ursprünglich beim
    //"starten" in TourApplication erzeugt wird -> sonst wird ein neues Object erzeugt und macht probleme
    private TourPlannerApplication tpa;
    private TourPlannerModel model;

    public TourPlannerController() {
        this.tpa = new TourPlannerApplication();
        this.model = TourPlannerModel.getInstance();
    }

    @FXML
    public void initialize() throws IOException {
        this.routeList.setItems(model.getTourNames());
        // this.tabTourname.setName
        //option.getChildre().clear() use a cell factory

        this.routeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String tourName = routeList.getSelectionModel().selectedItemProperty().getValue().toString();
                tabTourname.setText(tourName);
                descriptionTourTabController.updateTourTab(model.getTourByName(tourName));
                logTourTabController.updateLogs(model.getTourByName(tourName));
                routeTourTabController.update(model.getTourByName(tourName));
                //deleteTourPageController.updateDeleteTourname(model.getTourByName(tourName));

            }
        });

    }

    public void addTourOnButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        tpa.addRoute(stage);
    }

    public void deleteTourOnButtonClick(ActionEvent event) throws IOException{
        //**we need the tourId here
        String tourName = tabTourname.getText();
        //Source: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Do you want to delete " +  tourName + "?");
        a.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                model.deleteTour(tourName);
            }
        });
    }

    public void editTourOnButtonClick(ActionEvent actionEvent) throws IOException {
        /** When clicking the button "Edit" a new window is open and a new controller is created.
         * To know which tour we need to edit, we pass the tourName to the new window.
         * */
        //**we need the tourId here
        String tourName = tabTourname.getText();
        Stage stage = new Stage();
        tpa.editRoute(stage, tourName, descriptionTourTabController);
    }

    public void generateReportOnButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        IReportGenerator reportGenerator = new ReportGenerator(model.getTourByName(tabTourname.getText()));
        try {
            reportGenerator.generateReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateSummarizeReportOnButtonClick(ActionEvent event) throws FileNotFoundException {
        IReportGenerator reportGenerator = new ReportGenerator(model.getTours());
        try {
            reportGenerator.generateSummarizeReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToursOnButtonClick(ActionEvent event) {
        ICSVFileHandler icsvFileHandler = new CSVFileHandler();
        icsvFileHandler.exportTours(model.getTours());
    }
}