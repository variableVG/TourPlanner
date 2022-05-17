package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import Map.MapRequest;
import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;


@Data
public class RouterTourTabModel {


    IBusinessLayer business;
    //private CompletableFuture<ApiMap> completableFutureApiMap;
    Tour tour;

    public RouterTourTabModel() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        //this.setInitialMapImage();
        business = new BusinessLayer();
        this.tour = null;

    }

    public void requestRouteAPI(Tour tour) {
        if(tour == null) {
            System.out.println("please select a tour");
            return;
        }
        this.tour = tour;
        if(tour.getStaticMap() == null && !tour.getIsAPIrequested()) {
            try {
                System.out.println("In the model I am calling hte businnes");
                business.getMap(tour);
                tour.setIsAPIrequested(true);
            }catch (Exception e) {
                System.out.println("There is an exception in requestRouteAPI in RouterTourTabModel");
                System.out.println(e);
            }
        }


    }



}
