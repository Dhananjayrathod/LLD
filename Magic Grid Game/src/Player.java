import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Player {

    private final String id;
    private final String name;
    private int balance;
    private int hotelsOwned;
    private int position;
    private int invalidMoves;
    private Queue<Integer> moves;

    public Player(String id, String name, int startingBalance, List<Integer> moves) {
        this.id = id;
        this.name = name;
        this.balance = startingBalance;
        this.hotelsOwned = 0;
        this.position = 0;
        this.invalidMoves = 0;
        this.moves = new LinkedList<>(moves);
    }

    public Integer getNextMove() {
        if (moves.isEmpty()) {
            return null;
        }
        return moves.poll();
    }

    public boolean hasMoreMoves() {
        return !moves.isEmpty();
    }

    public void move(int steps){
        position += steps;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getHotelsOwned() {
        return hotelsOwned;
    }

    public int getPosition() {
        return position;
    }

    public int getInvalidMoves() {
        return invalidMoves;
    }

    public void increaseBalance(int amount) {
        balance += amount;
    }

    public void decreaseBalance(int amount) {
        balance -= amount;
    }

    public void incrementHotelsOwned() {
        hotelsOwned++;
    }

    public void incrementInvalidMoves() {
        invalidMoves++;
    }

    public void resetInvalidMoves() {
        invalidMoves = 0;
    }

}
