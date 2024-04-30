# Uno

## Introduction
This Uno game was initially created as a project from the course "Introduction to Software Engineering" taught at the University of Massachusetts Boston by Ryan Culpepper in the Fall of 2023.

In its current iteration, the entire game is run between AI players using random logic on the console. The rules of the game are a simplified version of the original game, with the following rules and features:
- The game parameters are customized by the user at the start of the game. This includes the number of players in the game, the number of cards each player starts with, the number of normal cards per digit and color, the number of special cards per color, and the number of wild cards per type.
- Every player's turn consists of attempting to play a card.
- If a player is unable to play a card, they must draw a card from the draw pile (refer to [Alternate Rule](#alternate-rule)).
- First player to have no cards in their hand wins the game.

Note: The following rules from the real life game don't apply to this game.
- There are no stacking of draw 2 and 4 cards to avoid drawing cards.
- Players with 1 card in their hand do not need to call "Uno".
- You cannot challenge draw four cards (a modern Uno game rule).
- The jump-in rule from the Uno Steam game is not implemented.

### Alternate Rule

There are two rulesets that dictate what happens when a player is unable to play a card. The original rule, as coded, dictates that a player will draw cards until they are able to play a card. The alternate rule dictates that a player only draws one card. They may play the new card if they are able to play it, otherwise, their turn will be skipped.

**The alternate rule is enabled by default.**

You may turn off the alternate rule and switch to the original rule. To do this, comment out the following code on lines 233-243 in `GameState.java`:
```java
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
```

Additionally, you need to uncomment out the following code on lines 225-229 in `GameState.java`:
```java
// Rule: Players unable to play a card draws until they play a card
while (next == null) {
    checkDecks();
    p.addToHand(draw.drawFromDeck());
    next = p.playCard(lastPlayed);
}
```

You may also reverse the effect by reverting the changes. The decision to make the alternate rule the default was made to align closer to the real game rules.

### Examples

In the root of this repository, the output of three example games are provided in Markdown files. The first two examples, `example.md` and `example2.md`, are games played with the original draw rule implemented. All others are games played with the alternate draw rule implemented. Additionally, all examples excluding `example.md` are played with the Draw Four card implemented.

## Testing

There are 3 ways to run the game. For which ever method you choose, you will need to comment out the code for the other methods.

### Option 1

You can run the game by defining all the game parameters from within the `main` method in the `GameState` class. An example is shown below:

```java
runGame(5, 7, 2, 4, 5);
```

### Option 2

**Default option**

You can also run the game by compiling the `GameState` class and running it either through the IDE or the command line. You should get each prompt asking you to enter each of the game parameters. An example is shown below:

```shell
java GameState
Enter the number of players: 
5
Enter the number of initial cards per player: 
7
Enter the number of normal cards per digit and color: 
2
Enter the number of special cards per color: 
4
Enter the number of wild cards: 
5
```

## Option 3

You can also run the game by compiling the `GameState` class and running it with the game parameters as command line arguments. An example is shown below:

```shell
java GameState 5 7 2 4 5
```

## Future Improvements

Possible future improvements include:
- Adding a GUI to the game.
- Allowing human players to join the game.
  - This could be done by allowing the player to input their moves through the console.
- Developing an AI that plays more strategically.
  - This could be done by implementing a more sophisticated algorithm for the AI players to determine which card to play.
  - This would replace the current way the game decides which card to play, which is by randomly selecting a playable card from the player's hand.
- Allowing stacking of draw 2 and 4 cards to avoid drawing cards.
- Making players call "Uno!" when they have one card left.
- Adding custom wild cards with challenges.
    - For example, a wild card that allows the player who draws it to challenge another player to a game of rock, paper, scissors. If the challenger wins, the challenged player draws 4 cards. If the challenged player wins, the challenger draws 4 cards.
    - Another example is the player who draws it must compete with another player of their choosing in an [Odds game](https://what-are-the-odds.info/) with odds at "1 in 10". The loser draws cards.
    - These custom wild cards can also be blank and the rule is decided by the player when they play the card.

I would like to keep this game to the original Uno rules. Therefore, rules like the jump-in and challenge rules will not be implemented at this stage.