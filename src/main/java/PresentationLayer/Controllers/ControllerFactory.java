package PresentationLayer.Controllers;

import PresentationLayer.Models.TourPlannerModel;
import lombok.Data;

@Data
public class ControllerFactory {

    //all models that have a controller
    private final TourPlannerModel tourPlannerModel;
    private String tourName;
    private int logId;
    DescriptionTourTabController descriptionTourTabController;
    LogTourTabController logTourTabController;

    public ControllerFactory() {
        this.tourPlannerModel = TourPlannerModel.getInstance();

    }

    public Object create(Class controllerClass) throws Exception{
        if (controllerClass == TourPlannerController.class ) {
            return new TourPlannerController();
        }else if (controllerClass == AddTourPageController.class) {
            return new AddTourPageController();
        }else if (controllerClass == AddLogPageController.class) {
            return new AddLogPageController(tourPlannerModel.getTourByName(tourName), logTourTabController);
        }else if(controllerClass == DescriptionTourTabController.class){
            return new DescriptionTourTabController(tourPlannerModel.getTourByName(tourName));
        }else if(controllerClass == RouteTourTabController.class){
            return new RouteTourTabController();
        }else if(controllerClass == LogTourTabController.class){
            return new LogTourTabController(tourPlannerModel.getTourByName(tourName));
        }/*else if(controllerClass == DeleteTourPageController.class){
            return new DeleteTourPageController();
        }*/else if(controllerClass == EditTourPageController.class){
            return new EditTourPageController(tourName, descriptionTourTabController);
            //**return new EditTourPageController(tourId);
        }else if(controllerClass == EditLogPageController.class) {
            return new EditLogPageController(tourName, logId, logTourTabController);
        }
        return null;
    }



}
