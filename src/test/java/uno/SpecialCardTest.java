package uno;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class SpecialCardTest {
    @Nested
    @DisplayName("Special Card: Constructor")
    class ExceptionTests {
        @Test
        @DisplayName("Valid card and color values")
        void validCard() {
            assertDoesNotThrow(() -> new SpecialCard("Reverse", "Red"));
        }

        @Test
        @DisplayName("Invalid card value")
        void invalidValue() {
            assertThrows(IllegalArgumentException.class, () -> new SpecialCard("Draw Four", "Red"));
        }

        @Test
        @DisplayName("Invalid color value")
        void invalidColor() {
            assertThrows(IllegalArgumentException.class, () -> new SpecialCard("Skip", "Purple"));
        }
    }

    @Nested
    @DisplayName("Special Card: Playable")
    class PlayableTests {
        final SpecialCard yellowDrawTwo = new SpecialCard("Draw Two", "Yellow");

        @Test
        @DisplayName("Yellow Draw Two is playable on Yellow 7")
        void playable1() {
            NormalCard yellow7 = new NormalCard(7, "Yellow");
            assertTrue(yellowDrawTwo.isPlayable(yellow7));
        }

        @Test
        @DisplayName("Yellow Draw Two is playable on Red Draw Two")
        void playable2() {
            SpecialCard redDrawTwo = new SpecialCard("Draw Two", "Red");
            assertTrue(yellowDrawTwo.isPlayable(redDrawTwo));
        }

        @Test
        @DisplayName("Yellow Draw Two is playable on Wild with Yellow effective color")
        void playable3() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Yellow");
            assertTrue(yellowDrawTwo.isPlayable(w));
        }

        @Test
        @DisplayName("Yellow Draw Two is playable on Yellow Skip")
        void playable4() {
            SpecialCard yellowSkip = new SpecialCard("Skip", "Yellow");
            assertTrue(yellowDrawTwo.isPlayable(yellowSkip));
        }

        @Test
        @DisplayName("Yellow Draw Two is not playable on Red 5")
        void notPlayable1() {
            NormalCard red5 = new NormalCard(5, "Red");
            assertFalse(yellowDrawTwo.isPlayable(red5));
        }

        @Test
        @DisplayName("Yellow Draw Two is not playable on Green Skip")
        void notPlayable2() {
            SpecialCard greenSkip = new SpecialCard("Skip", "Green");
            assertFalse(yellowDrawTwo.isPlayable(greenSkip));
        }

        @Test
        @DisplayName("Yellow Draw Two is not playable on Wild with Blue effective color")
        void notPlayable3() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Green");
            assertFalse(yellowDrawTwo.isPlayable(w));
        }
    }

    @Test
    @DisplayName("Special Card: toString")
    void toStringTest() {
        SpecialCard yellowDrawTwo = new SpecialCard("Draw Two", "Yellow");
        assertEquals("Yellow Draw Two", yellowDrawTwo.toString());

        SpecialCard redSkip = new SpecialCard("Skip", "Red");
        assertEquals("Red Skip", redSkip.toString());

        SpecialCard blueReverse = new SpecialCard("Reverse", "Blue");
        assertEquals("Blue Reverse", blueReverse.toString());
    }
}