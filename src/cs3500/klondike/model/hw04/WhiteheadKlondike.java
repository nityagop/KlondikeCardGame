package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a game of WhiteheadKlondike. WhiteheadKlondike
 * follows most of the rules of BasicKlondike but the rules for developing
 * valid cascade piles are different. For my implementation of WhiteheadKlondike
 * I chose to extend the BasicKlondike model to avoid code duplication.
 */
public class WhiteheadKlondike extends BasicKlondike {
  public WhiteheadKlondike() {
    super();
  }


  /**
   * Deals out cards to form the cascade pile formation based
   * on the given number of piles numPile.
   *
   * @param numPiles the number of cascade piles in the game.
   */
  @Override
  protected void dealCascadePiles(List<Card> deck, int numPiles) {
    for (int rows = 0; rows < numPiles; rows++) {

      for (int cols = rows; cols < numPiles; cols++) {
        if (rows == 0) {
          this.cascadePile.add(new ArrayList<>());
        }
        this.cascadePile.get(cols).add(this.drawPile.remove(0));
        this.cascadePile.get(cols).get(rows).setVisibility();
      }
    }
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    this.checkGameStatus();
    if (srcPile == destPile || srcPile < 0 || destPile < 0) {
      throw new IllegalArgumentException("Invalid inputs");
    }
    else if (srcPile >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Invalid input");
    } else if (numCards <= 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (destPile >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Too big");
    } else if (this.cascadePile.get(srcPile).size() - numCards < 0) {
      throw new IllegalArgumentException("Moving too many cards");
    }
    else {
      if (numCards > 1) {
        if (srcPile - numCards <= this.cascadePile.get(destPile).size()) {
          for (int i = numCards; i > 0; i--) {
            Card c = this.cascadePile.get(srcPile).get(this.cascadePile.get(srcPile).size() - i);
            this.movePileHelp(numCards, c, destPile);
            this.cascadePile.get(srcPile).remove(this.cascadePile.get(srcPile).size() - i);
            int topCardIndex = this.cascadePile.get(srcPile).size() - 1;
            if (!this.cascadePile.get(srcPile).isEmpty()) {
              if (!this.cascadePile.get(srcPile).get(topCardIndex).getVisibility()) {
                this.cascadePile.get(srcPile).get(topCardIndex).setVisibility();
              }
            }
          }
        }
      }
      else {
        if (srcPile - numCards <= this.cascadePile.get(destPile).size()) {
          Card c = this.cascadePile.get(srcPile).get(this.cascadePile.get(srcPile).size() -
                  numCards);
          this.movePileHelp(numCards, c, destPile);
          this.cascadePile.get(srcPile).remove(this.cascadePile.get(srcPile).size() -
                  numCards);
          int topCardIndex = this.cascadePile.get(srcPile).size() - 1;
          if (!this.cascadePile.get(srcPile).isEmpty()) {
            if (!this.cascadePile.get(srcPile).get(topCardIndex).getVisibility()) {
              this.cascadePile.get(srcPile).get(topCardIndex).setVisibility();
            }
          }
        }
      }
    }
  }



  /**
   * Moves the topmost draw-card to the destination pile.  If no draw cards remain,
   * reveal the next available draw cards
   *
   * @param destPile the 0-based index (from the left) of the destination pile for the
   *                 card
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if destination pile number is invalid
   * @throws IllegalStateException    if there are no draw cards, or if the move is not
   *                                  allowable
   */
  @Override
  public void moveDraw(int destPile) {
    this.checkGameStatus();
    if (destPile < 0) {
      throw new IllegalArgumentException("Invalid input");
    }
    else if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("No more cards left");
    }
    else if (destPile >= this.getNumPiles()) {
      throw new IllegalArgumentException("Too big");
    }
    else {
      Card c = this.drawPile.get(0);
      this.moveDrawHelp(c, destPile);
      this.drawPile.add(this.drawPile.remove(0));
    }
  }

  /**
   * A helper method for the movePile method.
   * that adds the given Card to the destination cascade pile.
   *
   * @param c        the Card to be moved from the given source cascade pile.
   * @param destPile the destination cascade pile index.
   */
  protected void movePileHelp(int numCards, Card c, int destPile) {
    List<Card> cardList = this.cascadePile.get(destPile);
    if (cardList.isEmpty()) {
      cardList.add(c);
    }
    else {
      Card c2 = cardList.get(cardList.size() - 1);
      if (numCards >  1) {
        if ((c2.getCardSuit().equals(c.getCardSuit()))) {
          if (c.getCardValue().getValue() + 1 == c2.getCardValue().getValue()) {
            if (!c.getVisibility()) {
              c.setVisibility();
            }
            cardList.add(c);
          }
          else {
            throw new IllegalStateException("Move not allowed, wrong number");
          }
        }
        else {
          throw new IllegalStateException("Move not allowed, wrong suit");
        }
      }
      else {
        if ((c2.getColor().equals(c.getColor()))) {
          if (c.getCardValue().getValue() + 1 == c2.getCardValue().getValue()) {
            if (!c.getVisibility()) {
              c.setVisibility();
            }
            cardList.add(c);
          }
          else {
            throw new IllegalStateException("Move not allowed, wrong number");
          }
        }
        else {
          throw new IllegalStateException("Move not allowed, wrong suit");
        }
      }
    }
  }


  /**
   * A helper method for the moveDraw method
   * that adds the given Card from the draw pile to the given cascade pile.
   *
   * @param c        the Card to be moved from the draw pile
   * @param destPile the cascade pile index
   */
  protected void moveDrawHelp(Card c, int destPile) {
    List<Card> cardList = this.cascadePile.get(destPile);
    if (cardList.isEmpty()) {
      cardList.add(c);
    }
    else {
      Card c2 = cardList.get(cardList.size() - 1);
      if ((c2.getCardSuit().equals(c.getCardSuit()))) {
        if (c.getCardValue().getValue() + 1 == c2.getCardValue().getValue()) {
          if (!c.getVisibility()) {
            c.setVisibility();
          }
          cardList.add(c);
        } else {
          throw new IllegalStateException("Move not allowed, wrong number");
        }
      } else {
        throw new IllegalStateException("Move not allowed, wrong color");
      }
    }
  }
}

