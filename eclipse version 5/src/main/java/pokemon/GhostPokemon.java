package main.java.pokemon;

import main.java.moves.Move;

import java.util.List;

public class GhostPokemon extends Pokemon {
    public GhostPokemon(int id, String name, int health, int attack, int defense, int speed,int grade, List<Move> moves) {
        super(id, name, "Ghost", health, attack, defense, speed, grade, moves);
    }
}
