package model;

/**
 * Created by dhananjay.rathod at 30/04/25.
 */

public class Board {

    private int height;
    private int width;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isOutOfBounds(Position position) {
        return position.getX() < 0 || position.getX() >= width
                || position.getY() < 0 || position.getY() >= height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
