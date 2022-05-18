package Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import Map.ApiMap;
import Map.MapRequest;
import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Data
public class RouterTourTabModel {


    IBusinessLayer business;
    //private CompletableFuture<ApiMap> completableFutureApiMap;
    Tour tour;

    public RouterTourTabModel() {
        //this.setInitialMapImage();
        business = new BusinessLayer();
        this.tour = null;

    }

    public CompletableFuture<ApiMap> requestRouteAPI(Tour tour) throws Exception {
        CompletableFuture<ApiMap> mapImage = null;
        if(tour == null) {
            throw new Exception("please select a tour");
        }
        else {
            this.tour = tour;
        }

        try {
            mapImage = business.getMap(tour);
            System.out.println("Map Image in The model is ");
            System.out.println(mapImage);

        }catch (Exception e) {
            System.out.println("There is an exception in requestRouteAPI in RouterTourTabModel");
            System.out.println(e);
        }

        return mapImage;

    }



}
