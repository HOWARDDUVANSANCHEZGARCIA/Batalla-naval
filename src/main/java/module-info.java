module com.battleship {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;

    opens com.battleship.controller to javafx.fxml;
    opens com.battleship.view to javafx.fxml;
    opens com.battleship.main to javafx.graphics;

    exports com.battleship.controller;
    exports com.battleship.view;
    exports com.battleship.model;


}
