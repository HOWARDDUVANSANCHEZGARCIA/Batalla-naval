package com.battleship.view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Draws a Pirate Sailing Ship (Brigantine type - 2 Masts).
 * <p>
 * This class implements the {@link ShipDrawer} interface to render the
 * ship corresponding to the "Destroyer" size (2 cells), but visually
 * represented as a Brigantine to fit the pirate theme.
 * </p>
 */
public class DestructorDrawer implements ShipDrawer {

    // Pirate theme colors
    private final Color WOOD_DARK = Color.web("#3E2723");  // Dark wood (Hull)
    private final Color WOOD_LIGHT = Color.web("#5D4037"); // Light wood (Deck/Details)
    private final Color SAIL_COLOR = Color.web("#EFEBE9"); // Off-white (Sails)
    private final Color FLAG_COLOR = Color.web("#212121"); // Black flag

    /**
     * Draws the ship onto the parent group based on orientation and size.
     *
     * @param parent       The Group container to add the shapes to.
     * @param width        The width of the area to draw in.
     * @param height       The height of the area to draw in.
     * @param isHorizontal True if the ship is horizontal, false if vertical.
     */
    @Override
    public void draw(Group parent, double width, double height, boolean isHorizontal) {
        if (isHorizontal) {
            drawHorizontal(parent, width, height);
        } else {
            drawVertical(parent, width, height);
        }
    }

    /**
     * Helper method to draw the ship horizontally.
     */
    private void drawHorizontal(Group parent, double width, double height) {
        // Use a 200x100 base for calculations (scaling ratio)
        double w = width / 200.0;
        double h = height / 100.0;

        // 1. THE HULL (Dark wood)
        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                10.0 * w, 60.0 * h,   // Stern top
                190.0 * w, 60.0 * h,  // Bow top (Tip)
                170.0 * w, 90.0 * h,  // Bow bottom
                30.0 * w, 90.0 * h    // Stern bottom
        );
        hull.setFill(WOOD_DARK);
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1);
        parent.getChildren().add(hull);

        // 2. HULL DETAIL (Lighter wood stripe)
        Line stripe = new Line(20 * w, 75 * h, 175 * w, 75 * h);
        stripe.setStroke(WOOD_LIGHT);
        stripe.setStrokeWidth(3);
        parent.getChildren().add(stripe);

        // 3. MASTS (Vertical poles)
        // Rear Mast (Lower)
        Rectangle mastRear = new Rectangle(60 * w, 20 * h, 4 * w, 40 * h);
        mastRear.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastRear);

        // Front Mast (Higher)
        Rectangle mastFront = new Rectangle(120 * w, 10 * h, 5 * w, 50 * h);
        mastFront.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastFront);

        // 4. SAILS (Old-style square sails)
        // Rear Sail
        Polygon sailRear = new Polygon(
                64 * w, 25 * h,
                100 * w, 25 * h,
                95 * w, 50 * h,
                64 * w, 50 * h
        );
        sailRear.setFill(SAIL_COLOR);
        sailRear.setStroke(Color.GRAY);
        sailRear.setStrokeWidth(0.5);
        parent.getChildren().add(sailRear);

        // Front Sail
        Polygon sailFront = new Polygon(
                125 * w, 15 * h,
                170 * w, 15 * h,
                165 * w, 45 * h,
                125 * w, 45 * h
        );
        sailFront.setFill(SAIL_COLOR);
        sailFront.setStroke(Color.GRAY);
        sailFront.setStrokeWidth(0.5);
        parent.getChildren().add(sailFront);

        // 5. PIRATE FLAG (On the front mast)
        Polygon flag = new Polygon(
                122 * w, 10 * h,
                100 * w, 15 * h,
                122 * w, 20 * h
        );
        flag.setFill(FLAG_COLOR);
        parent.getChildren().add(flag);
    }

    /**
     * Helper method to draw the ship vertically.
     */
    private void drawVertical(Group parent, double width, double height) {
        // Invert dimensions for vertical drawing
        // Reference base 100x200
        double w = width / 100.0;
        double h = height / 200.0;

        // 1. THE HULL
        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                40.0 * w, 10.0 * h,   // Stern left
                40.0 * w, 190.0 * h,  // Bow (Tip)
                70.0 * w, 170.0 * h,  // Bow right
                70.0 * w, 30.0 * h    // Stern right
        );
        // Adjustment to visually center it better vertically
        hull.setTranslateX(-5 * w);

        hull.setFill(WOOD_DARK);
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1);
        parent.getChildren().add(hull);

        // 2. MASTS
        // Rear
        Rectangle mastRear = new Rectangle(30 * w, 60 * h, 40 * w, 4 * h);
        mastRear.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastRear);

        // Front
        Rectangle mastFront = new Rectangle(20 * w, 120 * h, 50 * w, 5 * h);
        mastFront.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastFront);

        // 3. SAILS (Seen from above they look like inflated rectangles/ellipses)
        // Rear Sail
        Ellipse sailRear = new Ellipse(50 * w, 75 * h, 15 * w, 10 * h);
        sailRear.setFill(SAIL_COLOR);
        sailRear.setStroke(Color.GRAY);
        parent.getChildren().add(sailRear);

        // Front Sail
        Ellipse sailFront = new Ellipse(50 * w, 135 * h, 20 * w, 12 * h);
        sailFront.setFill(SAIL_COLOR);
        sailFront.setStroke(Color.GRAY);
        parent.getChildren().add(sailFront);
    }
}