package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ReadOnlyMusicModel} class.
 */
public class ReadOnlyMusicModelTest {
  /**
   * Tests building a ReadOnlyMusicModel and getting notes from it.
   *
   * @result correctly returns the desired notes from the model
   */
  @Test
  public void testGetNotes() {
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
    assertEquals(p.getNotes(), bList);
    s.addNotes(p, bList);
    assertEquals(p2.getNotes(), bList2);
    assertEquals(p3.getNotes(), bList2);
    bList.addAll(bList);
    assertEquals(p.getNotes(), bList);
  }

  /**
   * Tests building a ReadOnlyMusicModel and getting notes at a certain beat from it.
   *
   * @result correctly returns the desired notes at a certain beat from the model
   */
  @Test
  public void testGetNoteAtBeat() {
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
    assertEquals(2, s.getNotesAtBeat(0).get(0).playing);
    assertEquals(0, s.getNotesAtBeat(10).get(0).playing);
    assertEquals(0, s.getNotesAtBeat(100).get(0).playing);
    assertEquals(0, s.getNotesAtBeat(232).get(0).playing);
  }

  /**
   * Tests building a ReadOnlyMusicModel with an invalid pitch.
   *
   * @result correctly throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorAndToStringException() {
    Pitch p = new Pitch(0, 99);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p);
    MusicModel m1 = new MusicModel(pList, 0);

    m1.toString();
  }
}
