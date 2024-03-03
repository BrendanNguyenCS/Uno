package uno;

import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    @Nested
    @DisplayName("GameState: start game")
    class StartGame {
        @Nested
        @DisplayName("Exceptions")
        class StartGameExceptions {
            @Test
            @DisplayName("Invalid number of players")
            void invalidPlayers() {
                assertThrows(IllegalArgumentException.class, () -> GameState.startGame(1, 1, 1, 1, 1));
            }

            @Test
            @DisplayName("Invalid initial cards per player")
            void invalidInitialCards() {
                assertThrows(IllegalArgumentException.class, () -> GameState.startGame(2, 0, 1, 1, 1));
            }

            @Test
            @DisplayName("Invalid count digit cards per color")
            void invalidDigitCount() {
                assertThrows(IllegalArgumentException.class, () -> GameState.startGame(2, 2, 0, 1, 1));
            }

            @Test
            @DisplayName("Invalid count special cards per color")
            void invalidSpecialCount() {
                assertThrows(IllegalArgumentException.class, () -> GameState.startGame(2, 2, 1, -1, 1));
            }

            @Test
            @DisplayName("Invalid count wild cards")
            void invalidWildCount() {
                assertThrows(IllegalArgumentException.class, () -> GameState.startGame(2, 2, 0, 0, -1));
            }
        }

        @Test
        @DisplayName("Valid game state")
        void validGameState() {
            GameState gs = GameState.startGame(2, 2, 1, 1, 1);
            assertEquals(2, gs.getPlayers().size());
            assertEquals(2, gs.getPlayers().get(0).getHand().size());
            assertEquals(2, gs.getPlayers().get(1).getHand().size());
            assertEquals(48, gs.getDraw().getDeck().size());
            assertEquals(1, gs.getDiscard().getDeck().size());
        }
    }

    @Nested
    @DisplayName("GameState: initiate direction")
    class InitiateDirection {
        @Test
        @DisplayName("Initiate forward direction")
        void initiateForwardDirection() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            Player next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 1", next.getName());
            // initiate 3 turns in the forward direction
            for (int i = 1; i < 4; i++) {
                gs.initiateForwardDirection();
                next = gs.getPlayers().peekFirst();
                assert next != null;
                assertEquals("Player " + (i + 1), next.getName());
            }
            // next turn should be player 1 again
            gs.initiateForwardDirection();
            next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 1", next.getName());
        }

        @Test
        @DisplayName("Initiate reverse direction")
        void initiateReverseDirection() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            Player next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 1", next.getName());
            // initiate 3 turns in the reverse direction
            for (int i = 1; i < 4; i++) {
                gs.initiateReverseDirection();
                next = gs.getPlayers().peekFirst();
                assert next != null;
                assertEquals("Player " + (5 - i), next.getName());
            }
            // next turn should be player 1 again
            gs.initiateReverseDirection();
            next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 1", next.getName());
        }

        @Test
        @DisplayName("Initiate both directions")
        void playInBothDirections() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            Player next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 1", next.getName());
            // initiate 2 turns in the forward direction (Players 2 and 3 would play)
            for (int i = 1; i < 3; i++) {
                gs.initiateForwardDirection();
                next = gs.getPlayers().peekFirst();
                assert next != null;
                assertEquals("Player " + (i + 1), next.getName());
            }
            // initiate 1 turn in the reverse direction (Player 2 would play)
            gs.initiateReverseDirection();
            next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 2", next.getName());
            // initiate 3 turns in the forward direction (Players 3, 4, and 1 would play)
            for (int i = 1; i < 3; i++) {
                gs.initiateForwardDirection();
                next = gs.getPlayers().peekFirst();
                assert next != null;
                assertEquals("Player " + (i + 2), next.getName());
            }
            gs.initiateForwardDirection();
            next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 1", next.getName());
            // initiate 2 turns in the reverse direction
            gs.initiateReverseDirection();
            next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 4", next.getName());
            gs.initiateReverseDirection();
            next = gs.getPlayers().peekFirst();
            assert next != null;
            assertEquals("Player 3", next.getName());
        }
    }

    @Test
    @DisplayName("GameState: draw two to next player")
    void drawTwoToNextPlayer() {
        GameState gs = GameState.startGame(4, 2, 1, 1, 1);
        Player next = gs.getPlayers().peekFirst();
        assert next != null;
        assertEquals("Player 1", next.getName());
        gs.initiateForwardDirection();
        // simulate Player 1 has played a draw two card
        gs.drawTwoToNextPlayer();
        next = gs.getPlayers().peekFirst();
        assert next != null;
        assertEquals("Player 2", next.getName());
        assertEquals(4, next.getHand().size());
    }

    @Test
    @DisplayName("GameState: is game over")
    public void isGameOver() {
        GameState gs = GameState.startGame(4, 2, 1, 1, 1);
        assertFalse(gs.isGameOver());
        // simulate Player 1 has played all cards
        Player p = gs.getPlayers().peekFirst();
        assert p != null;
        p.getHand().clear();
        assertTrue(gs.isGameOver());
    }

    @Test
    @DisplayName("GameState: check decks")
    void checkDecks() {
        GameState gs = GameState.startGame(4, 2, 1, 1, 1);
        assertEquals(44, gs.getDraw().getDeck().size());
        assertEquals(1, gs.getDiscard().getDeck().size());
        // simulate draw deck is empty
        gs.getDiscard().addToDeck(gs.getDraw());
        gs.getDraw().clearDeck();
        assertEquals(0, gs.getDraw().getDeck().size());
        assertEquals(45, gs.getDiscard().getDeck().size());
        gs.checkDecks();
        assertEquals(44, gs.getDraw().getDeck().size());
        assertEquals(1, gs.getDiscard().getDeck().size());
    }

    @Nested
    @DisplayName("GameState: get current player")
    class GetCurrentPlayer {
        @Test
        @DisplayName("Empty player list")
        void getCurrentPlayerEmpty() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            gs.getPlayers().clear();
            assertThrows(NoSuchElementException.class, gs::getCurrentPlayer);
        }

        @Test
        @DisplayName("Forward direction")
        void getCurrentPlayerForward() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 2", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 3", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 4", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
        }

        @Test
        @DisplayName("Reverse direction")
        void getCurrentPlayerReverse() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 4", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 3", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 2", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
        }

        @Test
        @DisplayName("Both directions")
        void getCurrentPlayerBoth() {
            GameState gs = GameState.startGame(4, 2, 1, 1, 1);
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 2", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 2", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 1", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 4", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 3", gs.getCurrentPlayer().toString());
            gs.initiateReverseDirection();
            assertEquals("Player 2", gs.getCurrentPlayer().toString());
            gs.initiateForwardDirection();
            assertEquals("Player 3", gs.getCurrentPlayer().toString());
        }
    }

    @Test
    @DisplayName("GameState: main")
    void mainTest() {
        assertDoesNotThrow(() -> GameState.main(new String[]{}));
    }
}