package src.assets.components;

import src.assets.util.HighScore;

import static src.assets.util.HighScore.readDataFromFile;

public class TopPlayer implements Player {
    private String firstName;
    private String lastName;
    private int wins;

    public TopPlayer() {
        HighScore.DataModel topModel = readDataFromFile();
        assert topModel != null;
        setFirstName(topModel.firstName());
        setLastName(topModel.lastName());
        setWinStreak(topModel.winStreak());
    }

    @Override
    public void setFirstName(String first) {
        firstName = first;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setLastName(String last) {
        lastName = last;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    private void setWinStreak(int winStreak) {
        wins = winStreak;
    }

    public int getWinStreak() {
        return wins;
    }

    @Override
    public String displayPlayerAndStats() {
        if (!getLastName().equals("")) {
            return getFirstName() + " " + getLastName() + " ( " + getWinStreak() + " )";
        } else {
            return getFirstName() + " ( " + getWinStreak() + " )";
        }
    }

    @Override
    public String toString() {
        if (!getLastName().equals("")) {
            return getFirstName() + " " + getLastName();
        } else {
            return getFirstName();
        }
    }
}
