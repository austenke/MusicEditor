package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Builds a receiver that logs input to a StringBuilder instead of using the MIDI.
 */
public class LogReceiver implements Receiver {
  public StringBuilder s;

  public LogReceiver(StringBuilder s) {
    this.s = s;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage m = (ShortMessage) message;
    s.append(m.getCommand() + "-" + m.getChannel() + "-" + m.getData1() + "-"
            + m.getData2() + "\n");
  }

  @Override
  public void close() {
    return;
  }

}