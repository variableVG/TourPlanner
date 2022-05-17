package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class EditTourPageModel {

    Tour tour;
    IBusinessLayer business;

    private StringProperty tourName;
    private StringProperty origin;
    private StringProperty destination;
    private StringProperty transportType;
    private StringProperty distance;
    private StringProperty time;
    private StringProperty description;

    public EditTourPageModel(String tourName) {
        business = new BusinessLayer();
        tour = business.getTourByName(tourName);

        //**tour = business.getTourById(id);
    }

    public void updateTour() {
        business.updateTour(tour);
        TourPlannerModel.getInstance().updateTour(tour);
    }


}
