package Views;

import Models.AddLogPageModel;
import Models.AddTourPageModel;
import Models.Tour;
import Models.TourPlannerModel;
import lombok.Data;

@Data
public class ControllerFactory {

    //all models that have a controller
    private final TourPlannerModel tourPlannerModel;
    private final AddTourPageModel addTourPageModel;
    private String tourName;
    //private final AddLogPageModel addLogPageModel;
    //private final DescriptionTourTabModel descriptionTourTabModel;
    //private final RouteTourTabModel routeTourTabModel;
    //private final LogTourTabModel logTourTabModel;
    //private final DeleteTourPageController deleteTourPageController;

    public ControllerFactory() {
        this.tourPlannerModel = TourPlannerModel.getInstance();
        this.addTourPageModel = new AddTourPageModel();

    }

    public Object create(Class controllerClass) throws Exception{
        if (controllerClass == TourPlannerController.class ) {
            return new TourPlannerController();
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
        }else if(controllerClass == DeleteTourPageController.class){
            return new DeleteTourPageController();
        }
        else if(controllerClass == EditTourPageController.class){
            return new EditTourPageController(tourName);
        }
        return null;
    }
}
