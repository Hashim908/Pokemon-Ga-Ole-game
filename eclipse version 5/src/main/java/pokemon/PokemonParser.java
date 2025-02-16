package main.java.pokemon;

import main.java.core.CSVReader;

import java.util.ArrayList;
import java.util.List;

public class PokemonParser {

    public static List<Pokemon> parsePokemonData(String filePath) {
        List<Pokemon> pokemonList = new ArrayList<>();
        List<String[]> data = CSVReader.readCSV(filePath);

        for (String[] row : data) {
            try {
                if (row.length < 8) {
                    throw new IllegalArgumentException("Row data is incomplete: " + String.join(",", row));
                }

                int id = Integer.parseInt(row[0]);
                String name = row[1];
                String type = row[2];
                int health = Integer.parseInt(row[3]);
                int attack = Integer.parseInt(row[4]);
                int defense = Integer.parseInt(row[5]);
                int speed = Integer.parseInt(row[6]);
                int grade = Integer.parseInt(row[7]);
                Pokemon pokemon;

                switch (type) {
                    case "Fire":
                        pokemon = new FirePokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Water":
                        pokemon = new WaterPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Grass":
                        pokemon = new GrassPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Electric":
                        pokemon = new ElectricPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Ice":
                        pokemon = new IcePokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Fighting":
                        pokemon = new FightingPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Poison":
                        pokemon = new PoisonPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Ground":
                        pokemon = new GroundPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Flying":
                        pokemon = new FlyingPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Psychic":
                        pokemon = new PsychicPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Bug":
                        pokemon = new BugPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Rock":
                        pokemon = new RockPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Ghost":
                        pokemon = new GhostPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Dragon":
                        pokemon = new DragonPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Dark":
                        pokemon = new DarkPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Steel":
                        pokemon = new SteelPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Fairy":
                        pokemon = new FairyPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                        break;
                    case "Normal":
                    	pokemon = new NormalPokemon(id, name, health, attack, defense, speed, grade, new ArrayList<>());
                    	break;
                    default:
                        throw new IllegalArgumentException("Unknown Pokémon type: " + type);
                }

                pokemonList.add(pokemon);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing numerical data for Pokémon: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Error in Pokémon data: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error while parsing Pokémon data: " + e.getMessage());
            }
        }

        return pokemonList;
    }
}
