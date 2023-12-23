package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.PlayingCard;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.view.KlondikeTextualView;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A JUnit class for testing methods in BasicKlondike.
 */
public class KlondikeTests {

  BasicKlondike klondike;
  List<Card> deck;

  /**
   * Initializes a BasicKlondike object and its deck.
   */
  protected void initData() {
    this.klondike = new BasicKlondike();
    this.deck = new ArrayList<Card>();

    for (Suit s : Suit.values()) {
      for (Value v : Value.values()) {
        this.deck.add(new PlayingCard(v, s, false));
      }
    }
  }

  /**
   * Creates a sorted deck of cards.
   *
   * @param deck a list of cards to be sorted.
   */
  protected void sortDeck(List<Card> deck) {
    deck.sort(new Comparator<Card>() {

      @Override
      public int compare(Card o1, Card o2) {
        String card1 = o1.toString().substring(0, o1.toString().length() - 1);
        String card2 = o2.toString().substring(0, o2.toString().length() - 1);
        String card3 = o1.toString().substring(o1.toString().length() - 1);
        String card4 = o2.toString().substring(o2.toString().length() - 1);

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
            card1 = " ";
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
            card2 = " ";
        }

        if (card1.compareTo(card2) == 0) {
          return card3.compareTo(card4);
        }
        return card1.compareTo(card2);
      }
    });
  }


  /**
   * Tests the moveDraw() method when moving to a pile.
   * that does not exist.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawOutOfBoundsArg() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 1);
    this.klondike.moveDraw(3);
  }


  /**
   * Tests the movePile() method when there is an empty
   * space without a "king".
   */
  @Test
  public void testMovePile() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 1);
    this.klondike.moveToFoundation(0, 1);
    Assert.assertEquals(4, this.klondike.getNumFoundations());
  }

  /**
   * Tests the moveDraw() method when moving to a destination
   * pile of 0.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawToDestPileZero() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 1);
    this.klondike.moveDraw(0);
  }

  /**
   * Tests the moveDrawToFoundation() method when moving to a destination
   * pile of 0.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawToFoundationPileZero() {
    initData();
    BasicKlondike card = new BasicKlondike();
    this.klondike.startGame(this.deck, false, 4, 1);
    this.klondike.moveDrawToFoundation(0);
  }

  /**
   * Tests the moveDraw() method when moving to a destination
   * pile of 2.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawDestPileTwo() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 1);
    this.klondike.moveDraw(2);
  }

  /**
   * Tests the moveToFoundation() method with an invalid
   * source pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationInvalidSourcePile() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 1);
    this.klondike.moveToFoundation(3, 0);
  }

  /**
   * Tests the moveDraw() method through an
   * invalid move to an empty cascade pile.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveDrawToEmptySpace() {
    initData();
    this.klondike.startGame(this.deck, false, 1, 1);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.moveDraw(0);
  }

  /**
   * Tests the size of getDrawCards() after discarding cards
   * and moving cards.
   */
  @Test
  public void testGetDrawCardsSizeAfterDiscarding() {
    initData();
    Collections.reverse(this.deck);
    this.klondike.startGame(this.deck, false, 2, 2);
    this.klondike.discardDraw();
    Assert.assertEquals(2, this.klondike.getNumDraw());
  }

  /**
   * Tests the getDrawCards() method when there are
   * cards being removed.
   */
  @Test(expected = Exception.class)
  public void testGetDrawCardsSize() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 1);
    this.klondike.getDrawCards().remove(2);
  }

  /**
   * Tests the moveDrawToFoundation() method when the suit is correct
   * but the number is incorrect.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveToFoundationWrongCardValue() {
    initData();
    this.klondike.startGame(deck, false, 4, 1);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.discardDraw();
    this.klondike.discardDraw();
    this.klondike.moveDrawToFoundation(0);
  }

  /**
   * Tests the startGame() method in the BasicKlondike class to check
   * whether an exception is thrown if the game has already been started.
   */
  @Test(expected = IllegalStateException.class)
  public void testGameStartedTwice() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 1);

    BasicKlondike klondike2 = new BasicKlondike();
    klondike2.startGame(this.deck, false, 4, 2);

    BasicKlondike klondike3 = new BasicKlondike();
    klondike2.startGame(this.deck, false, 3, 1);

    Assert.assertEquals(this.klondike.getDeck(), klondike2.getDeck());
    Assert.assertEquals(this.klondike.getDeck(), klondike3.getDeck());
    Assert.assertEquals(klondike2.getDeck(), klondike3.getDeck());
  }

  /**
   * Tests the moveToFoundation() method when trying to
   * move a card to a pile that is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationOutOfBounds() {
    initData();
    this.klondike.startGame(deck, false, 1, 1);
    this.klondike.moveToFoundation(0, 0);
    this.klondike.moveToFoundation(3, 0);
  }

  /**
   * Tests the movePile() method with the same pileNumbers
   * and multiple cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMovePileWithSamePileNumbers() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 1);
    this.klondike.movePile(2, 2, 2);
  }

  /**
   * Tests an invalid move using the moveDrawToFoundation() method.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveDrawToFoundation() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 1);
    this.klondike.moveDrawToFoundation(3);
  }

  /**
   * Tests an invalid move using the moveDrawToFoundation() method.
   */
  @Test
  public void testCascade() {
    initData();
    this.klondike.startGame(this.deck, false, 4, 3);
    Assert.assertEquals(2, this.klondike.getPileHeight(1));
  }

  /**
   * Tests the moveToFoundation() method by moving an Ace to
   * an empty foundation pile.
   */
  @Test
  public void testMoveAceToEmptyFoundationPile() {
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.THREE, Suit.DIAMOND, false),
            new PlayingCard(Value.THREE, Suit.CLUB, false),
            new PlayingCard(Value.THREE, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, true),
            new PlayingCard(Value.TWO, Suit.DIAMOND, false),
            new PlayingCard(Value.ACE, Suit.DIAMOND, false));
    b.startGame(deck, false, 3, 2);
    b.moveToFoundation(0, 0);
    Assert.assertEquals(3, b.getNumFoundations());
    Assert.assertEquals(0, b.getPileHeight(0));

  }


  /**
   * Tests the isCardVisible() method in BasicKlondike.
   */
  @Test
  public void testCardIsVisible() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    Assert.assertTrue(this.klondike.isCardVisible(0, 0));
    Assert.assertTrue(this.klondike.isCardVisible(1, 1));
    Assert.assertTrue(this.klondike.isCardVisible(2, 2));
    Assert.assertFalse(this.klondike.isCardVisible(1, 0));
  }

  /**
   * Tests the discardDraw method to ensure the card gets moved to
   * the bottom of the draw pile and the size of the draw pile
   * remains the same.
   */
  @Test
  public void testDiscardDrawMovingToBottomOfDrawPile() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);
    this.klondike.discardDraw();
    Assert.assertEquals(3, this.klondike.getDrawCards().size());
    Assert.assertEquals(3, this.klondike.getNumDraw());
  }


  /**
   * Tests the getDrawCards() method.
   */
  @Test
  public void testDrawCards() {
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, false),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    List<Card> drawCards = Arrays.asList(new PlayingCard(Value.TWO, Suit.CLUB, false),
            new PlayingCard(Value.TWO, Suit.SPADE, false));
    b.startGame(deck, false, 2, 2);
    Assert.assertEquals(drawCards, b.getDrawCards());
  }


  /**
   * Tests the startGame method for invalid inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidInputs() {
    initData();
    this.klondike.startGame(this.deck, false, -1, 2);
    this.klondike.startGame(this.deck, false, 1, -2);

    List<Card> tooSmallDeck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false));
    this.klondike.startGame(tooSmallDeck, false, 3, 1);
    this.klondike.startGame(deck, false, 54, 1);
    this.klondike.startGame(deck, false, 2, 55);
  }

  /**
   * Tests the startGame method when shuffle = true to see
   * if the deck is shuffled.
   */
  @Test
  public void testStartGameShuffleTrue() {
    initData();
    this.klondike.startGame(this.deck, false, 1, 2);

    BasicKlondike b = new BasicKlondike();
    b.startGame(this.deck, true, 1, 2);

    Assert.assertNotEquals(b, this.klondike);
  }


  @Test
  public void testSimpleGame2() {
    BasicKlondike b = new BasicKlondike();

    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false));
    b.startGame(deck, false, 2, 1);
    b.moveToFoundation(0, 0);
    Assert.assertEquals(new PlayingCard(Value.ACE, Suit.CLUB, false),
            b.getCardAt(0));
    Assert.assertEquals(0, b.getPileHeight(0));

  }

  /**
   * Tests the moveDrawToFoundation method and getCardAt the given
   * foundation pile to ensure the foundation pile was updated.
   */
  @Test
  public void moveCardToFoundationCheckFoundationPile() {
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.THREE, Suit.DIAMOND, false),
            new PlayingCard(Value.THREE, Suit.CLUB, false),
            new PlayingCard(Value.THREE, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, true),
            new PlayingCard(Value.TWO, Suit.DIAMOND, false),
            new PlayingCard(Value.ACE, Suit.DIAMOND, false));
    b.startGame(deck, false, 3, 2);
    b.moveToFoundation(0, 0);
    Assert.assertEquals(new PlayingCard(Value.ACE, Suit.CLUB, false),
            b.getCardAt(0));
    b.moveDrawToFoundation(1);
    b.moveToFoundation(2, 0);
    Assert.assertEquals(new PlayingCard(Value.TWO, Suit.CLUB, false), b.getCardAt(0));
  }

  /**
   * Tests the moveToFoundation() method when all the inputs are valid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void moveToFoundationWithValidInputs() {
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.QUEEN, Suit.CLUB, false),
            new PlayingCard(Value.KING, Suit.SPADE, false),
            new PlayingCard(Value.KING, Suit.HEART, false),
            new PlayingCard(Value.QUEEN, Suit.DIAMOND, true),
            new PlayingCard(Value.ACE, Suit.DIAMOND, false),
            new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false));
    b.startGame(deck, false, 3, 2);
    Assert.assertEquals(new PlayingCard(Value.QUEEN, Suit.DIAMOND, false), b.getCardAt(1, 1));
    Assert.assertEquals(3, b.getPileHeight(2));
    b.moveToFoundation(2, 0);
    Assert.assertEquals(2, b.getPileHeight(2));
    b.moveToFoundation(2, 1);
    Assert.assertEquals(1, b.getPileHeight(2));
    Assert.assertEquals(new PlayingCard(Value.ACE, Suit.CLUB, false), b.getCardAt(0));
    Assert.assertEquals(new PlayingCard(Value.ACE, Suit.DIAMOND, false), b.getCardAt(1));
    Assert.assertEquals(new PlayingCard(Value.KING, Suit.HEART, false), b.getCardAt(2, 0));
  }

  /**
   * Tests the movePile method when all inputs are valid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void movePileWithValidInputs() {
    /*
       Two Club   King Spade      King Heart
                  Queen Diamond   Jack Club
                                  Ace Heart
     */
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.THREE, Suit.SPADE, false),
            new PlayingCard(Value.THREE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.DIAMOND, true),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.DIAMOND, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    b.startGame(deck, false, 3, 2);

    Assert.assertEquals(new PlayingCard(Value.ACE, Suit.SPADE, false), b.getCardAt(2, 2));
    b.moveToFoundation(2, 0);
    Assert.assertEquals(1, b.getPileHeight(0));
    Assert.assertEquals(new PlayingCard(Value.JACK, Suit.HEART, false), b.getCardAt(2, 1));
    b.movePile(2, 1, 0);
    Assert.assertEquals(new PlayingCard(Value.QUEEN, Suit.CLUB, false), b.getCardAt(0, 0));
    Assert.assertEquals(new PlayingCard(Value.JACK, Suit.HEART, false), b.getCardAt(0, 1));
    Assert.assertEquals(new PlayingCard(Value.KING, Suit.HEART, false), b.getCardAt(2, 0));
    Assert.assertFalse(b.isGameOver());
    Assert.assertEquals(1, b.getScore());
  }

  /**
   * Tests the discardDraw() method and getCardAt() to verify
   * the cards have been discarded.
   */
  @Test
  public void testDiscardDrawWithCardAt() {
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.TWO, Suit.CLUB, false),
            new PlayingCard(Value.THREE, Suit.DIAMOND, false),
            new PlayingCard(Value.THREE, Suit.CLUB, false),
            new PlayingCard(Value.THREE, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.SPADE, true),
            new PlayingCard(Value.TWO, Suit.DIAMOND, false),
            new PlayingCard(Value.ACE, Suit.DIAMOND, false));
    b.startGame(deck, false, 3, 2);
    b.moveDrawToFoundation(0);
    Assert.assertEquals(new PlayingCard(Value.ACE, Suit.SPADE, false), b.getCardAt(0));
    Assert.assertEquals(2, b.getNumDraw());
    Assert.assertEquals(1, b.getScore());
  }

  /**
   * Tests the startGame() method to ensure that an exception
   * is thrown when cards are null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullCardInDeck() {
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.QUEEN, Suit.CLUB, false),
            new PlayingCard(Value.KING, Suit.SPADE, false), null);
    b.startGame(deck, true, 2, 2);
  }


  /**
   * Tests the getNumRows() method in BasicKlondike.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetNumRows() {
    initData();

    this.klondike.getNumRows();

    this.klondike.startGame(this.deck, false, 2, 2);
    Assert.assertEquals(2, this.klondike.getNumRows());

    this.klondike.startGame(this.deck, false, 7, 2);
    Assert.assertEquals(7, this.klondike.getNumRows());
  }

  /**
   * Tests the getNumPiles() method in BasicKlondike.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetNumPiles() {
    initData();

    this.klondike.getNumPiles();

    this.klondike.startGame(this.deck, false, 2, 2);
    Assert.assertEquals(2, this.klondike.getNumPiles());

    BasicKlondike b = new BasicKlondike();
    b.startGame(this.deck, false, 7, 3);
    Assert.assertEquals(7, b.getNumPiles());
  }

  /**
   * Tests the getNumDraw() method in BasicKlondike.
   */
  @Test
  public void testGetNumDraw() {
    initData();
    this.klondike.startGame(this.deck, false, 2, 2);
    this.klondike.getNumDraw();
    Assert.assertEquals(2, this.klondike.getNumDraw());

    BasicKlondike b = new BasicKlondike();
    b.startGame(this.deck, false, 7, 3);
    Assert.assertEquals(3, b.getNumDraw());
  }

  /**
   * Tests the getNumFoundations() method in BasicKlondike.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetNumFoundations() {
    initData();
    this.klondike.getNumFoundations();
    this.klondike.startGame(this.deck, false, 2, 2);
    Assert.assertEquals(4, this.klondike.getNumFoundations());

    BasicKlondike b = new BasicKlondike();
    List<Card> deck2 = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    b.startGame(deck2, false, 2, 3);
    Assert.assertEquals(3, b.getNumFoundations());
  }

  /**
   * Tests the getPileHeight() method in BasicKlondike.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetPileHeight() {
    this.initData();
    this.klondike.getPileHeight(0);

    this.klondike.startGame(this.deck, false, 4, 2);
    Assert.assertEquals(1, this.klondike.getPileHeight(0));
    Assert.assertEquals(2, this.klondike.getPileHeight(1));
    Assert.assertEquals(3, this.klondike.getPileHeight(2));
    Assert.assertEquals(4, this.klondike.getPileHeight(3));
    this.klondike.getPileHeight(-3);
    this.klondike.getPileHeight(8);
  }

  /**
   * Tests the isGameOver() method in BasicKlondike
   * if the game hasn't started yet.
   */
  @Test(expected = IllegalStateException.class)
  public void testIsGameOver() {
    initData();
    this.klondike.isGameOver();
    this.klondike.startGame(this.deck, false, 4, 2);
    Assert.assertFalse(this.klondike.isGameOver());
  }

  /**
   * Tests the getCascadePile() in the KlondikeTextualView.
   */
  @Test
  public void testGetCascadePile() {
    BasicKlondike b = new BasicKlondike();
    KlondikeTextualView tv = new KlondikeTextualView(b);
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    b.startGame(deck, false, 2, 2);
    Assert.assertEquals(" A♣  ?\n    A♡\n", tv.getCascadePile());
  }

  /**
   * Tests the getDrawPile() in the KlondikeTextualView.
   */
  @Test
  public void testGetDrawPile() {
    BasicKlondike b = new BasicKlondike();
    KlondikeTextualView tv = new KlondikeTextualView(b);
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    b.startGame(deck, false, 2, 2);
    BasicKlondike b2 = new BasicKlondike();
    KlondikeTextualView tv2 = new KlondikeTextualView(b2);
    List<Card> emptyDraw = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false));
    b2.startGame(emptyDraw, false, 2, 2);
    Assert.assertEquals("Draw: 2♣, 2♠", tv.getDrawPile());
    Assert.assertEquals("Draw: ", tv2.getDrawPile());
  }

  /**
   * Tests the getFoundationPile() in the KlondikeTextualView.
   */
  @Test
  public void testGetFoundationPile() {
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    BasicKlondike b2 = new BasicKlondike();
    KlondikeTextualView tv2 = new KlondikeTextualView(b2);
    List<Card> emptyDraw = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false));
    b2.startGame(emptyDraw, false, 2, 2);
    Assert.assertEquals("Foundation: <none>, <none>, <none>", tv2.getFoundationPile());
    b2.moveToFoundation(0, 0);
    Assert.assertEquals("Foundation: A♣, <none>, <none>", tv2.getFoundationPile());
  }

  /**
   * Tests the toString() method in the KlondikeTextualView.
   */
  @Test
  public void testTextualViewToString() {
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    BasicKlondike b2 = new BasicKlondike();
    KlondikeTextualView tv2 = new KlondikeTextualView(b2);
    List<Card> emptyDraw = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false));
    b2.startGame(emptyDraw, false, 2, 2);
    Assert.assertEquals("Draw: \nFoundation: <none>, <none>, <none>\n A♣  ?\n    A♡\n",
            tv2.toString());
  }

  /**
   * Tests the KlondikeCreator method for when a BasicKlondike
   * game is chosen.
   */
  @Test
  public void testKlondikeCreator() {
    initData();
    KlondikeModel model = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    model.startGame(this.deck, false, 3, 3);
    Assert.assertEquals(this.klondike.getDeck(), model.getDeck());
  }

  /**
   * Tests an invalid KlondikeCreator method for when a BasicKlondike
   * game is chosen.
   */
  @Test(expected = AssertionError.class)
  public void testInvalidKlondikeCreator() {
    initData();
    KlondikeModel model = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    Assert.assertEquals(this.klondike, model);
  }
}




