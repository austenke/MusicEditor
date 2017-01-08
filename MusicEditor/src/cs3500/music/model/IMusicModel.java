package cs3500.music.model;

import java.util.List;

/**
 * An interface for a music model.
 */
public interface IMusicModel {

  /**
   * Generates a full row of pitches and their octaves to be used by a view, this allows the
   * model to only retain relevant pitches and still output a full list.
   *
   * @param lowestAndHighest a list with the two lowest and highest pitches
   *
   * @return a full list of Pitches with empty Pitch objects for the pitches MusicModel does not
   *     contain
   **/
  List<Pitch> generateOctaves(List<Pitch> lowestAndHighest);

  /**
   * Finds the lowest and highest pitches in the MusicModel so that they can be used to generate an
   * ordered row of pitches and the octave.
   *
   * @return a list of Pitch objects
   **/
  List<Pitch> findLowestAndHighestPitch();

  /**
   * Get the tempo of the model.
   *
   * @return the tempo as an int
   **/
  public int getTempo();

  /**
   * Set the tempo of the model to the provided int.
   **/
  public void setTempo(int t);

  /**
   * Return all the notes of the model.
   *
   * @return a list of notes
   **/
  public List<Note> getAllNotes();

  /**
   * Return all the notes of the model at a specific beat.
   *
   * @return a list of notes
   **/
  public List<Note> getNotesAtBeat(int beat);

  /**
   * Adds a note for a certain length at a certain beat.
   *
   * @param pitch the pitch of the Pitch object
   * @param octave the octave of the Pitch object
   * @param beat the beat the note falls on
   * @param duration the length of the note
   *
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   **/
  public void addNote(int pitch, int octave, int beat, int duration, int instrument, int volume);

  /**
   * Adds a note for a certain length at a certain beat.
   *
   * @param p the desired Pitch object in the MusicModel
   * @param beat the beat the note falls on
   * @param duration the length of the note
   *
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   **/
  public void addNote(Pitch p, int beat, int duration, int instrument, int volume);

  /**
   * A more direct method for adding notes, if the model does not contain the given pitch it
   * will add it as well.
   *
   * @param pitch the desired Pitch object in the MusicModel
   * @param beat the beat the note falls on
   * @param n the note to add
   **/
  public void addNote(Pitch pitch, int beat, Note n);

  /**
   * Returns the pitches inside the MusicModel.
   *
   * @return a list of pitches
   */
  public List<Pitch> getPitches();

  /**
   * Takes an integer for the pitch and octave, returns a corresponding Pitch object from this
   * MusicModel.
   *
   * @param pitch for the pitch of the desired object
   * @param octave for the octave of the desired object
   *
   * @return a Pitch object with the desired pitch and octave
   *
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   */
  public Pitch getPitch(int pitch, int octave);

  /**
   * Adds a list of notes to a Pitch object.
   *
   * @param pitch the pitch of the desired Pitch object in the MusicModel
   * @param octave the octave of the desired Pitch object in the MusicModel
   * @param notes the notes to be added
   *
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   **/
  public void addNotes(int pitch, int octave, List<Note> notes);

  /**
   * Adds a list of notes to a Pitch object.
   *
   * @param p the desired Pitch object in the MusicModel
   * @param notes the notes to be added
   *
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   **/
  public void addNotes(Pitch p, List<Note> notes);

  /**
   * Adds a Pitch object to a MusicModel.
   *
   * @param p the Pitch object to be added
   *
   * @throws IllegalArgumentException if the MusicModel already contains the given Pitch
   **/
  public void addPitch(Pitch p);

  /**
   * Removes a Pitch object from a MusicModel.
   *
   * @param p the Pitch object to be removed
   **/
  public void removePitch(Pitch p);

  /**
   * Combines two MusicModels so that they should are played simultaneously. If the pre-existing
   * MusicModel has the same Pitch as the new one, the new MusicModel will overwrite the old one.
   *
   * @param s the MusicModel to be combined
   **/
  public void combineMusicModelSimultaneous(IMusicModel s);

  /**
   * Combines two MusicModels so that they should are played consecutively. Beats will be added
   * to the pre-existing MusicModel so that the new one starts right after the last note of the
   * old one.
   *
   * @param s the MusicModel to be combined
   **/
  public void combineMusicModelConsecutive(IMusicModel s);

  /**
   * Iterates through all the pitches and finds the longest one.
   *
   * @return the largest number of beats in any Pitch
   **/
  public int maxBeats();
}
