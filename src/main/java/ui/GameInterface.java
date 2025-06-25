package ui;

// Minimal Swing/AWT imports
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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

public class GameInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private GameLogic game;
    private JLabel playerCardCount;
    private JLabel computerCardCount;
    private JTextArea gameLog;

    private JPanel imagePanel;
    private JLabel playerCardImage;
    private JLabel computerCardImage;

    // Window Size
    private final int xDefaultSize = 800;
    private final int yDefaultSize = 600;

    // Constructor
    public GameInterface() {
        setTitle("War Card Game");
        setSize(xDefaultSize, yDefaultSize); // Set pixel size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        setupMenu();
        setupGameArea();
        setupControls();

        startNewGame();
    }

    // method to create menu items (on top)
    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }

    // set up the top menu
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

    private void setupGameArea() {
        JPanel gamePanel = new JPanel(new BorderLayout(10, 10));

        // Game contexts
        JPanel countPanel = new JPanel(new GridLayout(1, 2, 10, 10));     
        playerCardCount = new JLabel("Your cards: 26", SwingConstants.CENTER);
        computerCardCount = new JLabel("Computer cards: 26", SwingConstants.CENTER);
        countPanel.add(playerCardCount);
        countPanel.add(computerCardCount);

        // Card images
        imagePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        playerCardImage = new JLabel();
        computerCardImage = new JLabel();
        
        // add images once UI loads them..
        imagePanel.add(playerCardImage);
        imagePanel.add(computerCardImage);

        gameLog = new JTextArea(10, 40);
        gameLog.setEditable(false);
        gameLog.setMargin(new Insets(5, 5, 5, 5));

        gamePanel.add(countPanel, BorderLayout.NORTH);
        gamePanel.add(new JScrollPane(gameLog), BorderLayout.CENTER);
        
        // add image panel once images rendered
        gamePanel.add(imagePanel);

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
    	// first option pane for user entry
        String name = JOptionPane.showInputDialog(this, "Enter your name:", "New Game", JOptionPane.PLAIN_MESSAGE);
        
        // if there is no name entered for user use "player" as default
        if (name == null || name.trim().isEmpty()) name = "Player";

        // start up logic, update UI and set message
        game = new GameLogic(name);
        updateUI();
        gameLog.setText("New game started! Good luck, " + name + "!\n");
    }

    // method to play round
    private void playRound() {
    	
    	// if no game instance start a new round
        if (game == null) {
            startNewGame();
            return;
        }

        // start a round
        String result = game.playRound();
        
        // put result in gameLogs
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

            // Show last played cards
            game.Card humanCard = game.getLastHumanCard();
            game.Card computerCard = game.getLastComputerCard();
            
            /* set images for the cards */ 
            // set images for player component
            playerCardImage.setIcon(humanCard != null ? humanCard.getImageAndResize() : null);
            playerCardImage.setHorizontalAlignment(SwingConstants.CENTER);
            
            // set images for computer component
            computerCardImage.setIcon(computerCard != null ? computerCard.getImageAndResize() : null);
            computerCardImage.setHorizontalAlignment(SwingConstants.CENTER);
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
