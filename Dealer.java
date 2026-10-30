import java.util.ArrayList;
import java.util.List;
/** The dealer's state and dealer-specific behavior hooks. */
public class Dealer extends Player {
    public Dealer(String name) { super(name); }
    int playersMax = 0;
    int playersMin = 21;

    public boolean shouldHit(List<Player> players) {
        if (players != null) {
            for (Player player : players) {
                if (player.getHand().getValue() > playersMax && !player.getHand().isBust()) {
                    playersMax = player.getHand().getValue();
                }
                if (player.getHand().getValue() < playersMin || player.getHand().isBust()) {
                    if (player.getHand().isBust()) {
                        playersMin = 0;
                    } else {
                        playersMin = player.getHand().getValue();
                    }
                }
            }
        }
        if (getHand().isBlackjack()) { return false; }
        else if (getHand().getValue() > playersMax) { return false; }
        else if (getHand().getValue() < playersMin) { return true; }
        else if (getHand().getValue() < 17) {
            return true;
        }
        return false;
    }
}
