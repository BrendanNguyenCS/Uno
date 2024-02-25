package uno;

import lombok.Getter;
import java.util.*;

/**
 * Represents a player in the Uno game.
 */
@Getter
public class Player {
    /**
     * The player's current hand
     */
    private final List<Card> hand;
    /**
     * The player's name
     */
    private final String name;

    /**
     * Constructor
     */
    public Player(String name) {
        hand = new ArrayList<>();
        this.name = name;
    }

    /**
     * Add a new card to the player's hand
     * @param c the card to add
     */
    public void addToHand(Card c) {
        hand.add(c);
    }

    /**
     * Plays a random playable card from the player's hand
     * @param lastPlayed the last played card
     * @return the card being played, or {@code null} if the player has no playable cards
     */
    public Card playCard(Card lastPlayed) {
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isPlayable(lastPlayed)) {
                playableCards.add(card);
            }
        }

        if (!playableCards.isEmpty()) {
            Random rand = new Random();
            int i = rand.nextInt(playableCards.size());
            Card c = playableCards.get(i);
            if (hand.remove(c)) {
                return c;
            }
        }

        return null;
    }

    /**
     * Determines if this player's hand is empty
     */
    public boolean hasEmptyHand() {
        return hand.isEmpty();
    }

    /**
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        return name;
    }
}
