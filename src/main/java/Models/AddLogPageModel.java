package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import lombok.Data;


import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;


@Data
public class AddLogPageModel {
    Tour tour;
    private IBusinessLayer business = new BusinessLayer();
    private Property<LocalDate> date = new SimpleObjectProperty<>();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty totalTime = new SimpleStringProperty();
    private StringProperty rating = new SimpleStringProperty();

    public AddLogPageModel(Tour tour) {
        this.tour = tour;
    }


    public void addLog() {

        if(validateFields()) {
            Log log = new Log(-1, date.getValue(), this.time.getValue(), this.comment.getValue(),
                    Integer.parseInt(this.difficulty.getValue()), this.totalTime.getValue(),
                    Integer.parseInt(this.rating.getValue()));

            this.tour.getLogs().add(log);
            TourPlannerModel.getInstance().updateTour(tour);
            business.addLog(this.tour, log);
        }

    }

    private boolean validateFields() {
        /**
         * */
        if(this.date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setContentText("The field date cannot be empty ");
            alert.show();
            return false;
        }
        return true;
    }


}
