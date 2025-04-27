/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Hotel extends Cell {

    private Player owner;

    @Override
    public boolean onLand(Player player, Player opponent) {

        if (owner == null) {
            if (player.getBalance() >= 1000) {
                player.decreaseBalance(1000);
                owner = player;
                player.incrementHotelsOwned();
                System.out.println(player.getName() + " bought a Hotel! 1000 deducted.");
                return true;
            } else {
                System.out.println(player.getName() + " landed on unowned Hotel but cannot afford to buy. Invalid move!");
                return false;
            }
        } else if (owner != player) {
            if (player.getBalance() >= 100) {
                player.decreaseBalance(100);
                owner.increaseBalance(100);
                System.out.println(player.getName() + " paid 100 rent to " + owner.getName());
                return true;
            } else {
                System.out.println(player.getName() + " cannot afford rent to " + owner.getName() + ". Invalid move!");
                return false;
            }
        }
        return true;
    }

}
