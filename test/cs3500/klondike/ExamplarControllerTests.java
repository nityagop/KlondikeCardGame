package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;


/**
 * A JUnit testing class for the KlondikeTextualController.
 */
public class ExamplarControllerTests {

  KlondikeModel bk;
  List<Card> deck;
  StringReader input;
  StringBuilder gameLog;
  KlondikeController c;

  /**
   * Creates a deck of cards.
   *
   */
  private void makeDeck() {
    List<String> values = Arrays.asList("3", "2", "A");
    List<String> suits = Arrays.asList("♡", "♢", "♠");

    for (int i = 0; i < values.size(); i++) {
      for (int j = 0; j < suits.size(); j++) {
        for (Card c : this.bk.getDeck()) {
          if (c.toString().equals(values.get(i) + suits.get(j))) {
            this.deck.add(c);
          }
        }
      }
    }
  }

  /**
   * Tests whether the correct message is printed when the game is quit.
   */
  @Test
  public void testGameContainsQuitMessage() {
    this.input = new StringReader("q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!"));
  }

  /**
   * Tests whether a call to the moveDraw method doesn't work
   * if there are not enough inputs to the method.
   */
  @Test
  public void testGameContainsMoveDrawNotEnoughInputs() {
    this.input = new StringReader("md q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Game quit"));

  }

  /**
   * Tests whether the game quit message is outputted when the game is quit.
   */
  @Test
  public void testGameContainsStateAndDrawAfterQuit() {
    this.input = new StringReader("q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!\n"
            + "State of game when quit:\n"
            + "Draw: A♡, A♢, A♠"));
  }

  /**
   * Tests whether the state of the game is printed
   * when the user quits.
   */
  @Test
  public void testGameContainsStateAfterQuit() {
    this.input = new StringReader("q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("State of game"));
  }


  /**
   * Tests the output when an input other than the allowed
   * moves is inputted.
   */
  @Test
  public void testInvalidInput() {
    this.input = new StringReader("hello q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }


  /**
   * Tests an invalid move pile move.
   */
  @Test
  public void testInvalidMovePile() {
    this.input = new StringReader("mpp 0 0 0 q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }

  /**
   * Tests a valid move pile move.
   */
  @Test
  public void testValidMovePile() {
    this.input = new StringReader("mpp 3 1 1 q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertEquals(2, this.bk.getPileHeight(0));
  }

  /**
   * Tests whether a movePile with an invalid input works.
   */
  @Test
  public void testValidMovePileInvalidInput() {
    this.input = new StringReader("mpp 3 abcdefghi 1 1 q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertEquals(2, this.bk.getPileHeight(0));
  }

  /**
   * Tests whether a moveDraw with the wrong index works.
   */
  @Test
  public void testGameContainsInvalidMoveDraw() {
    this.input = new StringReader("md 0 q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }


  /**
   * Tests whether a valid moveDraw works.
   */
  @Test
  public void testGameContainsValidMoveDraw() {
    this.input = new StringReader("md 3 q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertEquals(2, this.bk.getDrawCards().size());
  }


  /**
   * Tests whether the game is printed when the user quits.
   */
  @Test
  public void testGameQuitContainsDeckAndScore() {
    this.input = new StringReader("q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Draw: A♡, A♢, A♠\n"
                    + "Foundation: <none>, <none>, <none>\n"
                    + " 3♡  ?  ?\n"
                    + "    2♡  ?\n"
                    + "       2♠\n"
                    + "Score: 0"));
  }
}

