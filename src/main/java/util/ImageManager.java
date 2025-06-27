package util;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/*
 * Class that handles ImageManagement:
 * - Pre-loads images as ImageIcon to hashmap
 * - Stores background image
 * - Stores edge image
 */
public class ImageManager {
	private static final Map<String, ImageIcon> cardImageCache = new HashMap<>();
	private static Image backgroundImage;
	private static Image edgesImage;

	// method to load the background image of the table
	public static Image getBackgroundImage() {
		if (backgroundImage == null) {
			backgroundImage = loadImage("/background/table_bg.jpg");
		}
		return backgroundImage;
	}

	// method to load the edges image of the table
	public static Image getEdgesImage() {
		if (edgesImage == null) {
			edgesImage = loadImage("/background/edges_bg.jpg");
		}
		return edgesImage;
	}

	// method to get the image icon of a card based on its path
	public static ImageIcon getCardImage(String path) {
		if (!cardImageCache.containsKey(path)) {
			cardImageCache.put(path, new ImageIcon(ImageManager.class.getResource(path)));
		}
		return cardImageCache.get(path);
	}

	// write the paths of the images and send them to getImage method
	public static void preloadAllCardImages(int width, int height) {
		String[] suits = {"hearts", "diamonds", "clubs", "spades"};
		String[] ranks = {"2","3","4","5","6","7","8","9","10","jack","queen","king","ace"};
		for (String suit : suits) {
			for (String rank : ranks) {
				String path = "/cards/" + suit + "/" + rank + "_of_" + suit + ".png";
				getImage(path, width, height);
			}
		}
		// pre-load back of card
		getImage("/cards/card_commons/cover.png", width, height);
	}

	// load all cards in the hash map
	public static ImageIcon getImage(String path, int width, int height) {
		// the keys of the map are the paths with sizes
		String key = path + "_" + width + "x" + height;
		if (!cardImageCache.containsKey(key)) {
			java.net.URL imgURL = ImageManager.class.getResource(path);
			if (imgURL != null) {
				ImageIcon icon = new ImageIcon(imgURL);
				Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
				cardImageCache.put(key, new ImageIcon(scaled));
			} else {
				cardImageCache.put(key, new ImageIcon());
			}
		}
		return cardImageCache.get(key);
	}

	// get an image using the path
	private static Image loadImage(String path) {
		URL url = ImageManager.class.getResource(path);
		if (url != null) {
			return new ImageIcon(url).getImage();
		}
		return null;
	}
}
