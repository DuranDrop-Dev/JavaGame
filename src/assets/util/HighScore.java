package src.assets.util;

import java.io.*;

public class HighScore {
    public static void writeScore(String player, int wins) {
        // Write data to the text file
        writeDataToFile(String.valueOf(player), wins);

        // TEST: read data from the text file
        DataModel tempModel = readDataFromFile();
        assert tempModel != null;
        System.out.println("Name: " + tempModel.name());
        System.out.println("Win Streak: " + tempModel.winStreak());
    }

    private static void writeDataToFile(String name, int winStreak) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("record.txt"))) {
            // Write name and winStreak to the file
            writer.write(name);
            writer.newLine();
            writer.write(String.valueOf(winStreak));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataModel readDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("record.txt"))) {
            String name = reader.readLine(); // Read the name from the first line
            int winStreak = Integer.parseInt(reader.readLine()); // Read the age from the second line
            return new DataModel(name, winStreak);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public record DataModel(String name, int winStreak) {
    }
}