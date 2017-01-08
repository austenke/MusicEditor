package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MouseHandler} class.
 */
public class MouseHandlerTest {

  boolean test;

  /**
   * Tests the ability of the mouse handler to receive and run Runnables for different mouse
   * functions by building a Runnable that changes the value of a boolean and testing to see
   * if the boolean was changed. This test is specifically for the mouseReleased method as the
   * other two mouse functions are not used in MouseHandler. This test is for a left mouse click.
   *
   * @result the mouse handler will successfully run the given Runnables
   */
  @Test
  public void testSetHandlerLeft() {
    MouseHandler mouseHandle = new MouseHandler();
    test = false;

    assertEquals(test, false);

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    mouseHandle.setHandler("left", r);
    MouseEvent m = new MouseEvent(new JFrame(), 0, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
    mouseHandle.mouseReleased(m);

    assertEquals(true, test);
  }

  /**
   * Tests the ability of the mouse handler to receive and run Runnables for different mouse
   * functions by building a Runnable that changes the value of a boolean and testing to see
   * if the boolean was changed. This test is specifically for the mouseReleased method as the
   * other two mouse functions are not used in MouseHandler. This test is for a right mouse click.
   *
   * @result the mouse handler will successfully run the given Runnables
   */
  @Test
  public void testSetHandlerRight() {
    MouseHandler mouseHandle = new MouseHandler();
    test = false;

    assertEquals(test, false);

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    mouseHandle.setHandler("right", r);
    MouseEvent m = new MouseEvent(new JFrame(), 0, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON3);
    mouseHandle.mouseReleased(m);

    assertEquals(true, test);
  }

  /**
   * Tests using the handler with an invalid mouse function, should throw an exception.
   *
   * @result the mouse handler will successfully throw an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetHandlerException() {
    MouseHandler mouseHandle = new MouseHandler();

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    mouseHandle.setHandler("blarg", r);
  }
}
