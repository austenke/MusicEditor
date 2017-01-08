package cs3500.music.controller;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.HashMap;

/**
 * Handler for keyboard events.
 */
public class KeyboardHandler implements KeyListener {
  private Map<Integer, Runnable> typed;
  private Map<Integer, Runnable> pressed;
  private Map<Integer, Runnable> released;

  /**
   * Constructor for KeyboardHandler, initializes the fields.
   */
  public KeyboardHandler() {
    this.typed = new HashMap<Integer, Runnable>();
    this.pressed = new HashMap<Integer, Runnable>();
    this.released = new HashMap<Integer, Runnable>();
  }

  /**
   * Function to update the KeyboardHandler runnables for different given keys.
   *
   * @param map the key function to run the Runnable at
   * @param key the keycode of the key to run the Runnable at when pressed
   * @param r The Runnable to run when the specified action occurs
   *
   * @throws IllegalArgumentException if the function is provided an invalid keyboard
   */
  public void newRunnable(String map, int key, Runnable r) {
    switch (map) {
      case "typed":
        this.typed.put(key, r);
        break;
      case "pressed":
        this.pressed.put(key, r);
        break;
      case "released":
        this.released.put(key, r);
        break;
      default:
        throw new IllegalArgumentException("Must provide a valid string to newRunnable.");
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    int code = e.getKeyCode();
    if (typed.containsKey(code)) {
      typed.get(code).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (pressed.containsKey(code)) {
      pressed.get(code).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (released.containsKey(code)) {
      released.get(code).run();
    }
  }
}
