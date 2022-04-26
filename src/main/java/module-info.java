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

    opens Views to javafx.fxml;
    exports Views;
}