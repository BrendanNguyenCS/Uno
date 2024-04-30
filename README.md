# Uno

## Introduction
This Uno game was initially created as a project from the course "Introduction to Software Engineering" taught at the University of Massachusetts Boston by Ryan Culpepper in the Fall of 2023.

In its current iteration, the entire game is run between 4 AI players using random logic on the console. Future improvements could include a GUI and allowing human players to join the game. The rules of the game are a simplified version of the original game, with the following rules:
- The game is played with a deck where the number of digit, special, and wild cards can be customized (as seen in the `main` function in `GameState.java`).
- The game is played with 4 players, and the size of the players' initial hand can also be customized.
- Every player's turn consists of attempting to play a card.
- If a player is unable to play a card, they must draw a card from the deck until they are able to play a card.
- First player to have no cards in their hand wins the game.

Note: The following normal rules don't apply to this game.
- There are no stacking of draw 2 and 4 cards to avoid drawing cards.
- Players with 1 card in their hand do not need to call "Uno".
- You cannot challenge draw four cards (a modern Uno game rule).
- The jump-in rule from the Uno Steam game is not implemented.

Examples of a game can be seen in the `example.md` and `example2.md` markdown files in the root of this repository.

### Alternate Rule

You can edit the code to enable the alternate rule that a player that cannot play a card should only draw one card from the deck. To do this, comment out the following code on lines 225-230 in `GameState.java`:
```java
while (next == null) {
    checkDecks();
    // draw from the pile until they play a card
    p.addToHand(draw.drawFromDeck());
    next = p.playCard(lastPlayed);
}
```
Additionally, you need to uncomment the following code on lines 233-241 in `GameState.java`:
```java
if (next == null) {
    checkDecks();
    p.addToHand(draw.drawFromDeck());
    next = p.playCard(lastPlayed);
    if (next == null) {
        moveInDirection();
        return;
    }
}
```
## Testing

For ease of testing, you may comment out the all the code in `GameState.main` which gets all the game parameters from the command line. To replace this, you can uncomment any of the same games under the `/* Sample games to run */` comment in the same method. You can also create your own sample game.

```java
runGame(5, 7, 2, 4, 5);
```

## Future Improvements

Possible future improvements include:
- Adding a GUI to the game.
- Allowing human players to join the game.
- Allowing stacking of draw 2 and 4 cards to avoid drawing cards.
- Making players call "Uno!" when they have one card left.
- Adding custom wild cards with challenges.
    - For example, a wild card that allows the player who draws it to challenge another player to a game of rock, paper, scissors. If the challenger wins, the challenged player draws 4 cards. If the challenged player wins, the challenger draws 4 cards.
    - Another example is the player who draws it must compete with another player of their choosing in an [Odds game](https://what-are-the-odds.info/) with odds at "1 in 10". The loser draws cards.
    - These custom wild cards can also be blank and the rule is decided by the player when they play the card.

I would like to keep this game to the original Uno rules. Therefore, rules like the jump-in and challenge rules will not be implemented at this stage. 


