package cs3500.klondike;

import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A JUnit testing class for LimitedDrawKlondike and WhiteheadKlondike.
 */
public class ExamplarExtendedModelTests {

  KlondikeModel klondike;
  List<Card> deck;
  KlondikeModel whiteheadKlondike;


  /**
   * Initializes a BasicKlondike object and its deck.
   */
  private void initData() {
    this.deck = new ArrayList<>();
    this.makeDeck();
  }

  /**
   * Creates a deck of cards.
   */
  private void makeDeck() {
    List<String> values = Arrays.asList("3", "2", "A");
    List<String> suits = Arrays.asList("♡", "♢", "♠");

    for (String value : values) {
      for (String suit : suits) {
        for (Card c : this.klondike.getDeck()) {
          if (c.toString().equals(value + suit)) {
            this.deck.add(c);
          }
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLimitedDrawKlondikeInvalidNumRedraw() {
    LimitedDrawKlondike limitedDrawKlondike = new LimitedDrawKlondike(-1);
    limitedDrawKlondike.startGame(this.deck, false, 3, 3);
  }

  @Test
  public void testDiscardDrawLimitedDrawNotRemoving() {
    this.klondike = new LimitedDrawKlondike(2);
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(3, this.klondike.getDrawCards().size());
  }

  // diff color not building
  @Test(expected = IllegalStateException.class)
  public void testInvalidMovePileForWhiteheadKlondike() {
    this.klondike = new WhiteheadKlondike();
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.movePile(2, 1, 0);
  }

  // diff suit not building
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDraw() {
    this.klondike = new WhiteheadKlondike();
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.moveDraw(1);
    this.klondike.movePile(1, 2, 0);
  }

  @Test
  public void testSomething() {
    this.klondike = new WhiteheadKlondike();
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDrawToFoundation(0);
    this.klondike.moveToFoundation(1, 0);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.moveDraw(0);
    Assert.assertEquals(1, this.klondike.getPileHeight(0));
  }

  @Test
  public void test2() {
    this.klondike = new WhiteheadKlondike();
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDraw(1);
    Assert.assertEquals(3, this.klondike.getPileHeight(1));
  }

}