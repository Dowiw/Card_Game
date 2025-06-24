package game;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
        int[] values = {2,3,4,5,6,7,8,9,10,11,12,13,14};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void deal(Player player1, Player player2) {
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) player1.receiveCard(cards.get(i));
            else player2.receiveCard(cards.get(i));
        }
    }
}