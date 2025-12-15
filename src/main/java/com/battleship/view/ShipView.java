package com.battleship.view;

import com.battleship.model.Ship;
import com.battleship.model.ShipType;
import com.battleship.view.shipDrawers.*;
import javafx.scene.Group;

public class ShipView extends Group {
    private Ship ship;
    private ShipDrawer drawer;
    private boolean isHighlighted = false;

    public ShipView(Ship ship) {
        this.ship = ship;
        this.drawer = createDrawer(ship.getType());
        drawShip();
    }

    private ShipDrawer createDrawer(ShipType type) {
        switch (type) {
            case PORTAAVIONES:
                return new PortaavionesDrawer();
            case SUBMARINO:
                return new SubmarinoDrawer();
            case DESTRUCTOR:
                return new DestructorDrawer();
            case FRAGATA:
                return new FragataDrawer();
            default:
                throw new IllegalArgumentException("Tipo de barco desconocido: " + type);
        }
    }

    private void drawShip() {
        getChildren().clear();

        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();

        // ← USAR GridConfig.CELL_SIZE en lugar de 40
        double width = isHorizontal ? size * GridConfig.CELL_SIZE : GridConfig.CELL_SIZE;
        double height = isHorizontal ? GridConfig.CELL_SIZE : size * GridConfig.CELL_SIZE;

        drawer.draw(this, width, height, isHorizontal);
    }

    public Ship getShip() {
        return ship;
    }

    public void setHighlight(boolean highlighted) {
        this.isHighlighted = highlighted;
        if (highlighted) {
            setOpacity(0.7);
        } else {
            setOpacity(1.0);
        }
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void updateOrientation() {
        drawShip();
    }

    public void refresh() {
        drawShip();
    }
}
