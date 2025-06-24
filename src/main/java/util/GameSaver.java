package util;

import game.GameLogic;
import java.io.*;

public class GameSaver {
    public static void saveGame(GameLogic game, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(filename))) {
            out.writeObject(game);
        }
    }

    public static GameLogic loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(filename))) {
            GameLogic loaded = (GameLogic) in.readObject();
            // Reinitialize transient field
            loaded.getHumanPlayer().getCards();
            loaded.getComputerPlayer().getCards();
            return loaded;
        }
    }
}
