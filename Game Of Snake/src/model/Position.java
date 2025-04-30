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

    public Position getNextPosition(Direction direction) {
        switch (direction) {
            case UP:  return new Position(x, y - 1);
            case DOWN: return new Position(x, y + 1);
            case LEFT: return new Position(x-1, y);
            case RIGHT: return new Position(x+1, y);
            default: return this;
        }
    }
}
