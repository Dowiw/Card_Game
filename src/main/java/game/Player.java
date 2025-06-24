package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
    private final ArrayList<Card> cards;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public void receiveCard(Card card) {
        cards.add(card);
    }

    public Card playCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    public int cardCount() {
        return cards.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
