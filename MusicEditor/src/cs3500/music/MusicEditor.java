package cs3500.music;

import cs3500.music.controller.MusicController;

public final class MusicEditor {

  /**
   * Main class used to take arguments that are passed to the controller.
   */
  public static void main(String[] args) {
    String filename = "";
    String mode = "";
    if (args.length >= 2) {
      filename = args[0];
      mode = args[1];
    }
    else {
      throw new IllegalArgumentException("Must provide a file name and a view type.");
    }
    MusicController controller = new MusicController(filename, mode);
    controller.runView();
  }
}