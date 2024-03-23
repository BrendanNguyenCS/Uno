package uno;

import java.util.Arrays;

/**
 * Represents a special card in the Uno game.
 * <p>
 * Class invariant: Card values must be one of: <i>Skip</i>, <i>Reverse</i>, or <i>Draw Two</i>.
 */
public class SpecialCard extends Card {
    /**
     * Possible values for a Uno special card
     */
    public static final String[] values = {"Skip", "Reverse", "Draw Two"};

    /**
     * Constructor
     * @param value the card's value
     * @param color the card's (effective) color
     * @throws IllegalArgumentException if the card has an invalid special card value and/or an invalid color
     */
    public SpecialCard(String value, String color) {
        super("Special",
              Arrays.asList(values).contains(value) ? value : "",
              color);
    }

    /**
     * @return the string representation of this special card
     */
    @Override
    public String toString() {
        return getColor() + " " + getValue();
    }
}
