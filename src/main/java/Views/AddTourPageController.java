package Views;

import Models.AddTourPageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddTourPageController {

    @FXML public TextField tourNameTextField;
    @FXML public Label testTourNameLabel;
    @FXML public TextField fromTextField;
    @FXML private AddTourPageModel model = new AddTourPageModel();

    //@FXML private TextField tourNameInputTextField;
    //@FXML public Label tourNameOutputLabel;
    //@FXML private TextArea tourDescriptionTextArea;


    @FXML
    public void initialize(){
        this.tourNameTextField.textProperty().bindBidirectional(model.getTourName());
        //this.tourDescriptionTextArea.textProperty().bindBidirectional(model.getTourNameOutputProperty());
    }

    public void addTourOnClick(ActionEvent actionEvent){
        System.out.println("addTourOnClick has been pressed");
        System.out.println(tourNameTextField);
        model.addTour();

    }

}
