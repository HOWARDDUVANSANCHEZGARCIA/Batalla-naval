package com.battleship.view;



import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GridHandler {
    private final Pane pane;

    public GridHandler(Pane pane) {
        this.pane = pane;
        drawGrid();
    }


    private void drawGrid() {
        for (int row = 0; row < GridConfig.BOARD_SIZE; row++) {
            for (int col = 0; col < GridConfig.BOARD_SIZE; col++) {
                Rectangle cell = new Rectangle(
                        col * GridConfig.CELL_SIZE,
                        row * GridConfig.CELL_SIZE,
                        GridConfig.CELL_SIZE,
                        GridConfig.CELL_SIZE
                );


                if ((row + col) % 2 == 0) {
                    cell.setFill(Color.LIGHTBLUE);
                } else {
                    cell.setFill(Color.LIGHTCYAN);
                }

                cell.setStroke(Color.DARKBLUE);
                cell.setStrokeWidth(0.5);

                pane.getChildren().add(cell);
            }
        }


        addCoordinateLabels();
    }

    private void addCoordinateLabels() {
    }
}
