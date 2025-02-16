package main.java.pokemon;

import main.java.moves.Move;

import java.util.List;

public class RockPokemon extends Pokemon {
    public RockPokemon(int id, String name, int health, int attack, int defense, int speed, int grade, List<Move> moves) {
        super(id, name, "Rock", health, attack, defense, speed, grade, moves);
    }
}
