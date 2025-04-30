package model;

import enums.Direction;

/**
 * Created by dhananjay.rathod at 30/04/25.
 */

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public  int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj ) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode(){
        return 31 * x + y;
    }

    public Position getNextPosition(Direction direction, int boardWidth, int boardHeight) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP:
                newY = (y - 1 + boardHeight) % boardHeight;
                break;
            case DOWN:
                newY = (y + 1) % boardHeight;
                break;
            case LEFT:
                newX = (x - 1 + boardWidth) % boardWidth;
                break;
            case RIGHT:
                newX = (x + 1) % boardWidth;
                break;
        }
        return new Position(newX, newY);
    }
}
