package src.assets.util;

import static src.GUI.game;

public class GameThread extends Thread {
    private static volatile boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            Ship.createShip();
            synchronized (game) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void stopThread() {
        isRunning = false;
        System.out.println("Thread has stopped");
    }
}
