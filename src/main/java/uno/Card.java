package uno;

import lombok.*;

import java.util.*;

/**
 * Represents a card in the Uno game.
 */
@Getter
public abstract class Card {
    /**
     * The list of possible colors for Uno cards. The last element is "None" which is used for wild cards.
     */
    public static final String[] colors = {"Red", "Yellow", "Green", "Blue"};
    /**
     * The list of possible types for Uno cards.
     */
    public static final String[] types = {"Normal", "Special", "Wild"};
    /**
     * The card type of this Card. There are 3 types of Uno cards: "Normal", "Special", and "Wild".
     */
    private final String type;
    /**
     * The value of this Card. The possible value of the card is determined by its type.
     * <p>
     * - Normal cards have values between "0" and "9"<br>
     * - Special cards have values of either "Skip", "Reverse", or "Draw Two"<br>
     * - Wild cards have a value of "Wild"
     */
    private final String value;
    /**
     * The color of this card. Normal and special cards are colored red, yellow, green, or blue. Wild cards are colorless, but
     * the value of this variable will be the "effective" color once the card is played.
     */
    private String color;

    /**
     * Constructor
     * @param type the card's type
     * @param value the card's value
     * @param color the card's (effective) color
     * @throws IllegalArgumentException if any of the input values are invalid
     */
    public Card(String type, String value, String color) {
        if (Arrays.asList(types).contains(type) &&
            Arrays.asList(colors).contains(color) &&
            !value.isEmpty()) {
            this.type = type;
            this.color = color;
            this.value = value;
        } else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }

    /**
     * Constructor (to be utilized only by {@link WildCard})
     * @param type the card's type
     * @param value the card's value
     * @throws IllegalArgumentException if any of the input values are invalid
     */
    public Card(String type, String value) {
        if (Arrays.asList(types).contains(type) && !value.isEmpty()) {
            this.type = type;
            this.value = value;
            this.color = "None";
        } else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }

    /**
     * Sets the color of the card. This method is only used for wild cards.
     * @param color the color to set
     * @throws IllegalArgumentException if the color is not one of the 4 colors
     */
    public void setColor(String color) throws IllegalArgumentException {
        if (Arrays.asList(colors).contains(color)) {
            this.color = color;
        } else {
            throw new IllegalArgumentException("Invalid color");
        }
    }

    /**
     * Denotes whether the current card is playable. All wild cards are playable at any time while normal and special cards of
     * the same color or value as the last played card can be played.
     * @param lastPlayed the last played card
     */
    public boolean isPlayable(Card lastPlayed) {
        // For normal and special cards, the color or face value must match
        if (type.equals("Normal") || type.equals("Special")) {
            return color.equals(lastPlayed.color) || value.equals(lastPlayed.value);
        }
        // All wild cards are playable
        return true;
    }

    /**
     * Determines if this card is equal to another card. Two cards are equal if they have the same color, type, and value.
     * @param o the object to compare
     * @return {@code true} if the cards are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Card c) {
            return color.equals(c.color) && type.equals(c.type) && value.equals(c.value);
        }
        return false;
    }

    /**
     * Returns a string representation of this card.
     */
    public String toString;
}
