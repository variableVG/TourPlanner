package Views;

import Models.AddTourPageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddTourPageController {

    @FXML public TextField tourName;
    @FXML public TextField origin;
    @FXML public TextField destination;
    @FXML public TextField transportType;
    @FXML public TextField distance;
    @FXML public TextField time;
    @FXML public TextArea description;
    @FXML private AddTourPageModel model = new AddTourPageModel();

    //@FXML private TextField tourNameInputTextField;
    //@FXML public Label tourNameOutputLabel;
    //@FXML private TextArea tourDescriptionTextArea;


    @FXML
    public void initialize(){
        this.tourName.textProperty().bindBidirectional(model.getTourName());
        this.origin.textProperty().bindBidirectional(model.getOrigin());
        this.destination.textProperty().bindBidirectional(model.getDestination());
        this.transportType.textProperty().bindBidirectional(model.getTransportType());
        this.distance.textProperty().bindBidirectional(model.getDistance());
        this.time.textProperty().bindBidirectional(model.getTime());
        this.description.textProperty().bindBidirectional(model.getDescription());
        //this.tourDescriptionTextArea.textProperty().bindBidirectional(model.getTourNameOutputProperty());
    }

    public void addTourOnClick(ActionEvent actionEvent){
        System.out.println("addTourOnClick has been pressed");
        System.out.println(tourName);
        model.addTour();

    }

}
