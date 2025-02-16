package main.java.pokeballs;

public abstract class Pokeball {
    private String name;
    private int catchRate;

    public Pokeball(String name, int catchRate) {
        this.name = name;
        this.catchRate = catchRate;
    }

    public String getName() {
        return name;
    }

    public int getCatchRate() {
        return catchRate;
    }
    
    
}
