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
    
    private transient boolean warInProgress = false;
    
    private transient ArrayList<Card> humanCards;
    private transient ArrayList<Card> computerCards;
    
    // Track last played cards
    private transient Card lastHumanCard;
    private transient Card lastComputerCard;

    // Gamelogic Constructor
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
        System.out.println("Comparison: " + comparison);
        System.out.println("Rank: " + lastHumanCard.getRank() + " Suit: " + lastHumanCard.getSuit() + " Value: " + lastHumanCardValue);
        System.out.println("Rank: " + lastComputerCard.getRank() + " Suit: " + lastComputerCard.getSuit() + " Value: " + lastComputerCardValue);

        // end when there is a war to 
        if (comparison == 0) {
        	warInProgress = true;
        	return ("There is a WAR in progress!");
        }
        
        if (comparison > 0) { // if human value is greater (human wins round)
        	
        	// human gets all cards in current pot
            humanCards.addAll(currentPot);
            
            // shuffle cards for both players
            Collections.shuffle(humanCards);
            Collections.shuffle(computerCards);
            
            // clear current pot for next round
            currentPot.clear();
            System.out.println(human.getName() + " wins with " + lastHumanCard);
            return (human.getName() + " wins with " + lastHumanCard);
        } else if (comparison < 0) { // if computer value is greater (computer wins round)
        	
        	// computer gets all cards
            computer.getCards().addAll(currentPot);
            
            // shuffle cards
            Collections.shuffle(humanCards);
            Collections.shuffle(computerCards);
            
            // clear pot for next round
            currentPot.clear();
            System.out.println(computer.getName() + " wins with " + lastComputerCard);
            return (computer.getName() + " wins with " + lastComputerCard);
        } else { // there is war for comparison 0
        	warInProgress = true;
            return handleWar();
        }
    }

    // recursive calling to handle war
    public String handleWar() {
    	
    	// end loop when war is done
    	if (!warInProgress) return (playRound());
    	
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

        // no cards, end game
        if (lastHumanCard == null || lastComputerCard == null) {
            return gameOver();
        }

        currentPot.add(lastHumanCard);
        currentPot.add(lastComputerCard);
        
        int lastHumanCardValue = lastHumanCard.getValue();
        int lastComputerCardValue = lastComputerCard.getValue();
        
        int comparison = lastHumanCardValue - lastComputerCardValue;
        System.out.println("Comparison: " + comparison);
        System.out.println("Rank: " + lastHumanCard.getRank() + " Suit: " + lastHumanCard.getSuit() + " Value: " + lastHumanCardValue);
        System.out.println("Rank: " + lastComputerCard.getRank() + " Suit: " + lastComputerCard.getSuit() + " Value: " + lastComputerCardValue);
        
        if (comparison > 0) { // if human value is greater (human wins round)
        	
        	// human gets all cards in current pot
            humanCards.addAll(currentPot);
            
            // shuffle cards for both players
            Collections.shuffle(humanCards);
            Collections.shuffle(computerCards);
            
            // clear current pot for next round
            currentPot.clear();
            System.out.println(human.getName() + " wins with " + lastHumanCard); // debug
            
            // end war
            warInProgress = false;
            return (human.getName() + " wins with " + lastHumanCard);
        } else if (comparison < 0) { // if computer value is greater (computer wins round)
        	
        	// computer gets all cards
            computer.getCards().addAll(currentPot);
            
            // shuffle cards
            Collections.shuffle(humanCards);
            Collections.shuffle(computerCards);
            
            // clear pot for next round
            currentPot.clear();
            System.out.println(computer.getName() + " wins with " + lastComputerCard);
            
            // end war
            warInProgress = false;
            return (computer.getName() + " wins with " + lastComputerCard);
        } else { // there is war for comparison 0
            return handleWar();
        }
    }

	private String gameOver() {
        return human.cardCount() > computer.cardCount() ?
            human.getName() + " wins the game!" :
            computer.getName() + " wins the game!";
    }

    /*	getters	*/ 
    public boolean isWarInProgress() {
		return warInProgress;
	}
	
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
