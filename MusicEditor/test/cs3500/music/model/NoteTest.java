package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Note} class.
 */
public class NoteTest {

  /**
   * Tests building a note.
   *
   * @result the note is initialized with the correct information
   */
  @Test
  public void testConstructor() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    Pitch p = new Pitch(bList, 1, 3);
    assertEquals(3, p.getOctave());
    assertEquals(1, p.getPitch());
    assertEquals(bList, p.getNotes());
  }

  /**
   * Tests constructing an invalid note.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException() {
    Note n = new Note(0, 10, 128);
  }

  /**
   * Tests constructing an invalid note.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    Note n = new Note(0, 10, -1);
  }

  /**
   * Tests constructing an invalid note.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    Note n = new Note(-1, 10, 10);
  }

  /**
   * Tests constructing an invalid note.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException4() {
    Note n = new Note(3, 10, 10);
  }
}
