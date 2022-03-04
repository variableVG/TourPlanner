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
        //change here
        Scene scene = new Scene(fxmlLoader.load(), 500,500);
        stage.setTitle("Tour Planner Test, Yeah!");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        //change here
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}