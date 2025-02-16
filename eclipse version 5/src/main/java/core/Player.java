package main.java.core;

import main.java.pokemon.Pokemon;
import main.java.pokeballs.Pokeball;
import main.java.pokeballs.PokeballFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
	Scanner scanner = new Scanner(System.in);
	
    private String name;
    private List<Pokemon> pokemonList;
    private List<Pokeball> pokeballs;

    public Player(String name) {
        this.name = name;
        this.pokemonList = new ArrayList<>();
        this.pokeballs = new ArrayList<>();
        this.pokeballs.add(PokeballFactory.createPokeball("Standard Pokeball")); // Initial Poké Ball
    }

    public String getName() {
        return name;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void addPokemon(Pokemon pokemon) {
        pokemonList.add(pokemon);
    }

    public void ViewCapturedPokemon() {
        if (pokemonList.isEmpty()) {
        	TextUtils.printWithDelay("You have not captured any Pokémon yet.");
        } else {
        	TextUtils.printWithDelay("Your captured Pokémon:");
            for (Pokemon pokemon : pokemonList) {
                System.out.println(pokemon);
            }
        }
    }

    public int SelectPokemon() {
        TextUtils.printWithDelay("Choose your Pokemon (or enter 0 to return to the menu): ");
        for (int i = 0; i < pokemonList.size(); i++) {
            System.out.println((i + 1) + ". " + pokemonList.get(i).getName());
        }

        int choice = -1;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice == 0) {
                    TextUtils.printWithDelay("Returning to the menu...");
                    Game.displayMenu();
                    return -1;
                }
                if (choice < 1 || choice > pokemonList.size()) {
                    TextUtils.printWithDelay("Invalid choice. Please choose a number between 1 and " + pokemonList.size() + " or 0 to return to the menu.");
                    continue;
                }
                int index = choice - 1;
                if (pokemonList.get(index).getHealth() == 0) {
                    TextUtils.printWithDelay("This Pokemon has fainted. Please choose another Pokemon.");
                    continue;
                }
                return index;
            } catch (Exception e) {
                TextUtils.printWithDelay("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }
    
    public void addPokeball(Pokeball pokeball) {
        pokeballs.add(pokeball);
    }

    public List<Pokeball> getPokeballs() {
        return pokeballs;
    }
    
    public void viewPokeballs() {
        System.out.println("You have the following Poké Balls:");
        for (Pokeball pokeball : pokeballs) {
            System.out.println(pokeball.getName() + " (Catch Rate: " + pokeball.getCatchRate() + "%)");
        }
    }
    

}
