package model;

import enums.Direction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dhananjay.rathod at 30/04/25.
 */

public class Snake {

    private LinkedList<Position> body;
    private Direction currentDirection;
    private int growthRate;
    private int moveCounter;
    private boolean pendingGrowth;
    private Board board;

    public Snake(Position intialPosition, int growthRate, Board board) {
        this.board = board;
        this.body = new LinkedList<>();
        this.currentDirection = Direction.LEFT;

        this.body.add(new Position(intialPosition.getX(), intialPosition.getY()));
        this.body.add(new Position(intialPosition.getX() + 1, intialPosition.getY()));
        this.body.add(new Position(intialPosition.getX() + 2, intialPosition.getY()));

        this.moveCounter = 0;
        this.growthRate = growthRate;
        this.pendingGrowth = false;
    }

    public Position getHead() {
        return body.getFirst();
    }

    public List<Position> getBody() {
        return new ArrayList<>(body);
    }

    public void move(Direction direction) {

        if (currentDirection == Direction.UP && direction == Direction.DOWN
        || currentDirection == Direction.DOWN && direction == Direction.UP
        || currentDirection == Direction.LEFT && direction == Direction.RIGHT
        || currentDirection == Direction.RIGHT && direction == Direction.LEFT) {
            direction = currentDirection;
        }

        currentDirection = direction;

        Position newHead = getHead().getNextPosition(direction, board.getWidth(), board.getHeight());

        body.addFirst(newHead);

        if (pendingGrowth) {
            pendingGrowth = false;
        } else {
            body.removeLast();
        }

        moveCounter++;

        if (moveCounter % growthRate == 0) {
            pendingGrowth = true;
        }
    }

    public boolean collidesWithSelf() {
        Position head = getHead();

        for (int i = 1; i < getBody().size(); i++) {
            if (head.equals(body.get(i))){
                return  true;
            }
        }
        return false;
    }

}
