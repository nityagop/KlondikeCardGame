package cs3500.klondike.model.hw02;

/**
 * An enum class to represent 1 of the 13 values (A, J, K, Q, or 1-10) of a given Card in a
 * game of Klondike.
 */
public enum Value {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
  SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
  JACK(11), QUEEN(12), KING(13);

  private final int cardValue;

  /**
   * Constructs the Value of a Card.
   * @param cardValue the card number of the Card where A = 1,
   *                  J = 11, Q = 12, K = 13.
   */
  Value(int cardValue) {
    this.cardValue = cardValue;
  }

  /**
   * Returns the value of the card as a string.
   * @return A string representation of the given card value.
   */
  public String toString() {

    if (this.cardValue == 1) {
      return "A";
    }
    else if (this.cardValue == 11) {
      return "J";
    }
    else if (this.cardValue == 12) {
      return "Q";
    }
    else if (this.cardValue == 13) {
      return "K";
    }
    else {
      return Integer.toString(this.cardValue);
    }
  }

  /**
   * Determines whether this Value is the same as the given Value.
   * @param other Another instance of a Value.
   * @return true if the two Values have the same integer value.
   */
  public boolean sameValue(Value other) {
    return this.cardValue == other.cardValue;
  }

  /**
   * Returns the Value of this card as an integer.
   * @return an integer representation of this cards value.
   */
  public int getValue() {
    return this.cardValue;
  }
}
