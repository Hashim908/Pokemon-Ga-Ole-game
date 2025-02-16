package main.java.pokemon;

import main.java.moves.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
	private int ID;
    private String name;
    private String type;
    private int health;
    private int attack;
    private int defense;
    private int speed;
    private int grade;
    private List<Move> moves;

    public Pokemon(int id, String name, String type, int health, int attack, int defense, int speed, int grade, List<Move> moves) {
        this.ID = id;
    	this.name = name;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.grade = grade;
        this.moves = new ArrayList<>(moves);
    }
    
    public int getID() {
    	return ID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getGrade() {
        return grade;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isFainted() {
        return this.health <= 0;
    }

    @Override
    public String toString() {
        return name + " (Type: " + type + ", Health: " + health + ", Attack: " + attack + ", Defense: " + defense + ", Speed: " + speed + ")";
    }
}
