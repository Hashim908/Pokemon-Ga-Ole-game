package main.java.core;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Leaderboard {
    private List<ScoreEntry> scores;
    private static final int MAX_LEADERBOARD_SIZE = 5;
    private static final String LEADERBOARD_FILE = "C:\\Users\\ashar\\Desktop\\Uni work\\Sem 2\\OOP\\final assignment\\eclipse version\\src\\main\\resources\\Leaderboard.csv";

    public Leaderboard() {
        this.scores = new ArrayList<>();
        loadScoresFromFile();
    }

    public void displayLeaderboard() {
    	TextUtils.printWithDelay("Top 5 Scores:");
        scores.stream()
                .sorted(Comparator.comparingInt(ScoreEntry::getScore).reversed())
                .forEach(score -> TextUtils.printWithDelay("Player: " + score.getPlayerName() + ", Score: " + score.getScore()));
    }

    public void addScore(String playerName, int score) {
        ScoreEntry newScoreEntry = new ScoreEntry(playerName, score);
        scores.add(newScoreEntry);
        scores = scores.stream()
                .sorted(Comparator.comparingInt(ScoreEntry::getScore).reversed())
                .limit(MAX_LEADERBOARD_SIZE)
                .collect(Collectors.toList());
        saveScoresToFile();
    }

    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String playerName = data[0];
                    int score = Integer.parseInt(data[1]);
                    scores.add(new ScoreEntry(playerName, score));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found, starting with an empty leaderboard.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveScoresToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (ScoreEntry entry : scores) {
                writer.write(entry.getPlayerName() + "," + entry.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Nested ScoreEntry class
    public static class ScoreEntry {
        private String playerName;
        private int score;

        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }
    }
}
