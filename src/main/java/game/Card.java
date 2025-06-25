package game;

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
    
    // set image icon as "transient" making sure its out of serialization
    private transient ImageIcon image;

    // Constructor
    public Card(String suit, String rank, int value) {
        this.suit = suit.toLowerCase();
        this.rank = rank.toLowerCase();
        this.value = value;
        this.image = createImageIcon();
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
    
	public ImageIcon getImage() {
		if (image == null) {
			image = createImageIcon();
		}
		return image;
	}
    
    @Override
    public String toString() {
    	return (rank + " of " + suit);
    }
    
    // method to return file path of image
    private String getImageFileName() {
        String rankFile;
        switch (rank) {
            case "Jack": rankFile = "jack";
            break;
            case "Queen": rankFile = "queen";
            break;
            case "King": rankFile = "king";
            break;
            case "Ace": rankFile = "ace";
            break;
            default: rankFile = rank; // For number cards (2-10)
        }
        return (rankFile + "_of_" + suit + ".png");
    }
    
    // method to look for Image in resources
    private ImageIcon createImageIcon() {
    	String imagePath = "/cards/" + suit + "/" + getImageFileName();
        
        // URL to locate file (standard locator)
        java.net.URL imgURL = getClass().getResource(imagePath);

        if (imgURL != null) {
        	 System.out.println("Successfully loaded: " + rank + "_of_" + suit);
            return (new ImageIcon(imgURL)); // return URL if found
        } else {
            System.err.println("Couldn't find card image: " + imagePath);
            return (getBackOfCardImage());
        }
    }
    
    // getter for default cover
    private ImageIcon getBackOfCardImage() {
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
