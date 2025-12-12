package view.shipDrawers;

import javafx.scene.Group;

public interface ShipDrawer {
    void draw(Group parent, double width, double height, boolean isHorizontal);
}
