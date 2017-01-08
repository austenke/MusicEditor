package cs3500.music.view;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import cs3500.music.model.MusicModel;
import cs3500.music.model.Pitch;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.MusicReader;
import cs3500.music.util.CompositionBuilder;

import java.util.List;

/**
 * Tests for the {@link Midi} class.
 */
public class MidiTest {
  StringBuilder s;

  /**
   * Builds a midi with an empty model.
   *
   * @result returns an empty midi view
   */
  @Test
  public void testEmptyMidiView() {
    this.s = new StringBuilder();
    Pitch p = new Pitch(1, 0);
    List<Pitch> p2 = new ArrayList<Pitch>();
    p2.add(p);
    IMusicModel song = new MusicModel(p2, 0);
    Midi view = new Midi(song, new LogSynthesizer(s));
    view.run();
    assertEquals(s.toString(), "");
  }

  /**
   * Builds a midi with the Mary Little Lamb text file.
   *
   * @result returns the midi output from playing the file.
   */
  @Test
  public void testMaryLittleLambMidiView() {
    try {
      this.s = new StringBuilder();
      CompositionBuilder<IMusicModel> builder = new MusicModel.Builder();
      IMusicModel model = MusicReader.parseFile(new FileReader(
              "resources/mary-little-lamb.txt")
              , builder);
      Midi view = new Midi(model, new LogSynthesizer(s));
      view.run();
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Error in test testMaryLittleLambMidiView. Error is: "
              + e.getMessage());
    }
    assertEquals("144-0-65-72\n"
                    + "144-0-56-70\n"
                    + "128-0-65-85\n"
                    + "144-0-63-72\n"
                    + "128-0-63-79\n"
                    + "144-0-61-71\n"
                    + "144-0-63-79\n"
                    + "128-0-61-71\n"
                    + "128-0-56-79\n"
                    + "144-0-65-85\n"
                    + "144-0-56-79\n"
                    + "128-0-63-75\n"
                    + "128-0-65-78\n"
                    + "144-0-65-78\n"
                    + "128-0-65-74\n"
                    + "144-0-65-74\n"
                    + "128-0-65-82\n"
                    + "128-0-56-77\n"
                    + "144-0-56-77\n"
                    + "144-0-63-75\n"
                    + "128-0-63-77\n"
                    + "144-0-63-77\n"
                    + "128-0-63-75\n"
                    + "144-0-63-75\n"
                    + "144-0-65-82\n"
                    + "128-0-56-79\n"
                    + "144-0-56-79\n"
                    + "128-0-63-69\n"
                    + "128-0-65-73\n"
                    + "128-0-56-78\n"
                    + "144-0-68-84\n"
                    + "128-0-68-75\n"
                    + "144-0-68-75\n"
                    + "144-0-65-73\n"
                    + "144-0-56-78\n"
                    + "128-1-68-0\n"
                    + "128-0-65-84\n"
                    + "144-0-63-69\n"
                    + "128-0-63-80\n"
                    + "144-0-61-71\n"
                    + "144-0-63-80\n"
                    + "128-0-61-73\n"
                    + "144-0-65-84\n"
                    + "128-0-56-79\n"
                    + "144-0-56-79\n"
                    + "128-0-63-75\n"
                    + "128-0-65-76\n"
                    + "144-0-65-76\n"
                    + "128-0-65-74\n"
                    + "144-0-65-74\n"
                    + "128-0-65-77\n"
                    + "144-0-65-77\n"
                    + "128-0-65-81\n"
                    + "128-0-56-78\n"
                    + "144-0-56-78\n"
                    + "144-0-63-75\n"
                    + "128-0-63-74\n"
                    + "144-0-63-74\n"
                    + "144-0-65-81\n"
                    + "128-0-63-70\n"
                    + "128-1-65-0\n"
                    + "144-0-63-70\n"
                    + "128-1-56-0\n"
                    + "128-1-63-0\n"
                    + "144-0-61-73\n"
                    + "144-0-53-72\n"
            ,s.toString());
  }
}
