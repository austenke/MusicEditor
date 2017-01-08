package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import java.util.List;

/**
 * Works as a ViewInterface, but contains methods only to be used by GUI.
 */
public interface GuiViewInterface extends ViewInterface {
  /**
   * Run the view's model from the beginning of the song.
   */
  void run();

  /**
   * Adds a mouse handler to the GUI.
   *
   * @param listener the MouseListener to add
   */
  void addMouseHandler(MouseListener listener);

  /**
   * Removes a mouse handler from the GUI.
   */
  void removeMouseHandler(MouseListener m);

  /**
   * Adds a key handler to the GUI.
   *
   * @param listener the KeyListener to add
   */
  void addKeyHandler(KeyListener listener);

  /**
   * Only applies to a view with a midi component, pauses or unpauses the view as it is playing.
   * Restarts the song if it is at the end.
   */
  void togglePause();

  /**
   * Resets the view to the beginning.
   */
  void beginComp();

  /**
   * Moves the view to the end.
   */
  void endComp();

  /**
   * Scrolls the view to the left.
   */
  void scrollLeft();

  /**
   * Scrolls the view to the right.
   */
  void scrollRight();

  /**
   * Scrolls the view upwards.
   */
  void scrollUp();

  /**
   * Scrolls the view down.
   */
  void scrollDown();

  /**
   * Gets the location and note type of the previewed square.
   */
  List<Integer> getPreviewNoteInformation();

  /**
   * Function to preview a note on a mouse click.
   */
  void previewSquare(int pitch, int beat);
}
