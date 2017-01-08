package cs3500.music.view;

import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Pitch;
import cs3500.music.model.Note;

/**
 * A midi view class.
 */
public class Midi implements ViewInterface {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final Sequencer seq;
  private IMusicModel model;
  private boolean paused;

  /**
   * Construct a MidiView with the given model.
   *
   * @param model the model to render
   *
   * @throws IllegalStateException if the midi is not available
   */
  public Midi(IMusicModel model, boolean playOnStart) {
    Sequencer seq = null;
    try {
      this.model = model;
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      synth.loadAllInstruments(synth.getDefaultSoundbank());
      this.synth.open();
      seq = MidiSystem.getSequencer();
      seq.open();
      if (playOnStart) {
        paused = false;
      }
      else {
        paused = true;
      }
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException("Could not initialize midi.");
    }
    this.seq = seq;
  }

  /**
   * Construct a MidiView with the given model and synthesizer.
   *
   * @param model the model to render
   * @param synthesizer the synthesizer object
   *
   * @throws IllegalStateException if the midi is not available
   */
  public Midi(IMusicModel model, Synthesizer synthesizer) {
    this.seq = null;
    Objects.requireNonNull(model);
    this.model = model;
    this.synth = synthesizer;
    try {
      this.receiver = synth.getReceiver();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException("Could not initialize midi.");
    }
  }

  /**
   * Returns the percentage of the song completed by the midi.
   *
   * @return a float of the division between the sequencer's current tick and total length
   */
  public float songCompletion() {
    return ((float) seq.getTickPosition() / seq.getTickLength());
  }


  public void pause() {
    this.seq.stop();
    this.paused = true;
  }

  /**
   * Toggles the pause state of the midi.
   */
  public void togglePause() {
    if (this.paused) {
      this.seq.start();
    } else {
      this.seq.stop();
    }
    seq.setTempoInMPQ(this.model.getTempo());
    this.paused = !this.paused;
  }

  /**
   * Moves the tick position of the midi to the beginning and starts the song.
   */
  public void beginComp() {
    this.paused = false;
    this.seq.setTickPosition(0);
    this.seq.start();
    seq.setTempoInMPQ(this.model.getTempo());
  }

  /**
   * Moves the tick position of the midi to the end.
   */
  public void endComp() {
    this.paused = true;
    this.seq.setTickPosition(this.seq.getTickLength());
  }

  /**
   * Function to check if the midi is paused.
   *
   * @return true if the midi is paused, false if not
   */
  public boolean getPause() {
    return this.paused;
  }

  /**
   * Creates a track, gives it to the sequence and runs it. Will send to receiver if sequence is
   * unavailable.
   */
  @Override
  public void run() {
    try {
      Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ, 15);
      Track t = s.createTrack();
      List<Integer> instruments = new ArrayList<Integer>();
      int channel;
      for (int i = 0; i < this.model.maxBeats(); i++) {
        for (int i2 = 0; i2 < this.model.getPitches().size(); i2++) {
          Pitch p = this.model.getPitches().get(i2);
          int midiPitch = p.getPitch() + ((p.getOctave() * 12) + 12);
          Note fullNote = p.getNote(i);
          int note = fullNote.playing;
          boolean playing = false;
          if (p.getNote(i - 1).playing != 0) {
            playing = true;
          }
          if (!(instruments.contains(fullNote.instrument))) {
            instruments.add(fullNote.instrument);
          }
          channel = instruments.indexOf(fullNote.instrument);

          MidiMessage instr = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel,
                  fullNote.instrument, 0);
          t.add(new MidiEvent(instr, i * 15));

          if (note == 2 && !playing) {
            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channel,
                    midiPitch, fullNote.volume);
            t.add(new MidiEvent(start, i * 15));
            if (this.seq == null) {
              this.receiver.send(start, i * model.getTempo());
            }
          }
          else if (note == 2 && playing) {
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, channel,
                    midiPitch, fullNote.volume);
            t.add(new MidiEvent(stop, i * 15));
            if (this.seq == null) {
              this.receiver.send(stop, i * model.getTempo());
            }

            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channel,
                    midiPitch, fullNote.volume);
            t.add(new MidiEvent(start, i * 15));
            if (this.seq == null) {
              this.receiver.send(start, i * model.getTempo());
            }
          }
          else if (note == 0 && playing) {
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, channel,
                    midiPitch, fullNote.volume);
            t.add(new MidiEvent(stop, i * 15));
            if (this.seq == null) {
              this.receiver.send(stop, i * model.getTempo());
            }
          }
        }
      }
      if (this.seq != null) {
        this.seq.setSequence(s);
        seq.setTempoInMPQ(this.model.getTempo());
        this.seq.setTickPosition(0);
        if (this.paused) {
          this.seq.stop();
        } else {
          this.seq.start();
        }
      }
    }
    catch (Exception e) {
      throw new IllegalStateException("Midi failed with error message: " + e.getMessage());
    }
  }
}
