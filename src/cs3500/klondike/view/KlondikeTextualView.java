package cs3500.klondike.view;

import java.io.IOException;

import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A simple text-based rendering of the Klondike game.
 */
public class KlondikeTextualView implements TextView {
  private final KlondikeModel model;
  private  Appendable out;
  // ... any other fields you need

  /**
   * Constructs a KlondikeTextualView.
   * @param model A BasicKlondike instance used to render
   *              the Klondike game.
   */
  public KlondikeTextualView(KlondikeModel model) {
    this.model = model;
    this.out = new StringBuilder();
  }

  /**
   * Constructs a KlondikeTextualView with an Appendable.
   * @param model A BasicKlondike instance used to render the game.
   * @param out An Appendable used to communicate between the view and
   *            the controller.
   */
  public KlondikeTextualView(KlondikeModel model, Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("cannot be null");
    }
    else {
      this.out = out;
    }
    this.model = model;
  }

  /**
   * A String representation of the Klondike game.
   * @return A String of the Klondike game.
   */
  public String toString() {
    return this.getDrawPile() + "\n" + this.getFoundationPile() + "\n" + this.getCascadePile();
  }

  /**
   * Returns the String representation of the draw pile in
   * a game of Klondike.
   * @return A String rendering of the draw pile.
   */
  public String getDrawPile() {
    String d = "";
    d += "Draw: ";
    if (this.model.getDrawCards().isEmpty()) {
      d += this.model.getDrawCards();
    }
    if (this.model.getNumDraw() > 0) {
      for (int i = 0; i < this.model.getDrawCards().size(); i++) {
        d += this.model.getDrawCards().get(i).toString() + ", ";
      }
    }
    return d.substring(0, d.length() - 2);
  }

  /**
   * Returns the String representation of the foundation pile in
   * a game of Klondike.
   * @return A String rendering of the foundation pile.
   */
  public String getFoundationPile() {
    String f = "";
    int count = 0;
    try {
      for (int i = 0; i < 1; i++) {
        f += "Foundation" + ": ";
        for (int j = 0; j < this.model.getNumFoundations(); j++) {
          f +=  this.model.getCardAt(j).toString() + ", ";
          count++;

        }
      }
    }
    catch (Exception e) {
      for (int j = 0; j < this.model.getNumFoundations() - count; j++) {
        f += "<none>, ";
      }
      f = f.substring(0, f.length() - 2);
    }
    return f;
  }

  /**
   * Returns the String representation of the cascade pile in
   * a game of Klondike.
   * @return A String rendering of the cascade pile.
   */
  public String getCascadePile() {
    String c = "";

    for (int rows = 0; rows < model.getNumRows(); rows++) {
      for (int piles = 0; piles < model.getNumPiles(); piles++) {
        try {
          if (model.isCardVisible(piles, rows)) {
            c = c + " " + model.getCardAt(piles, rows).toString();
          }
          else {
            c += "  ?";
          }
        }
        catch (Exception e) {
          if (rows == 0) {
            c += "  X";
          }
          else {
            c += "   ";
          }
        }
      }
      c = c + "\n";
    }
    return c;
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() throws IOException {
    try {
      this.out.append(this.toString());
    }
    catch (IOException e) {
      throw new IOException("invalid message");
    }
  }

}



