package Views;

import Models.AddLogPageModel;
import Models.Tour;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddLogPageController {

    @FXML public TextField date;
    @FXML public TextField time;
    @FXML public TextField comment;
    @FXML public TextField difficulty;
    @FXML public TextField totalTime;
    @FXML public TextField rating;
    //add log page model
    //constructor
    Tour tour;
    AddLogPageModel model;

    AddLogPageController(Tour tour) {
        this.tour = tour;
        model = new AddLogPageModel(tour);

    }
    @FXML
    public void initialize(){

    }


}
