package Views;

import Models.AddTourPageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddTourPageController {

    private AddTourPageModel model = new AddTourPageModel();

    @FXML
    private TextField tourNameInputTextField;
    @FXML
    public Label tourNameOutputLabel;

    @FXML
    public void initialize(){
        this.tourNameInputTextField.textProperty().bindBidirectional(model.getTourNameInputProperty());
        this.tourNameOutputLabel.textProperty().bindBidirectional(model.getTourNameOutputProperty());
    }

    public void addTourNameOnClick(ActionEvent actionEvent){
        this.model.concat();
    }

}
