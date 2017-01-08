package cs3500.music.view;

import java.awt.event.MouseListener;
import java.awt.event.KeyListener;

import cs3500.music.model.IMusicModel;

import java.util.Timer;
import java.util.TimerTask;

import java.util.List;

/**
 * A view class for a combination of a GUI view and a Midi view.
 */
public class CompositeView implements GuiViewInterface {
  GUI guiView;
  Midi midiView;
  IMusicModel model;

  /**
   * Constructor for CompositeView, initializes the fields.
   */
  public CompositeView(IMusicModel model) {
    this.model = model;
    this.midiView = new Midi(model, false);
    this.guiView = new GUI(model, 0);
  }

  @Override
  public void addMouseHandler(MouseListener m) {
    this.guiView.addMouseHandler(m);
  }

  @Override
  public void removeMouseHandler(MouseListener m) {
    this.guiView.removeMouseHandler(m);
  }

  @Override
  public void addKeyHandler(KeyListener k) {
    this.guiView.addKeyHandler(k);
  }

  @Override
  public void togglePause() {
    if (midiView.songCompletion() == 1) {
      guiView.beginComp();
      midiView.beginComp();
    }
    else {
      midiView.togglePause();
    }
  }

  @Override
  public void beginComp() {
    guiView.beginComp();
    midiView.beginComp();
  }

  @Override
  public void endComp() {
    guiView.endComp();
    midiView.endComp();
  }

  @Override
  public void scrollRight() {
    guiView.scrollRight();
  }

  @Override
  public void scrollLeft() {
    guiView.scrollLeft();
  }

  @Override
  public void scrollUp() {
    guiView.scrollUp();
  }

  @Override
  public void scrollDown() {
    guiView.scrollDown();
  }

  @Override
  public void previewSquare(int pitch, int beat) {
    guiView.previewSquare(pitch, beat);
  }

  @Override
  public List<Integer> getPreviewNoteInformation() {
    return guiView.getPreviewNoteInformation();
  }

  /**
   * For use when adding notes to a model, updates the midi with the new notes.
   */
  public void updateMidi() {
    this.midiView.pause();
    this.midiView = new Midi(this.model, false);
    this.midiView.run();
  }

  /**
   * Runs both views, uses a timer that wakes up regularly to update the GUI with the progression
   * of the midi.
   */
  public void run() {
    try {
      guiView.run();
      midiView.run();
      TimerTask timerTask = new TimerTask() {
        public void run() {
          guiView.updateLine(midiView.songCompletion(), midiView.getPause());
        }
      };
      Timer t = new Timer();
      t.schedule(timerTask, 0, 2);
    }
    catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
