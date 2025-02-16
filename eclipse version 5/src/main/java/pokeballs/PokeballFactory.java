package main.java.pokeballs;

public class PokeballFactory {
    public static Pokeball createPokeball(String type) {
        switch (type) {
            case "Great Ball":
                return new GreatBall();
            case "Ultra Ball":
                return new UltraBall();
            case "Master Ball":
                return new MasterBall();
            default:
                return new StandardPokeball();
        }
    }
}
