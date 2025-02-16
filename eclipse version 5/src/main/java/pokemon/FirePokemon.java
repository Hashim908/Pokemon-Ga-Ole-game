package main.java.pokemon;

import main.java.moves.Move;

import java.util.List;

public class FirePokemon extends Pokemon {
    public FirePokemon(int id, String name, int health, int attack, int defense, int speed, int grade, List<Move> moves) {
        super(id, name, "Fire", health, attack, defense, speed, grade, moves);
    }
}
