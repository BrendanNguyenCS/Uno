# Uno
This Uno game was initially created as a project from the course "Introduction to Software Engineering" taught at the University of Massachusetts Boston by Ryan Culpepper in the Fall of 2023.

In its current iteration, the entire game is run between 4 AI players using random logic on the console. Future improvements could include a GUI and allowing human players to join the game. The rules of the game are a simplified version of the original game, with the following rules:
- The game is played with a deck where the number of digit, special, and wild cards can be customized (as seen in the `main` function in `GameState.java`).
- The game is played with 4 players, and the size of the players' initial hand can also be customized.
- Every player's turn consists of attempting to play a card.
- If a player is unable to play a card, they must draw a card from the deck until they are able to play a card.
- First player to have no cards in their hand wins the game.

Note: The following normal rules don't apply to this game.
- There are no stacking of draw 2 and 4 cards.
- Players with 1 card in their hand do not need to call "Uno".
- You cannot challenge draw four cards (a modern Uno game rule).
- The jump-in rule from the Uno Steam game is not implemented.

An example of a game can be seen in the `example.md` file in this repository.
