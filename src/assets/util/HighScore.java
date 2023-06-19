package src.assets.util;

import java.io.*;

public class HighScore {
    public static void writeScore(String firstName, String lastName, int wins) {
        // Write data to the text file
        writeDataToFile(firstName, lastName, wins);

        // TEST: read data from the text file
        DataModel tempModel = readDataFromFile();
        assert tempModel != null;
        System.out.println("First Name: " + tempModel.firstName());
        System.out.println("Last Name: " + tempModel.lastName());
        System.out.println("Win Streak: " + tempModel.winStreak());
    }

    private static void writeDataToFile(String firstName, String lastName, int winStreak) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/record.txt"))) {
            // Write name and winStreak to the file
            writer.write(firstName);
            writer.newLine();
            writer.write(lastName);
            writer.newLine();
            writer.write(String.valueOf(winStreak));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataModel readDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/record.txt"))) {
            String firstName = reader.readLine(); // Read the firstName from the first line
            String lastName = reader.readLine(); // Read the lastName from the first line
            int winStreak = Integer.parseInt(reader.readLine()); // Read the age from the second line
            return new DataModel(firstName, lastName, winStreak);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public record DataModel(String firstName, String lastName, int winStreak) {
    }
}