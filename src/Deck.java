import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	// Declares Card 2D array
	private Card[][] deck;

	// Initializes deck array
	Deck() {
		this.deck = new Card[4][13];

		for (int suit = 0; suit < 4; suit++) {
			for (int rank = 0; rank < 13; rank++) {
				// Each location in the deck array is initialized to a card
				deck[suit][rank] = new Card(suit, rank);
			}
		}

	}

	// Declares Card array list
	private ArrayList<Card> lDeck;

	// Shuffles deck
	public void shuffle() {
		lDeck = new ArrayList<Card>();

		for (int row = 0; row < deck.length; row++) {
			for (int column = 0; column < deck[row].length; column++) {
				lDeck.add(deck[row][column]);
			}
		}

		/**
		 * The Collections.shuffle() method doesn't work with 2D arrays, which is why
		 * all the cards were put into an array list and then shuffled.
		 */

		Collections.shuffle(lDeck);
	}

	// Draws card
	public Card drawCard() {
		return lDeck.remove(0);
	}
}
