package ui;

// Minimal Swing/AWT imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Card;
import game.GameLogic;
import util.ImageManager;

/*
 * Class for the interface (JFrame):
 * - Game Management
 * - UI Management
 */
public class GameInterface extends JFrame {
	private static final long serialVersionUID = 1L;

	// panel structures
	private GameLogic game;
	private JPanel gamePanel;
	private JLabel playerCardCount;
	private JLabel computerCardCount;
	private JLabel gameLog;

	// image panels and labels
	private JPanel imagePanel;
	private JLabel playerCardImage;
	private JLabel computerCardImage;
	
	// Add persistent stack panel for player's hand
	private JPanel playerHandStackPanel;

	// Window Size
	private final int xDefaultSize = 800;
	private final int yDefaultSize = 600;
	
	// last feature: background music
	private Clip backgroundClip;

	// Constructor
	public GameInterface() {
		setTitle("War Card Game");
		setSize(xDefaultSize, yDefaultSize); // Set pixel size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		ImageManager.preloadAllCardImages(120, 180);

		setupMenu();
		setupGameArea();
		setupControls();

		// wait for user to enter name before starting
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

	    // === New: Music Menu ===
	    JMenu musicMenu = new JMenu("Music");
	    JMenuItem musicOnItem = createMenuItem("On", e -> playBackgroundMusic());
	    JMenuItem musicOffItem = createMenuItem("Off", e -> stopBackgroundMusic());
	    musicMenu.add(musicOnItem);
	    musicMenu.add(musicOffItem);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(createMenuItem("About", e -> showAbout()));

		menuBar.add(fileMenu);
		menuBar.add(musicMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}

	private void setupGameArea() {
		gamePanel = new EdgePanel(10, 10);

		// game counter panel for the card counts
		JPanel countPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		playerCardCount = new JLabel("Your cards: 26", SwingConstants.CENTER);
		computerCardCount = new JLabel("Computer cards: 26", SwingConstants.CENTER);
		countPanel.add(playerCardCount);
		countPanel.add(computerCardCount);

		// card images and game status
		imagePanel = new GamePanel(1, 3, 10, 10);
		
		// imagePanel.setBackground(Color.BLUE); // for debug, comment if unneeded.
		gameLog = new JLabel("Initial Text.", SwingConstants.CENTER);
		gameLog.setForeground(Color.WHITE);
		playerCardImage = new JLabel();
		computerCardImage = new JLabel();

		// add images once UI loads them from left to right
		imagePanel.add(playerCardImage);
		imagePanel.add(gameLog, BorderLayout.CENTER); // add game log in the center
		imagePanel.add(computerCardImage);

		// add panels after render
		gamePanel.add(countPanel, BorderLayout.NORTH);
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

		// remove old stack panel if it exists
		if (playerHandStackPanel != null) {
			gamePanel.remove(playerHandStackPanel);
		}
		// create and add the initial stack panel
		playerHandStackPanel = createCardBackStack(game.getHumanCards().size());
		gamePanel.add(playerHandStackPanel, BorderLayout.SOUTH);

		updateUI();
		gameLog.setText("New game started! Good luck, " + name + "!\n");
		
		playBackgroundMusic();
	}

	// method to play round
	private void playRound() {
		// if no game instance start a new round
		if (game == null) {
			startNewGame();
			return;
		}

		// start a round
		String result;

		if (game.isWarInProgress()) {
			result = game.handleWar();
		} else {
			result = game.playRound();
		}

		// put result in gameLogs
		gameLog.setText(result);
		updateUI(); // start updating the UI

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

			// update last cards
			game.Card humanCard = game.getLastHumanCard();
			game.Card computerCard = game.getLastComputerCard();

			/* set images for the cards */
			// set images for player component
			playerCardImage.setIcon(humanCard != null ? humanCard.getImageIcon() : null);
			playerCardImage.setHorizontalAlignment(SwingConstants.CENTER);

			// set images for computer component
			computerCardImage.setIcon(computerCard != null ? computerCard.getImageIcon() : null);
			computerCardImage.setHorizontalAlignment(SwingConstants.CENTER);

			// Update stack panel for player's hand
			if (playerHandStackPanel != null) { // if there are cards on the stack, remove first
				gamePanel.remove(playerHandStackPanel);
			}
			playerHandStackPanel = createCardBackStack(game.getHumanPlayer().cardCount());
			gamePanel.add(playerHandStackPanel, BorderLayout.SOUTH);
			gamePanel.revalidate();
			gamePanel.repaint();
		}
	}

	// Helper method to create a stack of card backs
	private JPanel createCardBackStack(int count) {
		JPanel stackPanel = new JPanel(new HorizontalStackLayout(20)); // 20px overlap
		// if game is not ending yet and human player has cards
		if (game != null && game.getHumanPlayer().hasCards()) {
			// get cover.png
			ImageIcon backIcon = Card.getBackOfCardImage();
			// loop and add them into the stackPanel
			for (int i = 0; i < count; i++) {
				// scale the image to 30 by 40 pixels
				Image img = backIcon.getImage().getScaledInstance(30, 40, Image.SCALE_SMOOTH);
				stackPanel.add(new JLabel(new ImageIcon(img)));
			}
		}
		return stackPanel;
	}

	// store the data into GameSaver
	private void saveGame() {
		// if no game, print GTFO & error message
		if (game == null) {
			JOptionPane.showMessageDialog(this, "No game to save!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// create a chooser swing
		javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				// try to save the game
				util.GameSaver.saveGame(game, chooser.getSelectedFile().getPath());
				gameLog.setText("Game saved successfully!");
			} catch (java.io.IOException e) {
				// print error and GTFO
				JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// load the data from GameSaver
	private void loadGame() {
		// call swing right away
		javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				// load game based on selected file
				game = util.GameSaver.loadGame(chooser.getSelectedFile().getPath());
				updateUI();
				gameLog.setText("Game loaded successfully!");
			} catch (Exception e) {
				// if cannot be loaded, print errors
				JOptionPane.showMessageDialog(this, "Load failed: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void showAbout() {
		JOptionPane.showMessageDialog(this, """
											War Card Game
											Developed by:
											Kabuniang Buhawi Monjardin
											Fatih Selim Salihoglu
											Kostiantyn Ivanchenko
											Shukurolloh Abdulbokiev
											Ezra Runnath
											Fernando Belloza

											Rules:
											1. Highest card wins the round
											2. Equal cards trigger a war
											3. Winner takes all cards""",
				"About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// === last feature: Background music ===
	private void playBackgroundMusic() {
	    try {
	        if (backgroundClip != null && backgroundClip.isRunning()) {
	            System.out.println("Music already playing.");
	            return;
	        }
	        URL soundURL = getClass().getResource("/audio/main_bg.wav");
	        if (soundURL == null) {
	            System.err.println("Audio file not found!");
	            return;
	        }
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
	        backgroundClip = AudioSystem.getClip();
	        backgroundClip.open(audioIn);
	        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
//	        System.out.println("Background music started.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// Optional: Call this when closing the game to stop music cleanly
	public void stopBackgroundMusic() {
		if (backgroundClip != null && backgroundClip.isRunning()) {
			backgroundClip.stop();
			backgroundClip.close();
//			System.out.println("Background music stopped.");
		}
	}
}
