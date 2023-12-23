package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.view.KlondikeTextualView;


/**
 * A JUnit testing class for the KlondikeTextualController.
 */
public class KlondikeControllerTests {

  KlondikeModel bk;
  List<Card> deck;
  StringReader input;
  StringBuilder gameLog;
  KlondikeController c;

  /**
   * Initializes the game.
   */
  private void initData() {
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(this.bk, this.deck, false, 3, 3);
  }


  /**
   * Creates a deck of cards.
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
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!"));
  }

  /**
   * Tests whether the game quit message is outputted when the game is quit.
   */
  @Test
  public void testGameContainsStateAndDrawAfterQuit() {
    this.input = new StringReader("q");
    this.initData();
    this.c.playGame(this.bk, this.deck, false, 3, 3);
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!\n"
            + "State of game when quit:\n"
            + "Draw: A♡, A♢, A♠"));
  }

  /**
   * Tests whether the invalid move message is outputted if a bad letter
   * is inputted.
   */
  @Test
  public void testGameContainsBadLetter() {
    this.input = new StringReader("abc q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move. Play again"));
  }

  /**
   * Tests whether the state of the game is printed
   * when the user quits.
   */
  @Test
  public void testGameContainsStateAfterQuit() {
    this.input = new StringReader("q");
    this.initData();
    System.out.print(this.gameLog.toString());
    Assert.assertTrue(this.gameLog.toString().contains("State of game when quit:"));
  }


  /**
   * Tests the output when an input other than the allowed
   * moves is inputted.
   */
  @Test
  public void testInvalidInput() {
    this.input = new StringReader("hello q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }

  /**
   * Tests the output when there is one valid input and the other is
   * invalid for move pile.
   */
  @Test
  public void testsOneValidInputOneInvalidMoveDraw() {
    this.input = new StringReader("md 1 md -1 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!"));
  }

  /**
   * Tests the output when there is one valid input and the other is
   * invalid for move draw.
   */
  @Test
  public void testsOneValidInputOneInvalidMovePile() {
    this.input = new StringReader("mpp 3 1 1 mpp 1 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!"));
  }


  /**
   * Tests an invalid move to foundation move.
   */
  @Test
  public void testInvalidMoveToFoundation() {
    this.input = new StringReader("mpf -1 0 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }

  /**
   * Tests an invalid move draw to foundation move.
   */
  @Test
  public void testInvalidMoveDrawToFoundation() {
    this.input = new StringReader("mdf -1 0 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }

  /**
   * Tests an invalid move pile move.
   */
  @Test
  public void testInvalidMovePile() {
    this.input = new StringReader("mpp 0 0 0 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }

  /**
   * Tests a valid move pile move.
   */
  @Test
  public void testValidMovePile() {
    this.input = new StringReader("mpp 3 1 1 q");
    this.initData();
    Assert.assertEquals(2, this.bk.getPileHeight(0));
  }

  /**
   * Tests a valid move pile move with a bigger deck.
   */
  @Test
  public void testValidMovePileBigDeck() {
    this.input = new StringReader("mpp 5 1 1 q");
    this.initData();
    Assert.assertEquals(1, this.bk.getPileHeight(0));
  }


  /**
   * Tests a valid move pile move and move draw move.
   */
  @Test
  public void testValidMultipleMovePile() {
    this.input = new StringReader("mpp 3 1 1 md 1 mdf 1 q");
    this.initData();
    Assert.assertEquals(3, this.bk.getPileHeight(0));
    Assert.assertEquals(1, this.bk.getNumDraw());
  }

  /**
   * Tests a valid move pile move and move draw move.
   */
  @Test
  public void testValidMultipleMoves() {
    this.input = new StringReader("mpp 3 1 1 dd dd md 1 mpp 1 1 1 q");
    this.initData();
    Assert.assertEquals(3, this.bk.getPileHeight(0));
    Assert.assertEquals(2, this.bk.getNumDraw());
    System.out.print(this.gameLog.toString());
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!\n"
            + "State of game when quit:\n"
            + "Draw: A♠, A♢\n"));
  }

  /**
   * Tests whether a movePile with an invalid input works.
   */
  @Test
  public void testValidMovePileInvalidInput() {
    this.input = new StringReader("mpp 3 abcdefghi 1 1 q");
    this.initData();
    Assert.assertEquals(2, this.bk.getPileHeight(0));
  }

  /**
   * Tests whether a movePile with an invalid input with a quit in the
   * middle works.
   */
  @Test
  public void testValidMovePileQuitInMiddle() {
    this.input = new StringReader("mpp 3 q q 1 1 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Game quit!"));
  }

  /**
   * Tests whether a movePile with not enough inputs works.
   */
  @Test
  public void testValidMovePileNotEnoughInputs() {
    this.input = new StringReader("mpp 3 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Game quit"));
  }

  /**
   * Tests whether a moveDraw with the wrong index works.
   */
  @Test
  public void testGameContainsInvalidMoveDraw() {
    this.input = new StringReader("md 0 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move"));
  }

  /**
   * Tests whether a valid moveDraw works.
   */
  @Test
  public void testGameContainsValidMoveDraw() {
    this.input = new StringReader("md 3 q");
    this.initData();
    Assert.assertEquals(2, this.bk.getDrawCards().size());
  }

  /**
   * Tests whether a valid moveDrawToFoundation works.
   */
  @Test
  public void testGameContainsValidMoveDrawToFoundation() {
    this.input = new StringReader("mdf 1 q");
    this.initData();
    Assert.assertEquals(2, this.bk.getNumDraw());
  }

  /**
   * Tests whether a call to the moveDraw method doesn't work
   * if there are not enough inputs to the method.
   */
  @Test
  public void testGameContainsMoveDrawNotEnoughInputs() {
    this.input = new StringReader("md q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move. Play again"));
  }

  /**
   * Tests whether the game is printed when the user quits before
   * making any moves.
   */
  @Test
  public void testGameQuitContainsDeckAndScore() {
    this.input = new StringReader("q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Draw: A♡, A♢, A♠\n"
            + "Foundation: <none>, <none>, <none>\n"
            + " 3♡  ?  ?\n"
            + "    2♡  ?\n"
            + "       2♠\n"
            + "Score: 0"));
  }

  /**
   * Tests whether the game throws an IllegalArgymentException
   * if the controller has invalid inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckInvalidControllerInputs() {
    this.input = null;
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);

    this.input = new StringReader("dd dd dd q");
    this.gameLog = null;
    this.c = new KlondikeTextualController(input, gameLog);
  }

  /**
   * Tests whether the game throws an IllegalArgumentException
   * when playGame inputs are null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckInvalidPlayGameInputs() {
    this.input = new StringReader("dd dd dd q");
    this.bk = new BasicKlondike();
    this.deck = new ArrayList<>();
    this.makeDeck();
    this.gameLog = new StringBuilder();
    this.c = new KlondikeTextualController(input, gameLog);
    this.c.playGame(null, this.deck, false, 3, 3);
    this.c.playGame(null, null, false, 3, 3);
  }

  /**
   * Tests whether the game handles a valid discardDraw call.
   */
  @Test
  public void testGameDiscardAllDraw() {
    this.input = new StringReader("dd dd dd q");
    this.initData();
    Assert.assertEquals(3, this.bk.getNumDraw());
  }

  /**
   * Tests a valid moveToFoundation input.
   */
  @Test
  public void testValidMovePileToFoundation() {
    this.input = new StringReader("mpp 3 1 1 md 1 mpf 1 1 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Draw: A♠, A♢\n"
            + "Foundation: A♡, <none>, <none>\n"));
  }

  /**
   * Tests whether the game handles a proper game over if
   * unwinnable.
   */
  @Test
  public void testGameOverUnwinnable() {
    this.input = new StringReader("mpp 3 1 1 mdf 1 mdf 2 mdf 3 mpf 2 1 mpf 1 3 q");
    this.initData();
    Assert.assertEquals(3, this.bk.getScore());
    Assert.assertTrue(this.gameLog.toString().contains("Game over. Score: 3"));
  }

  /**
   * Tests whether the movePile methods work with zero.
   */
  @Test
  public void testMovePileWithZero() {
    this.input = new StringReader("mpp 3 1 0 q");
    this.initData();
    Assert.assertTrue(this.gameLog.toString().contains("Invalid move."));
  }


  /**
   * Tests whether the game handles a proper game over if
   * the user wins.
   */
  @Test
  public void testGameOverGameWin() {
    this.input = new StringReader("mpp 3 1 1 mdf 1 mdf 2 mdf 3 mpf 2 1 mpf 1 3 " +
            "mpf 3 2 mpf 1 1 mpf 2 2 mpf 3 3 q");
    this.initData();
    Assert.assertEquals(3, this.bk.getScore());
    Assert.assertTrue(this.gameLog.toString().contains("Game win!"));
  }

  /**
   * Tests whether an exception is thrown if
   * the appendable has no more inputs.
   */
  @Test(expected = Exception.class)
  public void testNoMoreInputs() {
    this.input = new StringReader("");
    this.initData();
    Scanner reader = new Scanner(this.input);
    reader.next();
    c.playGame(this.bk, this.deck, false, 3, 3);
  }

  /**
   * Tests the render methods.
   */
  @Test
  public void testRender() {
    this.input = new StringReader("mdf 3 q");
    this.initData();
    KlondikeTextualView tv = new KlondikeTextualView(this.bk, this.gameLog);

    try {
      tv.render();
      Assert.assertTrue(this.gameLog.toString().contains("Draw: A♡, A♢, A♠\n"));
    } catch (IOException io) {
      throw new IllegalStateException("render failed");
    }
  }

  /**
   * Tests whether an exception is thrown if the inputs
   * for the render method are invalid.
   */
  @Test
  public void testRenderExceptions() {
    this.input = new StringReader("hello my name is q");
    initData();
    KlondikeTextualView tv = new KlondikeTextualView(this.bk, this.gameLog);

    try {
      tv.render();
      Assert.assertFalse(this.gameLog.toString().contains("hello my name is q"));
    } catch (IOException io) {
      throw new IllegalStateException("render failed");
    }
  }
}

