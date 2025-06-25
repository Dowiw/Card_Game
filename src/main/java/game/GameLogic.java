package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class GameLogic implements Serializable {
	// do not change values of serial, human, computer during game execution
	private static final long serialVersionUID = 1L;
	private final Player human;
    private final Player computer;
    private transient ArrayList<Card> currentPot;
    
    private transient ArrayList<Card> humanCards;
    private transient ArrayList<Card> computerCards;
    
    // Track last played cards
    private transient Card lastHumanCard;
    private transient Card lastComputerCard;

    public GameLogic(String playerName) {
        this.human = new Player(playerName);
        this.computer = new Player("Computer");
        this.currentPot = new ArrayList<>();
		this.humanCards = new ArrayList<>();
		this.computerCards = new ArrayList<>();
        new Deck().deal(human, computer);
    }

    // logic for a round in game logic
    public String playRound() {
    	humanCards = human.getCards();
    	computerCards = computer.getCards();

        if (!human.hasCards() || !computer.hasCards()) {
            return gameOver();
        }

        // a card from each will be played
        Card humanCard = human.playCard();
        Card computerCard = computer.playCard();

        // store last played cards
        lastHumanCard = humanCard;
        lastComputerCard = computerCard;

        // no cards, end game
        if (lastHumanCard == null || lastComputerCard == null) {
            return gameOver();
        }

        currentPot.add(lastHumanCard);
        currentPot.add(lastComputerCard);
        
        int lastHumanCardValue = lastHumanCard.getValue();
        int lastComputerCardValue = lastComputerCard.getValue();
        
        int comparison = lastHumanCardValue - lastComputerCardValue;

        if (comparison > 0) { // if human value is greater (human wins round)
        	
        	// human gets all cards in current pot
            humanCards.addAll(currentPot);
            
            // shuffle cards for both players
            Collections.shuffle(humanCards);
            Collections.shuffle(computerCards);
            
            // clear current pot for next round
            currentPot.clear();
            return (human.getName() + " wins with " + lastHumanCard);
        } else if (comparison < 0) { // if computer value is greater (computer wins round)
        	
        	// computer gets all cards
            computer.getCards().addAll(currentPot);
            
            // shuffle cards
            Collections.shuffle(humanCards);
            Collections.shuffle(computerCards);
            
            // clear pot for next round
            currentPot.clear();
            return computer.getName() + " wins with " + lastComputerCard;
        } else { // there is war for comparison 0
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
        
        // Store last played cards for war
        lastHumanCard = humanCard;
        lastComputerCard = computerCard;

        if (lastHumanCard == null || lastComputerCard == null) {
            return gameOver();
        }

        currentPot.add(lastHumanCard);
        currentPot.add(lastComputerCard);
        
        int lastHumanCardValue = lastHumanCard.getValue();
        int lastComputerCardValue = lastComputerCard.getValue();
        
        int comparison = lastHumanCardValue - lastComputerCardValue;

        if (comparison > 0) { // human wins (positive value)
            human.getCards().addAll(currentPot);
            currentPot.clear();
            return "WAR! " + human.getName() + " wins!";
        } else if (comparison < 0) { // computer wins (negative value)
            computer.getCards().addAll(currentPot);
            currentPot.clear();
            return "WAR! " + computer.getName() + " wins!";
        } else { // cards are equal when 0
            return handleWar(); // recursive call until someone wins
        }
    }

    private String gameOver() {
        return human.cardCount() > computer.cardCount() ?
            human.getName() + " wins the game!" :
            computer.getName() + " wins the game!";
    }

    /*	getters	*/ 
    public Player getHumanPlayer() {
    	return human;
    }

    public Player getComputerPlayer() {
        return computer;
    }
    
    public Card getLastHumanCard() {
        return lastHumanCard;
    }

    public Card getLastComputerCard() {
        return lastComputerCard;
    }

	public ArrayList<Card> getHumanCards() {
		return humanCards;
	}

	public ArrayList<Card> getComputerCards() {
		return computerCards;
	}
}
