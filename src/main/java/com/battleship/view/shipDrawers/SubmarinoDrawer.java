package com.battleship.view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Draws a Caravel (Carabela - 3 Light Masts).
 * <p>
 * This class implements the {@link ShipDrawer} interface to render the
 * ship corresponding to the "Submarine" size (3 cells), but visually
 * represented as a Caravel to fit the pirate theme.
 * Key feature: The rear sail is triangular (lateen sail).
 * </p>
 */
public class SubmarinoDrawer implements ShipDrawer {

    // Color Palette
    private final Color WOOD_DARK = Color.web("#3E2723");  // Dark Hull
    private final Color WOOD_LIGHT = Color.web("#6D4C41"); // Medium Wood
    private final Color SAIL_COLOR = Color.web("#FFF3E0"); // Cream/Off-white Sail
    private final Color DETAILS = Color.web("#8D6E63");    // Details

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
        // Normalize dimensions. Base 300x100 (3 cells)
        double w = width / 300.0;
        double h = height / 100.0;

        // 1. THE HULL (More curved than the destroyer)
        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                20.0 * w, 50.0 * h,    // Stern top
                280.0 * w, 55.0 * h,   // Bow top (Tip)
                260.0 * w, 85.0 * h,   // Bow bottom
                35.0 * w, 85.0 * h     // Stern bottom
        );
        hull.setFill(WOOD_DARK);
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);

        // 2. POOP DECK (Raised rear structure)
        Polygon poopDeck = new Polygon();
        poopDeck.getPoints().addAll(
                20.0 * w, 50.0 * h,
                90.0 * w, 52.0 * h,
                90.0 * w, 40.0 * h,
                25.0 * w, 40.0 * h
        );
        poopDeck.setFill(WOOD_LIGHT);
        poopDeck.setStroke(Color.BLACK);
        parent.getChildren().add(poopDeck);

        // 3. RAILING / SIDE DETAILS
        Line railing = new Line(25 * w, 55 * h, 270 * w, 60 * h);
        railing.setStroke(DETAILS);
        railing.setStrokeWidth(2);
        parent.getChildren().add(railing);

        // 4. MASTS (3: Mizzen, Main, Foremast)
        // Rear (Mizzen - small)
        Rectangle mastRear = new Rectangle(60 * w, 25 * h, 4 * w, 30 * h);
        mastRear.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastRear);

        // Center (Main - Large)
        Rectangle mastMain = new Rectangle(140 * w, 15 * h, 6 * w, 45 * h);
        mastMain.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastMain);

        // Front (Foremast - Medium)
        Rectangle mastFront = new Rectangle(220 * w, 20 * h, 5 * w, 40 * h);
        mastFront.setFill(WOOD_LIGHT);
        parent.getChildren().add(mastFront);

        // 5. SAILS (Key difference: rear is triangular/lateen)

        // Rear Sail (Triangular - Characteristic of the Caravel)
        Polygon sailRear = new Polygon(
                62 * w, 25 * h,      // Top tip
                85 * w, 50 * h,      // Bottom right tip
                62 * w, 50 * h       // Tip attached to mast
        );
        sailRear.setFill(SAIL_COLOR);
        sailRear.setStroke(Color.GRAY);
        parent.getChildren().add(sailRear);

        // Center Sail (Large Square with optional very faint red cross)
        Polygon sailMain = new Polygon(
                110 * w, 20 * h,
                170 * w, 20 * h,
                165 * w, 50 * h,
                115 * w, 50 * h
        );
        sailMain.setFill(SAIL_COLOR);
        sailMain.setStroke(Color.GRAY);
        parent.getChildren().add(sailMain);

        // Front Sail (Small Square)
        Polygon sailFront = new Polygon(
                200 * w, 25 * h,
                240 * w, 25 * h,
                235 * w, 50 * h,
                205 * w, 50 * h
        );
        sailFront.setFill(SAIL_COLOR);
        sailFront.setStroke(Color.GRAY);
        parent.getChildren().add(sailFront);

        // 6. CROW'S NEST (On the main mast)
        Circle crowNest = new Circle(143 * w, 25 * h, 5 * w);
        crowNest.setFill(Color.web("#3E2723"));
        parent.getChildren().add(crowNest);
    }

    /**
     * Helper method to draw the ship vertically.
     */
    private void drawVertical(Group parent, double width, double height) {
        // Base 100x300
        double w = width / 100.0;
        double h = height / 300.0;

        // 1. THE HULL
        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                35.0 * w, 20.0 * h,    // Stern left
                45.0 * w, 280.0 * h,   // Bow (Tip)
                65.0 * w, 280.0 * h,   // Bow (Wide tip)
                65.0 * w, 20.0 * h     // Stern right
        );
        hull.setFill(WOOD_DARK);
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);

        // Poop deck
        Rectangle poopDeck = new Rectangle(30 * w, 20 * h, 40 * w, 30 * h);
        poopDeck.setFill(WOOD_LIGHT);
        poopDeck.setStroke(Color.BLACK);
        parent.getChildren().add(poopDeck);

        // 2. MASTS AND SAILS (Aerial view)

        // Rear Mast (Lateen sail seen from above is a diagonal line)
        Line sailRear = new Line(50 * w, 60 * h, 70 * w, 80 * h);
        sailRear.setStroke(SAIL_COLOR);
        sailRear.setStrokeWidth(4);
        parent.getChildren().add(sailRear);

        // Center Mast (Inflated square sail)
        Ellipse sailMain = new Ellipse(50 * w, 140 * h, 25 * w, 15 * h);
        sailMain.setFill(SAIL_COLOR);
        sailMain.setStroke(Color.GRAY);
        parent.getChildren().add(sailMain);

        // Front Mast
        Ellipse sailFront = new Ellipse(50 * w, 220 * h, 18 * w, 10 * h);
        sailFront.setFill(SAIL_COLOR);
        sailFront.setStroke(Color.GRAY);
        parent.getChildren().add(sailFront);

        // Mast points
        parent.getChildren().add(new Circle(50*w, 60*h, 3*w, WOOD_DARK));
        parent.getChildren().add(new Circle(50*w, 140*h, 4*w, WOOD_DARK));
        parent.getChildren().add(new Circle(50*w, 220*h, 3*w, WOOD_DARK));
    }
}