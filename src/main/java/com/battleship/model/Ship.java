package com.battleship.model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Ship {

    private ShipType type;
    private int size;
    private int row;
    private int col;
    private boolean horizontal;
    private int hits;
    private Color color;

    // Posiciones que ocupa el barco en el tablero
    private List<Position> positions = new ArrayList<>();

    // Constructor que acepta ShipType y orientación
    public Ship(ShipType type, boolean horizontal) {
        this.type = type;
        this.size = type.getSize();
        // Si no manejas color por tipo, puedes dejar un color fijo o ignorarlo
        this.color = Color.GRAY;
        this.horizontal = horizontal;
        this.hits = 0;
        this.row = -1;  // posición inválida hasta colocarlo
        this.col = -1;
    }

    // Constructor alternativo con tamaño y color directos (por si lo usas en otro lado)
    public Ship(int size, Color color) {
        this.size = size;
        this.color = color;
        this.hits = 0;
        this.horizontal = true;
        this.row = -1;
        this.col = -1;
    }

    public ShipType getType() { return type; }

    public int getSize() { return size; }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }

    public boolean isHorizontal() { return horizontal; }

    public void setHorizontal(boolean horizontal) { this.horizontal = horizontal; }

    public void hit() { hits++; }

    public int getHits() { return hits; }

    public boolean isSunk() { return hits >= size; }

    public Color getColor() { return color; }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isPlaced() { return row >= 0 && col >= 0; }

    // ----- Integración con Board -----

    // Calcula y guarda las posiciones que ocupa el barco
    public void setPositions(int startRow, int startCol) {
        positions.clear();
        this.row = startRow;
        this.col = startCol;

        if (horizontal) {
            for (int i = 0; i < size; i++) {
                positions.add(new Position(startRow, startCol + i));
            }
        } else {
            for (int i = 0; i < size; i++) {
                positions.add(new Position(startRow + i, startCol));
            }
        }
    }

    public List<Position> getPositions() {
        return positions;
    }

    public boolean occupiesPosition(Position target) {
        for (Position pos : positions) {
            if (pos.getRow() == target.getRow() && pos.getCol() == target.getCol()) {
                return true;
            }
        }
        return false;
    }
}
