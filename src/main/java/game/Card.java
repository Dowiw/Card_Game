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
        this.suit = suit;
        this.rank = rank;
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

    // get the image icon from the image manager
	public ImageIcon getImageIcon() {
		String path = "/cards/" + suit + "/" + getImageFileName();
		return ImageManager.getImage(path, CARD_WIDTH, CARD_HEIGHT);
	}

	// to string, is basically when the object is called, its details get printed
    @Override
    public String toString() {
    	// capitalize the first letter of the rank & suit
    	String capRank = rank.substring(0, 1).toUpperCase() + rank.substring(1);
    	String capSuit = suit.substring(0, 1).toUpperCase() + suit.substring(1);
    	
    	return (capRank + " of " + capSuit);
    }

    // method to return file name of image
    private String getImageFileName() {
        String rankFile;
        rankFile = switch (rank) {
                case "jack" -> "jack";
                case "queen" -> "queen";
                case "king" -> "king";
                case "ace" -> "ace";
                default -> rank;
            }; // For number cards (2-10)
        return (rankFile + "_of_" + suit + ".png");
    }

    // getter for default cover
    public static ImageIcon getBackOfCardImage() {
    	String coverPath = "/cards/card_commons/cover.png";
    	return ImageManager.getCardImage(coverPath);
    }
}
