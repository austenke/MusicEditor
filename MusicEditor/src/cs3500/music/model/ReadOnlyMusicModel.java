package cs3500.music.model;

import java.util.List;

/**
 * An IMusicModel that takes a model and supresses any mutator methods so that it can only
 * be read.
 */
public class ReadOnlyMusicModel implements IMusicModel {
  IMusicModel model;

  /**
   * Constructor for ReadOnlyMusicModel, takes a model.
   *
   * @param model model to read off of
   */
  public ReadOnlyMusicModel(IMusicModel model) {
    this.model = model;
  }

  @Override
  public List<Pitch> generateOctaves(List<Pitch> lowestAndHighest) {
    return this.model.generateOctaves(lowestAndHighest);
  }

  @Override
  public List<Pitch> findLowestAndHighestPitch() {
    return this.model.findLowestAndHighestPitch();
  }

  @Override
  public int getTempo() {
    return this.model.getTempo();
  }

  @Override
  public List<Note> getAllNotes() {
    return this.model.getAllNotes();
  }

  @Override
  public List<Note> getNotesAtBeat(int beat) {
    return this.model.getNotesAtBeat(beat);
  }

  @Override
  public void setTempo(int t) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public Pitch getPitch(int pitch, int octave) {
    return this.model.getPitch(pitch, octave);
  }

  @Override
  public List<Pitch> getPitches() {
    return this.model.getPitches();
  }

  @Override
  public void addNote(int pitch, int octave, int beat, int duration, int instrument, int volume) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void addNote(Pitch p, int beat, int duration, int instrument, int volume) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void addNote(Pitch pitch, int beat, Note n) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void addNotes(int pitch, int octave, List<Note> notes) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void addNotes(Pitch p, List<Note> notes) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void addPitch(Pitch p) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void removePitch(Pitch p) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void combineMusicModelSimultaneous(IMusicModel s) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public void combineMusicModelConsecutive(IMusicModel s) {
    throw new IllegalArgumentException("Cannot mutate read only model.");
  }

  @Override
  public int maxBeats() {
    return this.model.maxBeats();
  }
}
