package uno;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    @DisplayName("Player: constructor")
    void constructor() {
        Player me = new Player("Brendan");
        assertEquals("Brendan", me.getName());
        assertEquals(0, me.getScore());
        assertTrue(me.getHand().isEmpty());
    }

    @Test
    @DisplayName("Player: add points")
    void addPoints() {
        Player me = new Player("Brendan");
        me.addPoints(100);
        assertEquals(100, me.getScore());
    }

    @Test
    @DisplayName("Player: add to hand")
    void addToHand() {
        Player me = new Player("Brendan");
        Card c = new NormalCard(1, "Red");
        me.addToHand(c);
        assertEquals(1, me.getHand().size());
    }

    @Test
    @DisplayName("Player: check name")
    void getName() {
        Player me = new Player("Brendan");
        assertEquals("Brendan", me.getName());
    }

    @Test
    @DisplayName("Player: toString")
    void toStringTest() {
        Player me = new Player("Brendan");
        assertEquals("Brendan", me.toString());
    }

    @Nested
    @DisplayName("Player: play card")
    class PlayCard {
        @Test
        @DisplayName("Only 1 playable card")
        void hasPlayableCard() {
            Player me = new Player("Brendan");
            Card c = new NormalCard(1, "Red");
            me.addToHand(c);
            assertEquals(1, me.getHand().size());
            Card lastPlayed = new SpecialCard("Draw Two", "Red");
            me.playCard(lastPlayed);
            assertEquals(0, me.getHand().size());
        }

        @Test
        @DisplayName("Multiple playable cards")
        void hasMultiplePlayableCards() {
            Player me = new Player("Brendan");
            Card c = new NormalCard(1, "Red");
            Card c2 = new WildCard();
            Card c3 = new SpecialCard("Skip", "Blue");
            me.addToHand(c);
            me.addToHand(c2);
            me.addToHand(c3);
            assertEquals(3, me.getHand().size());
            Card lastPlayed = new SpecialCard("Draw Two", "Red");
            me.playCard(lastPlayed);
            assertEquals(2, me.getHand().size());
        }

        @Test
        @DisplayName("No playable cards")
        void hasNoPlayableCards() {
            Player me = new Player("Brendan");
            Card c = new NormalCard(1, "Red");
            Card c2 = new NormalCard(9, "Yellow");
            Card c3 = new SpecialCard("Skip", "Blue");
            me.addToHand(c);
            me.addToHand(c2);
            me.addToHand(c3);
            assertEquals(3, me.getHand().size());
            Card lastPlayed = new SpecialCard("Draw Two", "Green");
            assertNull(me.playCard(lastPlayed));
            assertEquals(3, me.getHand().size());
        }
    }

    @Test
    @DisplayName("Player: has empty hand")
    void hasEmptyHand() {
        Player me = new Player("Brendan");
        assertTrue(me.hasEmptyHand());
        Card c = new NormalCard(1, "Red");
        me.addToHand(c);
        assertFalse(me.hasEmptyHand());
        me.playCard(c);
        assertTrue(me.hasEmptyHand());
    }
}