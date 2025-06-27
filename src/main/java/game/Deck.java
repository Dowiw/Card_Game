package game;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

public class Deck {
	private final ArrayList<Card> cards;

	public Deck() {
		cards = new ArrayList<>();

		System.out.println("Loading Images...");
		initializeDeck();

		// shuffle cards using shuffle method
		Collections.shuffle(cards);

		System.out.println("Cards shuffled.");
	}

	// method to initialize card data
	private void initializeDeck() {
		String[] suits = {"hearts", "diamonds", "clubs", "spades"};
		String[] ranks = {"2","3","4","5","6","7","8","9","10","jack","queen","king","ace"};
		int[] values = {2,3,4,5,6,7,8,9,10,11,12,13,14};

		for (String suit : suits) {
			for (int i = 0; i < ranks.length; i++) {
				cards.add(new Card(suit, ranks[i], values[i]));
			}
		}
	}

	public void deal(Player player1, Player player2) {
		for (int i = 0; i < cards.size(); i++) {
			if (i % 2 == 0) player1.receiveCard(cards.get(i));
			else player2.receiveCard(cards.get(i));
		}
	}

	public ImageIcon getBackOfCardImage() {
		return (cards.getFirst().getBackOfCardImage());
	}
}
