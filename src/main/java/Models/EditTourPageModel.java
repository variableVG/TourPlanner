package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import lombok.Data;

@Data
public class EditTourPageModel {

    Tour tour;
    IBusinessLayer business;

    public EditTourPageModel(String tourName) {
        business = new BusinessLayer();
        tour = business.getTourByName(tourName);
    }
}
