package cs3500.klondike.model.hw02;

/**
 * Represents a card used to play card games with. Each implementation
 * of this interface will include and value and a suit which is then
 * formatted as X of Suit where X is some value and Suit is (♣, ♠, ♡, ♢)
 */
public interface Card {

  /**
   * Renders a card with its value followed by its suit as one of
   * the following symbols (♣, ♠, ♡, ♢).
   * For example, the 3 of Hearts is rendered as {@code "3♡"}.
   * @return the formatted card.
   */
  String toString();

  /**
   * Determines whether a given PlayingCard is the same as this
   * PlayingCard.
   * @param that a given PlayingCard.
   * @return true if the PlayingCard is the same suit and value.
   */
  boolean sameCard(Card that);

  /**
   * Determines the color of the given suit.
   * @return String of either red or black.
   */
  String getColor();

  /**
   * Retuns the Value of this Card.
   * @return the representation of a card number of this Card.
   */
  Value getCardValue();

  /**
   * Retuns the Suit of this Card.
   * @return the representation of a card suit of this Card.
   */
  Suit getCardSuit();

  /**
   * Changes the visibility of the given Card to true if false
   * and false if true.
   * @return true if the original value was false and false if
   *         the original value was true.
   */
  boolean setVisibility();

  /**
   * Returns the visibility of this Card.
   * @return true if this Card is visible, false if not.
   */
  boolean getVisibility();

  /**
   * Returns the numDrawnSoFar of this Card.
   * @return the number of times this Card has
   *         been drawn so far in the game.
   */
  int getNumDrawnSoFar();

  /**
   * Increments the numDrawnSoFar as the Card
   * continues to be redrawn.
   */
  void incrementNumDrawnSoFar();

}

