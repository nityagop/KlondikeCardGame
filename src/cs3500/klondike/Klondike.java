package cs3500.klondike;

import java.io.InputStreamReader;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

/**
 * A class the represents the driver of this game of klondike.
 * Includes a main method which is responsible for recieving user
 * inputs and making the game playable. I chose to make my
 * playable instance of a klondike game with a deck that is not
 * shuffled.
 */
public class Klondike {

  /**
   * The main method of this class that is responsible for
   * making the game of Klondike playable. Allows the user
   * to input their desired type of Klondike.
   *
   * @param args The command line arguments that allow the game
   *             to start.
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    KlondikeController controller = new KlondikeTextualController(rd, ap);
    if (rd.toString().isEmpty() || args.length == 0 || args[0].isEmpty()) {
      throw new IllegalArgumentException("Cannot have empty input");
    } else {
      int numPiles = 7;
      int numDraw = 3;
      int numRedraws = 2;
      KlondikeModel m;

      if (args[0].contains("limited")) {
        if (args.length == 2) {
          try {
            numRedraws = Integer.parseInt(args[1]);
            if (numRedraws < 0) {
              throw new IllegalArgumentException();
            }
          }
          catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for numRedrawsAllowed");
          }
        }
      }

      switch (args[0]) {
        case "basic":
          try {
            if (args.length == 3) {
              numPiles = Integer.parseInt(args[1]);
              numDraw = Integer.parseInt(args[2]);
            } else if (args.length == 2) {
              numPiles = Integer.parseInt(args[1]);
            }
            m = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
            controller.playGame(m, m.getDeck(), false, numPiles, numDraw);
          } catch (Exception e) {
            System.out.print("Score: 0");
            return;
          }
          break;
        case "limited":
          if (args.length == 1) {
            throw new IllegalArgumentException("Must have limited R");
          }
          else {
            try {
              if (args.length == 4) {
                numRedraws = Integer.parseInt(args[1]);
                numPiles = Integer.parseInt(args[2]);
                numDraw = Integer.parseInt(args[3]);
              } else if (args.length == 3) {
                numRedraws = Integer.parseInt(args[1]);
                numPiles = Integer.parseInt(args[2]);
              }
              else if (args.length == 2) {
                try {
                  numRedraws = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException e) {
                  throw new IllegalArgumentException();
                }
              }
              m = new LimitedDrawKlondike(numRedraws);
              controller.playGame(m, m.getDeck(), false, numPiles, numDraw);
            }
            catch (Exception e) {
              System.out.print("Score: 0");
              return;
            }
          }
          break;
        case "whitehead":
          try {
            if (args.length == 3) {
              numPiles = Integer.parseInt(args[1]);
              numDraw = Integer.parseInt(args[2]);
            } else if (args.length == 2) {
              numPiles = Integer.parseInt(args[1]);
            }
            m = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
            controller.playGame(m, m.getDeck(), false, numPiles, numDraw);
          } catch (Exception e) {
            System.out.print("Score: 0");
            return;
          }
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
  }
}


