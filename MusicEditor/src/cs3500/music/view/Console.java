package cs3500.music.view;

import cs3500.music.model.IMusicModel;

import java.util.List;

import cs3500.music.model.Pitch;

/**
 * Class for the console view of a model, implements ViewInterface.
 */
public class Console implements ViewInterface {

  private IMusicModel model;

  public Console(IMusicModel model) {
    this.model = model;
  }

  /**
   * Iterates through all the pitches and finds the longest one.
   *
   * @return the largest number of beats in any Pitch
   **/
  private int maxBeats() {
    int max = 0;
    for (Pitch p: this.model.getPitches()) {
      if (p.getNotes().size() > max) {
        max = p.getNotes().size();
      }
    }
    return max;
  }

  /**
   * Returns a string depending on which note is passed to it.
   *
   * @param getNote the integer of the note
   *
   * @return a string corresponding to the note value
   *
   * @throws IllegalArgumentException if the note is invalid
   **/
  private String generateNote(int getNote) {
    switch (getNote) {
      case 0:
        return "     ";
      case 1:
        return "  |  ";
      case 2:
        return "  X  ";
      default:
        throw new IllegalArgumentException("Invalid note value, must be 0, 1, or 2.");
    }
  }

  /**
   * Pads a given integer depending on what the largest one is.
   *
   * @param beat the integer of the current beat
   * @param max the largest beat
   *
   * @return a padded string with the current beat
   *
   * @throws IllegalArgumentException if the max beat is smaller than the current one
   **/
  private String padBeat(int beat, int max) {
    if (max >= beat) {
      return String.format("%" + String.valueOf(max).length() + "s", beat);
    }
    else {
      throw new IllegalArgumentException("Current beat cannot be bigger than max");
    }
  }

  /**
   * Takes a list of pitches and turns them into a string with their corresponding note names.
   *
   * @param pitchList a list of all pitches to be turned into strings
   *
   * @return a string containing all pitches and their octaves
   *
   * @throws IllegalStateException if an octave is larger than 99, this is a view restriction
   **/
  private String generateOctaveText(List<Pitch> pitchList) {
    String s = "";
    for (int i = 0; i < pitchList.size(); i++) {
      Pitch p = pitchList.get(i);
      if (p.getOctave() > 99) {
        throw new IllegalStateException("The MusicModel toString method does not support more"
                + "than 100 octaves");
      }
      s += String.format("%" + 5 + "s", p.toString());
    }
    return s;
  }

  /**
   * Generates a row of notes at the given beat.
   *
   * @param pitchList a list of all pitches to be turned into strings
   * @param beat the current beat
   *
   * @return a string of a row of notes at the given beat
   **/
  private String generateRow(List<Pitch> pitchList, int beat) {
    String s = "";
    for (int i = 0; i < pitchList.size(); i++) {
      Pitch p = pitchList.get(i);
      s += this.generateNote(p.getNote(beat).playing);
    }
    return s;
  }

  /**
   * Generates a complete text version of the model and returns it as a string.
   *
   * @return a string render of the model
   **/
  public String runConsole() {
    if (model.getPitches().size() == 0) {
      return "";
    }
    List<Pitch> lowestAndHighest = this.model.findLowestAndHighestPitch();
    List<Pitch> lPitch = this.model.generateOctaves(lowestAndHighest);
    int maxBeats = this.maxBeats();
    String octaves = String.format("%" + String.valueOf(maxBeats).length() + "s",
            this.generateOctaveText(lPitch));
    String beats = "";
    for (int i = 0; i < maxBeats - 1; i++) {
      beats += this.padBeat(i, maxBeats) + this.generateRow(lPitch, i) + "\n";
    }
    beats += this.padBeat(maxBeats - 1, maxBeats) + this.generateRow(lPitch, maxBeats - 1);
    return octaves + "\n" + beats;
  }

  @Override
  public void run() {
    System.out.println(runConsole());
  }
}
