package cs3500.music.model;

import java.util.List;
import java.util.ArrayList;
import cs3500.music.util.CompositionBuilder;


/**
 * Represents a MusicModel, which is a list of Pitch objects.
 */
public class MusicModel implements IMusicModel {
  private List<Pitch> pitches;
  private int tempo;

  /**
   * Constructor for MusicModel, if list of pitches contains duplicates,
   * it will only add the first one.
   *
   * @param pitches list of pitches to add to the MusicModel
   * @throws IllegalArgumentException if a pitch has invalid pitch, octave, or notes
   */
  public MusicModel(List<Pitch> pitches, int tempo) {
    this.pitches = new ArrayList<Pitch>();
    for (Pitch p: pitches) {
      if (p.getPitch() < 1 || p.getPitch() > 12) {
        throw new IllegalArgumentException("Pitch integer must be between 1 and 12.");
      }
      if (p.getOctave() < 0) {
        throw new IllegalArgumentException("Octaves cannot be below 0.");
      }
      for (int i = 0; i < p.getNotes().size(); i++) {
        if (p.getNotes().get(i).playing < 0 || p.getNotes().get(i).playing > 2) {
          throw new IllegalArgumentException("Pitch notes can only be 0, 1, or 2.");
        }
      }
      if (!(this.pitches.contains(p))) {
        this.pitches.add(p);
      }
    }
    this.tempo = tempo;
  }

  @Override
  public List<Pitch> generateOctaves(List<Pitch> lowestAndHighest) {
    int startPitch = lowestAndHighest.get(0).getPitch();
    int endPitch = lowestAndHighest.get(1).getPitch();
    int startOctave = lowestAndHighest.get(0).getOctave();
    int endOctave = lowestAndHighest.get(1).getOctave();
    if (endOctave >= startOctave) {
      List<Pitch> generatePitches = new ArrayList<Pitch>();
      for (int i = startOctave; i < endOctave + 1; i++) {
        for (int a = 1; a < 13; a++) {
          Pitch p = new Pitch(a, i);
          if (i == startOctave && i == endOctave) {
            if (a >= startPitch && a <= endPitch) {
              if (this.pitches.contains(p)) {
                generatePitches.add(this.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else if (i == startOctave) {
            if (a >= startPitch) {
              if (this.pitches.contains(p)) {
                generatePitches.add(this.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else if (i == endOctave) {
            if (a <= endPitch) {
              if (this.pitches.contains(p)) {
                generatePitches.add(this.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else {
            if (this.pitches.contains(p)) {
              generatePitches.add(this.getPitch(a, i));
            }
            else {
              generatePitches.add(p);
            }
          }
        }
      }
      return generatePitches;
    }
    else {
      throw new IllegalArgumentException("Please put in a valid start and end");
    }
  }

  @Override
  public List<Pitch> findLowestAndHighestPitch() {
    Pitch lowest = this.pitches.get(0);
    Pitch highest = this.pitches.get(0);
    for (Pitch temp : this.pitches) {
      if (temp.getOctave() < lowest.getOctave() || (temp.getOctave() == lowest.getOctave()
              && temp.getPitch() < lowest.getPitch())) {
        lowest = temp;
      }
      if (temp.getOctave() > highest.getOctave() || (temp.getOctave() == highest.getOctave()
              && temp.getPitch() > highest.getPitch())) {
        highest = temp;
      }
    }
    List<Pitch> l = new ArrayList<Pitch>();
    l.add(lowest);
    l.add(highest);
    return l;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public List<Note> getAllNotes() {
    List<Note> notes = new ArrayList<Note>();
    for (Pitch p: this.getPitches()) {
      for (Note n: p.getNotes()) {
        notes.add(n);
      }
    }
    return notes;
  }

  @Override
  public List<Note> getNotesAtBeat(int beat) {
    List<Note> notes = new ArrayList<Note>();
    for (Pitch p: this.getPitches()) {
      notes.add(p.getNote(beat));
    }
    return notes;
  }

  @Override
  public void setTempo(int t) {
    this.tempo = t;
  }

  @Override
  public Pitch getPitch(int pitch, int octave) {
    Pitch p = new Pitch(pitch, octave);
    for (Pitch a : this.pitches) {
      if (a.equals(p)) {
        return a;
      }
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  /**
   * Takes a Pitch object, returns a corresponding Pitch object from this MusicModel.
   *
   * @param p a Pitch object for the desired Pitch in the MusicModel
   * @return a Pitch object matching the given Pitch
   *
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   */
  public Pitch getPitch(Pitch p) {
    for (Pitch a : this.pitches) {
      if (a.equals(p)) {
        return a;
      }
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public List<Pitch> getPitches() {
    return this.pitches;
  }

  @Override
  public void addNote(int pitch, int octave, int beat, int duration, int instrument, int volume) {
    Pitch p = new Pitch(pitch, octave);
    if (this.pitches.contains(p)) {
      this.getPitch(pitch, octave).addNote(beat, duration, instrument, volume);
    }
    else {
      throw new IllegalArgumentException("MusicModel does not contain this pitch.");
    }
  }

  @Override
  public void addNote(Pitch pitch, int beat, Note n) {
    if (this.pitches.contains(pitch)) {
      this.getPitch(pitch).addNote(beat, n);
    }
    else {
      this.addPitch(pitch);
      this.addNote(pitch, beat, n);
    }
  }

  @Override
  public void addNote(Pitch p, int beat, int duration, int instrument, int volume) {
    if (this.pitches.contains(p)) {
      this.getPitch(p).addNote(beat, duration, instrument, volume);
    }
    else {
      throw new IllegalArgumentException("MusicModel does not contain this pitch.");
    }
  }

  @Override
  public void addNotes(int pitch, int octave, List<Note> notes) {
    Pitch p = new Pitch(pitch, octave);
    if (this.pitches.contains(p)) {
      this.getPitch(pitch, octave).addNotes(notes);
    }
    else {
      throw new IllegalArgumentException("MusicModel does not contain this pitch.");
    }
  }

  @Override
  public void addNotes(Pitch p, List<Note> notes) {
    if (this.pitches.contains(p)) {
      this.getPitch(p).addNotes(notes);
    } else {
      throw new IllegalArgumentException("MusicModel does not contain this pitch.");
    }
  }

  @Override
  public void addPitch(Pitch p) {
    if (this.pitches.contains(p)) {
      throw new IllegalArgumentException("MusicModel already contains this Pitch.");
    } else {
      this.pitches.add(p);
    }
  }

  @Override
  public void removePitch(Pitch p) {
    for (int i = 0; i < this.pitches.size(); i++) {
      Pitch a = this.pitches.get(i);
      if (a.equals(p)) {
        this.pitches.remove(i);
        return;
      }
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public void combineMusicModelSimultaneous(IMusicModel s) {
    for (Pitch p : s.getPitches()) {
      if (this.pitches.contains(p)) {
        this.removePitch(p);
        this.pitches.add(p);
      } else {
        this.pitches.add(p);
      }
    }
  }

  @Override
  public void combineMusicModelConsecutive(IMusicModel s) {
    int max = this.maxBeats();
    for (int i = 0; i < this.pitches.size(); i++) {
      List<Note> empty = new ArrayList<Note>();
      Pitch p = this.pitches.get(i);
      int loop = max - p.getNotes().size();
      for (int a = 0; a < loop; a++) {
        empty.add(new Note(0));
      }
      this.addNotes(p, empty);
    }
    for (Pitch p : s.getPitches()) {
      List<Note> empty = new ArrayList<Note>();
      if (this.pitches.contains(p)) {
        this.addNotes(p, p.getNotes());
      } else {
        for (int a = 0; a < max; a++) {
          empty.add(new Note(0));
        }
        empty.addAll(p.getNotes());
        p.setNotes(empty);
        this.pitches.add(p);
      }
    }
  }

  @Override
  public int maxBeats() {
    int max = 0;
    for (Pitch p : this.pitches) {
      if (p.getNotes().size() > max) {
        max = p.getNotes().size();
      }
    }
    return max;
  }

  /**
   * Builder for MusicModels.
   */
  public static final class Builder implements CompositionBuilder<IMusicModel> {

    private List<Pitch> notes;
    private int tempo;

    /**
     * Constructs a new Song Builder object.
     */
    public Builder() {
      this.notes = new ArrayList<Pitch>();
      this.tempo = 120;
    }

    @Override
    public IMusicModel build() {
      return new MusicModel(this.notes, this.tempo);
    }

    @Override
    public CompositionBuilder<IMusicModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument, int pitch,
                                                   int volume) {
      int realPitch = (pitch % 12) + 1;
      int realOctave = (int) Math.floor(pitch / 12) - 1;
      Pitch checkPitch = new Pitch(realPitch, realOctave);
      int duration = end - start - 1;

      if (pitch < 0 || pitch > 127 || volume < 0 || volume > 127) {
        throw new IllegalArgumentException("Please make sure all your notes have a valid"
                + "volume, pitch, and instrument.");
      }

      if (this.notes.contains(checkPitch)) {
        for (Pitch p: this.notes) {
          if (p.equals(checkPitch)) {
            p.addNote(start, duration, instrument, volume);
          }
        }
      } else {
        checkPitch.addNote(start, duration, instrument, volume);
        this.notes.add(checkPitch);
      }

      return this;
    }
  }
}