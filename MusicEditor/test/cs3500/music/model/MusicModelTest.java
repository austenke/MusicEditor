package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.view.Console;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MusicModel} class.
 */
public class MusicModelTest {
  /**
   * Tests adding notes to the model.
   *
   * @result the given notes are successfully added to the model
   */
  @Test
  public void testAddNotes() {
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
   * Tests the constructor of the model to initialize with the right pitches.
   *
   * @result the model constructor initializes with the right pitches
   */
  @Test
  public void testModelConstructor() {
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
    assertEquals(pList, s.getPitches());
  }

  /**
   * Tests creating an empty model.
   *
   * @result the model has an empty list of pitches
   */
  @Test
  public void testEmptyModelConstructor() {
    List<Pitch> pList = new ArrayList<Pitch>();
    MusicModel s = new MusicModel(pList, 0);
    Console c = new Console(s);
    assertEquals(pList, s.getPitches());
  }

  /**
   * Tests the constructor with an invalid pitch.
   *
   * @result the model throws an IllegalArgumentException
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
   * Tests combining two models in a consecutive order.
   *
   * @result the models are combined one after the other
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
    assertEquals(0, s.getNotesAtBeat(2).get(2).instrument);
    assertEquals(0, s.getNotesAtBeat(2).get(0).instrument);
    assertEquals(0, s.getNotesAtBeat(2).get(1).instrument);
    assertEquals(0, s.getNotesAtBeat(0).get(2).instrument);
    assertEquals(0, s.getNotesAtBeat(22).get(0).instrument);
    assertEquals(0, s.getNotesAtBeat(12).get(1).instrument);
  }

  /**
   * Tests combining two models in a consecutive order again.
   *
   * @result the models are combined one after the other again
   */
  @Test
  public void testCombineConsecutive2() {
    List<Note> bList = new ArrayList<Note>();
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
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p);
    pList.add(p2);
    pList.add(p3);
    pList2.add(p3);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelConsecutive(s2);
    Console c = new Console(s);
    assertEquals(0, s.getAllNotes().get(0).instrument);
    assertEquals(0, s.getAllNotes().get(2).instrument);
    assertEquals(0, s.getAllNotes().get(3).instrument);
    assertEquals(0, s.getAllNotes().get(10).instrument);
    assertEquals(0, s.getAllNotes().get(11).instrument);
    assertEquals(0, s.getAllNotes().get(12).instrument);
    assertEquals(0, s.getAllNotes().get(13).instrument);
    assertEquals(0, s.getAllNotes().get(15).instrument);
    assertEquals(0, s.getAllNotes().get(20).instrument);
    assertEquals(0, s.getAllNotes().get(23).instrument);
    assertEquals(0, s.getAllNotes().get(25).instrument);
    assertEquals(0, s.getAllNotes().get(39).instrument);
  }

  /**
   * Tests combining two models in a consecutive order again.
   *
   * @result the models are combined one after the other again
   */
  @Test
  public void testCombineConsecutive3() {
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
    Pitch p4 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p);
    pList.add(p2);
    pList.add(p3);
    pList2.add(p4);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelConsecutive(s2);
    Console c = new Console(s);
    assertEquals(1, s.getPitches().get(0).getPitch());
    assertEquals(2, s.getPitches().get(1).getPitch());
    assertEquals(3, s.getPitches().get(2).getPitch());
  }

  /**
   * Tests combining two models in a simultaneous order.
   *
   * @result the models are combined together
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
    assertEquals(2, s.getPitches().get(0).getPitch());
    assertEquals(3, s.getPitches().get(1).getPitch());
    assertEquals(1, s.getPitches().get(2).getPitch());
  }

  /**
   * Tests combining two models in a simultaneous order again.
   *
   * @result the models are combined together again
   */
  @Test
  public void testCombineSimultaneous2() {
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
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList2.add(p);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelSimultaneous(s2);
    assertEquals(2, s.getPitches().get(0).getPitch());
    assertEquals(3, s.getPitches().get(1).getPitch());
    assertEquals(1, s.getPitches().get(2).getPitch());
  }

  /**
   * Tests getting the tempo of the model.
   *
   * @result the tempo of the model as an int
   */
  @Test
  public void testGetTempo() {
    List<Pitch> pList = new ArrayList<Pitch>();
    MusicModel s = new MusicModel(pList, 100);
    assertEquals(100, s.getTempo());
  }

  /**
   * Tests getting the tempo of the model again.
   *
   * @result the tempo of the model as an int again
   */
  @Test
  public void testGetTempo2() {
    List<Pitch> pList = new ArrayList<Pitch>();
    MusicModel s = new MusicModel(pList, 0);
    assertEquals(0, s.getTempo());
  }
}
