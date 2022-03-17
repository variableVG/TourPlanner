package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700,500);
        stage.setTitle("Tour Planner Application");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setScene(scene);
        stage.show();
    }

    public void addRoute(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("add-tour-page.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("add-log-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500,500);
        stage.setTitle("New Log");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setX(600);
        stage.setY(150);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}