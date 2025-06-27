package game;

import java.io.Serializable;

import javax.swing.ImageIcon;

import util.ImageManager;

/*
 * The class for Cards.
 * Uses Serialized library ensures data saved.
 * Saved states can be called by user.
 */
public final class Card implements Serializable {
	private static final long serialVersionUID = 1L; // assign as first serial id
	private final String suit; // hearts, spades, diamonds, clubs
    private final String rank; // e.g. Ace
    private final int value; // value for logic (1 - ace, ...)

    // Card Size (do not remove final keyword)
    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 180;

    // Constructor
    public Card(String suit, String rank, int value) {
        this.suit = suit.toLowerCase();
        this.rank = rank.toLowerCase();
        this.value = value;
    }

    // Getters
    public int getValue() {
    	return value;
    }

    public String getRank() {
    	return rank;
    }

    public String getSuit() {
    	return suit;
    }

	public ImageIcon getImageIcon() {
		String path = "/cards/" + suit + "/" + getImageFileName();
		return ImageManager.getImage(path, CARD_WIDTH, CARD_HEIGHT);
	}

    @Override
    public String toString() {
    	return (rank + " of " + suit);
    }

    // method to return file path of image
    private String getImageFileName() {
        String rankFile;
        switch (rank) {
            case "jack": rankFile = "jack";
            break;
            case "queen": rankFile = "queen";
            break;
            case "king": rankFile = "king";
            break;
            case "ace": rankFile = "ace";
            break;
            default: rankFile = rank; // For number cards (2-10)
        }
        return (rankFile + "_of_" + suit + ".png");
    }

    // getter for default cover
    public static ImageIcon getBackOfCardImage() {
    	String coverPath = "/cards/card_commons/cover.png";
    	return ImageManager.getCardImage(coverPath);
    }
}
