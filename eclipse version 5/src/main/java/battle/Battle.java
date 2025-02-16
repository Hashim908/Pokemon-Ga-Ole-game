package main.java.battle;

import main.java.core.Player;
import main.java.moves.Move;
import main.java.pokeballs.Pokeball;
import main.java.pokeballs.PokeballFactory;
import main.java.pokemon.Pokemon;
import main.java.core.TextUtils;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Battle {
    private Player player;
    private Pokemon opponentPokemon;
    private Pokemon playerPokemon;
    private Scanner scanner;
    private Random random;
    private boolean playerWinner;

    public Battle(Player player, Pokemon opponentPokemon, Pokemon playerPokemon) {
        this.player = player;
        this.opponentPokemon = opponentPokemon;
        this.playerPokemon = playerPokemon;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.playerWinner = false;
    }

    public void start() {
        TextUtils.printWithDelay("Battle start!");
        TextUtils.printWithDelay(playerPokemon.getName() + " vs " + opponentPokemon.getName());

        while (!playerPokemon.isFainted() && !opponentPokemon.isFainted()) {
            playerTurn();
            if (opponentPokemon.isFainted()) {
                TextUtils.printWithDelay(opponentPokemon.getName() + " fainted! " + playerPokemon.getName() + " wins!");
                playerWinner = true;
                rewardPokeballs();
                return;
            }

            try {
                Thread.sleep(1000); // Used to make a delay between moves
            } catch (InterruptedException e) {
                System.out.println("Battle interrupted!");
                Thread.currentThread().interrupt(); // Restore interrupted status
            }

            opponentTurn();
            if (playerPokemon.isFainted()) {
                TextUtils.printWithDelay(playerPokemon.getName() + " fainted! " + opponentPokemon.getName() + " wins!");
                playerWinner = false;
                return;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Battle interrupted!");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void playerTurn() {
        TextUtils.printWithDelay("Choose a move:");
        for (int i = 0; i < playerPokemon.getMoves().size(); i++) {
            System.out.println((i + 1) + ". " + playerPokemon.getMoves().get(i).getName());
        }

        Move move = null;
        boolean validChoice = false;

        while (!validChoice) {
            try {
                int choice = scanner.nextInt();
                if (choice < 1 || choice > playerPokemon.getMoves().size()) {
                    throw new IndexOutOfBoundsException("Invalid move selection. Please choose a valid move number.");
                }
                move = playerPokemon.getMoves().get(choice - 1);
                validChoice = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }

        useMove(playerPokemon, opponentPokemon, move);
    }

    private void opponentTurn() {
        int choice = random.nextInt(opponentPokemon.getMoves().size());
        Move move = opponentPokemon.getMoves().get(choice);
        useMove(opponentPokemon, playerPokemon, move);
    }

    private void useMove(Pokemon attacker, Pokemon defender, Move move) {
        double effectiveness = TypeEffectiveness.getEffectiveness(move.getType(), defender.getType());
        int damage = calculateDamage(move.getPower(), attacker.getAttack(), defender.getDefense(), effectiveness);
        TextUtils.printWithDelay(attacker.getName() + " used " + move.getName() + "!");
        System.out.println("");
        TextUtils.printWithDelay("It's " + getEffectivenessString(effectiveness) + " effective!");
        System.out.println("");
        defender.takeDamage(damage);
        TextUtils.printWithDelay(defender.getName() + " took " + damage + " damage!");
        System.out.println("");
    }

    private int calculateDamage(int power, int attack, int defense, double effectiveness) {
        return (int) (((2 * 50 / 5 + 2) * power * attack / defense / 50 + 2) * effectiveness);
    }

    private String getEffectivenessString(double effectiveness) {
        if (effectiveness > 1.0) {
            return "super";
        } else if (effectiveness < 1.0) {
            return "not very";
        } else {
            return "normally";
        }
    }

    private void rewardPokeballs() {
        TextUtils.printWithDelay("You won the battle! You earned a new Poké Ball!");
        Pokeball newPokeball = null;
        int randomBall = random.nextInt(3);
        if (randomBall < 4) {
        	newPokeball = PokeballFactory.createPokeball("Poke Ball");
        }
        else if (randomBall < 7) {
        	newPokeball = PokeballFactory.createPokeball("Great Ball");
        }
        else if (randomBall < 9) {
        	newPokeball = PokeballFactory.createPokeball("Ultra Ball");
        }
        else if (randomBall < 10) {
        	newPokeball = PokeballFactory.createPokeball("Master Ball");
        }
        else {
        	System.out.println("Error occurred while generating pokeballs");
        	return;
        }
     
        player.addPokeball(newPokeball);
    }

    public boolean isPlayerWinner() {
        return playerWinner;
    }
}
