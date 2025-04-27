import java.util.List;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Game {

    private final List<Cell> grid;
    private final List<Player> players;


    public Game(List<Cell> grid, List<Player> players) {
        this.grid = grid;
        this.players = players;
    }

    public void start() {
        boolean gameOver = false;

        while(!gameOver) {
            gameOver = true;
            for (Player player : players) {
                if (player.hasMoreMoves()) {
                    gameOver = false;
                    int moveSteps = player.getNextMove();
                    System.out.println("\n" + player.getName() + "'s turn: Moving " + moveSteps + " steps.");

                    Cell nextCell = grid.get(player.getPosition() + moveSteps -1);

                    if (nextCell == null) {
                        System.out.println("Invalid move! Cell does not exist.");
                        player.incrementInvalidMoves();
                        continue;
                    }

                    boolean success = nextCell.onLand(player, getOpponent(player));

                    if (success) {
                        player.move(moveSteps);
                        player.resetInvalidMoves();
                    } else {
                        player.incrementInvalidMoves();
                    }

                    if (player.getInvalidMoves() == 3) {
                        System.out.println(player.getName() + " made 3 invalid moves. They are disqualified!");
                        players.remove(player);
                        if (players.size() == 1) {
                            System.out.println(players.get(0).getName() + " wins as the last standing player!");
                            return;
                        }
                        break;
                    }

                }
            }
        }

        Player winner = null;
        int maxScore = Integer.MIN_VALUE;

        System.out.println("\nGame Over! Final scores:");
        for (Player player : players) {
            int finalScore = player.getBalance() + player.getHotelsOwned() * 1000;
            System.out.println(player.getName() + ": " + finalScore);
            if (finalScore > maxScore) {
                maxScore = finalScore;
                winner = player;
            }
        }

        if (winner != null) {
            System.out.println(winner.getName() + " won!");
        }
    }

    private Player getOpponent(Player player) {
        for (Player p : players) {
            if (p != player) {
                return p;
            }
        }
        return null;
    }
}
