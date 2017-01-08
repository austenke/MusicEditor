package cs3500.music.controller;

import java.io.FileReader;

import java.awt.event.KeyEvent;
import java.util.Collections;

import java.util.List;

import cs3500.music.model.Pitch;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiViewInterface;
import cs3500.music.view.ViewBuilder;
import cs3500.music.view.ViewInterface;

import cs3500.music.model.Note;

import cs3500.music.view.CompositeView;

/**
 * Controller for a MusicModel.
 */
public class MusicController {
  IMusicModel model;
  ViewInterface view;

  /**
   * Constructor for MusicController, takes a model to use.
   *
   * @param model the model for the controller to use
   * @param view the ViewInterface to use
   */
  public MusicController(IMusicModel model, ViewInterface view) {
    this.model = model;
    this.view = view;
    if (view instanceof GuiViewInterface) {
      createKeyListener();
      createMouseListener();
    }
  }

  /**
   * Constructor for MusicController, takes a filename to build a model from.
   *
   * @param filename the name of the file to build a model from
   * @param view the string name of the view to use
   *
   * @throws IllegalStateException if the file cannot be opened and run
   */
  public MusicController(String filename, String view) {
    try {
      CompositionBuilder<IMusicModel> builder = new MusicModel.Builder();
      this.model = MusicReader.parseFile(new FileReader(filename), builder);
      ViewBuilder myView = new ViewBuilder();
      this.view = myView.run(view, model);
      if (this.view instanceof GuiViewInterface) {
        createKeyListener();
        createMouseListener();
      }
    }
    catch (Exception e) {
      throw new IllegalStateException("Controller is unable to open and run "
              + filename + ". Error message is: " + e.getMessage());
    }
  }

  /**
   * A key command function, uses the enter key to confirm and add a note to the model from
   * the view/controller.
   *
   * @throws IllegalArgumentException if the controller view is not a GuiViewInterface
   */
  public void addNote() {
    if (view instanceof  GuiViewInterface) {
      GuiViewInterface gViewInterface = (GuiViewInterface) view;

      List<Integer> previewLocation = gViewInterface.getPreviewNoteInformation();

      int x = previewLocation.get(0);
      int y = previewLocation.get(1);
      List<Pitch> l = model.generateOctaves(model.findLowestAndHighestPitch());
      Collections.reverse(l);

      if (x == -100) {
        throw new IllegalArgumentException("Please select a note location first.");
      } else if (y > l.size() || x > model.maxBeats()) {
        throw new IllegalArgumentException("That is an invalid location to place a note.");
      }

      Note n = new Note(previewLocation.get(2) + 1, 1, 110);

      this.model.addNote(l.get(y), x, n);

      gViewInterface.previewSquare(0, -100);

      if (this.view instanceof CompositeView) {
        ((CompositeView) this.view).updateMidi();
      }
    }
    else {
      throw new IllegalArgumentException("Controller needs a GuiViewInterface to run this.");
    }
  }

  /**
   * A mouse command function, removes the note at the location of the cursor.
   *
   * @throws IllegalArgumentException if the controller view is not a GuiViewInterface
   */
  public void removeNote(int x, int y) {
    if (view instanceof  GuiViewInterface) {
      GuiViewInterface gViewInterface = (GuiViewInterface) view;

      int beatLoc = ((20 * Math.round((x - 40) / 20)) / 20);
      int pitchLoc = ((20 * Math.round((y - 25) / 20)) / 20);

      List<Pitch> l = model.generateOctaves(model.findLowestAndHighestPitch());
      Collections.reverse(l);

      if (pitchLoc > l.size() || beatLoc > model.maxBeats()) {
        throw new IllegalArgumentException("That is an invalid location to remove a note.");
      }

      for (Pitch p : model.getPitches()) {
        if (p == l.get(pitchLoc)) {
          p.getNotes().set(beatLoc, new Note(0));
          int i = 1;
          while (p.getNotes().get(beatLoc + i).playing == 1) {
            p.getNotes().set(beatLoc + i, new Note(0));
            i++;
            if (beatLoc + i == p.getNotes().size()) {
              return;
            }
          }
        }
      }

      gViewInterface.run();
      gViewInterface.previewSquare(0, -100);

      if (view instanceof CompositeView) {
        ((CompositeView) view).updateMidi();
      }
    }
    else {
      throw new IllegalArgumentException("Controller needs a GuiViewInterface to run this.");
    }
  }

  /**
   * Renders a view of the model.
   */
  public void runView() {
    this.view.run();
  }

  /**
   * Creates a KeyboardHandler class, adds Runnables to initialize different key commands and then
   * attaches it to the view.
   *
   * @throws IllegalArgumentException if the controller view is not a GuiViewInterface
   */
  private void createKeyListener() {
    if (view instanceof  GuiViewInterface) {
      GuiViewInterface gViewInterface = (GuiViewInterface) view;
      KeyboardHandler keyHandle = new KeyboardHandler();

      // Adds a command to jump to the beginning of the song and play it with the home button.
      Runnable r = new Runnable() {
        public void run() {
          gViewInterface.beginComp();
        }
      };
      keyHandle.newRunnable("released", KeyEvent.VK_HOME, r);

      // Adds a command to jump to the end of the song with the end button.
      r = new Runnable() {
        public void run() {
          gViewInterface.endComp();
        }
      };
      keyHandle.newRunnable("released", KeyEvent.VK_END, r);

      // Adds a command to scroll to the left with the left arrow key.
      r = new Runnable() {
        public void run() {
          gViewInterface.scrollLeft();
        }
      };
      keyHandle.newRunnable("pressed", KeyEvent.VK_LEFT, r);

      // Adds a command to scroll to the right with the right arrow key.
      r = new Runnable() {
        public void run() {
          gViewInterface.scrollRight();
        }
      };
      keyHandle.newRunnable("pressed", KeyEvent.VK_RIGHT, r);

      // Adds a command to scroll up with the up arrow key.
      r = new Runnable() {
        public void run() {
          gViewInterface.scrollUp();
        }
      };
      keyHandle.newRunnable("pressed", KeyEvent.VK_UP, r);

      // Adds a command to scroll down with the down arrow key.
      r = new Runnable() {
        public void run() {
          gViewInterface.scrollDown();
        }
      };
      keyHandle.newRunnable("pressed", KeyEvent.VK_DOWN, r);

      // Adds a command to pause the view with the spacebar. Will not do anything if just GUI.
      r = new Runnable() {
        public void run() {
          gViewInterface.togglePause();
        }
      };
      keyHandle.newRunnable("released", KeyEvent.VK_SPACE, r);

      // Adds a command to adda note at the location of the preview with the enter key.
      r = new Runnable() {
        public void run() {
          addNote();
        }
      };
      keyHandle.newRunnable("released", KeyEvent.VK_ENTER, r);

      gViewInterface.addKeyHandler(keyHandle);
    }
    else {
      throw new IllegalArgumentException("Controller needs a GuiViewInterface to run this.");
    }
  }

  /**
   * Creates a MouseHandler class, adds Runnables to initialize different mouse commands and then
   * attaches it to the view.
   *
   * @throws IllegalArgumentException if the controller view is not a GuiViewInterface
   */
  private void createMouseListener() {
    if (view instanceof  GuiViewInterface) {
      GuiViewInterface gViewInterface = (GuiViewInterface) view;

      MouseHandler mouseHandle = new MouseHandler();

      Runnable r = new Runnable() {
        public void run() {
          int x = ((20 * Math.round(mouseHandle.getX() - 40) / 20) / 20);
          int y = ((20 * Math.round(mouseHandle.getY() - 25) / 20) / 20);
          if (x < model.maxBeats()
                  && y < model.generateOctaves(model.findLowestAndHighestPitch()).size()
                  && x >= 0
                  && y >= 0) {
            gViewInterface.previewSquare(y, x);
          }
        }
      };

      mouseHandle.setHandler("left", r);

      r = new Runnable() {
        public void run() {
          removeNote(mouseHandle.getX(), mouseHandle.getY());
        }
      };

      mouseHandle.setHandler("right", r);

      gViewInterface.addMouseHandler(mouseHandle);
    }
    else {
      throw new IllegalArgumentException("Controller needs a GuiViewInterface to run this.");
    }
  }
}
