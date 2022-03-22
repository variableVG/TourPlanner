package Views;

import Models.TourPlannerModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerController  {
    @FXML
    public Label tabTourname;//extends Application
    @FXML
    public AnchorPane descriptionTourTab;
    @FXML
    public ListView routeList;
    @FXML
    private DescriptionTourTabController descriptionTourTabController;

    private TourPlannerApplication tpa = new TourPlannerApplication();
    private TourPlannerModel model = new TourPlannerModel();

    public TourPlannerController() throws IOException {

    }


    @FXML
    public void initialize() throws IOException {
        this.routeList.setItems(model.getTourNames());
        // this.tabTourname.setName

        this.routeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String tourName = routeList.getSelectionModel().selectedItemProperty().getValue().toString();
                tabTourname.setText(tourName);
                descriptionTourTabController.updateTour(model.getTourByName(tourName));

            }
        });

    }


    @FXML
    private Label testTextObjectPlaceholder;

    public void onRouteButtonClick(ActionEvent actionEvent) {
        testTextObjectPlaceholder.setText("here you can see the route aka map, Yeah!");
    }

    public void onDescriptionButtonClick(ActionEvent actionEvent) {
        testTextObjectPlaceholder.setText("here you can see the description of the route, Yippie!\nfrom:\nto:\ntransport type:\ntour distance:\nestimated time:\n");
    }

    public void onLogsButtonClick(ActionEvent actionEvent) {
        testTextObjectPlaceholder.setText("here you can see your logs and logs of other users, Yesssa! (in a table)\n\tdate:\n\ttime:\n\tdistance:");
    }

    public void addTourOnButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        tpa.addRoute(stage);

        /*try {
            this.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /*@Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("add-tour-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500,500);
        stage.setTitle("Tour Planner Test, Yeah!");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setScene(scene);
        stage.show();
    }*/
}