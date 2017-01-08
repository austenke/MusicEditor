package cs3500.music.model;

/**
 * Class used to represent a note. A note keeps track of whether or not it is playing, it's
 * instrument, and it's volume.
 */
public class Note {
  public final int playing;
  public final int instrument;
  public final int volume;

  /**
   * Constructor for Note.
   *
   * @param playing whether or not a note is playing at this moment
   * @param instrument the instrument the note is to be played on
   * @param volume the volume the note is to be playing at
   *
   * @throws IllegalArgumentException if note is given invalid items
   */
  public Note(int playing, int instrument, int volume) {
    if (playing < 0 || playing > 2 || volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Invalid information given to note.");
    }

    this.playing = playing;
    this.volume = volume;
    this.instrument = instrument;
  }

  /**
   * Constructor for Note.
   *
   * @param playing whether or not a note is playing at this moment
   *
   * @throws IllegalArgumentException if note is given invalid items
   */
  public Note(int playing) {

    if (playing < 0 || playing > 2) {
      throw new IllegalArgumentException("Invalid information given to note.");
    }

    this.playing = playing;
    this.volume = 0;
    this.instrument = 0;
  }
}
