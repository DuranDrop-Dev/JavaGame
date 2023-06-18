package src.assets.components;

public class Player implements PlayerStats {
    private String firstName;
    private String lastName;
    private int wins;
    public Player() {
        firstName = "";
        lastName = "";
    }
    public Player(String first) {
        setFirstName(first);
        setLastName("");
    }
    public Player(String first, String last) {
        setFirstName(first);
        setLastName(last);
    }
    public void setFirstName(String first) {
        firstName = first;
    }

    public void setLastName(String last) {
        lastName = last;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void resetWinStreak() {
        wins = 0;
    }
    @Override
    public int getWinStreak() {
        return wins;
    }

    @Override
    public void setWinStreak() {
        setWinStreak(0);
    }

    @Override
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
