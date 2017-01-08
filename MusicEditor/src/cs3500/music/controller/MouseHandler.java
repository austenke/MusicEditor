package cs3500.music.controller;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * MouseListener class that can update itself with Runnables to run at a specified mouse action.
 */
public class MouseHandler implements MouseListener {
  private Runnable left;
  private Runnable middle;
  private Runnable right;
  private int x;
  private int y;

  /**
   * Function to update the MouseHandler runnables for different given mouse actions.
   *
   * @param button the mouse action needed to run the Runnable
   * @param r The Runnable to run when the specified action occurs
   *
   * @throws IllegalArgumentException if the function is provided an invalid mouse action
   */
  public void setHandler(String button, Runnable r) {
    switch (button) {
      case "left":
        this.left = r;
        break;
      case "middle":
        this.middle = r;
        break;
      case "right":
        this.right = r;
        break;
      default:
        throw new IllegalArgumentException("Please give a valid mouse action.");
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    return;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    return;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Update x and y fields with current location of mouse
    this.x = e.getX();
    this.y = e.getY();
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        if (left != null) {
          left.run();
        }
        break;
      case MouseEvent.BUTTON2:
        if (middle != null) {
          middle.run();
        }
        break;
      case MouseEvent.BUTTON3:
        if (right != null) {
          right.run();
        }
        break;
      default:
        return;
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    return;
  }

  @Override
  public void mouseExited(MouseEvent e) {
    return;
  }

  /**
   * Returns the x location of the mouse.
   *
   * @return the x coordinate of the mouse as an integer
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the x location of the mouse.
   *
   * @return the x coordinate of the mouse as an integer
   */
  public int getY() {
    return y;
  }
}
