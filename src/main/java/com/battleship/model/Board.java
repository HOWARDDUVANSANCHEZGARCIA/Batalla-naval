package com.battleship.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Board implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 10;

    private CellState[][] grid;
    private List<Ship> ships;

    public Board() {
        this.grid = new CellState[SIZE][SIZE];
        this.ships = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = CellState.EMPTY;
            }
        }
    }


    public boolean canPlaceShip(Ship ship, int startRow, int startCol) {
        int size = ship.getSize();
        boolean horizontal = ship.isHorizontal();


        if (horizontal) {
            if (startCol + size > SIZE) return false;
        } else {
            if (startRow + size > SIZE) return false;
        }


        for (int i = 0; i < size; i++) {
            int row = horizontal ? startRow : startRow + i;
            int col = horizontal ? startCol + i : startCol;

            if (grid[row][col] != CellState.EMPTY) {
                return false;
            }
        }

        return true;
    }


    public boolean placeShip(Ship ship, int startRow, int startCol) {
        if (!canPlaceShip(ship, startRow, startCol)) {
            return false;
        }


        ship.setPositions(startRow, startCol);


        for (Position pos : ship.getPositions()) {
            grid[pos.getRow()][pos.getCol()] = CellState.SHIP;
        }


        ships.add(ship);
        return true;
    }


    public void removeShip(Ship ship) {

        for (Position pos : ship.getPositions()) {
            grid[pos.getRow()][pos.getCol()] = CellState.EMPTY;
        }


        ships.remove(ship);
    }


    public CellState processShot(int row, int col) {
        // Validar límites
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException("Posición fuera de límites");
        }

        // Verificar si ya se disparó aquí
        CellState current = grid[row][col];
        if (current == CellState.WATER || current == CellState.HIT || current == CellState.SUNK) {
            return current; // Ya disparado
        }

        // Disparo al agua
        if (current == CellState.EMPTY) {
            grid[row][col] = CellState.WATER;
            return CellState.WATER;
        }

        // Disparo a un barco
        if (current == CellState.SHIP) {
            grid[row][col] = CellState.HIT;

            // Buscar el barco impactado
            Ship hitShip = findShipAt(row, col);
            if (hitShip != null) {
                hitShip.hit();

                // Verificar si el barco se hundió
                if (hitShip.isSunk()) {
                    markShipAsSunk(hitShip);
                    return CellState.SUNK;
                }
            }

            return CellState.HIT;
        }

        return CellState.EMPTY;
    }


    private Ship findShipAt(int row, int col) {
        Position target = new Position(row, col);
        for (Ship ship : ships) {
            if (ship.occupiesPosition(target)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Marca todas las celdas de un barco como hundido
     */
    private void markShipAsSunk(Ship ship) {
        for (Position pos : ship.getPositions()) {
            grid[pos.getRow()][pos.getCol()] = CellState.SUNK;
        }
    }

    /**
     * Verifica si todos los barcos han sido hundidos
     */
    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }


    public CellState getCellState(int row, int col) {
        return grid[row][col];
    }

    public List<Ship> getShips() {
        return new ArrayList<>(ships);
    }

    public int getSize() {
        return SIZE;
    }
}
