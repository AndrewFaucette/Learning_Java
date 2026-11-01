/** A human-controlled blackjack player. */
public class Player {
    private final String name;
    private final Hand hand = new Hand();
    private int points;
    private int bet = 0;

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }
    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public int getPoints() { return points; }
    public void addPoints(int points) { this.points += points; }
    public void setBet(int bet) { this.bet = bet; }
    public int getBet() { return bet; }
    public void receiveCard(Card card) { hand.addCard(card); }
    public void clearHand() { hand.clear(); }
}
