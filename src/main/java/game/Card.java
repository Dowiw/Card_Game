package game;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/*
 * The class for Cards.
 * Uses Serialized library ensures data saved.
 * Saved states can be called by user.
 */
public class Card implements Serializable {
	private static final long serialVersionUID = 1L; // assign as first serial id
	private final String suit; // hearts, spades, diamonds, clubs
    private final String rank; // e.g. Ace
    private final int value; // value for logic (1 - ace, ...)
    
    // Card Size (do not remove final keyword)
    private final int cardWidth = 120;
    private final int cardHeight = 180;
    
    // set image icon as "transient" making sure its out of serialization
    private transient ImageIcon imageIcon;

    // Constructor
    public Card(String suit, String rank, int value) {
        this.suit = suit.toLowerCase();
        this.rank = rank.toLowerCase();
        this.value = value;
        this.imageIcon = createImageIcon();
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

	public ImageIcon getImageAndResize() {
		if (imageIcon == null) {
			imageIcon = createImageIcon();
		}
		
		// scale image down to fitting pixels
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH);
        
		ImageIcon newImageIcon = new ImageIcon(newImage);
		return (newImageIcon);
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

    // method to look for Image in resources
    public ImageIcon createImageIcon() {
    	String imagePath = "/cards/" + suit + "/" + getImageFileName();

        // URL to locate file (standard locator)
        java.net.URL imgURL = getClass().getResource(imagePath);
        
        if (imgURL != null) {
            return (new ImageIcon(imgURL)); // return URL if found
        } else {
            System.err.println("Couldn't find card image: " + imagePath);
            return (getBackOfCardImage());
        }
    }

    // getter for default cover
    public ImageIcon getBackOfCardImage() {
    	String coverPath = "/cards/card_commons/cover.png";

    	// URL to locate default cover
        java.net.URL imgURL = getClass().getResource(coverPath);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find back of card image: cover.png");

            // Create a blank image as final fallback
            return new ImageIcon(new java.awt.image.BufferedImage(100, 150,
                java.awt.image.BufferedImage.TYPE_INT_ARGB));
        }
    }
}
