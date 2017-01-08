package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Scrollable;
import javax.swing.JPanel;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Pitch;

/**
 * A java JPanel that contains a full rendering of the model.
 */
public class ConcreteGuiViewPanel extends JPanel implements Scrollable {
  protected IMusicModel model;
  private Graphics graphics;
  private int line;
  private List<Integer> previewSquare;

  /**
   * Constructs a ConcreteGuiViewPanel.
   *
   * @param model the model to build from
   * @param line the location of the red line that is used in a compositve view
   */
  public ConcreteGuiViewPanel(IMusicModel model, int line) {
    this.model = model;
    this.line = line;
    this.previewSquare = new ArrayList<Integer>();
    // Initialize the location of the preview square so that it starts offscreen
    previewSquare.add(-100);
    previewSquare.add(0);
    previewSquare.add(0);
  }

  @Override
  public void paintComponent(Graphics graphic) {
    this.graphics = graphic;
    this.beatCounter();
  }

  /**
   * Prints the beat counter at the top of each measure.
   */
  private void beatCounter() {
    int measures = (int) Math.ceil((double) this.model.maxBeats() / 16);
    for (int i = 0; i <= measures; i++) {
      graphics.drawString(Integer.toString(i * 16),
              40 + i * 320,
              20);
    }
    this.pitchValues();
  }

  /**
   * Prints all pitches and octaves from highest to lowest.
   */
  private void pitchValues() {
    Pitch lowest = this.model.findLowestAndHighestPitch().get(0);
    Pitch highest = this.model.findLowestAndHighestPitch().get(1);
    int rowHeight = 40;

    Pitch p = highest;
    while (p.getOctave() > lowest.getOctave()
            || (p.getPitch() >= lowest.getPitch() && p.getOctave() == lowest.getOctave())) {
      graphics.drawString(p.toString(),
                    0,
                    rowHeight);
      rowHeight += 20;
      if (p.getPitch() == 1) {
        p = new Pitch(12, p.getOctave() - 1);
      }
      else {
        p = new Pitch(p.getPitch() - 1, p.getOctave());
      }
    }
    this.renderPitches();
  }

  /**
   * Takes the notes from all pitches and renders them as squares, colored on whether or not
   * they are being played.
   */
  private void renderPitches() {
    List<Pitch> pitches = this.model.generateOctaves(this.model.findLowestAndHighestPitch());
    Collections.reverse(pitches);

    for (int a = 0;
         a < pitches.size();
         a++) {
      Pitch p = pitches.get(a);
      for (int i = 0; i < p.getNotes().size(); i++) {
        if (p.getNote(i).playing != 0) {
          if (p.getNote(i).playing == 1) {
            graphics.setColor(new Color(0, 255, 0));
          }
          else {
            graphics.setColor(new Color(0, 0, 0));
          }
          graphics.fillRect(40 + 20 * i, 25 + 20 * a, 20, 20);
        }
      }
    }
    this.buildBackground();
  }

  /**
   * Prints the measures as a grid.
   */
  private void buildBackground() {
    int measures = (int) Math.ceil(((double) this.model.maxBeats() / 4));
    Graphics2D g2 = (Graphics2D) graphics;
    graphics.setColor(new Color(36, 165, 237));
    g2.setStroke(new BasicStroke(1));
    for (int i = 0; i < measures; i ++) {
      for (int p = 0;
           p < this.model.generateOctaves(this.model.findLowestAndHighestPitch()).size();
           p ++) {
        g2.drawRect(40 + i * 80,25 + p * 20, 80, 20);
      }
    }
    this.renderPreviewBlock();
  }

  /**
   * Creates a preview square that will be used to preview the location of potential notes. When
   * grey, the square previews the location of a down note, and when light green it is previewing
   * the location of the duration. The square intentionally starts offscreen so that it can be
   * moved to the mouse location when a mouse button is pressed.
   */
  private void renderPreviewBlock() {
    if (this.previewSquare.get(2) == 1) {
      graphics.setColor(new Color(169, 169, 169));
    }
    else {
      graphics.setColor(new Color(147, 255, 137));
    }
    graphics.fillRect(41 + 20 * this.previewSquare.get(0),
            26 + 20 * this.previewSquare.get(1), 19, 19);
    if (line != -1) {
      this.renderLine();
    }
  }

  /**
   * Prints the measures as a grid.
   */
  private void renderLine() {
    Graphics2D g2 = (Graphics2D) graphics;
    g2.setColor(Color.RED);
    g2.setStroke(new BasicStroke(2));
    g2.drawLine(40 + this.line, 25, 40 + this.line,
            (this.model.generateOctaves(this.model.findLowestAndHighestPitch()).size() + 1)
                    * 20 + 5);
  }

  /**
   * Returns information about the location and type of the preview square.
   */
  public List<Integer> getPreviewNoteInformation() {
    return this.previewSquare;
  }

  /**
   * Moves the preview square to the location of the provided pitch and beat.
   */
  public void previewSquare(int pitch, int beat) {
    if (pitch == this.previewSquare.get(1) && beat == this.previewSquare.get(0)) {
      if (this.previewSquare.get(2) == 1) {
        this.previewSquare.set(2, 0);
      }
      else {
        this.previewSquare.set(2, 1);
      }
    }
    else {
      this.previewSquare.set(0, beat);
      this.previewSquare.set(1, pitch);
      this.previewSquare.set(2, 1);
    }
    this.revalidate();
    this.repaint();
  }

  /**
   * Updates the location of the red line.
   */
  public void updateLine(float i) {
    float f = (this.model.maxBeats() * 20) * i;
    this.line = (int) f;
    this.revalidate();
    this.repaint();
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 10;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 10;
  }
}