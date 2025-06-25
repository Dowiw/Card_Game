package game;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLogic implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Player human;
    private final Player computer;
    private transient ArrayList<Card> currentPot;

    public GameLogic(String playerName) {
        this.human = new Player(playerName);
        this.computer = new Player("Computer");
        this.currentPot = new ArrayList<>();
        new Deck().deal(human, computer);
    }

    public String playRound() {
        if (!human.hasCards() || !computer.hasCards()) {
            return gameOver();
        }

        Card humanCard = human.playCard();
        Card computerCard = computer.playCard();

        if (humanCard == null || computerCard == null) {
            return gameOver();
        }

        currentPot.add(humanCard);
        currentPot.add(computerCard);

        int comparison = Integer.compare(humanCard.getValue(), computerCard.getValue());

        if (comparison > 0) {
            human.getCards().addAll(currentPot);
            currentPot.clear();
            return human.getName() + " wins with " + humanCard;
        } else if (comparison < 0) {
            computer.getCards().addAll(currentPot);
            currentPot.clear();
            return computer.getName() + " wins with " + computerCard;
        } else {
            return handleWar();
        }
    }

    private String handleWar() {
        if (!human.hasCards() || !computer.hasCards()) {
            return gameOver();
        }

        // Add face-down cards
        currentPot.add(human.playCard());
        currentPot.add(computer.playCard());

        // Add face-up cards
        Card humanCard = human.playCard();
        Card computerCard = computer.playCard();

        if (humanCard == null || computerCard == null) {
            return gameOver();
        }

        currentPot.add(humanCard);
        currentPot.add(computerCard);

        int comparison = Integer.compare(humanCard.getValue(), computerCard.getValue());

        if (comparison > 0) {
            human.getCards().addAll(currentPot);
            currentPot.clear();
            return "WAR! " + human.getName() + " wins!";
        } else if (comparison < 0) {
            computer.getCards().addAll(currentPot);
            currentPot.clear();
            return "WAR! " + computer.getName() + " wins!";
        } else {
            return handleWar();
        }
    }

    private String gameOver() {
        return human.cardCount() > computer.cardCount() ?
            human.getName() + " wins the game!" :
            computer.getName() + " wins the game!";
    }

    public Player getHumanPlayer() {
    	return human;
    }
    public Player getComputerPlayer() { return computer; }
}
