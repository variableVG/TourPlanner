package Views;

import Models.AddLogPageModel;
import Models.AddTourPageModel;
import Models.Tour;
import Models.TourPlannerModel;

public class ControllerFactory {
    private final TourPlannerModel tourPlannerModel;
    private final AddTourPageModel addTourPageModel;
    private final AddLogPageModel addLogPageModel;

    private final Tour tour;

    public ControllerFactory() {
        this.tourPlannerModel = TourPlannerModel.getInstance();
        this.addTourPageModel = new AddTourPageModel();
        this.tour = new Tour("");

    }


    public Object create(Class controllerClass) {
        if (controllerClass == TourPlannerController.class ) {
            //return new TourPlannerModel(this.tourPlannerModel) ;
            return this.tourPlannerModel;
        }
        else if (controllerClass == AddTourPageController.class) {
            return new AddTourPageController(this.addTourPageModel);
        }
        else if (controllerClass == AddLogPageController.class) {
            return new AddLogPageController()
        }

        return null;
    }
}
