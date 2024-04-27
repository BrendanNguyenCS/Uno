package uno;

/**
 * Represents a normal card in the Uno game.
 * <p>
 * Class invariant: Card values must be an integer between {@code 0} and {@code 9} inclusive.
 */
public class NormalCard extends Card {
    /**
     * Constructor
     * @param value the card's value
     * @param color the card's (effective) color
     * @throws IllegalArgumentException if the card's value is not between {@code 0} and {@code 9} inclusive and/or the color is invalid
     */
    public NormalCard(int value, String color) throws IllegalArgumentException {
        super("Normal",
              value >= 0 && value <= 9 ? String.valueOf(value) : "",
              color);
    }

    /**
     * @return the string representation of this normal card
     */
    @Override
    public String toString() {
        return getColor() + " " + getValue();
    }
}
