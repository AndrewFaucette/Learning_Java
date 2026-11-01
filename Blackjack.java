import java.util.ArrayList;
import java.util.List;

/** Coordinates one or more rounds of blackjack. */
public class Blackjack {
    private final ConsoleInput input = new ConsoleInput();
    private final List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private Dealer dealer;

    public void start() {
        System.out.println("Welcome to Blackjack!");
        int playerCount = input.readInt("How many players? ", 1, 10);
        int startingPoints = input.readInt("How many starting points for each player? ", 1, 10000);
        createPlayers(playerCount, startingPoints);
        dealer = new Dealer("Dealer", 0);

        boolean keepPlaying = true;
        while (keepPlaying) {
            playRound();
            keepPlaying = input.readYesNo("Play another round? (y/n): ");
        }
        input.close();
        System.out.println("Thanks for playing!");
    }

    private void createPlayers(int playerCount, int startingPoints) {
        // TODO: Ask for names and validate duplicate names.
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i, startingPoints));
        }
    }

    private void playRound() {
        resetRound();
        collectBets();
        dealOpeningCards();
        playPlayerTurns();
        playDealerTurn();
        resolveRound();
    }

    private void resetRound() {
        deck = new Deck();
        deck.shuffle();
        players.forEach(Player::clearHand);
        dealer.clearHand();
    }

    private void collectBets() {
        for (Player player : players) {
            int bet = input.readInt(player.getName() + ", enter your bet (0-" + player.getPoints() + "): ", 0, player.getPoints());
            player.setBet(bet);
            player.addPoints(-bet); // Deduct the bet from the player's points
            System.out.println(player.getName() + " bets " + bet + " points.");
        }
    }

    private void dealOpeningCards() {
        for (int i = 0; i < 2; i++) {
            players.forEach(player -> player.receiveCard(deck.draw()));
            dealer.receiveCard(deck.draw());
        }
        for (Player player : players) {
            if (player.getBet() == 0) {
                System.out.println(player.getName() + " has no bet and is skipped.");
                continue;
            }
            System.out.println(player.getName() + "'s hand: " + player.getHand());
        }
        System.out.println(dealer.getName() + "'s hand: " + dealer.getHand().getCards().get(0) + " and [hidden]");
    }

    private void playPlayerTurns() {
        boolean playing = true;
        //If the dealer has a blackjack, the round ends immediately and all players lose unless they also have a blackjack.
        if (dealer.getHand().isBlackjack()) { 
            System.out.println(dealer.getName() + " has blackjack! All players lose unless they also have blackjack.");
            return;
        }
        //Each player takes their turn in order, hitting or standing until they bust or choose to stand.
        for (Player player : players) {
            playing = true;
            if (player.getBet() == 0) { continue; }
            System.out.println(player.getName() + "'s turn.");
            while(playing) {
                System.out.println("Current hand: " + player.getHand());
                if (player.getHand().isBlackjack()) {
                    System.out.println("Blackjack!");
                    playing = false;
                } else if(input.readYesNo("Do you hit? (y/n): ")) {
                    player.receiveCard(deck.draw());
                    System.out.println("You drew a " + player.getHand().getCards().get(player.getHand().getCards().size() - 1));
                    // Check for bust or blackjack after hitting
                    if(player.getHand().isBust()) {
                        System.out.println("Bust! Your hand value is " + player.getHand().getValue());
                        playing = false;
                    }
                } else {
                    playing = false;
                }
            }
        }
    }

    private void playDealerTurn() {
        System.out.println(dealer.getName() + "'s turn.");
        System.out.println(dealer.getName() + "'s hand: " + dealer.getHand());
        if (dealer.shouldHit(players)) {
            while (dealer.shouldHit(null)) {
                dealer.receiveCard(deck.draw());
                System.out.println(dealer.getName() + " drew a " + dealer.getHand().getCards().get(dealer.getHand().getCards().size() - 1));
                if (dealer.getHand().isBust()) {
                    System.out.println(dealer.getName() + " busts with a hand value of " + dealer.getHand().getValue());
                    return;
                } else if (dealer.getHand().isBlackjack()) {
                    System.out.println(dealer.getName() + " has blackjack!");
                    return;
                }
            }
            System.out.println(dealer.getName() + " stands.");
        } else {
            System.out.println(dealer.getName() + " stands.");
        }
    }

    private void resolveRound() {
        for (Player player : players) {
            if (player.getBet() == 0) { continue; }
            int playerValue = player.getHand().getValue();
            int dealerValue = dealer.getHand().getValue();
            System.out.println(player.getName() + "/" + dealer.getName() + ": " + playerValue + "/" + dealerValue);

            if (playerValue > 21 || (playerValue < dealerValue && dealerValue <= 21)) {
                System.out.println(player.getName() + " loses.");
            } else if (dealer.getHand().isBust() || playerValue > dealerValue) {
                System.out.println(player.getName() + " wins!");
                player.addPoints(player.getBet() * 2); // Player wins double their bet
            } else {
                System.out.println(player.getName() + " and " + dealer.getName() + " push (tie).");
                player.addPoints(player.getBet()); // Player gets their bet back
            }
            System.out.println(player.getName() + " now has " + player.getPoints() + " points.");
        }
    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}
