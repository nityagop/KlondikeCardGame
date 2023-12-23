package cs3500.klondike.model.hw02;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * A class that represents an implementation of a Card. Includes
 * a Value and Suit as well as a boolean to represent whether the
 * card is face up or not.
 */
public class PlayingCard implements Card {

  private final Value value;
  private final Suit suit;
  private boolean isVisible;
  private int numDrawSoFar;

  /**
   * Constructs a PlayingCard.
   * @param value An enum to represent the number value of a card.
   * @param suit An enum to represent the suit of a card.
   * @param isVisible A boolean to represent whether the card is
   *                  visible (i.e open facing).
   * @throws IllegalArgumentException if the Suit or Value is invalid.
   */
  public PlayingCard(Value value, Suit suit, boolean isVisible) {
    this.value = Objects.requireNonNull(value);
    if (Stream.of("♣", "♠", "♡", "♢").anyMatch(s -> !suit.toString().equals(s))) {
      this.suit = Objects.requireNonNull(suit);
    }
    else {
      throw new IllegalArgumentException("Card suits must be ♣, ♠, ♡, or ♢");
    }
    this.isVisible = false;
    this.numDrawSoFar = 0;
  }

  /**
   * Determines the color of the given suit.
   * @return String of either red or black.
   */
  public String getColor() {
    if (this.suit.equals(Suit.SPADE) || this.suit.equals(Suit.CLUB)) {
      return "black";
    }
    else {
      return "red";
    }
  }

  /**
   * Changes the visibility of the given Card to true if false
   * and false if true.
   * @return true if the original value was false and false if
   *         the original value was true.
   */
  public boolean setVisibility() {
    return this.isVisible = !this.isVisible;
  }

  /**
   * Returns the Value of this Card.
   * @return the representation of a card number of this Card.
   */
  public Value getCardValue() {
    return this.value;
  }

  /**
   * Returns the Suit of this Card.
   * @return the representation of a card suit of this Card.
   */
  public Suit getCardSuit() {
    return this.suit;
  }

  /**
   * Returns the numDrawnSoFar of this Card.
   * @return the number of times this Card has
   *         been drawn so far in the game.
   */
  public int getNumDrawnSoFar() {
    return this.numDrawSoFar;
  }

  /**
   * Increments the numDrawnSoFar as the Card
   * continues to be redrawn.
   */
  public void incrementNumDrawnSoFar() {
    this.numDrawSoFar++;
  }


  /**
   * Determines whether this object is equal to the given object.
   * @param other An object.
   * @return true if the objects equal each other.
   */
  public boolean equals(Object other) {
    if ((other instanceof PlayingCard)) {
      PlayingCard that = (PlayingCard)other;
      return this.sameCard(that);
    }
    return false;
  }

  /**
   * Determines whether a given PlayingCard is the same as this
   * PlayingCard.
   * @param that a given PlayingCard.
   * @return true if the PlayingCard is the same suit and value.
   */
  public boolean sameCard(Card that) {
    return this.value.sameValue(that.getCardValue())
            && this.suit.sameSuit(that.getCardSuit())
            && this.isVisible == that.getVisibility();
  }


  /**
   * Returns the visibility of this Card.
   * @return true if this Card is visible, false if not.
   */
  public boolean getVisibility() {
    return this.isVisible;
  }

  /**
   * Provides the hashcode of the given PlayingCard.
   * @return an int hashcode of the PlayingCard.
   */
  public int hashCode() {
    return Objects.hash(this.suit, this.value, this.isVisible);
  }

  /**
   * Renders a card with its value followed by its suit as one of
   * the following symbols (♣, ♠, ♡, ♢).
   * For example, the 3 of Hearts is rendered as {@code "3♡"}.
   * @return the formatted card.
   */
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }
}
