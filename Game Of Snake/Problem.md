
**Problem Title: Game of Snake**

[Problem Description:]()

Remember the old phone game of Snake? If not, let’s look at this first: Snake
The snake can move up, down, left or right in a 2-dimensional board of arbitrary size.
Let’s try to implement the base logic of this game.
Rules:
Every time moveSnake() is called, the snake moves up, down, left or right
The snake’s initial size is 3 and grows by 1 every 5 moves
The game ends when the snake hits itself
We can use the following as a starting point (pseudo-code):


interface SnakeGame {

    moveSnake(snakeDirection);

    isGameOver();

}

[- * * -  -
 - - * - -
 - - - - -
 - - - - -
 - - - - - ]

snakePos = [[0,0], [0,1], [0,2]]
