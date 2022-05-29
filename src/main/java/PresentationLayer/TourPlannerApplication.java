package PresentationLayer;

import DataAccessLayer.DatabaseConnection;
import BusinessLayer.Logger.ILoggerWrapper;
import BusinessLayer.Logger.LoggerFactory;
import PresentationLayer.Controllers.ControllerFactory;
import PresentationLayer.Controllers.DescriptionTourTabController;
import PresentationLayer.Controllers.LogTourTabController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerApplication extends Application {

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    ControllerFactory factory = new ControllerFactory();

    public static void main(String[] args) {
        logger.debug("Program start");
        launch();
        DatabaseConnection.getInstance().close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //factory = new ControllerFactory();//maybe as class variable
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "main-page.fxml");
        startScene(fxmlLoader,stage,"Tour Planner Application",700,500,400,400);
    }

    public void addRoute(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "Controllers/add-tour-page.fxml");
        startScene(fxmlLoader,stage,"New Tour",500,500,400,400);
    }

    public void editRoute(Stage stage, String tourName, DescriptionTourTabController descriptionTourTabController) throws IOException {
        factory.setTourName(tourName);
        factory.setDescriptionTourTabController(descriptionTourTabController);
        //**factory.setTourId(tourId);
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "Controllers/editTourPage.fxml");
        startScene(fxmlLoader,stage,"Edit Tour \"" + tourName + "\"",500,500,400,400);
    }

    public void addLog(Stage stage, String tourName, LogTourTabController tabController) throws IOException{
        factory.setTourName(tourName);
        factory.setLogTourTabController(tabController);
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "Controllers/add-log-page.fxml");
        startScene(fxmlLoader,stage,"New Tour Log",500,400,400,400);
    }

    public void editLog(Stage stage, String tourName, int logId, LogTourTabController logTourTabController) throws IOException {
        factory.setTourName(tourName);
        factory.setLogId(logId);
        factory.setLogTourTabController(logTourTabController);
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "Controllers/editLogPage.fxml");
        startScene(fxmlLoader,stage,"Edit Log in Tour \"" + tourName + "\"",500,400,400,400);
    }

    private FXMLLoader getFxmlLoader(ControllerFactory factory, String fxmlFile ) {
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        TourPlannerApplication.class.getResource(fxmlFile),
                        null,
                        new JavaFXBuilderFactory(),
                        controller -> {
                            try {
                                return factory.create(controller);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
        return fxmlLoader;
    }

    private void startScene(FXMLLoader fxmlLoader, Stage stage, String stageName, int height, int width, int minHeight, int minWidth) throws IOException {
        Scene scene = new Scene(fxmlLoader.load(), height, width);
        stage.setTitle(stageName);
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);
        stage.setScene(scene);
        stage.show();
    }
}