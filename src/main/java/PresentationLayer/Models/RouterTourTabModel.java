package PresentationLayer.Models;

import BusinessLayer.BusinessLayer;
import BusinessLayer.IBusinessLayer;
import lombok.Data;

import java.util.concurrent.CompletableFuture;


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

    public void requestRouteAPI(Tour tour) throws Exception {
        if(tour == null) {
            throw new Exception("please select a tour");
        }
        else {
            this.tour = tour;
        }

        business.getMap(tour);

        try {
            //business.getMap(tour);
            //System.out.println("BusinessLayer.Map Image in The model is ");
            //System.out.println(mapImage);

        }catch (Exception e) {
            System.out.println("There is an exception in requestRouteAPI in RouterTourTabModel");
            System.out.println(e);
        }

        //return mapImage;

    }



}
