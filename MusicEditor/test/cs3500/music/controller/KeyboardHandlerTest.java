package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link KeyboardHandler} class.
 */
public class KeyboardHandlerTest {

  boolean test;

  /**
   * Tests the ability of the keyboard handler to receive and run Runnables at different keys by
   * building a Runnable that changes the value of a boolean and testing to see if the boolean was
   * changed. This test is specifically for the keyPressed method.
   *
   * @result the keyboard handler will successfully run the given Runnables
   */
  @Test
  public void testNewRunnablePressed() {
    KeyboardHandler keyHandle = new KeyboardHandler();
    test = false;

    assertEquals(test, false);

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    keyHandle.newRunnable("pressed", KeyEvent.VK_HOME, r);
    KeyEvent key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_HOME);
    keyHandle.keyPressed(key);

    assertEquals(test, true);

    test = false;

    assertEquals(test, false);

    keyHandle.newRunnable("pressed", KeyEvent.VK_A, r);
    key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_A);
    keyHandle.keyPressed(key);

    assertEquals(test, true);

    test = false;

    assertEquals(test, false);

    keyHandle.newRunnable("pressed", KeyEvent.VK_Z, r);
    key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_Z);
    keyHandle.keyPressed(key);

    assertEquals(test, true);
  }

  /**
   * Tests the ability of the keyboard handler to receive and run Runnables at different keys by
   * building a Runnable that changes the value of a boolean and testing to see if the boolean was
   * changed. This test is specifically for the keyTyped method.
   *
   * @result the keyboard handler will successfully run the given Runnables
   */
  @Test
  public void testNewRunnableTyped() {
    KeyboardHandler keyHandle = new KeyboardHandler();
    test = false;

    assertEquals(test, false);

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    keyHandle.newRunnable("typed", KeyEvent.VK_HOME, r);
    KeyEvent key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_HOME);
    keyHandle.keyTyped(key);

    assertEquals(test, true);

    test = false;

    assertEquals(test, false);

    keyHandle.newRunnable("typed", KeyEvent.VK_A, r);
    key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_A);
    keyHandle.keyTyped(key);

    assertEquals(test, true);

    test = false;

    assertEquals(test, false);

    keyHandle.newRunnable("typed", KeyEvent.VK_Z, r);
    key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_Z);
    keyHandle.keyTyped(key);

    assertEquals(test, true);
  }

  /**
   * Tests the ability of the keyboard handler to receive and run Runnables at different keys by
   * building a Runnable that changes the value of a boolean and testing to see if the boolean was
   * changed. This test is specifically for the keyReleased method.
   *
   * @result the keyboard handler will successfully run the given Runnables
   */
  @Test
  public void testNewRunnableReleased() {
    KeyboardHandler keyHandle = new KeyboardHandler();
    test = false;

    assertEquals(test, false);

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    keyHandle.newRunnable("released", KeyEvent.VK_HOME, r);
    KeyEvent key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_HOME);
    keyHandle.keyReleased(key);

    assertEquals(test, true);

    test = false;

    assertEquals(test, false);

    keyHandle.newRunnable("released", KeyEvent.VK_A, r);
    key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_A);
    keyHandle.keyReleased(key);

    assertEquals(test, true);

    test = false;

    assertEquals(test, false);

    keyHandle.newRunnable("released", KeyEvent.VK_Z, r);
    key = new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_Z);
    keyHandle.keyReleased(key);

    assertEquals(test, true);
  }

  /**
   * Tests using the handler with an invalid key function, should throw an exception.
   *
   * @result the key handler will successfully throw an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetHandlerException() {
    KeyboardHandler keyHandle = new KeyboardHandler();

    Runnable r = new Runnable() {
      public void run() {
        test = true;
      }
    };

    keyHandle.newRunnable("blarg", KeyEvent.VK_Z, r);
  }
}
