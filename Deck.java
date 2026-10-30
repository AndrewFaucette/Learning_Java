import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A standard 52-card deck. */
public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() { Collections.shuffle(cards); }

    public Card draw() {
        // TODO: Decide how your program should handle an empty deck.
        if (cards.isEmpty()) throw new IllegalStateException("The deck is empty.");
        return cards.remove(cards.size() - 1);
    }

    public int cardsRemaining() { return cards.size(); }
}
