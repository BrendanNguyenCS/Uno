package uno;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Nested
    @DisplayName("Deck: add to deck")
    class AddToDeck {
        @Test
        @DisplayName("adding a single card")
        void addOneCard() {
            Deck d = new Deck();
            Card c = new NormalCard(6, "Red");
            d.addToDeck(c);
            assertTrue(d.getDeck().contains(c));
        }

        @Test
        @DisplayName("Adding multiple cards")
        void addMultipleCards() {
            Deck d = new Deck();
            Deck oldDeck = new Deck(1, 1, 1);
            d.addToDeck(oldDeck);
            for (Card c : oldDeck.getDeck()) {
                assertTrue(d.getDeck().contains(c));
            }
        }
    }

    @Test
    @DisplayName("Deck: check for empty deck")
    void isDeckEmpty() {
        Deck d = new Deck();

        // init empty deck
        assertTrue(d.isDeckEmpty());

        // add and remove cards from this deck
        d.addToDeck(new NormalCard(6, "Red"));
        assertFalse(d.isDeckEmpty());
        d.drawFromDeck();
        assertTrue(d.isDeckEmpty());
    }

    @Nested
    @DisplayName("Deck: initiate deck")
    class InitiateDeck {
        @Test
        @DisplayName("Base path")
        void initiateDeck1() {
            Deck d = new Deck(1, 1, 1);
            assertEquals(53, d.getDeck().size());
        }

        @Nested
        @DisplayName("Exceptions")
        class InitiateDeckExceptions {
            @Test
            @DisplayName("Invalid count digit cards per color")
            void invalidDigitCount() {
                assertThrows(IllegalArgumentException.class, () -> new Deck(0, 1, 1));
            }

            @Test
            @DisplayName("Invalid count special cards per color")
            void invalidSpecialCount() {
                assertThrows(IllegalArgumentException.class, () -> new Deck(1, -1, 1));
            }

            @Test
            @DisplayName("Invalid count wild cards")
            void invalidWildCount() {
                assertThrows(IllegalArgumentException.class, () -> new Deck(1, 1, -1));
            }
        }
    }

    @Test
    @DisplayName("Deck: draw from deck")
    void drawFromDeck() {
        Deck d = new Deck();
        Card c = new NormalCard(6, "Red");
        d.addToDeck(c);
        assertEquals(c, d.drawFromDeck());
    }

    @Test
    @DisplayName("Deck: clear deck")
    void clearDeck() {
        Deck d = new Deck(5, 3, 6);
        d.clearDeck();
        assertTrue(d.isDeckEmpty());
    }

    @Test
    @DisplayName("Deck: get top card")
    void getTopCard() {
        Deck d = new Deck();
        Card c = new NormalCard(6, "Red");
        d.addToDeck(c);
        assertEquals(c, d.getTopCard());
    }
}