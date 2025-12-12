package com.example.controller;

import model.Board;
import model.Ship;
import view.GridConfig;
import view.ShipView;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Hace que un nodo (barco) sea arrastrable en un grid/tablero
 * CON VALIDACIÓN DE NO SUPERPOSICIÓN
 */
public class DraggableMakerGrid {

    private double offsetX;
    private double offsetY;
    private Board board;  // Tablero para validar superposiciones

    public DraggableMakerGrid(Board board) {
        this.board = board;
    }

    /**
     * Hace que un ShipView sea arrastrable y se ajuste al grid
     */
    public void makeDraggable(Node node) {
        if (!(node instanceof ShipView)) {
            throw new IllegalArgumentException("El nodo debe ser un ShipView");
        }

        ShipView shipView = (ShipView) node;

        // Variable para guardar la posición anterior válida
        final double[] lastValidPosition = new double[2];
        lastValidPosition[0] = shipView.getLayoutX();
        lastValidPosition[1] = shipView.getLayoutY();

        node.setOnMousePressed(event -> {
            offsetX = event.getSceneX() - node.getLayoutX();
            offsetY = event.getSceneY() - node.getLayoutY();

            node.setCursor(javafx.scene.Cursor.CLOSED_HAND);

            // Si el barco está en el tablero, removerlo temporalmente
            Ship ship = shipView.getShip();
            if (ship.getPositions() != null && !ship.getPositions().isEmpty()) {
                board.removeShip(ship);
            }
        });

        node.setOnMouseDragged(event -> {
            Node parent = node.getParent();
            if (parent == null) return;

            Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());

            double mouseXInParent = event.getSceneX() - parentBounds.getMinX();
            double mouseYInParent = event.getSceneY() - parentBounds.getMinY();

            int gridCol = (int)(mouseXInParent / GridConfig.CELL_SIZE);
            int gridRow = (int)(mouseYInParent / GridConfig.CELL_SIZE);

            Ship ship = shipView.getShip();
            int shipSize = ship.getSize();
            boolean isHorizontal = ship.isHorizontal();

            // Validar límites del tablero
            boolean withinBounds = false;
            if (isHorizontal) {
                if (gridCol >= 0 && gridCol + shipSize <= GridConfig.BOARD_SIZE &&
                        gridRow >= 0 && gridRow < GridConfig.BOARD_SIZE) {
                    withinBounds = true;
                }
            } else {
                if (gridCol >= 0 && gridCol < GridConfig.BOARD_SIZE &&
                        gridRow >= 0 && gridRow + shipSize <= GridConfig.BOARD_SIZE) {
                    withinBounds = true;
                }
            }

            if (withinBounds) {
                double posX = gridCol * GridConfig.CELL_SIZE;
                double posY = gridRow * GridConfig.CELL_SIZE;

                // Aplicar la posición temporalmente
                node.setLayoutX(posX);
                node.setLayoutY(posY);

                // Validar si puede colocar el barco aquí (sin superposición)
                if (board.canPlaceShip(ship, gridRow, gridCol)) {
                    // Posición válida - cambiar a color normal
                    shipView.setHighlight(false);
                    lastValidPosition[0] = posX;
                    lastValidPosition[1] = posY;
                } else {
                    // Posición inválida - resaltar en rojo
                    shipView.setHighlight(true);
                }
            }
        });

        node.setOnMouseReleased(event -> {
            node.setCursor(javafx.scene.Cursor.HAND);

            Node parent = node.getParent();
            if (parent == null) return;

            Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());

            double mouseXInParent = event.getSceneX() - parentBounds.getMinX();
            double mouseYInParent = event.getSceneY() - parentBounds.getMinY();

            int gridCol = (int)(mouseXInParent / GridConfig.CELL_SIZE);
            int gridRow = (int)(mouseYInParent / GridConfig.CELL_SIZE);

            Ship ship = shipView.getShip();

            // Intentar colocar el barco en el tablero
            boolean placed = false;
            if (mouseXInParent >= 0 && mouseXInParent < GridConfig.BOARD_WIDTH &&
                    mouseYInParent >= 0 && mouseYInParent < GridConfig.BOARD_HEIGHT) {
                placed = board.placeShip(ship, gridRow, gridCol);
            }

            if (placed) {
                // Colocación exitosa - quitar highlight
                shipView.setHighlight(false);
            } else {
                // No se pudo colocar - volver a posición anterior
                node.setLayoutX(lastValidPosition[0]);
                node.setLayoutY(lastValidPosition[1]);
                shipView.setHighlight(false);

                // Si estaba en el tablero antes, volver a colocarlo
                if (lastValidPosition[0] < GridConfig.BOARD_WIDTH &&
                        lastValidPosition[1] < GridConfig.BOARD_HEIGHT) {
                    int lastRow = (int)(lastValidPosition[1] / GridConfig.CELL_SIZE);
                    int lastCol = (int)(lastValidPosition[0] / GridConfig.CELL_SIZE);
                    board.placeShip(ship, lastRow, lastCol);
                }
            }
        });

        node.setOnMouseEntered(event -> {
            node.setCursor(javafx.scene.Cursor.HAND);
        });
    }

    public static int[] getGridPosition(double pixelX, double pixelY) {
        int col = (int)(pixelX / GridConfig.CELL_SIZE);
        int row = (int)(pixelY / GridConfig.CELL_SIZE);
        return new int[]{row, col};
    }
}
