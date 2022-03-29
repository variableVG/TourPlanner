package Views;

import Models.AddLogPageModel;
import Models.AddTourPageModel;
import Models.Tour;
import Models.TourPlannerModel;

public class ControllerFactory {

    //all models that have a controller
    private final TourPlannerModel tourPlannerModel;
    private final AddTourPageModel addTourPageModel;
    //private final AddLogPageModel addLogPageModel;
    //private final DescriptionTourTabModel descriptionTourTabModel;
    //private final RouteTourTabModel routeTourTabModel;
    //private final LogTourTabModel logTourTabModel;
    private final Tour tour;

    public ControllerFactory() {
        this.tourPlannerModel = TourPlannerModel.getInstance();
        this.addTourPageModel = new AddTourPageModel();
        this.tour = new Tour("");
    }

    public Object create(Class controllerClass) throws Exception{
        if (controllerClass == TourPlannerController.class ) {
            return new TourPlannerController(this.tourPlannerModel);
            //return this.tourPlannerModel;
        }else if (controllerClass == AddTourPageController.class) {
            return new AddTourPageController(this.addTourPageModel);
        }else if (controllerClass == AddLogPageController.class) {
            return new AddLogPageController();
        }else if(controllerClass == DescriptionTourTabController.class){
            return new DescriptionTourTabController();
        }else if(controllerClass == RouteTourTabController.class){
            return new RouteTourTabController();
        }else if(controllerClass == LogTourTabController.class){
            return new LogTourTabController();
        }
        return null;
    }
}
