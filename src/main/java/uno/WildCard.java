package uno;

import java.util.Random;

/**
 * Represents a wild card in the Uno game
 * <p>
 * Class invariant: Wild cards can't have a type or value other than <i>Wild</i>. Wild cards are the only cards in the Uno game that can change their effective color.
 * <p>
 * When the game starts, a wild card will have no effective color. Whenever a wild card is placed, a new effective color will be set.
 */
public class WildCard extends Card {
    /**
     * Constructor
     */
    public WildCard() {
        super("Wild", "Wild", "None");
    }

    /**
     * Sets the effective color of this card. Is guarded so that this is only possible for wild cards.
     * @param color the new effective color
     */
    public void setEffectiveColor(String color) {
        super.setColor(color);
    }

    /**
     * Randomly sets the effective color of this card. Is guarded so that this is only possible for wild cards.
     */
    public void setRandomEffectiveColor() {
        Random r = new Random();
        int i = r.nextInt(colors.length - 1);
        setEffectiveColor(colors[i]);
    }

    /**
     * @return a string representation of this wild card
     */
    @Override
    public String toString() {
        return "Wild set to " + getColor();
    }
}
