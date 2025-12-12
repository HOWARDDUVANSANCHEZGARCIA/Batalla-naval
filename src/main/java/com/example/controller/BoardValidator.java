package com.example.controller;

import model.Board;
import model.Ship;
import model.ShipType;

import java.util.HashMap;
import java.util.Map;


public class BoardValidator {


    public static boolean isValidBoard(Board board) {
        return hasAllShips(board) && noOverlapping(board);
    }


    public static boolean hasAllShips(Board board) {
        Map<ShipType, Integer> shipCounts = new HashMap<>();


        for (ShipType type : ShipType.values()) {
            shipCounts.put(type, 0);
        }


        for (Ship ship : board.getShips()) {
            ShipType type = ship.getType();
            shipCounts.put(type, shipCounts.get(type) + 1);
        }


        for (ShipType type : ShipType.values()) {
            if (shipCounts.get(type) != type.getQuantity()) {
                return false;
            }
        }

        return true;
    }


    private static boolean noOverlapping(Board board) {

        return true;
    }


    public static String getValidationError(Board board) {
        if (!hasAllShips(board)) {
            return "Faltan barcos por colocar o hay barcos de más.";
        }

        if (!noOverlapping(board)) {
            return "Hay barcos superpuestos.";
        }

        return "Tablero válido";
    }
}
