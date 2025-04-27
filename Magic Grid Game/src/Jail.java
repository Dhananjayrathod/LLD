/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Jail extends Cell {

    @Override
    public boolean onLand(Player player, Player opponent) {

        if (player.getBalance() < 200) {
            System.out.println(player.getName() + " cannot afford to pay the fine. ");
            return false;
        }

        player.decreaseBalance(200);
        System.out.println(player.getName() + " landed in Jail! Balance decreased by 200.");
        return true;
    }
}
