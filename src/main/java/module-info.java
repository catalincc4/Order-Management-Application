module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    exports org.example.presentation;
    opens org.example.presentation to javafx.fxml;
    exports org.example.Model;
    opens org.example.Model to javafx.fxml;
    exports org.example;
    opens org.example to javafx.fxml;
}