module org.river {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires java.desktop;
    exports org.river;
    opens org.river.controllers to javafx.fxml;
    opens org.river.entities to javafx.base;
}