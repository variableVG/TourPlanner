package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class AddLogPageModel {
    Tour tour;
    private IBusinessLayer business = new BusinessLayer();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty totalTime = new SimpleStringProperty();
    private StringProperty rating = new SimpleStringProperty();

    public AddLogPageModel(Tour tour) {
        this.tour = tour;
    }


    public void addLog() {

        Log log = new Log(this.date.getValue(), this.time.getValue(), this.comment.getValue(),
                Integer.parseInt(this.difficulty.getValue()), this.totalTime.getValue(),
                Integer.parseInt(this.rating.getValue()));

        business.addLog(this.tour, log);

        //TODO: Actualize the frontend in TourPlannerModel

    }
}
