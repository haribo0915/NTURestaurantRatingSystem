module org.river {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    exports org.river;
    opens org.river.controllers to javafx.fxml;
}