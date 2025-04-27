/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Empty extends Cell {

    @Override
    public boolean onLand(Player player, Player opponent) {
        System.out.println(player.getName() + " landed on Empty cell. Nothing happens.");
        return true;
    }
}
