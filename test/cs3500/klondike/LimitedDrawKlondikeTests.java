package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

/**
 * A JUnit test class for the LimitedDrawKlondike. I chose to inherit
 * my test class from BasicKlondike to avoid have so many repeated tests.
 */
public class LimitedDrawKlondikeTests extends KlondikeTests {

  BasicKlondike klondike;
  List<Card> deck;

  /**
   * Initializes the game.
   */
  protected void initData() {
    this.klondike = new LimitedDrawKlondike(1);
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

  /**
   * Tests invalid inputs for the LimitedDraw constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLimitedConstructorInputs() {
    this.initData();
    this.klondike.startGame(this.deck, false, -3, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLimitedDrawKlondikeInvalidNumRedraw() {
    LimitedDrawKlondike limitedDrawKlondike = new LimitedDrawKlondike(-1);
    limitedDrawKlondike.startGame(this.deck, false, 3, 3);
  }


  @Test
  public void testDiscardDrawRemoveAllCards() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(0, this.klondike.getDrawCards().size());
  }

  @Test
  public void testDiscardDrawNumRedraws() {
    initData();
    this.klondike =  new LimitedDrawKlondike(2);
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(0, this.klondike.getDrawCards().size());
  }

  @Test
  public void testDiscardDrawValidNumberDiscarded() {
    initData();
    this.klondike =  new LimitedDrawKlondike(2);
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(3, this.klondike.getDrawCards().size());
  }

  @Test
  public void testDiscardDrawFirstNumberRemoved() {
    initData();
    this.klondike =  new LimitedDrawKlondike(2);
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(2, this.klondike.getDrawCards().size());
  }

  @Test
  public void testDiscardDrawFirstAndSecondRemoved() {
    initData();
    this.klondike =  new LimitedDrawKlondike(2);
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(1, this.klondike.getDrawCards().size());
  }


  /**
   * Tests the KlondikeCreator method for when a LimitedDrawKlondike
   * game is chosen.
   */
  @Test
  public void testKlondikeCreator() {
    initData();
    KlondikeModel model = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    model.startGame(this.deck, false, 3, 3);
    Assert.assertEquals(this.klondike.getDeck(), model.getDeck());
  }

  /**
   * Tests the discardDraw method with an empty draw pile.
   */
  @Test(expected = Exception.class)
  public void testEmptyDrawPileDiscardDraw() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 0);
    this.klondike.discardDraw();
  }
}
