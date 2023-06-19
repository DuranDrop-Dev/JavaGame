package src.assets.components;

public class ActivePlayer implements Player {
    private String firstName;
    private String lastName;
    private int wins;

    public ActivePlayer() {
        firstName = "";
        lastName = "";
    }

    public ActivePlayer(String first) {
        firstName = first;
        lastName = "";
    }

    public ActivePlayer(String first, String last) {
        firstName = first;
        lastName = last;
    }

    @Override
    public void setLastName(String last) {
        lastName = last;
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
    public String getLastName() {
        return lastName;
    }

    public void resetWinStreak() {
        wins = 0;
    }

    public int getWinStreak() {
        return wins;
    }

    public void setWinStreak() {
        setWinStreak(0);
    }

    public void setWinStreak(int winStreak) {
        wins += winStreak;
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
