/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Treasure extends Cell {

    @Override
    public boolean onLand(Player player, Player opponent) {
        player.increaseBalance(200);
        System.out.println(player.getName() + " found Treasure! Balance increased by 200.");
        return true;
    }
}
