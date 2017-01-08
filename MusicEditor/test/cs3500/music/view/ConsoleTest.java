package cs3500.music.view;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.MusicModel;
import cs3500.music.model.Pitch;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Console} class.
 */
public class ConsoleTest {

  /**
   * Tests building a console view with an empty model.
   *
   * @result should return an empty string
   */
  @Test
  public void testEmptyModel() {
    List<Pitch> pList = new ArrayList<Pitch>();
    MusicModel s = new MusicModel(pList, 0);
    Console c = new Console(s);
    assertEquals("", c.runConsole());
  }

  /**
   * Tests building a console view with a model.
   *
   * @result should return the model as a string
   */
  @Test
  public void testModelConstructorAndToString() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    List<Note> bList2 = new ArrayList<Note>();
    bList2.add(new Note(2));
    bList2.add(new Note(0));
    bList2.add(new Note(2));
    bList2.add(new Note(1));
    bList2.add(new Note(1));
    bList2.add(new Note(1));
    bList2.add(new Note(0));
    bList2.add(new Note(2));
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList.add(p);
    MusicModel s = new MusicModel(pList, 0);
    Console c = new Console(s);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1               \n"
            + "2  X    X    X  \n"
            + "3  |    |    |  \n"
            + "4  |    |    |  \n"
            + "5       |    |  \n"
            + "6               \n"
            + "7       X    X  ", c.runConsole());
  }

  /**
   * Tests building a console view with an invalid pitch.
   *
   * @result should throw an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorAndToStringException() {
    Pitch p = new Pitch(0, 99);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p);
    MusicModel m1 = new MusicModel(pList, 0);

    m1.toString();
  }

  /**
   * Tests combining two models and displaying them as a text view.
   *
   * @result should display the new model
   */
  @Test
  public void testCombineConsecutive() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    List<Note> bList2 = new ArrayList<Note>();
    bList2.add(new Note(0));
    bList2.add(new Note(0));
    bList2.add(new Note(2));
    bList2.add(new Note(1));
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p);
    pList.add(p2);
    pList2.add(p3);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelConsecutive(s2);
    Console c = new Console(s);
    assertEquals("   C0  C#0   D0\n"
            + " 0               \n"
            + " 1               \n"
            + " 2  X    X       \n"
            + " 3  |    |       \n"
            + " 4  |            \n"
            + " 5               \n"
            + " 6               \n"
            + " 7            X  \n"
            + " 8            |  \n"
            + " 9               ", c.runConsole());
  }

  /**
   * Tests building a console view of two models combined.
   *
   * @result should return the model as a text view
   */
  @Test
  public void testCombineSimultaneous() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    List<Note> bList2 = new ArrayList<Note>();
    bList2.add(new Note(2));
    bList2.add(new Note(0));
    bList2.add(new Note(2));
    bList2.add(new Note(1));
    bList2.add(new Note(1));
    bList2.add(new Note(1));
    bList2.add(new Note(0));
    bList2.add(new Note(2));
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList.add(p);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList, 0);
    s.combineMusicModelSimultaneous(s2);
    Console c = new Console(s);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1               \n"
            + "2  X    X    X  \n"
            + "3  |    |    |  \n"
            + "4  |    |    |  \n"
            + "5       |    |  \n"
            + "6               \n"
            + "7       X    X  ", c.runConsole());
  }
}
