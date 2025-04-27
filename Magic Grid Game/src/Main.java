import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Cell> grid = new ArrayList<>();
        grid.add(new Empty());
        grid.add(new Hotel());
        grid.add(new Treasure());
        grid.add(new Jail());
        grid.add(new Hotel());
        grid.add(new Treasure());
        grid.add(new Empty());
        grid.add(new Jail());
        grid.add(new Hotel());
        grid.add(new Empty());

        List<Integer> movesPlayer1 = Arrays.asList(1, 2, 3, 2, 1);
        List<Integer> movesPlayer2 = Arrays.asList(2, 1, 2, 2, 1);

        Player player1 = new Player("p1", "Player 1", 3000, movesPlayer1);
        Player player2 = new Player("p2", "Player 2", 3000, movesPlayer2);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Game game = new Game(grid, players);
        game.start();
    }
}