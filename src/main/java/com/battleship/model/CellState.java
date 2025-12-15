package com.battleship.model;

public enum CellState {
    EMPTY("Vacío"),         // Celda sin disparar
    WATER("Agua"),          // Disparo al agua (X)
    SHIP("Barco"),          // Celda con barco (no visible para oponente)
    HIT("Tocado"),          // Parte del barco impactada
    SUNK("Hundido");        // Barco completamente hundido

    private final String description;

    CellState(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}

