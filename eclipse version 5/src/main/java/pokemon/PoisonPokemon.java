package main.java.pokemon;

import main.java.moves.Move;

import java.util.List;

public class PoisonPokemon extends Pokemon {
    public PoisonPokemon(int id, String name, int health, int attack, int defense, int speed,int grade, List<Move> moves) {
        super(id, name, "Poison", health, attack, defense, speed, grade, moves);
    }
}
