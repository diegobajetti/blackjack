public class Card {

	// Declares variables suit and rank
	private int suit, rank;

	// Initializes suit and rank of card
	Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	private String[] suits = { "C", "D", "H", "S" };
	private String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

	// Returns String
	public String toString() {
		return "<html><div style='text-align: center;'>" + ranks[rank] + "<br>" + suits[suit] + "</html>";
	}

	// Declares variable value
	private int value;

	// Calculates value of card
	public int getValue() {
		// Executes if card is an ace
		if (rank == 0) {
			// Executes if ace is true
			if (ace) {
				value = 11;
			}

			// Executes if ace is false
			else {
				value = 1;
			}
		}

		// Executes if card is a face card (Jack, Queen, King)
		else if (rank > 9) {
			value = 10;
		}

		// Executes if card is between 2 and 10
		else {
			value = rank + 1;
		}

		return value;
	}

	// Declares and initializes boolean variable
	boolean ace = true;

	// Method is used when the value of the ace has to be changed from 11 to 1
	public void setValueOfAce() {
		ace = false;
	}
}
