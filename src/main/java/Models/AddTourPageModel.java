package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddTourPageModel {

    private StringProperty tourNameInput = new SimpleStringProperty();
    private StringProperty tourNameOutput = new SimpleStringProperty();

    public StringProperty getTourNameInputProperty(){
        return this.tourNameInput;
    }

    public StringProperty getTourNameOutputProperty(){
        return this.tourNameOutput;
    }

    public void concat(){
        this.tourNameOutput.setValue(this.tourNameInput.getValue());
    }

}
