package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicKlondike;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a LimitedDraw game of Klondike. In this variant of the
 * game the user will only have a certain number of redraws and after
 * all those redraws have been used the card will be discarded.
 * For my implementation of LimitedDrawKlondike I chose to extend my
 * BasicKlondike class to avoid code duplication. I overrode the discardDraw
 * method since that is different in a game of BasicKlondike vs LimitedDrawKlondike.
 */
public class LimitedDrawKlondike extends BasicKlondike {

  private final int numTimesRedrawAllowed;
  private final List<Integer> numTimesCardDrawn;

  /**
   * Constructs a LimitedDrawKlondike.
   * @param numTimesRedrawAllowed the maximum number of times
   *                              a user can discard draw and then
   *                              redraw that same card
   */
  public LimitedDrawKlondike(int numTimesRedrawAllowed) {
    super();
    if (numTimesRedrawAllowed < 0) {
      throw new IllegalArgumentException("Bad input");
    }
    else {
      this.numTimesRedrawAllowed = numTimesRedrawAllowed;
    }
    this.numTimesCardDrawn = new ArrayList<>();

  }

  /**
   * Constructs a LimitedDrawKlondike with
   * default arguments.
   */
  public LimitedDrawKlondike() {
    this.numTimesRedrawAllowed = 2;
    this.numTimesCardDrawn = new ArrayList<>();
  }

  /**
   * Discards the topmost draw-card.
   *
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if move is not allowable
   */
  @Override
  public void discardDraw() {
    this.checkGameStatus();
    if (!this.drawPile.isEmpty()) {

      // increment the number of times the top card has been discarded
      this.drawPile.get(0).incrementNumDrawnSoFar();

      // add the number of draw to the list of all the cards and
      // how many times they've been drawn
      this.numTimesCardDrawn.add(0, this.drawPile.get(0).getNumDrawnSoFar());

      if (this.numTimesCardDrawn.get(0) > 1) {
        this.numTimesCardDrawn.remove(this.numTimesCardDrawn.size() - 1);
      }

      // discard the card
      this.drawPile.add(this.drawPile.remove(0));

      if (!this.checkNumDraws()) {
        this.drawPile.remove(this.drawPile.size() - 1);
      }

      if (this.numTimesCardDrawn.isEmpty()) {
        throw new IllegalStateException("no cards");
      }
    }
  }

  /**
   * Determines whether any of the cards in the draw pile
   * have exceeded the numTimesRedrawAllowed.
   * @return false if any of the cards in the draw pile have been drawn
   *         more than the numTimesRedrawAllowed.
   */
  private boolean checkNumDraws() {
    boolean result = false;
    for (Integer integer : this.numTimesCardDrawn) {
      if (integer < this.numTimesRedrawAllowed) {
        result = true;
      }
      else {
        break;
      }
    }
    return result;
  }

}

