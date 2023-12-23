package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A JUnit testing class for BasicKlondike.
 */
public class ExamplarModelTests {

  BasicKlondike klondike;
  List<Card> deck;

  /**
   * Initializes a BasicKlondike object and its deck.
   */
  private void initData() {
    this.klondike = new BasicKlondike();
    this.deck = this.klondike.getDeck();
  }

  /**
   * Creates a sorted deck of cards.
   * @param deck a list of cards to be sorted.
   */
  public void sortDeck(List<Card> deck) {
    Collections.sort(deck, new Comparator<Card>() {

      @Override
      public int compare(Card o1, Card o2) {
        String card1 = o1.toString().substring(0, o1.toString().length() - 1);
        String card2 = o2.toString().substring(0, o2.toString().length() - 1);
        String card3 =  o1.toString().substring(o1.toString().length() - 1);
        String card4 = o2.toString().substring( o2.toString().length() - 1);

        switch (card1) {
          case "A":
            card1 = "1";
            break;
          case "J":
            card1 = "11";
            break;
          case "Q":
            card1 = "12";
            break;
          case "K":
            card1 = "13";
            break;
          default:
            card1 = "";
        }

        switch (card2) {
          case "A":
            card2 = "1";
            break;
          case "J":
            card2 = "11";
            break;
          case "Q":
            card2 = "12";
            break;
          case "K":
            card2 = "13";
            break;
          default:
            card2 = "";
        }

        if (card1.compareTo(card2) == 0) {
          return card3.compareTo(card4);
        }
        return card1.compareTo(card2);
      }
    });
  }


  /**
   * Tests the moveDraw() method when moving to a pile
   * that does not exist.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawOutOfBoundsArg() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 52);
    this.klondike.moveDraw(3);
  }

  /**
   * Tests the movePile() method when there is an empty
   * space without a "king".
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePileToEmptyNonKingSpace() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.movePile(1, 1, 0);
  }

  /**
   * Tests the moveToFoundation() method when the number is
   * correct but the suit is not.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveToFoundationWrongCardSuit() {
    BasicKlondike card = new BasicKlondike();
    List<Card> deck = card.getDeck();
    sortDeck(deck);
    card.startGame(card.getDeck(), false, 4, 52);
    card.moveToFoundation(0, 0);
    card.moveToFoundation(2, 0);
  }

  /**
   * Tests the moveDraw() method when moving to a destination
   * pile of 0.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawToDestPileZero() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 52);
    this.klondike.moveDraw(0);
  }

  /**
   * Tests an invalid move using the moveDrawToFoundation() method.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawToFoundation() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.moveDrawToFoundation(3);
  }

  /**
   * Tests the moveDrawToFoundation() method when moving to a destination.
   * pile of 0
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawToFoundationPileZero() {
    initData();
    BasicKlondike card = new BasicKlondike();
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.moveDrawToFoundation(0);
  }

  /**
   * Tests the moveDraw() method when moving to a destination.
   * pile of 2
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawDestPileTwo() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.moveDraw(2);
  }

  /**
   * Tests the movePile() method with the same pileNumbers
   * and multiple cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMovePileWithSamePileNumbers() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.movePile(2, 2, 2);
  }

  /**
   * Tests the movePile() method when there are not
   * enough cards to move from the source pile to the dest pile.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePileNotEnoughCardsToMove() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 52);
    this.klondike.movePile(2, 1, 0);
  }

  /**
   * Tests the moveToFoundation() method with an invalid
   * foundation pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationInvalidFoundationPile() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 52);
    this.klondike.moveToFoundation(2, 4);
  }

  /**
   * Tests the moveToFoundation() method with an invalid
   * source pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationInvalidSourcePile() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 52);
    this.klondike.moveToFoundation(3, 0);
  }

  /**
   * Tests the moveDraw() method through an
   * invalid move to an empty cascade pile.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveDrawToEmptySpace() {
    initData();
    sortDeck(this.deck);
    this.klondike.startGame(this.deck, false, 1, 52);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.moveDraw(0);
  }

  /**
   * Tests the pileHeight() method moving cards from one
   * pile to the other.
   */
  @Test
  public void testPileHeightAfterMovePileToAnotherPile() {
    initData();
    sortDeck(this.deck);
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.movePile(1, 1, 3);
    Assert.assertEquals(5, this.klondike.getPileHeight(3));
  }

  /**
   * Tests the size of getDrawCards() after discarding cards
   * and moving cards.
   */
  @Test
  public void testGetDrawCardsSizeAfterDiscarding() {
    initData();
    sortDeck(this.deck);
    Collections.reverse(this.deck);
    this.klondike.startGame(this.deck, false, 2, 52);
    this.klondike.discardDraw();
    this.klondike.moveDraw(1);
    Assert.assertEquals(48, this.klondike.getDrawCards().size());
  }

  /**
   * Tests the moveToFoundation() method when trying to
   * move a card to a pile that is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationOutOfBounds() {
    initData();
    sortDeck(this.deck);
    this.klondike.startGame(deck, false, 1, 52);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.moveToFoundation(3, 0);
  }

  /**
   * Tests the moveDrawToFoundation() method when the suit is corrent
   * but the number is incorrect.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveToFoundationWrongCardValue() {
    initData();
    sortDeck(this.deck);
    this.klondike.startGame(deck, false, 4, 52);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.moveDrawToFoundation(0);
  }

  /**
   * Tests the getDrawCards() method when there are
   * cards being removed.
   */
  @Test(expected = Exception.class)
  public void testGetDrawCardsSize() {
    initData();
    sortDeck(this.deck);
    this.klondike.startGame(this.deck, false, 4, 52);
    this.klondike.getDrawCards().remove(2);
  }
}



