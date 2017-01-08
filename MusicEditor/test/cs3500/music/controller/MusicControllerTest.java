package cs3500.music.controller;

import cs3500.music.view.CompositeView;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link MusicController} class.
 */
public class MusicControllerTest {
  MusicController controller;

  /**
   * Builds a controller from the mary little lamb text file, and uses a composite view to test
   * all functions available.
   *
   * @result the controller field will be initialized with this controller
   */
  @Before
  public void load() {
    controller = new MusicController("resources/mary-little-lamb.txt", "composite");
  }

  /**
   * Tests adding a note by running the previewSquare function in the gui, and then the addNote
   * function in the controller.
   *
   * @result the controller will successfully add a note at the preview square location
   */
  @Test
  public void testAddNote() {
    assertEquals(0, controller.model.getPitches().get(3).getNote(10).playing);

    CompositeView c = (CompositeView) controller.view;
    c.previewSquare(3, 10);
    controller.addNote();

    assertEquals(2, controller.model.getPitch(5, 4).getNote(10).playing);
  }

  /**
   * Tests adding a note with an invalid location, should throw an exception.
   *
   * @result the controller throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException() {
    assertEquals(2, controller.model.getPitch(5, 4).getNote(10).playing);

    CompositeView c = (CompositeView) controller.view;
    c.previewSquare(99999, 10);

    controller.addNote();
  }

  /**
   * Tests removing a note by running the removeNote function with the x and y coordinate of the
   * 10th note of the pitch E4, which was the note that was just added.
   *
   * @result the controller will successfully remove the note at the given coordinate
   */
  @Test
  public void testRemoveNote() {
    assertEquals(2, controller.model.getPitch(5, 4).getNote(10).playing);

    controller.removeNote(240, 85);

    assertEquals(0, controller.model.getPitch(5, 4).getNote(10).playing);
  }

  /**
   * Tests removing a note with an invalid location, should throw an exception.
   *
   * @result the controller throws an IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteException() {
    assertEquals(2, controller.model.getPitch(5, 4).getNote(10).playing);

    controller.removeNote(999999999, 85);
  }
}
