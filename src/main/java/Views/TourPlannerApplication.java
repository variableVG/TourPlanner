package Views;

import DataAccessLayer.Database;
import DataAccessLayer.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ControllerFactory factory = new ControllerFactory();//maybe as class variable
        //String fxmlFile = "main-page.fxml";
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "main-page.fxml");
        //FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("main-page.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(Factory.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700,500);
        stage.setTitle("Tour Planner Application");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setScene(scene);
        stage.show();
    }

    public void addRoute(Stage stage) throws IOException{
        ControllerFactory factory = new ControllerFactory();//maybe as class variable
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "add-tour-page.fxml");
        //FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("add-tour-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500,500);
        stage.setTitle("New Tour");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setX(600);
        stage.setY(150);
        stage.setScene(scene);
        stage.show();
    }
    public void addLog(Stage stage) throws IOException{
        ControllerFactory factory = new ControllerFactory();//maybe as class variable
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "add-log-page.fxml");
        //FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("add-log-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500,400);
        stage.setTitle("New Log");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setX(600);
        stage.setY(150);
        stage.setScene(scene);
        stage.show();
    }

    private FXMLLoader getFxmlLoader(ControllerFactory factory, String fxmlFile ) {
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        TourPlannerApplication.class.getResource(fxmlFile),
                        null,
                        new JavaFXBuilderFactory(),
                        controller -> {
                            try {
                                System.out.println(controller.toString());
                                return factory.create(controller);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
        DatabaseConnection.getInstance().close();
    }

    /*public void deleteRoute(Stage stage) {
        ControllerFactory factory = new ControllerFactory();//maybe as class variable
        FXMLLoader fxmlLoader = getFxmlLoader(factory, "delete-tour-page.fxml");
        //FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("add-log-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200,300);
        stage.setTitle("Delete Tour");
        stage.setX(600);
        stage.setY(150);
        stage.setScene(scene);
        stage.show();
    }*/
}