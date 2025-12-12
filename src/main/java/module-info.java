module com.com.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.battleship.main to javafx.fxml;
    opens com.battleship.controller to javafx.fxml;

    exports com.battleship.main;
}