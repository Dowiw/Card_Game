package ui;

// Minimal Swing/AWT imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import game.GameLogic;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame {
    /*
	 *
	 */
	private static final long serialVersionUID = 1L;
	private GameLogic game;
    private JLabel playerCardCount;
    private JLabel computerCardCount;
    private JTextArea gameLog;

    public GameInterface() {
        setTitle("War Card Game");
        setSize(600, 500); // Set pixel size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        setupMenu();
        setupGameArea();
        setupControls();

        startNewGame();
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("New Game", e -> startNewGame()));
        fileMenu.add(createMenuItem("Save Game", e -> saveGame()));
        fileMenu.add(createMenuItem("Load Game", e -> loadGame()));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(createMenuItem("About", e -> showAbout()));

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }

    private void setupGameArea() {
        JPanel gamePanel = new JPanel(new BorderLayout(10, 10));

        JPanel countPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        playerCardCount = new JLabel("Your cards: 26", SwingConstants.CENTER);
        computerCardCount = new JLabel("Computer cards: 26", SwingConstants.CENTER);
        countPanel.add(playerCardCount);
        countPanel.add(computerCardCount);

        gameLog = new JTextArea(10, 40);
        gameLog.setEditable(false);
        gameLog.setMargin(new Insets(5, 5, 5, 5));

        gamePanel.add(countPanel, BorderLayout.NORTH);
        gamePanel.add(new JScrollPane(gameLog), BorderLayout.CENTER);

        add(gamePanel, BorderLayout.CENTER);
    }

    private void setupControls() {
        JPanel controlPanel = new JPanel();

        JButton playButton = new JButton("Play Round");
        playButton.addActionListener(e -> playRound());
        controlPanel.add(playButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void startNewGame() {
        String name = JOptionPane.showInputDialog(this, "Enter your name:", "New Game", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) name = "Player";

        game = new GameLogic(name);
        updateUI();
        gameLog.setText("New game started! Good luck, " + name + "!\n");
    }

    private void playRound() {
        if (game == null) {
            startNewGame();
            return;
        }

        String result = game.playRound();
        gameLog.append(result + "\n");
        updateUI();
        gameLog.setCaretPosition(gameLog.getDocument().getLength());

        if (!game.getHumanPlayer().hasCards() || !game.getComputerPlayer().hasCards()) {
            JOptionPane.showMessageDialog(this,
                game.getHumanPlayer().hasCards() ? "You win!" : "Computer wins!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateUI() {
        if (game != null) {
            playerCardCount.setText("Your cards: " + game.getHumanPlayer().cardCount());
            computerCardCount.setText("Computer cards: " + game.getComputerPlayer().cardCount());
        }
    }

    private void saveGame() {
        if (game == null) {
            JOptionPane.showMessageDialog(this, "No game to save!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                util.GameSaver.saveGame(game, chooser.getSelectedFile().getPath());
                gameLog.append("Game saved successfully!\n");
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadGame() {
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                game = util.GameSaver.loadGame(chooser.getSelectedFile().getPath());
                updateUI();
                gameLog.append("Game loaded successfully!\n");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Load failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
            "War Card Game\n" +
            "Developed by: [Your Name]\n" +
            "EU University - Summer 2025\n\n" +
            "Rules:\n" +
            "1. Highest card wins the round\n" +
            "2. Equal cards trigger a war\n" +
            "3. Winner takes all cards",
            "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
