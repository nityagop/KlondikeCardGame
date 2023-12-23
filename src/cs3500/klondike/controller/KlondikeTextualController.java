package cs3500.klondike.controller;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.view.KlondikeTextualView;
import cs3500.klondike.view.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A class that represents the implementation of the KlondikeController.
 * It is responsible for reading in the user input and outputting.
 * the deck of cards as the user plays a game of Klondike.
 */
public class KlondikeTextualController implements cs3500.klondike.controller.KlondikeController {

  private final Readable input;
  private final Appendable output;


  /**
   * Constructs a KlondikeTextualController.
   *
   * @param r A Readable used to gather user input.
   * @param a An Appendable to handle user output.
   * @throws IllegalArgumentException if either the Readable or Appendable is null.
   */
  public KlondikeTextualController(Readable r, Appendable a) throws IllegalArgumentException {
    if (r == null || a == null) {
      throw new IllegalArgumentException("Cannot have null values");
    }
    this.input = r;
    this.output = a;
  }

  /**
   * The primary method for beginning and playing a game.
   *
   * @param model The game of solitaire to be played
   * @param deck The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numPiles How many piles should be in the initial deal
   * @param numDraw How many draw cards should be visible
   * @throws IllegalArgumentException if the model is null
   * @throws IllegalStateException if the game cannot be started,
   *          or if the controller cannot interact with the player.
   */
  @Override
  public void playGame(KlondikeModel model, List<Card> deck, boolean shuffle,
                       int numPiles, int numDraw) {

    TextView tv = new KlondikeTextualView(model, this.output);
    if (deck == null || model == null) {
      throw new IllegalArgumentException("Cannot have null values");
    }
    Scanner reader = new Scanner(this.input);

    try {
      model.startGame(deck, shuffle, numPiles, numDraw);
    } catch (Exception ioe) {
      try {
        this.output.append("Could not start game");
      } catch (IOException e) {
        throw new IllegalStateException("could not start game");
      }
    }

    try {
      while (!model.isGameOver() && reader.hasNext()) {
        try {
          tv.render();
          this.output.append("\n");
        } catch (Exception e) {
          throw new IllegalStateException("could not render");
        }

        moveCards(model, reader, tv);

        if (model.isGameOver()) {
          if (model.getScore() == deck.size()) {
            try {
              this.output.append("You win!\n");
            } catch (IOException e) {
              throw new IllegalArgumentException("game quit");
            }
            break;
          } else {
            try {
              this.output.append("Game over. ");
              this.output.append("Score:" + " ").append(String.valueOf(model.getScore()))
                      .append("\n");
            } catch (IOException e) {
              try {
                this.output.append("Game quit!\nState of game when quit:\n");
                tv.render();
                this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
              } catch (IOException ex) {
                throw new IllegalStateException("No more inputs");
              }
            }
          }
        }
      }
    }
    catch (Exception e) {
      throw new IllegalStateException("No more inputs");
    }
  }



  /**
   * A helper method for the playGame method. Uses a switch statement to
   * move the cards based on the given user input.
   *
   * @param model A BasicKlondike object, to be played.
   * @param s     A Readable scanner for user input.
   */
  private void moveCards(KlondikeModel model, Scanner s, TextView tv) {

    try {
      while (s.hasNext()) {

        if (model.isGameOver()) {
          break;
        }
        String move = s.next();

        switch (move) {
          case "q":
          case "Q":
            try {
              this.output.append("Game quit!\nState of game when quit:\n");
              tv.render();
              this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
            } catch (IOException ex) {
              throw new IllegalStateException("Game not able to quit");
            }
            break;
          case "mpp":
            try {

              int srcPile = Integer.parseInt(getNextValue(s, tv, model));
              int numCards = Integer.parseInt(getNextValue(s, tv, model));
              int destPile = Integer.parseInt(getNextValue(s, tv, model));

              if (srcPile < 0 || numCards < 0 || destPile < 0) {
                throw new IllegalStateException();
              }

              try {
                if (srcPile == 0 && destPile == 0) {
                  model.movePile(srcPile, numCards, destPile);
                  tv.render();
                } else if (srcPile == 0) {
                  model.movePile(srcPile, numCards, destPile - 1);
                  tv.render();
                } else if (destPile == 0) {
                  model.movePile(srcPile, numCards, destPile - 1);
                  tv.render();
                } else {
                  model.movePile(srcPile - 1, numCards, destPile - 1);
                  tv.render();
                }
              } catch (Exception e) {
                this.output.append("Invalid move. Play again").append(e.getMessage()).append("\n");
                tv.render();
                this.output.append("\n");
              }
            } catch (Exception e) {
              try {
                this.output.append("Game quit!\nState of game when quit:\n");
                tv.render();
                this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
              } catch (IOException ioe) {
                this.renderQuitGame(s.next(), tv, model);
              }
            }
            break;
          case "md":
            try {
              try {
                int cascadePile = Integer.parseInt(getNextValue(s, tv, model));

                if (cascadePile < 0) {
                  throw new IllegalStateException();
                }

                if (cascadePile == 0) {
                  model.moveDraw(cascadePile);
                } else {
                  model.moveDraw(cascadePile - 1);
                }
                tv.render();
              } catch (Exception e) {
                this.output.append("Invalid move. Play again.").append(e.getMessage()).append("\n");
                tv.render();
                this.output.append("\n");
              }
            } catch (Exception e) {
              try {
                this.output.append("Game quit!\nState of game when quit:\n");
                tv.render();
                this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
              } catch (IOException ioe) {
                this.renderQuitGame(s.next(), tv, model);
              }
            }
            break;
          case "mdf":
            try {
              try {
                int foundationPile = Integer.parseInt(getNextValue(s, tv, model));

                if (foundationPile < 0) {
                  throw new IllegalStateException();
                }
                if (foundationPile == 0) {
                  model.moveDrawToFoundation(foundationPile);
                  tv.render();
                } else {
                  model.moveDrawToFoundation(foundationPile - 1);
                  tv.render();
                }
              } catch (Exception e) {
                this.output.append("Invalid move. Play again.").append(e.getMessage()).append("\n");
                tv.render();
                this.output.append("\n");
              }
            } catch (Exception e) {
              try {
                this.output.append("Game quit!\nState of game when quit:\n");
                tv.render();
                this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
              } catch (IOException ex) {
                throw new IllegalStateException("game quit went wrong");
              }
            }
            break;
          case "mpf":
            try {
              try {
                int cascadeSource = Integer.parseInt(getNextValue(s, tv, model));
                int foundationDest = Integer.parseInt(getNextValue(s, tv, model));

                if (cascadeSource < 0 || foundationDest < 0) {
                  throw new IllegalStateException();
                }
                if (cascadeSource == 0) {
                  model.moveToFoundation(cascadeSource, foundationDest - 1);
                } else {
                  model.moveToFoundation(cascadeSource - 1, foundationDest - 1);
                }
                tv.render();
              } catch (Exception e) {
                this.output.append("Invalid move. Play again.").append(e.getMessage()).append("\n");
                tv.render();
                this.output.append("\n");
              }
            } catch (Exception e) {
              try {
                this.output.append("Game quit!\nState of game when quit:\n");
                tv.render();
                this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
              } catch (IOException ex) {
                throw new IllegalStateException("game quit went wrong");
              }
            }
            break;
          case "dd":
            if (model.getDrawCards().size() == 1) {
              this.output.append("Invalid move. Play again.").append("\n");
              tv.render();
              this.output.append("\n");
            }
            model.discardDraw();
            try {
              tv.render();

            } catch (Exception e) {
              try {
                this.output.append("Invalid move. Play again.").append(e.getMessage()).append("\n");
                tv.render();
                this.output.append("\n");
              } catch (IOException ioe) {
                throw new IllegalArgumentException(ioe.getMessage());
              }
            }
            break;
          default:
            try {
              this.output.append("Invalid move. Play again.\n");
              tv.render();
              this.output.append("\n");
            } catch (IOException ex) {
              throw new IllegalArgumentException("Not integers");
            }
            break;
        }
      }
    }
    catch (IllegalStateException ise) {
      throw new IllegalStateException("No more inputs");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Gets the next value of the user input while determining
   * whether it is an integer, invalid input, or a "q"/"Q".
   *
   * @param s     The scanner that has the user input.
   * @param tv    The TextView that is appended if the input is quit.
   * @param model The KlondikeModel instance being played.
   * @return A string of the next valid input.
   */
  private String getNextValue(Scanner s, TextView tv, KlondikeModel model) {
    boolean goloop = true;
    String result = "";
    while (goloop) {
      if (s.hasNextInt()) {
        result = s.next();
        goloop = false;
      }
      else {
        if (s.hasNext()) {
          result = s.next();
        } else {
          this.renderQuitGame(s.next(), tv, model);
        }
      }
    }
    return result;
  }

  /**
   * Creates the output for when a game is quit.
   *
   * @param s     The given string input to determine whether the game
   *              is being quit.
   * @param tv    The given TextView to render the board.
   * @param model The given KlondikeModel to get the final score of the
   *              game
   */
  private void renderQuitGame(String s, TextView tv, KlondikeModel model) {
    if (s.equals("q") || s.equals("Q")) {
      try {
        this.output.append("Game quit!\nState of game when quit:\n");
        tv.render();
        this.output.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
      } catch (IOException ex) {
        throw new IllegalStateException("game quit went wrong");
      }
    } else {
      throw new IllegalStateException("No quit in game");
    }
  }
}




