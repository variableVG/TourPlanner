package BusinessLayer.Map;
;

import java.awt.image.BufferedImage;

import lombok.Data;

@Data
public class ApiMap {
    // https://github.com/hoereth/google-static-map-creator
    // Username: TourPlanner_3
    // Application: TourPlanner
    // Cosumer Key: qcJrJbF1oBJN4pGOFkbG3gNhqLHXPv06
    // Consumer Secret: hAY2hNZvov7m7XYW

    private String value;
    BufferedImage bufferedImageMap;


    public ApiMap(BufferedImage bufferedImageMap) {
        this.bufferedImageMap = bufferedImageMap;
    }




}
