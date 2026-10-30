import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Cards held by one player or the dealer. */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) { cards.add(card); }
    public void clear() { cards.clear(); }
    public List<Card> getCards() { return Collections.unmodifiableList(cards); }

    public int getValue() {
        int total = cards.stream().mapToInt(Card::getValue).sum();
        for (Card card : cards) {
            if (card.getRank() == Card.Rank.ACE && total > 21) {
                total -= 10; // Count Ace as 1 instead of 11
            }
        }
        return total;
    }

    public boolean isBust() { return getValue() > 21; }
    public boolean isBlackjack() { return cards.size() >= 2 && getValue() == 21; }

    @Override
    public String toString() { return cards + " (value: " + getValue() + ")"; }
}
