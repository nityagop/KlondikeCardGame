package cs3500.klondike.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent an implementation of a KlondikeModel. A BasicKlondike
 * allows a user to play a game of Klondike which includes moving cards from
 * one pile to another, moving cards from the draw, and moving cards from the draw
 * or cascade piles to the foundation piles.
 */
public class BasicKlondike implements KlondikeModel {
  protected ArrayList<List<Card>> foundationPile;
  protected ArrayList<List<Card>> cascadePile;
  protected List<Card> drawPile;

  protected boolean gameStarted;


  /**
   * Constructs a BasicKlondike.
   */
  public BasicKlondike() {
    this.foundationPile = new ArrayList<List<Card>>();
    this.drawPile = new ArrayList<Card>();
    this.cascadePile = new ArrayList<List<Card>>();
    this.gameStarted = false;
  }

  /**
   * Return a valid and complete deck of cards for a game of Klondike.
   * There is no restriction imposed on the ordering of these cards in the deck.
   * The validity of the deck is determined by the rules of the specific game in
   * the classes implementing this interface.  This method may be called as often
   * as desired.
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();

    for (Suit s : Suit.values()) {
      for (Value v : Value.values()) {
        Card c = new PlayingCard(v, s, false);
        deck.add(c);
      }
    }
    return deck;
  }


  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {

    if (this.gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    // validates the deck
    this.validDeck(deck);

    // checks for invalid inputs
    if (numDraw < 1) {
      throw new IllegalArgumentException("numDraw is too small");
    } else if (numPiles < 1) {
      throw new IllegalArgumentException("numPiles is too small");
    } else if (deck.size() < numPiles || deck.size() < numDraw) {
      throw new IllegalArgumentException("deck is too small");
    }

    // shuffle deck and validate it once again
    if (shuffle) {
      Collections.shuffle(deck);
      this.validDeck(deck);
    }

    // all the cards from the inputted deck go to the draw pile
    this.drawPile.addAll(deck);

    // initialize the number of foundations piles as there are aces
    for (int i = 0; i < getNumAces(this.drawPile); i++) {
      this.foundationPile.add(new ArrayList<>());
    }

    // if the draw pile is large enough, deal the cascade piles out
    if (this.drawPile.size() > numPiles) {
      this.dealCascadePiles(this.drawPile, numPiles);
    }


    // set the visibility of the top num draw length cards to true and set the
    // rest of the draw pile to be not visible
    if (numDraw < this.drawPile.size()) {
      for (int i = 0; i < numDraw; i++) {
        this.drawPile.get(i).setVisibility();
      }
      for (int i = 0; i < this.drawPile.size() - numDraw; i++) {
        this.drawPile.get(i);
      }
    } else {
      for (Card card : this.drawPile) {
        card.setVisibility();
      }
    }
    this.gameStarted = true;
  }

  /**
   * Determines if a deck is valid and can be played. In our BasicKlondike
   * a deck is considered valid if it consists of equal-length single-suit runs
   * of consecutive values starting from Ace.
   *
   * @throws IllegalArgumentException if a card in the given deck is null
   * @throws IllegalArgumentException if the deck does not contain equal-length
   *                                  single-suit runs of consecutive values starting from Ace
   */
  protected void validDeck(List<Card> deck) {
    List<Card> suitsDeck = this.getAceList(deck);
    List<Card> deckCopy = this.getDeck();
    List<Card> consecutiveDeck = new ArrayList<>();
    int largestValue = this.getMaxNumber(deck);

    for (Card c : suitsDeck) {
      for (int i = 0; i < largestValue + 1; i++) {
        for (Card card : deck) {
          if (card == null || c == null) {
            throw new IllegalArgumentException("Cannot have null cards in the deck");
          }
          if (card.getCardSuit().sameSuit(c.getCardSuit()) &&
                  card.getCardValue().getValue() == i + 1) {
            consecutiveDeck.add(card);
            deckCopy.remove(card);
            break;
          }
        }
      }
    }
    if (consecutiveDeck.size() != (suitsDeck.size() * largestValue)) {
      throw new IllegalArgumentException("Invalid Deck");
    }
  }

  /**
   * Find the number of aces in the given deck to get the number of.
   *
   * @return an int value of the number of aces.
   */
  protected int getNumAces(List<Card> deck) {
    int countAce = 0;
    for (Card card : deck) {
      if (card.getCardValue().sameValue(Value.ACE)) {
        countAce++;
      }
    }
    return countAce;
  }

  /**
   * Determines the number of Aces in this given deck and
   * returns a list of them.
   *
   * @return a list of cards only comprised of aces.
   */
  List<Card> getAceList(List<Card> deck) {
    List<Card> aceDeck = new ArrayList<>();
    if (deck == null) {
      throw new IllegalArgumentException("Cannot have null cards in the deck");
    } else if (deck.isEmpty()) {
      throw new IllegalArgumentException("Cannot have an empty deck");
    }
    for (Card card : deck) {
      if (card == null) {
        throw new IllegalArgumentException("Cannot have a null card");
      } else {
        if (card.getCardValue().sameValue(Value.ACE)) {
          aceDeck.add(card);
        }
      }
    }
    return aceDeck;
  }

  /**
   * Finds the largest card value in the given deck where
   * A = 1, J = 11, Q = 12, K = 13.
   *
   * @return an integer of the largest value in the deck.
   */
  int getMaxNumber(List<Card> deck) {
    int max = -1000000;
    for (Card c : deck) {
      if (c.getCardValue().getValue() > max) {
        max = c.getCardValue().getValue();
      }
    }
    return max;
  }

  /**
   * Deals out cards to form the cascade pile formation based
   * on the given number of piles numPile.
   *
   * @param numPiles the number of cascade piles in the game.
   */
  protected void dealCascadePiles(List<Card> deck, int numPiles) {
    for (int rows = 0; rows < numPiles; rows++) {

      for (int cols = rows; cols < numPiles; cols++) {
        if (rows == 0) {
          this.cascadePile.add(new ArrayList<>());
        }
        this.cascadePile.get(cols).add(this.drawPile.remove(0));
        if (rows == cols) {
          this.cascadePile.get(cols).get(rows).setVisibility();
        }
      }
    }
  }

  /**
   * Moves the requested number of cards from the source pile to the destination pile,
   * if allowable by the rules of the game.
   *
   * @param srcPile  the 0-based index (from the left) of the pile to be moved
   * @param numCards how many cards to be moved from that pile
   * @param destPile the 0-based index (from the left) of the destination pile for the
   *                 moved cards
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if either pile number is invalid, if the pile
   *                                  numbers are the same, or there are not enough cards to
   *                                  move from
   *                                  the srcPile to the destPile (i.e. the move is not physically
   *                                  possible)
   * @throws IllegalStateException    if the move is not allowable (i.e. the move is not
   *                                  logically possible)
   */
  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    this.checkGameStatus();
    if (srcPile == destPile || srcPile < 0 || destPile < 0) {
      throw new IllegalArgumentException("Invalid inputs");
    } //else if (destPile < srcPile && numCards > 1) {
    //throw new IllegalArgumentException("Not enough cards to move");
    else if (srcPile >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Invalid input");
    } else if (numCards <= 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (destPile >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Too big");
    } else if (this.cascadePile.get(srcPile).size() - numCards < 0) {
      throw new IllegalArgumentException("Moving too many cards");
    } else {
      // get the top most card as a placeholder
      if (srcPile - numCards <= this.cascadePile.get(destPile).size()) {
        Card c = this.cascadePile.get(srcPile).get(this.cascadePile.get(srcPile).size() - numCards);
        // pass the card into a helper that determines whether it can be moved
        this.movePileHelp(c, destPile);
        // remove the card from the source pile because it has been moved to the other pile
        this.cascadePile.get(srcPile).remove(this.cascadePile.get(srcPile).size() - numCards);
        int topCardIndex = this.cascadePile.get(srcPile).size() - 1;
        if (!this.cascadePile.get(srcPile).isEmpty()) {
          if (!this.cascadePile.get(srcPile).get(topCardIndex).getVisibility()) {
            this.cascadePile.get(srcPile).get(topCardIndex).setVisibility();
          }
        }
      }
    }
  }

  /**
   * A helper method for the movePile method.
   * that adds the given Card to the destination cascade pile.
   *
   * @param c        the Card to be moved from the given source cascade pile.
   * @param destPile the destination cascade pile index.
   */
  protected void movePileHelp(Card c, int destPile) {
    List<Card> cardList = this.cascadePile.get(destPile);

    if (cardList.isEmpty()) {
      // can only move to an empty pile if a king is there
      if (c.toString().contains("K")) {
        cardList.add(c);
      } else {
        throw new IllegalStateException("Only kings can move to empty spaces");
      }
    } else {
      Card c2 = cardList.get(cardList.size() - 1);
      if (!(c2.getColor().equals(c.getColor()))) {
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
    } else if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("No more cards left");
    } else if (destPile >= this.getNumPiles()) {
      throw new IllegalArgumentException("Too big");
    } else {
      Card c = this.drawPile.get(0);
      this.moveDrawHelp(c, destPile);
      //this.drawPile.remove(0);
      this.drawPile.add(this.drawPile.remove(0));
    }
  }

  /**
   * A helper method for the moveDraw method
   * that adds the given Card from the draw pile to the given cascade pile.
   *
   * @param c        the Card to be moved from the draw pile
   * @param destPile the cascade pile index
   */
  void moveDrawHelp(Card c, int destPile) {
    List<Card> cardList = this.cascadePile.get(destPile);
    if (cardList.isEmpty()) {
      if (c.toString().contains("K")) {
        cardList.add(c);
      } else {
        throw new IllegalStateException("oOly kings can move to empty spaces");
      }
    } else {
      Card c2 = cardList.get(cardList.size() - 1);
      if (!c2.getColor().equals(c.getColor())) {
        if (c.getCardValue().getValue() + 1 == c2.getCardValue().getValue()) {
          cardList.add(c);
        } else {
          throw new IllegalStateException("Move not allowable, wrong number");
        }
      } else {
        throw new IllegalStateException("Move not allowable, wrong color");
      }
    }
  }

  /**
   * Moves the top card of the given pile to the requested foundation pile.
   *
   * @param srcPile        the 0-based index (from the left) of the pile to move a card
   * @param foundationPile the 0-based index (from the left) of the foundation pile to
   *                       place the card
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if either pile number is invalid
   * @throws IllegalStateException    if the source pile is empty or if the move is not
   *                                  allowable
   */
  @Override
  public void moveToFoundation(int srcPile, int foundationPile) {
    this.checkGameStatus();
    if (srcPile < 0 || foundationPile < 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (srcPile >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Invalid input");
    } else if (foundationPile >= this.foundationPile.size()) {
      throw new IllegalArgumentException("Invalid input");
    } else if (this.cascadePile.isEmpty()) {
      throw new IllegalArgumentException("Empty cascade pile");
    } else if (this.cascadePile.get(srcPile).isEmpty()) {
      throw new IllegalStateException("Empty src pile");
    } else {
      Card c = this.cascadePile.get(srcPile).get(this.cascadePile.get(srcPile).size() - 1);
      this.moveFoundationHelp(c, foundationPile);
      this.cascadePile.get(srcPile).remove(this.cascadePile.get(srcPile).size() - 1);
      int topCardIndex = this.cascadePile.get(srcPile).size() - 1;
      if (!this.cascadePile.get(srcPile).isEmpty()) {
        if (!this.cascadePile.get(srcPile).get(topCardIndex).getVisibility()) {
          this.cascadePile.get(srcPile).get(topCardIndex).setVisibility();
        }
      }
    }
  }

  /**
   * A helper method for the moveToFoundation/moveDrawToFoundation method
   * that adds the given Card to the foundation pile.
   *
   * @param c        the Card to be moved from the cascade pile or draw pile.
   * @param destPile the foundation pile index
   */
  void moveFoundationHelp(Card c, int destPile) {
    List<Card> cardList = this.foundationPile.get(destPile);
    if (cardList.isEmpty()) {
      if (c.toString().contains("A")) {
        cardList.add(c);
      } else {
        throw new IllegalStateException("Invalid move, no ace in foundation pile");
      }
    } else {
      Card c2 = cardList.get(cardList.size() - 1);

      if (c2.getCardSuit().sameSuit(c.getCardSuit())) {
        if (c.getCardValue().getValue() == c2.getCardValue().getValue() + 1) {
          cardList.add(c);
        } else {
          throw new IllegalStateException("Wrong number");
        }
      } else {
        throw new IllegalStateException("Wrong suit");
      }
    }
  }


  /**
   * Moves the topmost draw-card directly to a foundation pile.
   *
   * @param foundationPile the 0-based index (from the left) of the foundation pile to
   *                       place the card
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the foundation pile number is invalid
   * @throws IllegalStateException    if there are no draw cards or if the move is not
   *                                  allowable
   */
  @Override
  public void moveDrawToFoundation(int foundationPile) {
    this.checkGameStatus();
    if (foundationPile < 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (foundationPile >= this.foundationPile.size()) {
      throw new IllegalArgumentException("Invalid input");
    } else if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("Empty draw pile");
    } else {
      Card c = this.drawPile.get(0);
      this.moveFoundationHelp(c, foundationPile);
      this.drawPile.remove(0);
    }
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
    if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("no draw pile");
    }
    // if true, changes visibility of the card to be discarded to false
    this.drawPile.get(this.drawPile.size() - 1).setVisibility();

    // adds removed card to the end of the list
    this.drawPile.add(this.drawPile.remove(0));
    //this.drawPile.remove(this.drawPile.size() - 1);

    // change the new top card to visibility = true
    this.drawPile.get(this.drawPile.size() - 1).setVisibility();

  }

  /**
   * Returns the number of rows currently in the game.
   *
   * @return the height of the current table of cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumRows() {
    this.checkGameStatus();
    int max = -1000000;
    for (List<Card> pile : this.cascadePile) {
      if (pile.size() > max) {
        max = pile.size();
      }
    }
    return max;
  }

  /**
   * Returns the number of piles for this game.
   *
   * @return the number of piles
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumPiles() {
    this.checkGameStatus();
    return this.cascadePile.size();

  }

  /**
   * Returns the currently available draw cards.
   * There should be at most {@link KlondikeModel#getNumDraw} cards (the number
   * specified when the game started) -- there may be fewer, if cards have been removed.
   * NOTE: Users of this method should not modify the resulting list.
   *
   * @return the ordered list of available draw cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumDraw() {
    int count = 0;
    this.checkGameStatus();
    for (Card card : this.drawPile) {
      if (card.getVisibility()) {
        count++;
      }
    }
    return this.getDrawCards().size();
  }

  /**
   * Signal if the game is over or not.  A game is over if there are no more
   * possible moves to be made, or draw cards to be used (or discarded).
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public boolean isGameOver() {
    boolean result = false;
    this.checkGameStatus();


    for (int i = 0; i < this.getNumFoundations(); i++) {
      if (this.foundationPile.get(i).size() != this.getMaxNumber(this.drawPile)) {
        result = false;
        break;
      }
    }

    if (this.drawPile.isEmpty()) {
      result = true;
    }
    if (this.drawPile.isEmpty() && this.foundationPile.isEmpty()) {
      result = true;
    }
    else if (this.foundationPile.isEmpty()) {
      result = true;
    }

    return result;
  }

  /**
   * Return the current score, which is the sum of the values of the topmost cards
   * in the foundation piles.
   *
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getScore() {
    this.checkGameStatus();
    int numCards = 0;
    for (int i = 0; i < this.getNumFoundations(); i++) {
      for (int j = 0; j < this.foundationPile.get(i).size(); j++) {
        numCards += 1;
      }
    }
    return numCards;
  }

  /**
   * Returns the number of cards in the specified pile.
   *
   * @param pileNum the 0-based index (from the left) of the pile
   * @return the number of cards in the specified pile
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if pile number is invalid
   */
  @Override
  public int getPileHeight(int pileNum) {
    this.checkGameStatus();
    if (pileNum >= this.cascadePile.size()) {
      throw new IllegalArgumentException("invalid input");
    } else if (pileNum < 0) {
      throw new IllegalArgumentException("invalid input");
    } else {
      return this.cascadePile.get(pileNum).size();
    }
  }

  /**
   * Returns whether the card at the specified coordinates is face-up or not.
   *
   * @param pileNum column of the desired card (0-indexed from the left)
   * @param card    row of the desired card (0-indexed from the top)
   * @return whether the card at the given position is face-up or not
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public boolean isCardVisible(int pileNum, int card) {
    this.checkGameStatus();
    if (pileNum < 0 || card < 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (card >= this.getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Invalid card");
    } else if (pileNum >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Invalid pileNum");
    } else {
      for (int i = 0; i < this.cascadePile.size(); i++) {
        if (this.cascadePile.get(pileNum).get(card).getVisibility()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the card at the specified coordinates, if it is visible.
   * If no visible card available at that posiiton, throw an
   * illegal argument exception.
   * @param pileNum column of the desired card (0-indexed from the left)
   * @param card    row of the desired card (0-indexed from the top)
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public Card getCardAt(int pileNum, int card) {
    this.checkGameStatus();
    if (pileNum < 0 || card < 0) {
      throw new IllegalArgumentException("invalid inputs");
    } else if (pileNum >= this.cascadePile.size()) {
      throw new IllegalArgumentException("invalid pileNum");
    } else if (this.cascadePile.get(pileNum).isEmpty()) {
      throw new IllegalArgumentException("empty pile");
    }
    if (this.isCardVisible(pileNum, card)) {
      return this.cascadePile.get(pileNum).get(card);
    } else {
      throw new IllegalArgumentException("invisible card");
    }
  }

  /**
   * Returns the card at the top of the specified foundation pile.
   * If no card at that position return null.
   * @param foundationPile 0-based index (from the left) of the foundation pile
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the foundation pile number is invalid
   */
  @Override
  public Card getCardAt(int foundationPile) {
    this.checkGameStatus();
    if (foundationPile < 0) {
      throw new IllegalArgumentException();
    } else if (foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("invalid pileNum");
    } else if (this.foundationPile.get(foundationPile).isEmpty()) {
      return null;
    } else {
      return this.foundationPile.get(foundationPile).
              get(this.foundationPile.get(foundationPile).size() - 1);
    }
  }

  @Override
  public List<Card> getDrawCards() {
    List<Card> drawCards = new ArrayList<>();
    this.checkGameStatus();
    for (int i = 0; i < this.drawPile.size(); i++) {
      if (this.drawPile.get(i).getVisibility()) {
        drawCards.add(this.drawPile.get(i));
      }
    }
    return drawCards;
  }

  /**
   * Return the number of foundation piles in this game.
   *
   * @return the number of foundation piles
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumFoundations() {
    this.checkGameStatus();
    return this.foundationPile.size();
  }

  /**
   * Determines whether the game has started or not.
   *
   * @throws IllegalArgumentException if the game has not started yet.
   */
  protected void checkGameStatus() {
    if (!this.gameStarted) {
      throw new IllegalStateException("The game has not started yet");
    }
  }
}