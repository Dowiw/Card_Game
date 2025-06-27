package util;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.net.URL;

public class ImageManager {
	private static final Map<String, ImageIcon> cardImageCache = new HashMap<>();
	private static Image backgroundImage;

	public static Image getBackgroundImage() {
		if (backgroundImage == null) {
			backgroundImage = loadImage("/background/table_bg.jpg");
		}
		return backgroundImage;
	}

	public static ImageIcon getCardImage(String path) {
		if (!cardImageCache.containsKey(path)) {
			cardImageCache.put(path, new ImageIcon(ImageManager.class.getResource(path)));
		}
		return cardImageCache.get(path);
	}

	public static void preloadAllCardImages(int width, int height) {
		String[] suits = {"hearts", "diamonds", "clubs", "spades"};
		String[] ranks = {"2","3","4","5","6","7","8","9","10","jack","queen","king","ace"};
		for (String suit : suits) {
			for (String rank : ranks) {
				String path = "/cards/" + suit + "/" + rank + "_of_" + suit + ".png";
				getImage(path, width, height);
			}
		}
		// Preload back of card
		getImage("/cards/card_commons/cover.png", width, height);
	}

	public static ImageIcon getImage(String path, int width, int height) {
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

	private static Image loadImage(String path) {
		URL url = ImageManager.class.getResource(path);
		if (url != null) {
			return new ImageIcon(url).getImage();
		}
		return null;
	}
}
