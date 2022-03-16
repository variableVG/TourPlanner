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
    @FXML private AddTourPageModel model = new AddTourPageModel();

    //@FXML private TextField tourNameInputTextField;
    //@FXML public Label tourNameOutputLabel;
    //@FXML private TextArea tourDescriptionTextArea;


    @FXML
    public void initialize(){
        this.tourNameTextField.textProperty().bindBidirectional(model.getTourNameInputProperty());
        this.testTourNameLabel.textProperty().bindBidirectional(model.getTourNameOutputProperty());
        //this.tourDescriptionTextArea.textProperty().bindBidirectional(model.getTourNameOutputProperty());
    }

    public void addTourOnClick(ActionEvent actionEvent){
        this.model.concat();
    }

}
