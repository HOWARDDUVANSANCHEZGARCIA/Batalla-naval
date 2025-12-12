package com.battleship.view;


public class GridConfig {
    public static final int CELL_SIZE = 50;       // 50px por celda (antes 40px)
    public static final int BOARD_SIZE = 10;      // Tablero 10x10
    public static final int BOARD_WIDTH = CELL_SIZE * BOARD_SIZE;   // 500px
    public static final int BOARD_HEIGHT = CELL_SIZE * BOARD_SIZE;  // 500px

    private GridConfig() {} // Utility class
}
