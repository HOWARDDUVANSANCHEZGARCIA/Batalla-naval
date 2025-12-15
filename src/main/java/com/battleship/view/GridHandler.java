package com.battleship.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Handles the visual drawing of the game grid.
 * <p>
 * This class is responsible for rendering the grid structure on the provided JavaFX Pane.
 * It creates the visual cells (rectangles) and applies CSS styling to form the board.
 * </p>
 */
public class GridHandler {
    private final Pane pane;

    /**
     * Initializes the GridHandler and immediately draws the grid.
     *
     * @param pane The JavaFX Pane container where the grid will be rendered.
     */
    public GridHandler(Pane pane) {
        this.pane = pane;
        drawGrid();
    }

    /**
     * Draws the grid cells based on the Board dimensions.
     * <p>
     * Iterates through the rows and columns defined in {@link GridConfig}, creates a
     * {@link Rectangle} for each cell, applies the "grid-cell" CSS class for styling,
     * and adds it to the pane.
     * </p>
     */
    private void drawGrid() {
        for (int row = 0; row < GridConfig.BOARD_SIZE; row++) {
            for (int col = 0; col < GridConfig.BOARD_SIZE; col++) {
                Rectangle cell = new Rectangle(
                        col * GridConfig.CELL_SIZE,
                        row * GridConfig.CELL_SIZE,
                        GridConfig.CELL_SIZE,
                        GridConfig.CELL_SIZE
                );

                // Apply the CSS class defined in styles.css
                cell.getStyleClass().add("grid-cell");

                pane.getChildren().add(cell);
            }
        }

        addCoordinateLabels();
    }

    /**
     * Placeholder method for adding coordinate labels (e.g., A-J, 1-10) to the grid.
     * Currently not implemented.
     */
    private void addCoordinateLabels() {
    }
}