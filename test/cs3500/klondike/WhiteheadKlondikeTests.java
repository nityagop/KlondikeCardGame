package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.PlayingCard;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * A JUnit class for testing methods in WhiteheadKlondike. I chose to
 * inherit my
 */

public class WhiteheadKlondikeTests extends KlondikeTests {

  WhiteheadKlondike klondike;
  List<Card> deck;

  /**
   * Initializes the game.
   */
  @Override
  protected void initData() {
    this.klondike = new WhiteheadKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();

  }

  /**
   * Creates a deck of cards.
   */
  protected void makeDeck() {
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
   * Tests invalid inputs for the WhiteheadKlondike constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWhiteheadConstructorInputs() {
    this.initData();
    this.klondike.startGame(this.deck, false, -3, -3);
  }

  /**
   * Tests a valid movePile for a WhiteheadKlondike instance.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputs() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.movePile(-1, -1, -1);
    Assert.assertEquals(2, this.klondike.getPileHeight(0));
  }

  /**
   * Tests a valid movePile for a WhiteheadKlondike instance.
   */
  @Test
  public void testValidMovePile() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.movePile(1, 1, 0);
    Assert.assertEquals(2, this.klondike.getPileHeight(0));
  }

  /**
   * Tests the movePile() method when there is an empty
   * space without a king.
   */
  @Test
  public void testMovePileNoKing() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDrawToFoundation(0);
    this.klondike.moveToFoundation(1, 0);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.movePile(2, 1, 0);
    Assert.assertEquals(0, this.klondike.getPileHeight(0));
  }

  /**
   * Tests a movePile with multiple cards where one of the cards has
   * the wrong suit.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMovePileMultipleCardsWrongSuit() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.moveDraw(2);
    this.klondike.movePile(2, 2, 0);
    Assert.assertEquals(1, this.klondike.getPileHeight(0));
  }

  /**
   * Tests a movePile with multiple cards where one of the cards has
   * the wrong suit.
   */
  @Test
  public void testValidMovePileMultipleCards() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDraw(1);
    this.klondike.movePile(1, 2, 0);
    Assert.assertEquals(3, this.klondike.getPileHeight(0));
  }

  /**
   * Tests a movePile with multiple cards where one of the cards has
   * the wrong suit.
   */
  @Test
  public void testValidMovePileMultipleCardsToEmptyWithNoKing() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDrawToFoundation(0);
    this.klondike.moveToFoundation(1, 0);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.discardDraw();
    this.klondike.moveDraw(2);
    this.klondike.movePile(2, 2, 0);
    Assert.assertEquals(2, this.klondike.getPileHeight(0));
  }

  /**
   * Tests a valid moveDraw for a Whitehead Klondike instance.
   */
  @Test
  public void testValidMoveDraw() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDraw(1);
    Assert.assertEquals(3, this.klondike.getPileHeight(1));
  }

  /**
   * Tests a invalid input in moveDraw for a Whitehead Klondike instance.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawInvalidInput() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.moveDraw(-1);
  }

  /**
   * Tests a moveDraw for a Whitehead Klondike instance if the draw pile is empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawInvalidDrawPile() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 0);
    this.klondike.moveDraw(1);
  }

  /**
   * Tests an invalid move pile move for a WhiteheadKlondike.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMovePileForWhiteheadKlondike() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.movePile(2, 1, 0);
  }

  /**
   * Tests the visibility of a card and determine whether the card is
   * visible.
   */
  @Test
  public void testVisibility() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    Assert.assertTrue(this.klondike.isCardVisible(1,0 ));
    Assert.assertTrue(this.klondike.isCardVisible(2,0 ));
    Assert.assertTrue(this.klondike.isCardVisible(2,2 ));
  }

  /**
   * Tests the getDrawCards() method.
   */
  @Test
  public void testDrawCards() {
    this.initData();
    List<Card> drawCards = new ArrayList<>();
    drawCards.add(new PlayingCard(Value.ACE, Suit.HEART, false));
    drawCards.add(new PlayingCard(Value.ACE, Suit.DIAMOND, false));
    this.klondike.startGame(this.deck, false, 3, 2);
    Assert.assertEquals(drawCards.size(), this.klondike.getDrawCards().size());
  }

  /**
   * Tests the movePile() method with the same pileNumbers
   * and multiple cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMovePileWithSamePileNumbers() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.movePile(2, 2, 2);
  }

  /**
   * Tests the discardDraw method to ensure the card gets moved to
   * the bottom of the draw pile and the size of the draw pile
   * remains the same.
   */
  @Test
  public void testDiscardDrawMovingToBottomOfDrawPile() {
    this.initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    Assert.assertEquals(3, this.klondike.getDrawCards().size());
    this.klondike.discardDraw();
    Assert.assertEquals(3, this.klondike.getNumDraw());
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    Assert.assertEquals(3, this.klondike.getNumDraw());
  }


  /**
   * Tests the KlondikeCreator method for when a WhiteheadKlondike
   * game is chosen.
   */
  @Test
  public void testKlondikeCreator() {
    initData();
    KlondikeModel model = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    model.startGame(this.deck, false, 3, 3);
    Assert.assertEquals(this.klondike.getDeck(), model.getDeck());
  }

}
