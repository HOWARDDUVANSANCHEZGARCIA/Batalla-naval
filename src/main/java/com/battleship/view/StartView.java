package com.battleship.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class StartView {

    public Parent getInstance() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/battleship/view/start-view.fxml"));
        return loader.load();
    }
}
