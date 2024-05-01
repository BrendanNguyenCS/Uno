package uno;

import lombok.Getter;
import java.util.*;

/**
 * Represents a game of Uno
 * <p>
 * Class invariants:
 * <ul>
 *     <li>The game must have at least 2 {@link Player players} to start.</li>
 *     <li>Players must have 1 or more {@link NormalCard normal} card per digit and color.</li>
 *     <li>Players can have 0 or more {@link SpecialCard special} cards per color.</li>
 *     <li>Players can have 0 or more {@link WildCard wild} cards per kind.</li>
 * </ul>
 */
@Getter
public class GameState {
    /**
     * The Uno game's draw pile
     */
    private final Deck draw;
    /**
     * The Uno game's discard pile
     */
    private final Deck discard;
    /**
     * The direction. The default direction is forward ({@code 1 > 2 > 3 > 4}).
     * If the direction is reversed, the order is {@code 4 > 3 > 2 > 1}.
     */
    private boolean direction = true;
    /**
     * The players involved in the game
     */
    private final LinkedList<Player> players;
    /**
     * The number of times the deck has been refilled
     */
    private int countRefillDeck = 0;

    /**
     * Represents the beginning of a {@link uno Uno} game
     * @param countPlayers the number of players
     * @param countInitialCardsPerPlayer the number of cards initially dealt to each player
     * @param countDigitCardsPerColor the number of {@link NormalCard normal} cards for each digit and color
     * @param countSpecialCardsPerColor the number of {@link SpecialCard special} cards of each kind for each color
     * @param countWildCards the number of total {@link WildCard wild} cards per kind
     *
     * @throws NoSuchElementException if no cards are left in the deck
     */
    private GameState(int countPlayers,
                     int countInitialCardsPerPlayer,
                     int countDigitCardsPerColor,
                     int countSpecialCardsPerColor,
                     int countWildCards) {
        // create draw and discard piles
        draw = new Deck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
        discard = new Deck();


        // add new players
        players = new LinkedList<>();
        for (int i = 0; i < countPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        // add hands to players
        for (int i = 0; i < countInitialCardsPerPlayer; i++) {
            for (Player p : players) {
                Card c;
                try {
                    c = draw.drawFromDeck();
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("No cards left in the deck");
                }
                p.addToHand(c);
            }
        }

        // the top card in the draw pile is moved to the discard pile to begin the game
        Card top = draw.drawFromDeck();

        // check if the top card is a wild card
        if (top instanceof WildCard w) {
            w.setRandomEffectiveColor();
        }

        discard.addToDeck(top);
        System.out.println("The first card is " + top);
    }

    /**
     * After this method ends, the game state should represent the
     * situation immediately before the first player takes their first turn.
     * <p>
     * That is, the players should be arranged, their initial hands have been dealt,
     * and the discard pile and draw pile have been created.
     *
     * @param countPlayers the number of players
     * @param countInitialCardsPerPlayer the number of cards initially dealt to each player
     * @param countDigitCardsPerColor the number of {@link NormalCard normal} cards for each digit and color
     * @param countSpecialCardsPerColor the number of {@link SpecialCard special} cards of each kind for each color
     * @param countWildCards the number of total {@link WildCard wild} cards of each kind
     * @return the game state
     * @throws IllegalArgumentException if the number of players is less than 2, the number of initial cards per player is less than 1, the number of digit cards per color is less than 1, the number of special cards per color is less than 0, or the number of wild cards is less than 0
     */
    public static GameState startGame(int countPlayers,
                                      int countInitialCardsPerPlayer,
                                      int countDigitCardsPerColor,
                                      int countSpecialCardsPerColor,
                                      int countWildCards) {
        if (countPlayers >= 2 && countInitialCardsPerPlayer > 0 && countDigitCardsPerColor > 0 && countSpecialCardsPerColor >= 0 && countWildCards >= 0) {
            return new GameState(countPlayers,
                                 countInitialCardsPerPlayer,
                                 countDigitCardsPerColor,
                                 countSpecialCardsPerColor,
                                 countWildCards);
        }
        else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }

    /**
     * @return the name of the current player
     * @throws NoSuchElementException if no players are found
     */
    Player getCurrentPlayer() {
        Player p = players.peekFirst();
        if (p == null) {
            throw new NoSuchElementException("No players found");
        }
        return p;
    }

    /**
     * Indicates whether the game is over.
     */
    boolean isGameOver() {
        for (Player p : players) {
            if (p.hasEmptyHand()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shifts the players in the forward direction
     * <p>
     * If the current order is {@code 1 > 2 > 3 > 4}, then the new order will be {@code 2 > 3 > 4 > 1}
     */
    void initiateForwardDirection() {
        Player p = players.removeFirst();
        players.addLast(p);
    }

    /**
     * Shifts the players in the reverse direction
     * <p>
     * If the current order is {@code 1 > 2 > 3 > 4}, then the new order will be {@code 4 > 1 > 2 > 3}
     */
    void initiateReverseDirection() {
        Player l = players.removeLast();
        players.addFirst(l);
    }

    /**
     * Moves the game in the current direction
     */
    void moveInDirection() {
        if (direction) {
            initiateForwardDirection();
        } else {
            initiateReverseDirection();
        }
    }

    /**
     * Draws {@code n} cards from the draw pile and adds them to the next player's hand
     * @param n the number of cards to draw
     * @return the next player who drew the cards
     */
    Player drawCardsToNextPlayer(int n) {
        Player p = players.peekFirst();
        if (p != null) {
            for (int i = 0; i < n; i++) {
                checkDecks();
                p.addToHand(draw.drawFromDeck());
            }
        }
        return p;
    }

    /**
     * Checks if the draw pile is empty, and if so, adds the discard pile to it
     */
    void checkDecks() {
        // if draw pile is empty, add discard pile to it and shuffle
        if (draw.isDeckEmpty()) {
            // save the current top card from discard pile
            Card c = discard.drawFromDeck();

            draw.addToDeck(discard);
            draw.shuffleDeck();

            discard.clearDeck();
            discard.addToDeck(c);

            countRefillDeck++;
        }
    }

    /**
     * The current player takes their turn, and if they play a special card
     * the corresponding effects are performed. When the method returns,
     * the next player is ready to take their turn.If the game is already over,
     * this method has no effect.
     * @throws NoSuchElementException if no players are founds
     */
    public void runOneTurn() {
        if (!isGameOver()) {
            // get current player
            Player p = players.peekFirst();
            if (p == null) {
                throw new NoSuchElementException("No players found");
            }

            // get previously played card from discard pile
            Card lastPlayed = discard.getTopCard();

            // check if the player has any playable cards
            Card next = p.playCard(lastPlayed);

            // Rule: Players unable to play a card draws until they play a card
            /*while (next == null) {
                checkDecks();
                p.addToHand(draw.drawFromDeck());
                next = p.playCard(lastPlayed);
            }*/

            // Alternate rule: Players unable to play a card only draws once from the deck
            if (next == null) {
                checkDecks();
                System.out.println("\t" + p + " has drawn a card.");
                p.addToHand(draw.drawFromDeck());
                next = p.playCard(lastPlayed);
                if (next == null) {
                    moveInDirection();
                    System.out.println("\t" + p + " was unable to play.");
                    return;
                }
            }

            // check type of the next card
            switch (next) {
                case NormalCard n:
                    discard.addToDeck(n);
                    System.out.println("\tThey have played " + next + ".");
                    break;
                case SpecialCard s:
                    switch (s.getValue()) {
                        case "Draw Two":
                            System.out.println("\tThey have played " + next + ".");
                            checkDecks();
                            moveInDirection();
                            Player drawing = drawCardsToNextPlayer(2);
                            System.out.println("\t" + drawing + " will draw two cards and be skipped.");
                            break;
                        case "Reverse":
                            if (direction) {
                                initiateReverseDirection();
                            } else {
                                initiateForwardDirection();
                            }
                            direction = !direction;
                            System.out.println("\tThey have played " + next + ".");
                            discard.addToDeck(s);
                            return;
                        case "Skip":
                            System.out.println("\tThey have played " + next + ".");
                            moveInDirection();
                            Player skipped = players.peekFirst();
                            System.out.println("\t" + skipped + " will be skipped.");
                            break;
                    }
                    discard.addToDeck(s);
                    break;
                case WildCard w:
                    switch (w.getValue()) {
                        case "Wild":
                            w.setRandomEffectiveColor();
                            System.out.println("\tThey have played " + next + ".");
                            break;
                        case "Draw Four":
                            w.setRandomEffectiveColor();
                            System.out.println("\tThey have played " + next + ".");
                            checkDecks();
                            moveInDirection();
                            Player drawing = drawCardsToNextPlayer(4);
                            System.out.println("\t" + drawing + " will draw four cards and be skipped.");
                            break;
                    }
                    discard.addToDeck(w);
                    break;
                default:
                    break;
            }

            // End the game if the current player has won
            if (p.hasEmptyHand()) {
                return;
            }

            moveInDirection();
        }
    }

    /**
     * Runs a game of Uno with the given parameters
     * @param countPlayers the number of players
     * @param countInitialCardsPerPlayer the number of cards initially dealt to each player
     * @param countDigitCardsPerColor the number of {@link NormalCard normal} cards for each digit and color
     * @param countSpecialCardsPerColor the number of {@link SpecialCard special} cards of each kind for each color
     * @param countWildCards the number of total {@link WildCard wild} cards of each kind
     */
    private static void runGame(int countPlayers, int countInitialCardsPerPlayer, int countDigitCardsPerColor, int countSpecialCardsPerColor, int countWildCards) {
        System.out.println("Starting game...");
        System.out.println("--------------------");
        int turnCount = 0;
        GameState g = GameState.startGame(countPlayers, countInitialCardsPerPlayer, countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
        Player p;
        while (!g.isGameOver()) {
            System.out.println("--------------------");
            p = g.getCurrentPlayer();
            System.out.println(p + "'s turn");
            g.runOneTurn();
            int handSize = p.getHand().size();
            switch (handSize) {
                case 1 -> System.out.println("\t" + p + " has UNO!");
                case 0 -> System.out.println("\t" + p + " has won!");
                default -> System.out.println("\t" + p + " has " + handSize + " cards.");
            }
            turnCount++;
        }
        System.out.println("--------------------");
        System.out.println("Game over!");
        System.out.println("There have been " + turnCount + " turns.");
        switch (g.countRefillDeck) {
            case 0 -> System.out.println("The deck was never refilled.");
            case 1 -> System.out.println("The deck was refilled once.");
            case 2 -> System.out.println("The deck was refilled twice.");
            default -> System.out.println("The deck was refilled " + g.countRefillDeck + " times.");
        }
    }

    public static void main(String[] args) {
        /* Option 1: Sample games to run */
        // runGame(2, 7, 2, 2, 4);
        // runGame(4, 7, 2, 2, 4);
        // runGame(6, 8, 3, 3, 3);

        /* Option 2: Get input from command line */
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of players: ");
        int countPlayers = in.nextInt();
        System.out.println("Enter the number of initial cards per player: ");
        int countInitialCardsPerPlayer = in.nextInt();
        System.out.println("Enter the number of normal cards per digit and color: ");
        int countDigitCardsPerColor = in.nextInt();
        System.out.println("Enter the number of special cards per color: ");
        int countSpecialCardsPerColor = in.nextInt();
        System.out.println("Enter the number of wild cards: ");
        int countWildCards = in.nextInt();
        in.close();
        runGame(countPlayers, countInitialCardsPerPlayer, countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);

        /* Option 3: Use command line arguments */
        /*if (args != null) {
            runGame(Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4])
            );
        }*/
    }
}
