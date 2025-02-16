package main.java.pokemon;

import main.java.moves.Move;

import java.util.List;

public class IcePokemon extends Pokemon {
    public IcePokemon(int id, String name, int health, int attack, int defense, int speed,int grade, List<Move> moves) {
        super(id, name, "Ice", health, attack, defense, speed, grade, moves);
    }
}
