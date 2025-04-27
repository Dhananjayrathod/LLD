**Magic Grid Game**

**Game Setup:**

**_Board:_** A linear grid of cells. The exact number of cells needs to be defined (e.g., 50 cells). The last cell is the designated "End".

**Cell Types:** The grid contains four types of cells:
    
    1. Hotel: Can be bought and generate rent.
    2. Treasure: Awards money.
    3. Jail: Penalizes money.
    4. Empty: No effect.

**Players:** 2 players.

Starting Balance: Both players start with the same initial balance, let's say B = 5000.

Starting Position: Both players start at the cell before the first grid cell (position 0).


**Gameplay:**

Players take turns rolling a standard six-sided die (values 1 to 6).
The player moves their token forward the number of cells indicated by the die roll.
Upon landing on a cell, the player performs an action based on the cell type and game state.
If a player's move requires a payment (Jail fee, Hotel purchase, Rent payment) and they do not have sufficient balance, it counts as an invalid move. The Player stays in the same cell.
The count of consecutive invalid moves resets to zero whenever a player makes a valid move (a move where any required payment can be made, or where no payment is required).

**Cell Actions & Financial Rules:**

**Jail:** Landing on a 'Jail' cell requires the player to pay a fee of 200. If the player's balance is less than 200, this is an invalid move, and no payment is made.

**Treasure:** Landing on a 'Treasure' cell instantly increases the player's balance by 200.
Empty: Landing on an 'Empty' cell has no effect.

**Hotel:**

1. Unowned: If a player lands on an unowned 'Hotel' cell:
They must buy it for a cost of 1000 if their balance is 1000 or greater. The player becomes the owner.
If their balance is less than 1000, they cannot buy it. This is an invalid move. The hotel remains unowned.

2. Owned by Opponent: If a player lands on a 'Hotel' cell owned by the opponent:
They must pay 100 in rent to the hotel owner if their balance is 100 or greater.
If their balance is less than 100, they cannot pay the rent. This is an invalid move.

3. Owned by Self: Landing on a 'Hotel' cell already owned by the player has no effect.

**Winning Conditions:**
   There are two ways the game can end:

1. By Invalid Moves: If a player makes three consecutive invalid moves, they immediately lose, and the other player is declared the winner.
By Reaching the End: If both players successfully reach or pass the final "End" cell of the grid, the game ends immediately. The player with the higher Final Score wins. If scores are tied, the game is a draw.
2. Final Score Calculation (Only if game ends by reaching the end):
   Final Score = Player's Current Balance + (Number of Hotels Owned by Player * 1000)

 **Example Scenario:**

Assume Starting Balance B = 5000. Grid has 20 cells. Player A and Player B start at position 0.

Turn 1:
Player A rolls a 3. Lands on Cell 3 (Unowned Hotel). Balance = 5000. A must buy it. Balance becomes 4000. A owns Hotel@3. (Valid move)
Player B rolls a 4. Lands on Cell 4 (Treasure). Balance = 5000 + 200 = 5200. (Valid move)

Turn 2:
Player A rolls a 5. Lands on Cell 3 + 5 = Cell 8 (Jail). Balance = 4000 - 200 = 3800. (Valid move)
Player B rolls a 6. Lands on Cell 4 + 6 = Cell 10 (Empty). Balance = 5200. (Valid move)

Turn 3:
Player A rolls a 2. Lands on Cell 8 + 2 = Cell 10 (Empty). Balance = 3800. (Valid move)
Player B rolls a 3. Lands on Cell 10 + 3 = Cell 13 (Unowned Hotel). Balance = 5200. B must buy it. Balance becomes 4200. B owns Hotel@13. (Valid move)

Turn 4:
Player A rolls a 3. Lands on Cell 10 + 3 = Cell 13 (Hotel owned by B). Balance = 3800. A must pay 100 rent to B. A's balance becomes 3700. B's balance becomes 4300. (Valid move)
Player B rolls a 4. Lands on Cell 13 + 4 = Cell 17 (Jail). Balance = 4300 - 200 = 4100. (Valid move)

... Scenario: Invalid Move ...
Later in the game, Player A's balance is 50.
Player A rolls and lands on Cell 8 (Jail). Payment required = 200. Balance (50) < 200. Invalid Move 1. Balance remains 50.
Next turn, Player A rolls and lands on Cell 13 (Hotel owned by B). Rent required = 100. Balance (50) < 100. Invalid Move 2. Balance remains 50. B gets no rent.
Next turn, Player A rolls and lands on Cell 3 (Hotel owned by A). No payment required. Valid Move. Consecutive invalid move count resets to 0.

... Scenario: Game End ...
Player A reaches the end. Player B reaches the end on their next turn. Game ends.
Player A: Balance = 2500, owns 2 Hotels (Hotel@3, Hotel@18). Final Score = 2500 + (2 * 1000) = 4500.
Player B: Balance = 1800, owns 3 Hotels (Hotel@13, Hotel@7, Hotel@15). Final Score = 1800 + (3 * 1000) = 4800.
Player B wins.
