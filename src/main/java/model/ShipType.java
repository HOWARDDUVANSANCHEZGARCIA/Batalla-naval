package model;


public enum ShipType {
    PORTAAVIONES("Portaaviones", 4, 1),
    SUBMARINO("Submarino", 3, 2),
    DESTRUCTOR("Destructor", 2, 3),
    FRAGATA("Fragata", 1, 4);

    private final String name;
    private final int size;
    private final int quantity;

    ShipType(String name, int size, int quantity) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getSize() { return size; }
    public int getQuantity() { return quantity; }

    /**
     * @return Total de barcos en el juego (10)
     */
    public static int getTotalShips() {
        int total = 0;
        for (ShipType type : values()) {
            total += type.quantity;
        }
        return total;
    }
}
