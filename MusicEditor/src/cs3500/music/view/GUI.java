package cs3500.music.view;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.Point;

import javax.swing.JScrollPane;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import cs3500.music.model.IMusicModel;

import java.util.List;

/**
 * A java frame in Swing, functions as a GUI view class.
 */
public class GUI extends javax.swing.JFrame implements GuiViewInterface {
  private ConcreteGuiViewPanel panel;
  private JScrollPane scrollPane;
  private int maxBeats;

  /**
   * Takes a model, creates new GUI.
   */
  public GUI(IMusicModel model, int line) {
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    ConcreteGuiViewPanel main = new ConcreteGuiViewPanel(model, line);
    main.setPreferredSize(new Dimension(model.maxBeats() * 20 + 65,
            model.generateOctaves(model.findLowestAndHighestPitch()).size() * 20 + 45));
    JScrollPane scrollFrame = new JScrollPane(main);
    main.setAutoscrolls(true);
    this.add(scrollFrame);
    this.pack();
    scrollPane = scrollFrame;
    panel = main;
    maxBeats = model.maxBeats();
  }

  /**
   * Updates the location of the red line. Scrolls the view if the line is at the edge of the
   * screen.
   */
  public void updateLine(float i, boolean getPause) {
    panel.updateLine(i);
    Point p = scrollPane.getViewport().getViewPosition();
    // Calculates the current width of the screen to see if it needs to be scrolled
    double currentWidth = p.getX() + scrollPane.getViewport().getWidth();
    if (!getPause && (maxBeats * 20) * i + 40 > currentWidth) {
      scrollPane.getViewport().setViewPosition(new Point((int) currentWidth, (int) p.getY()));
    }
    else if (i < .05) {
      scrollPane.getViewport().setViewPosition(new Point(0, (int) p.getY()));
    }
  }

  @Override
  public void beginComp() {
    Point p = scrollPane.getViewport().getViewPosition();
    scrollPane.getViewport().setViewPosition(new Point(0, (int) p.getY()));
  }

  @Override
  public void endComp() {
    Point p = scrollPane.getViewport().getViewPosition();
    scrollPane.getViewport().setViewPosition(new Point((maxBeats * 20) + 40, (int) p.getY()));
  }

  @Override
  public void scrollLeft() {
    Point p = scrollPane.getViewport().getViewPosition();
    if (p.getX() != 0) {
      scrollPane.getViewport().setViewPosition(new Point((int) p.getX() - 20, (int) p.getY()));
    }
  }

  @Override
  public void scrollRight() {
    Point p = scrollPane.getViewport().getViewPosition();
    scrollPane.getViewport().setViewPosition(new Point((int) p.getX() + 20, (int) p.getY()));
  }

  @Override
  public void scrollUp() {
    Point p = scrollPane.getViewport().getViewPosition();
    if (p.getY() != 0) {
      scrollPane.getViewport().setViewPosition(new Point((int) p.getX(), (int) p.getY() - 20));
    }
  }

  @Override
  public void scrollDown() {
    Point p = scrollPane.getViewport().getViewPosition();
    scrollPane.getViewport().setViewPosition(new Point((int) p.getX(), (int) p.getY() + 20));
  }

  @Override
  public void togglePause() {
    return;
  }

  @Override
  public void run() {
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1400, 800);
  }

  @Override
  public void addKeyHandler(KeyListener k) {
    this.addKeyListener(k);
  }

  @Override
  public void addMouseHandler(MouseListener m) {
    panel.addMouseListener(m);
  }

  @Override
  public void removeMouseHandler(MouseListener m) {
    this.removeMouseListener(m);
  }

  @Override
  public void previewSquare(int pitch, int beat) {
    panel.previewSquare(pitch, beat);
  }

  @Override
  public List<Integer> getPreviewNoteInformation() {
    return panel.getPreviewNoteInformation();
  }
}
