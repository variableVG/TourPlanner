module main.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
    //requires jackson.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.swing;
    requires org.json;
    requires org.apache.logging.log4j;
    requires layout;
    requires kernel;
    requires io;

    opens PresentationLayer.Controllers to javafx.fxml;
    exports PresentationLayer.Controllers;
    exports PresentationLayer;
    opens PresentationLayer to javafx.fxml;
}