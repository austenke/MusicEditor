package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Pitch} class.
 */
public class PitchTest {

  /**
   * Tests constructing and getting information from a pitch.
   *
   * @result the pitch builds and returns the given inputs
   */
  @Test
  public void testConstructorAndGetMethods() {
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
   * Tests constructing and getting information from a pitch again.
   *
   * @result the pitch builds and returns the given inputs
   */
  @Test
  public void testConstructorAndGetMethods2() {
    List<Integer> bList = new ArrayList<Integer>();
    Pitch p = new Pitch(1, 3);
    assertEquals(3, p.getOctave());
    assertEquals(1, p.getPitch());
    assertEquals(bList, p.getNotes());
  }

  /**
   * Tests constructing an invalid pitch.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException() {
    Pitch p = new Pitch(0, 3);
  }

  /**
   * Tests constructing an invalid pitch.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    Pitch p = new Pitch(13, 3);
  }

  /**
   * Tests constructing an invalid pitch.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    Pitch p = new Pitch(12, -1);
  }

  /**
   * Tests constructing an invalid pitch.
   *
   * @result the class throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException4() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(10));
    Pitch p = new Pitch(bList, 12, 0);
  }

  /**
   * Tests the isPlaying function with different notes.
   *
   * @result the pitch correctly returns whether or not a note is playing
   */
  @Test
  public void testIsPlaying() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    Pitch p = new Pitch(bList, 1, 3);
    assertEquals(2, p.getNote(2).playing);
    assertEquals(1, p.getNote(3).playing);
    assertEquals(1, p.getNote(4).playing);
  }

  /**
   * Tests the addNote function with different notes.
   *
   * @result the pitch correctly adds the given notes
   */
  @Test
  public void testAddNote() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(6, 0, 0, 0);
    assertEquals(new Note(0).playing, p.getNote(5).playing);
    assertEquals(2, p.getNote(6).playing);
    assertEquals(0, p.getNote(7).playing);
  }

  /**
   * Tests the addNote function with different notes again.
   *
   * @result the pitch correctly adds the given notes again
   */
  @Test
  public void testAddNote2() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(2, 0, 0, 0);
    assertEquals(0, p.getNote(1).playing);
    assertEquals(2, p.getNote(2).playing);
    assertEquals(0, p.getNote(3).playing);
  }

  /**
   * Tests the addNote function with different notes again.
   *
   * @result the pitch correctly adds the given notes again
   */
  @Test
  public void testAddNote3() {
    List<Note> bList = new ArrayList<Note>();
    bList.add(new Note(0));
    bList.add(new Note(0));
    bList.add(new Note(2));
    bList.add(new Note(1));
    bList.add(new Note(1));
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(2, 4, 0, 0);
    assertEquals(0, p.getNote(1).playing);
    assertEquals(2, p.getNote(2).playing);
    assertEquals(1, p.getNote(3).playing);
    assertEquals(1, p.getNote(4).playing);
    assertEquals(1, p.getNote(5).playing);
    assertEquals(1, p.getNote(6).playing);
    assertEquals(0, p.getNote(7).playing);
  }

  /**
   * Tests the addNote function with different notes again.
   *
   * @result the pitch correctly adds the given notes again
   */
  @Test
  public void testAddNote4() {
    List<Note> bList = new ArrayList<Note>();
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(10, 4, 0, 0);
    assertEquals(0, p.getNote(3).playing);
    assertEquals(0, p.getNote(9).playing);
    assertEquals(2, p.getNote(10).playing);
    assertEquals(1, p.getNote(11).playing);
    assertEquals(1, p.getNote(12).playing);
    assertEquals(1, p.getNote(13).playing);
    assertEquals(1, p.getNote(14).playing);
    assertEquals(0, p.getNote(15).playing);
  }

  /**
   * Tests the equals function of Pitch.
   *
   * @result the pitches correctly determine whether or not they are equal to each other
   */
  @Test
  public void testEquals() {
    Pitch p = new Pitch(1, 3);
    Pitch q = new Pitch(1, 3);
    Pitch w = new Pitch(2, 3);
    assertEquals(true, p.equals(q));
    assertEquals(false, p.equals(w));
  }
}
