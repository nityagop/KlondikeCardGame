package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A Factory class for the KlondikeModel. Responsible for creating
 * a model out of the three: basic, limited, or whitehead.
 */
public class KlondikeCreator {

  /**
   * An enum to represent the type of klondike that will be played.
   * Either basic, limited, or whitehead.
   */
  public enum GameType {
    BASIC, LIMITED, WHITEHEAD;
  }

  /**
   * The method responsible for determining model to create based
   * on the given GameType.
   * @param type The enum representing either a basic, limited, or whitehead game.
   * @return A new instance KlondikeModel based on the user input.
   */
  public static KlondikeModel create(GameType type) {
    try {
      switch (type) {
        case BASIC:
          return new BasicKlondike();
        case LIMITED:
          return new LimitedDrawKlondike();
        case WHITEHEAD:
          return new WhiteheadKlondike();
        default:
          throw new IllegalArgumentException("wrong game");
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException();
    }
  }
}
