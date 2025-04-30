package service;

import enums.Direction;
import model.Board;
import model.Position;
import model.Snake;

import java.util.List;

/**
 * Created by dhananjay.rathod at 30/04/25.
 */

public class SnakeGameImpl implements SnakeGame{

    private Snake snake;
    private Board board;
    private boolean gameOver;

    public SnakeGameImpl(int height, int width) {
        this.board = new Board(height, width);
        this.snake = new Snake(new Position(0, 2), 5);
        this.gameOver = false;
    }


    @Override
    public void moveSnake(Direction snakeDirection) {
        if(gameOver) {
            return;
        }

        snake.move(snakeDirection);

        Position head = snake.getHead();
        if (board.isOutOfBounds(head) || snake.collidesWithSelf()) {
            gameOver = true;
        }
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    public char[][] getBoardState() {

        char[][] boardState = new char[board.getHeight()][board.getWidth()];

        // Initialize empty board
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                boardState[i][j] = '-';
            }
        }

        // Add snake to board
        List<Position> snakeBody = snake.getBody();
        for (Position pos : snakeBody) {
            if (pos.getX() >= 0 && pos.getX() < board.getWidth() &&
                    pos.getY() >= 0 && pos.getY() < board.getHeight()) {
                boardState[pos.getY()][pos.getX()] = '*';
            }
        }

        return boardState;
    }

    public void printBoardState() {
        char[][] boardState = getBoardState();
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                System.out.print(boardState[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
