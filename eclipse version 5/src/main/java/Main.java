package main.java;

import main.java.core.Game;
import main.java.core.Player;
import main.java.pokemon.Pokemon;
import main.java.pokemon.PokemonParser;
import main.java.moves.Move;
import main.java.moves.MoveParser;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize wild Pokemon list from CSV file
        List<Pokemon> wildPokemonList = PokemonParser.parsePokemonData("C:\\Users\\ashar\\Desktop\\Uni work\\Sem 2\\OOP\\final assignment\\eclipse version\\src\\main\\resources\\pokemon.csv");
        
        // Initialize move list from CSV file
        List<Move> moveList = MoveParser.parseMoveData("C:\\Users\\ashar\\Desktop\\Uni work\\Sem 2\\OOP\\final assignment\\eclipse version\\src\\main\\resources\\moves.csv");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your player name: ");
        
        String playerName = scanner.nextLine();

        Player player = new Player(playerName);

        Game game = new Game(player, wildPokemonList, moveList);
        
        // Start the game
        game.start();
    }
}
