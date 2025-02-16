package main.java.moves;

import main.java.core.CSVReader;

import java.util.ArrayList;
import java.util.List;

public class MoveParser {

    public static List<Move> parseMoveData(String filePath) {
        List<Move> moveList = new ArrayList<>();
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            String name = row[0];
            int power = Integer.parseInt(row[1]);
            String type = row[2];
            Move move = new Move(name, power, type);
            moveList.add(move);
        }
        return moveList;
    }
}
