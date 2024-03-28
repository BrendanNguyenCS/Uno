package uno;

import org.junit.jupiter.api.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class WildCardTest {
    @Nested
    @DisplayName("Wild Card: Set effective color")
    class SetEffectiveColor {
        @Test
        @DisplayName("Set invalid color")
        void invalidColor() {
            WildCard w = new WildCard();
            assertThrows(IllegalArgumentException.class, () -> w.setEffectiveColor("Purple"));
        }

        @Test
        @DisplayName("Set valid color")
        void validColor() {
            WildCard w = new WildCard();
            assertDoesNotThrow(() -> w.setEffectiveColor("Blue"));
        }

        @Test
        @DisplayName("Set valid color for card with existing color")
        void validColorWithExistingColor() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Blue");
            assertDoesNotThrow(() -> w.setEffectiveColor("Red"));
        }

        @Test
        @DisplayName("Set invalid color for card with existing color")
        void invalidColorWithExistingColor() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Blue");
            assertThrows(IllegalArgumentException.class, () -> w.setEffectiveColor("Purple"));
        }
    }

    @Nested
    @DisplayName("Wild Card: Set random effective color")
    class SetRandomEffectiveColor {
        @Test
        @DisplayName("None cannot be an effective color")
        void noneIsInvalid() {
            WildCard w = new WildCard();
            assertEquals("None", w.getColor());
            w.setRandomEffectiveColor();

            // We want to reset the color enough times that every color should be chosen at least once
            // Ensures that the none option is not chosen
            for (int i = 0; i < 10; i++) {
                assertNotEquals("None", w.getColor());
                w.setRandomEffectiveColor();
            }
        }

        @Test
        @DisplayName("Effective color is set")
        void correctSet() {
            WildCard w = new WildCard();
            w.setRandomEffectiveColor();
            assertTrue(Set.of("Red", "Yellow", "Green", "Blue").contains(w.getColor()));
        }
    }

    @Nested
    @DisplayName("Wild Card: Playable")
    class PlayableTests {
        final WildCard w = new WildCard();

        @Test
        @DisplayName("Wild is playable on Red 6")
        void playable1() {
            w.setRandomEffectiveColor();
            NormalCard red6 = new NormalCard(6, "Red");
            assertTrue(w.isPlayable(red6));
        }

        @Test
        @DisplayName("Wild is playable on Blue Skip")
        void playable2() {
            w.setRandomEffectiveColor();
            SpecialCard blueSkip = new SpecialCard("Skip", "Blue");
            assertTrue(w.isPlayable(blueSkip));
        }

        @Test
        @DisplayName("Wild is playable on Wild with Red effective color")
        void playable3() {
            w.setRandomEffectiveColor();
            WildCard w2 = new WildCard();
            w2.setEffectiveColor("Red");
            assertTrue(w.isPlayable(w2));
        }
    }

    @Nested
    @DisplayName("Wild Card: toString")
    class ToString {
        @Test
        @DisplayName("toString returns correct string")
        void newWildCardString() {
            WildCard w = new WildCard();
            assertEquals("Wild set to None", w.toString());
        }

        @Test
        @DisplayName("toString returns correct string")
        void setWildCardString() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Blue");
            assertEquals("Wild set to Blue", w.toString());
        }
    }
}