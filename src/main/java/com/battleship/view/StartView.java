package com.battleship.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * Clase encargada de cargar y gestionar la vista de Inicio (Start).
 */
public class StartView {

    /**
     * Carga el archivo FXML y devuelve el nodo raíz.
     * @return Parent (Nodo raíz de la vista)
     * @throws IOException Si no encuentra el archivo FXML
     */
    public Parent getInstance() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/battleship/view/start-view.fxml"));
        return loader.load();
    }
}