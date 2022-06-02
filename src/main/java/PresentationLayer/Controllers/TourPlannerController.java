package PresentationLayer.Controllers;

import PresentationLayer.CSVFileHandler.CSVFileHandler;
import PresentationLayer.CSVFileHandler.ICSVFileHandler;
import PresentationLayer.Models.Tour;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TourPlannerController  {

    @FXML public VBox parent;
    private boolean isLightMode = true;

    @FXML public Label tabTourname;//extends Application
    @FXML public AnchorPane descriptionTourTab;
    @FXML public ListView routeList;
    @FXML public AnchorPane logTourTab;
    @FXML public AnchorPane routeTourTab;
    @FXML public TextField searchTextTextField;
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
    public void initialize() {
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
        IReportGenerator reportGenerator = new ReportGenerator();
        try {
            reportGenerator.generateReport(model.getTourByName(tabTourname.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateSummarizeReportOnButtonClick(ActionEvent event) throws FileNotFoundException {
        IReportGenerator reportGenerator = new ReportGenerator();
        try {
            reportGenerator.generateSummarizeReport(model.getTours());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToursOnButtonClick(ActionEvent event) {
        ICSVFileHandler icsvFileHandler = new CSVFileHandler();
        icsvFileHandler.exportTours(model.getTours());
    }

    public void importToursOnButtonClick(ActionEvent event) {
        ICSVFileHandler icsvFileHandler = new CSVFileHandler();
        List<Tour> tours = icsvFileHandler.importTours();
        if(tours != null){
            model.addTourList(tours);
        }else{
            System.out.println("CSV file is null (empty)");
        }
    }
    /*public void searchTextOnButtonClick(ActionEvent actionEvent) {

        //warum nicht einfach model.getAllTours(this.searchTextTextField.getText())

        if(!this.searchTextTextField.getText().isEmpty()) {
            model.searchText(this.searchTextTextField.getText());
            initialize();
        }
        else { //If the searchFiedl is empty, then reset the list with all values in the DB
            model.getAllTours();
        }

    }*/

    public void searchTextOnButtonClick(ActionEvent actionEvent){
        model.getAllTours(this.searchTextTextField.getText());
    }

    public void changeTheme(ActionEvent event) {
        isLightMode = !isLightMode;
        if(isLightMode){
            setLightMode();
        }else{
            setDarkMode();
        }
        //System.out.println(parent.getStylesheets().toString());
    }

    private void setLightMode(){
        File f = new File("src/main/resources/PresentationLayer/Styles/darkMode.css");
        parent.getStylesheets().remove("file:///" + f.getAbsolutePath().replace("\\", "/"));

        f = new File("src/main/resources/PresentationLayer/Styles/lightMode.css");
        parent.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }

    private void setDarkMode(){
        File f = new File("src/main/resources/PresentationLayer/Styles/lightMode.css");
        parent.getStylesheets().remove("file:///" + f.getAbsolutePath().replace("\\", "/"));

        f = new File("src/main/resources/PresentationLayer/Styles/darkMode.css");
        parent.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }
}