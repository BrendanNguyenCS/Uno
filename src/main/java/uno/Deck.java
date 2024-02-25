package uno;

import lombok.Getter;
import java.util.*;

/**
 * Represents the card deck in the Uno game
 * <p>
 * Class invariants
 * <ul>
 *     <li>The deck must have 1 or more {@link NormalCard normal} card per digit and color.</li>
 *     <li>The deck must have 0 or more {@link SpecialCard special} cards per color.</li>
 *     <li>The deck must have 0 or more {@link WildCard wild} cards.</li>
 * </ul>
 */
@Getter
public class Deck {
    /**
     * The card deck
     */
    private final LinkedList<Card> deck;

    /**
     * Creates a new full deck
     * @param countDigitCardsPerColor the number of normal cards for each digit and color
     * @param countSpecialCardsPerColor the number of special cards of each kind for each color
     * @param countWildCards the number of total wild cards
     */
    public Deck(int countDigitCardsPerColor,
                int countSpecialCardsPerColor,
                int countWildCards) {
        deck = new LinkedList<>();
        initiateDeck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
    }

    /**
     * Creates an empty deck
     */
    public Deck() {
        deck = new LinkedList<>();
    }

    /**
     * @return {@code true} if the deck is empty, {@code false} otherwise
     */
    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    /**
     * Create a new full deck of Uno cards given the conditions
     * @param countDigitCardsPerColor the number of normal cards for each digit and color
     * @param countSpecialCardsPerColor the number of special cards of each kind for each color
     * @param countWildCards the number of total wild cards
     */
    public void initiateDeck(int countDigitCardsPerColor,
                         int countSpecialCardsPerColor,
                         int countWildCards) {

        if (countDigitCardsPerColor <= 0 ||
            countSpecialCardsPerColor < 0 ||
            countWildCards < 0) {
            throw new IllegalArgumentException("Invalid values detected");
        }

        // List of possible colors and special card values
        String[] colors = { "Red", "Yellow", "Blue", "Green" };
        String[] valuesSpecial = SpecialCard.values;

        // add wild cards
        for (int i = 0; i < countWildCards; i++) {
            deck.add(new WildCard());
        }

        // add normal and special cards
        for (String color : colors) {
            // add normal cards per color
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k < countDigitCardsPerColor; k++) {
                    deck.add(new NormalCard(j, color));
                }
            }

            // add special cards per color
            for (String special : valuesSpecial) {
                for (int j = 0; j < countSpecialCardsPerColor; j++) {
                    deck.add(new SpecialCard(special, color));
                }
            }
        }

        // shuffle the deck
        shuffleDeck();
    }

    /**
     * Shuffles the deck using {@link Collections#shuffle}
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Adds a card to the top of the deck
     * @param c the card to be added
     */
    public void addToDeck(Card c) {
        deck.addFirst(c);
    }

    /**
     * Adds cards to the top of the deck
     * @param d the deck to add
     */
    public void addToDeck(Deck d) {
        deck.addAll(0, d.deck);
    }

    /**
     * Draws the top card from the deck
     * @return the drawn card
     */
    public Card drawFromDeck() {
        return deck.removeFirst();
    }

    /**
     * Empties the current deck. Should only be used after merging with another deck.
     */
    public void clearDeck() {
        deck.clear();
    }

    /**
     * Returns the top card of the deck without removing it
     */
    public Card getTopCard() {
        return deck.peekFirst();
    }
}
