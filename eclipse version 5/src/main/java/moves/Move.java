package main.java.moves;

public class Move {
    private String name;
    private String type;
    private int power;

    public Move(String name, int power, String type) {
        this.name = name;
        this.power = power;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return name + " (Type: " + type + ", Power: " + power + ")";
    }
}
