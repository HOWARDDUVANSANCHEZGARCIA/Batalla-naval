package com.battleship.view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Draws a War Galleon (Man o' War - 3 Masts).
 * <p>
 * This class implements the {@link ShipDrawer} interface to render the
 * ship corresponding to the "Aircraft Carrier" size (4 cells), but visually
 * represented as a Galleon/Man o' War to fit the pirate theme.
 * Features gold details and visible cannons.
 * </p>
 */
public class PortaavionesDrawer implements ShipDrawer {

    // "Premium" Color Palette
    private final Color WOOD_DARK = Color.web("#281814");  // Very dark wood
    private final Color WOOD_MID = Color.web("#4E342E");   // Medium wood
    private final Color GOLD = Color.web("#FFD700");       // Gold details (Flagship)
    private final Color SAIL_COLOR = Color.web("#EFEBE9"); // White sails
    private final Color CANNON_COLOR = Color.BLACK;        // Cannons

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
        // Normalize dimensions. Since it's long (4 cells), use a 400x100 base
        double w = width / 400.0;
        double h = height / 100.0;

        // 1. THE HULL (Long and robust)
        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                20.0 * w, 50.0 * h,    // Stern top (High poop deck)
                380.0 * w, 60.0 * h,   // Bow top
                350.0 * w, 90.0 * h,   // Bow bottom
                40.0 * w, 90.0 * h     // Stern bottom
        );
        hull.setFill(WOOD_DARK);
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);

        // 2. POOP DECK (The raised rear section where the captain stands)
        Rectangle poopDeck = new Rectangle(20 * w, 40 * h, 60 * w, 10 * h);
        poopDeck.setFill(WOOD_MID);
        poopDeck.setStroke(Color.BLACK);
        parent.getChildren().add(poopDeck);

        // 3. GOLD DETAIL (Side trim)
        Line goldTrim = new Line(25 * w, 70 * h, 340 * w, 70 * h);
        goldTrim.setStroke(GOLD);
        goldTrim.setStrokeWidth(3);
        parent.getChildren().add(goldTrim);

        // 4. CANNONS (Black circles along the hull)
        for (int i = 0; i < 5; i++) {
            Circle cannon = new Circle((80 + (i * 60)) * w, 80 * h, 4 * h);
            cannon.setFill(CANNON_COLOR);
            parent.getChildren().add(cannon);
        }

        // 5. MASTS (3 Masts: Mizzen, Main, Foremast)
        // Rear (Mizzen)
        Rectangle mastRear = new Rectangle(90 * w, 20 * h, 5 * w, 40 * h);
        mastRear.setFill(WOOD_MID);
        parent.getChildren().add(mastRear);

        // Center (Main - Largest)
        Rectangle mastMain = new Rectangle(190 * w, 10 * h, 7 * w, 50 * h);
        mastMain.setFill(WOOD_MID);
        parent.getChildren().add(mastMain);

        // Front (Foremast)
        Rectangle mastFront = new Rectangle(290 * w, 15 * h, 6 * w, 45 * h);
        mastFront.setFill(WOOD_MID);
        parent.getChildren().add(mastFront);

        // 6. SAILS (Large and square)
        // Rear Sail
        Polygon sailRear = new Polygon(
                70 * w, 25 * h, 110 * w, 25 * h, 105 * w, 50 * h, 75 * w, 50 * h
        );
        sailRear.setFill(SAIL_COLOR);
        sailRear.setStroke(Color.GRAY);
        parent.getChildren().add(sailRear);

        // Center Sail (Main)
        Polygon sailMain = new Polygon(
                160 * w, 15 * h, 220 * w, 15 * h, 215 * w, 55 * h, 165 * w, 55 * h
        );
        sailMain.setFill(SAIL_COLOR);
        sailMain.setStroke(Color.GRAY);
        parent.getChildren().add(sailMain);

        // Front Sail
        Polygon sailFront = new Polygon(
                260 * w, 20 * h, 320 * w, 20 * h, 315 * w, 55 * h, 265 * w, 55 * h
        );
        sailFront.setFill(SAIL_COLOR);
        sailFront.setStroke(Color.GRAY);
        parent.getChildren().add(sailFront);

        // 7. FLAGSHIP FLAG (On the main mast)
        Polygon flag = new Polygon(
                193 * w, 5 * h, 230 * w, 10 * h, 193 * w, 15 * h
        );
        flag.setFill(Color.DARKRED); // Royal red flag
        parent.getChildren().add(flag);
    }

    /**
     * Helper method to draw the ship vertically.
     */
    private void drawVertical(Group parent, double width, double height) {
        // Base 100x400
        double w = width / 100.0;
        double h = height / 400.0;

        // 1. HULL
        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                50.0 * w, 20.0 * h,    // Stern
                40.0 * w, 380.0 * h,   // Bow left
                60.0 * w, 380.0 * h,   // Bow right
                90.0 * w, 20.0 * h     // Stern right (wide)
        );
        // Centering adjustment
        hull.getPoints().set(0, 20.0 * w); // Stern left
        hull.getPoints().set(6, 80.0 * w); // Stern right

        hull.setFill(WOOD_DARK);
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);

        // Poop Deck
        Rectangle poopDeck = new Rectangle(20 * w, 20 * h, 60 * w, 20 * h);
        poopDeck.setFill(WOOD_MID);
        poopDeck.setStroke(Color.BLACK);
        parent.getChildren().add(poopDeck);

        // Cannons (Vertical)
        for (int i = 0; i < 5; i++) {
            Circle cannonRight = new Circle(75 * w, (80 + (i * 60)) * h, 4 * w);
            cannonRight.setFill(CANNON_COLOR);
            parent.getChildren().add(cannonRight);

            Circle cannonLeft = new Circle(25 * w, (80 + (i * 60)) * h, 4 * w);
            cannonLeft.setFill(CANNON_COLOR);
            parent.getChildren().add(cannonLeft);
        }

        // MASTS and SAILS (Simplified vertical view)
        // Rear
        Rectangle mastRear = new Rectangle(10 * w, 80 * h, 80 * w, 5 * h);
        mastRear.setFill(SAIL_COLOR); // Use sail color as if it were the yardarm
        mastRear.setStroke(Color.GRAY);
        parent.getChildren().add(mastRear);

        // Center
        Rectangle mastMain = new Rectangle(5 * w, 180 * h, 90 * w, 8 * h);
        mastMain.setFill(SAIL_COLOR);
        mastMain.setStroke(Color.GRAY);
        parent.getChildren().add(mastMain);

        // Front
        Rectangle mastFront = new Rectangle(15 * w, 280 * h, 70 * w, 6 * h);
        mastFront.setFill(SAIL_COLOR);
        mastFront.setStroke(Color.GRAY);
        parent.getChildren().add(mastFront);

        // Masts (center points)
        parent.getChildren().add(new Circle(50*w, 82*h, 4*w, WOOD_MID));
        parent.getChildren().add(new Circle(50*w, 184*h, 6*w, WOOD_MID));
        parent.getChildren().add(new Circle(50*w, 283*h, 5*w, WOOD_MID));
    }
}