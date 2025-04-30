package service;

import enums.Direction;

/**
 * Created by dhananjay.rathod at 30/04/25.
 */

public interface SnakeGame {

    void moveSnake(Direction snakeDirection);

    boolean isGameOver();
}
