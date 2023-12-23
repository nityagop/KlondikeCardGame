package cs3500.klondike.model.hw02;

/**
 * An enum class to represent the Suit (♣, ♠, ♡, ♢) of a given Card in a
 * game of Klondike.
 */
public enum Suit {

  DIAMOND("♢"), HEART("♡"), SPADE("♠"), CLUB("♣");

  private final String symbol;

  /**
   * Constructs the Suit of a Card.
   * @param symbol the Card symbol as a String.
   */
  Suit(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Determines whether this Suit is the same as the given Suit.
   * @param other Another instance of a Suit.
   * @return true if the two Suits have the same String value.
   */
  public boolean sameSuit(Suit other) {
    return this.symbol.equals(other.symbol);
  }

  /**
   * Returns the Suit of this card as String.
   * @return a String representation of this cards Suit.
   */
  public String toString() {
    return this.symbol;
  }
}
