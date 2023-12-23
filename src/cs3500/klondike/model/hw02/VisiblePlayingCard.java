package cs3500.klondike.model.hw02;

/**
 * A class that handles the visibility of a card. If the
 * card is face up, it is visible. If it is face down,
 * it is not visible.
 */
public class VisiblePlayingCard {
  private boolean isVisible;

  public VisiblePlayingCard(Card card, boolean isVisible) {
    this.isVisible = isVisible;
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
   * Returns the visibility of this Card.
   * @return true if this Card is visible, false if not.
   */
  public boolean getVisibility() {
    return this.isVisible;
  }
}
