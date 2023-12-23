package cs3500.klondike.model.hw02;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A JUnit class to test private methods of BasicKlondike.
 */
public class KlondikePrivateTests {

  BasicKlondike klondike;
  List<Card> deck;
  Card c;
  Card cCopy;
  Card ace;

  /**
   * Initializes a BasicKlondike object and its deck.
   */
  private void initData() {
    this.klondike = new BasicKlondike();
    this.deck = new ArrayList<Card>();
    this.c = new PlayingCard(Value.SIX, Suit.CLUB, false);
    this.cCopy = new PlayingCard(Value.SIX, Suit.CLUB, false);
    this.ace = new PlayingCard(Value.ACE, Suit.HEART, true);


    for (Suit s : Suit.values()) {
      for (Value v : Value.values()) {
        this.deck.add(new PlayingCard(v, s, false));
      }
    }
  }

  /**
   * Tests the getNumAces method in the BasicKlondike class.
   */
  @Test
  public void testGetNumAces() {
    initData();
    this.klondike.startGame(this.deck, false, 3, 3);

    BasicKlondike b = new BasicKlondike();
    List<Card> deck2 = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    b.startGame(deck2, false, 3, 3);

    Assert.assertEquals(4, this.klondike.getNumAces(this.deck));
    Assert.assertEquals(3, b.getNumAces(deck2));
  }

  /**
   * Tests the getAceList() method for the BasicKlondike.
   * class
   */
  @Test
  public void testGetAceList() {
    initData();

    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));
    List<Card> aceList = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false));

    this.klondike.startGame(deck, false, 2, 3);
    Assert.assertEquals(aceList, this.klondike.getAceList(deck));
  }

  /**
   * Tests the getMaxNumber() method in BasicKlondike.
   */
  @Test
  public void testGetMaxNumber() {
    initData();
    this.klondike.startGame(this.deck, false, 2, 3);

    List<Card> maxAce = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false));

    List<Card> maxTwo = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.HEART, false),
            new PlayingCard(Value.TWO, Suit.CLUB, true),
            new PlayingCard(Value.TWO, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.HEART, false));

    Assert.assertEquals(13, this.klondike.getMaxNumber(this.deck));
    Assert.assertEquals(1, this.klondike.getMaxNumber(maxAce));
    Assert.assertEquals(2, this.klondike.getMaxNumber(maxTwo));
  }

  /**
   * Tests the validDeck() method in BasicKlondike.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValidDeck() {
    initData();
    this.klondike.validDeck(this.deck);

    BasicKlondike b = new BasicKlondike();
    List<Card> deck2 = Arrays.asList(new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.QUEEN, Suit.DIAMOND, true),
            new PlayingCard(Value.ACE, Suit.CLUB, false));

    b.validDeck(deck2);

    BasicKlondike b2 = new BasicKlondike();
    List<Card> deck3 = Arrays.asList(new PlayingCard(Value.ACE, Suit.SPADE, false),
            new PlayingCard(Value.ACE, Suit.DIAMOND, true),
            new PlayingCard(Value.ACE, Suit.CLUB, false));
    b2.validDeck(deck3);
  }

  /**
   * Tests the checkGameStatus() method.
   */
  @Test(expected = IllegalStateException.class)
  public void testCheckGameStatus() {
    initData();
    this.klondike.validDeck(this.deck);
    this.klondike.checkGameStatus();
    this.klondike.startGame(this.deck, false, 2, 3);
    this.klondike.checkGameStatus();
  }

  /**
   * Tests the movePileHelp() method.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveHelpers() {
    /*
       Ace club    Two club           Three diamond
                   ACe spade            twi spade
                                        Ace heart
     */
    BasicKlondike b = new BasicKlondike();
    List<Card> deck = Arrays.asList(new PlayingCard(Value.ACE, Suit.CLUB, false),
            new PlayingCard(Value.TWO, Suit.CLUB, false),
            new PlayingCard(Value.THREE, Suit.DIAMOND, false),
            new PlayingCard(Value.ACE, Suit.SPADE, true),
            new PlayingCard(Value.TWO, Suit.DIAMOND, false),
            new PlayingCard(Value.ACE, Suit.DIAMOND, false),
            new PlayingCard(Value.THREE, Suit.CLUB, false),
            new PlayingCard(Value.THREE, Suit.SPADE, false),
            new PlayingCard(Value.TWO, Suit.SPADE, false));
    b.startGame(deck, false, 3, 2);

    b.moveFoundationHelp(new PlayingCard(Value.ACE, Suit.CLUB, false), 0);
    b.moveFoundationHelp(new PlayingCard(Value.ACE, Suit.DIAMOND, false), 1);
    b.movePileHelp(new PlayingCard(Value.ACE, Suit.SPADE, false), 2);
    b.discardDraw();
    b.discardDraw();
    b.moveDrawHelp(new PlayingCard(Value.TWO, Suit.SPADE, false), 0);
  }

  /**
   * Tests the dealCascadePile() method.
   */
  @Test(expected = IllegalStateException.class)
  public void testDealCascades() {
    initData();
    this.klondike.dealCascadePiles(this.deck, this.klondike.getNumPiles());
    this.klondike.startGame(this.deck, false, 3, 2);
    this.klondike.dealCascadePiles(this.deck, this.klondike.getNumPiles());
  }

  /**
   * Tests the methods in the Card class.
   */
  @Test(expected = AssertionError.class)
  public void testCardClassMethods() {
    initData();
    Assert.assertFalse(this.ace.getVisibility());
    this.ace.setVisibility();
    Assert.assertTrue(this.ace.getVisibility());
    Assert.assertFalse(this.c.getVisibility());

    Assert.assertEquals("red", this.ace.getColor());
    Assert.assertEquals("black", this.c.getColor());

    Assert.assertEquals(Value.SIX, this.c.getCardValue());
    Assert.assertEquals(Suit.CLUB, this.c.getCardSuit());

    Assert.assertTrue(this.c.sameCard(this.cCopy));
    Assert.assertFalse(this.c.sameCard(this.ace));
    Assert.assertEquals(this.c, this.cCopy);
    Assert.assertNotEquals(this.c, this.ace);

    Assert.assertEquals("6♣", this.c.toString());
    Assert.assertEquals("♣", this.c.getCardSuit().toString());
    Assert.assertEquals("A♡", this.ace.toString());
    Assert.assertEquals("♡", this.ace.getCardSuit().toString());
    Assert.assertEquals(1, this.ace.getCardValue().getValue());
    Assert.assertEquals(6, this.c.getCardValue().getValue());

    Assert.assertTrue(this.c.getCardSuit().sameSuit(this.cCopy.getCardSuit()));
    Assert.assertFalse(this.ace.getCardSuit().sameSuit(this.cCopy.getCardSuit()));
    Assert.assertTrue(this.c.getCardValue().sameValue(this.cCopy.getCardValue()));
    Assert.assertFalse(this.ace.getCardValue().sameValue(this.cCopy.getCardValue()));

    Assert.assertEquals(85131202, this.c.hashCode());
    Assert.assertEquals(2028863065, this.ace.hashCode());
  }
}
