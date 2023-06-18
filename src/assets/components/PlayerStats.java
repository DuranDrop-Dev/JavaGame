package src.assets.components;

public interface PlayerStats {
    String displayPlayerAndStats();
    int getWinStreak();

    void setWinStreak();

    void setWinStreak(int winStreak);
}
