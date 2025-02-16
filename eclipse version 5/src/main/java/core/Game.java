package main.java.core;

import main.java.pokemon.*;
import main.java.pokeballs.*;
import main.java.moves.Move;
import main.java.battle.Battle;
import java.util.*;

public class Game {
    private Player player;
    private Scanner scanner;
    private Random random;
    private List<Pokemon> wildPokemonList;
    //private List<Pokeball> playerPokeballs;
    private List<Move> moveList;
    private int score;
    private Leaderboard leaderboard;

    public Game(Player player, List<Pokemon> wildPokemonList, List<Move> moveList) {
        this.player = player;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.wildPokemonList = wildPokemonList;
        this.moveList = moveList;
        //this.playerPokeballs = new ArrayList<>();
        this.score = 0;
        this.leaderboard = new Leaderboard();
        
        for (int i = 0; i < 4; i++) {
            this.player.addPokeball(PokeballFactory.createPokeball("Pokeball"));
        }
    }

    public void start() {
        System.out.println("Welcome to Pokémon Ga-Olé!");
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            handleMenuChoice(choice);
        }
    }
    
    public static void displayMenu() {
    	showMenu();
    }

    private static void showMenu() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("1. Catch Pokémon");
        System.out.println("2. Battle");
        System.out.println("3. View captured Pokémon");
        System.out.println("4. View current score");
        System.out.println("5. View Leaderboard");
        System.out.println("6. Settings");
        System.out.println("7. Exit");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> catchPokemon();
            case 2 -> battle();
            case 3 -> player.ViewCapturedPokemon();
            case 4 -> viewScore();
            case 5 -> displayLeaderboard();
            case 6 -> textSpeed();
            case 7 -> exitGame();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void catchPokemon() {
        if (player.getPokeballs().isEmpty()) {
            TextUtils.printWithDelay("You have no Poké Balls left!");
            return;
        }

        // Generate three random Pokemon
        Pokemon[] wildPokemonArray = new Pokemon[3];
        for (int i = 0; i < 3; i++) {
            wildPokemonArray[i] = getRandomPokemon();
        }

        // Display the Pokemon stats
        TextUtils.printWithDelay("Three wild Pokémon appeared! Choose one to catch:");
        for (int i = 0; i < 3; i++) {
            Pokemon wildPokemon = wildPokemonArray[i];
            System.out.println((i + 1) + ". " + wildPokemon.getName() +
                    " (Type: " + wildPokemon.getType() +
                    ", HP: " + wildPokemon.getHealth() +
                    ", ATK: " + wildPokemon.getAttack() +
                    ", DEF: " + wildPokemon.getDefense() +
                    ", SPD: " + wildPokemon.getSpeed() +
                    ", Grade: " + wildPokemon.getGrade() + ")");
        }

        // Get player's choice
        int chosenPokemonIndex = -1;
        boolean validChoice = false;
        while (!validChoice) {
            TextUtils.printWithDelay("Enter the number of the Pokémon you want to catch (1-3), or 0 to run away:");
            try {
                chosenPokemonIndex = scanner.nextInt();
                if (chosenPokemonIndex == 0) {
                    TextUtils.printWithDelay("You ran away safely.");
                    return;
                } else if (chosenPokemonIndex >= 1 && chosenPokemonIndex <= 3) {
                    validChoice = true;
                } else {
                    TextUtils.printWithDelay("Invalid choice. Please enter a number between 0 and 3.");
                }
            } catch (InputMismatchException e) {
                TextUtils.printWithDelay("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }

        Pokemon chosenPokemon = wildPokemonArray[chosenPokemonIndex - 1];

        // Attempt to catch the Pokemon
        boolean pokemonCaught = false;
        while (!pokemonCaught) {
            if (player.getPokeballs().isEmpty()) {
                TextUtils.printWithDelay("You have no Poké Balls left!");
                return;
            }

            // Loop until a valid PokeBall choice is made
            boolean validBallChoice = false;
            Pokeball chosenBall = null;

            while (!validBallChoice) {
                TextUtils.printWithDelay("Choose a Poké Ball:");
                for (int i = 0; i < player.getPokeballs().size(); i++) {
                    System.out.println((i + 1) + ". " + player.getPokeballs().get(i).getName());
                }

                try {
                    int ballChoice = scanner.nextInt() - 1;

                    if (ballChoice < 0 || ballChoice >= player.getPokeballs().size()) {
                        TextUtils.printWithDelay("Invalid choice. Please choose a valid Poké Ball.");
                    } else {
                        chosenBall = player.getPokeballs().get(ballChoice);
                        validBallChoice = true;
                    }
                } catch (InputMismatchException e) {
                    TextUtils.printWithDelay("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            try {
                boolean success = attemptCatch(chosenPokemon, chosenBall);
                if (success) {
                    TextUtils.printWithDelay("You caught the wild " + chosenPokemon.getName() + "!");
                    player.addPokemon(chosenPokemon);
                    score += 10; // Base score for catching a Pokémon
                    score += chosenPokemon.getGrade() * 20; // Additional score based on grade
                    pokemonCaught = true;
                    
                } else {
                    TextUtils.printWithDelay("The wild " + chosenPokemon.getName() + " broke free!");
                   
                    if (player.getPokeballs().isEmpty()) {
                        TextUtils.printWithDelay("You have no more Poké Balls left!");
                        return;
                    }
                }
                player.getPokeballs().remove(chosenBall);
            } catch (Exception e) {
                TextUtils.printWithDelay("An error occurred while attempting to catch the Pokémon. Please try again.");
                e.printStackTrace();
            }
        }
    }

    private boolean attemptCatch(Pokemon wildPokemon, Pokeball pokeball) {
        if (pokeball.getName().equals("Master Ball")) {
            return true; // Master Ball guarantees capture
        }

        int catchRate = pokeball.getCatchRate();
        int wildPokemonGrade = wildPokemon.getGrade();
        int captureChance = catchRate - (wildPokemonGrade * 30);
        return random.nextInt(100) < captureChance;
    }

    private void battle() {
        if (player.getPokemonList().isEmpty()) {
            TextUtils.printWithDelay("You don't have any Pokémon to battle with!");
            return;
        }
        Pokemon opponent = getRandomPokemon();
        TextUtils.printWithDelay("A wild " + opponent.getName() + " appeared!");
        
        int pokemonIndex = player.SelectPokemon();
        if (pokemonIndex == -1) {
            // User chose to return to the menu
            return;
        }
        
        Pokemon playerPokemon = player.getPokemonList().get(pokemonIndex);
        Battle battle = new Battle(player, opponent, playerPokemon);
        battle.start();
        
        if (battle.isPlayerWinner()) {
            score += 5; // Base score for defeating a Pokémon
            score += opponent.getGrade() * 10; // Additional score based on grade
        }
    }

    private Pokemon getRandomPokemon() {
        int index = random.nextInt(wildPokemonList.size());
        Pokemon wildPokemon = wildPokemonList.get(index);
        Pokemon pokemon;
        switch (wildPokemon.getType()) {
            case "Fire" -> pokemon = new FirePokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Water" -> pokemon = new WaterPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Grass" -> pokemon = new GrassPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Bug" -> pokemon = new BugPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Dark" -> pokemon = new DarkPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Dragon" -> pokemon = new DragonPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Electric" -> pokemon = new ElectricPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Fairy" -> pokemon = new FairyPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Fighting" -> pokemon = new FightingPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Flying" -> pokemon = new FlyingPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Ghost" -> pokemon = new GhostPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Ground" -> pokemon = new GroundPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Ice" -> pokemon = new IcePokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Poison" -> pokemon = new PoisonPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Psychic" -> pokemon = new PsychicPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Rock" -> pokemon = new RockPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Steel" -> pokemon = new SteelPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            case "Normal" -> pokemon = new NormalPokemon(wildPokemon.getID(), wildPokemon.getName(), wildPokemon.getHealth(), wildPokemon.getAttack(), wildPokemon.getDefense(), wildPokemon.getSpeed(), wildPokemon.getGrade(), new ArrayList<>());
            default -> throw new IllegalArgumentException("Unknown Pokémon type: " + wildPokemon.getType());
        }

        // Assign type-based moves
        for (int i = 0; i < 3; i++) { // assign 3 moves
            int choice = random.nextInt(moveList.size());
            Move potentialMove = moveList.get(choice);
            if ((potentialMove.getType().equals(wildPokemon.getType()) || potentialMove.getType().equals("Normal")) && !pokemon.getMoves().contains(potentialMove)) {
                pokemon.addMove(moveList.get(choice));
            } else {
                i--;
            }
        }
        return pokemon;
    }

    private void textSpeed() {
        TextUtils.printWithDelay("What speed would you like the text to display at?");
        System.out.println("1. Slow");
        System.out.println("2. Normal");
        System.out.println("3. Fast");
        int choice = scanner.nextInt();
        try {
            switch (choice) {
                case 1 -> TextUtils.setDelay(50);
                case 2 -> TextUtils.setDelay(30);
                case 3 -> TextUtils.setDelay(10);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
    }

    private void displayLeaderboard() {
        leaderboard.displayLeaderboard();
    }
    
    private void exitGame() {
        leaderboard.addScore(player.getName(), score);
        TextUtils.printWithDelay("Thank you for playing Pokémon Ga-Olé! Your final score is: " + score);
        System.exit(0);
    }

    
    private void viewScore() {
    	TextUtils.printWithDelay("Your current score is: " + score);
    }

    @Override
    public String toString() {
        return "Game{" +
                "player=" + player +
                ", scanner=" + scanner +
                ", random=" + random +
                ", wildPokemonList=" + wildPokemonList +
                ", moveList=" + moveList +
                ", score=" + score +
                ", leaderboard=" + leaderboard +
                '}';
    }
}
