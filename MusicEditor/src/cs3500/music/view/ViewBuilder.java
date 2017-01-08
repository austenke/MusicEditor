package cs3500.music.view;

import cs3500.music.model.ReadOnlyMusicModel;
import cs3500.music.model.IMusicModel;

/**
 * Factory class used to render views.
 */
public final class ViewBuilder {
  /**
   * Method that returns a view of the given type.
   *
   * @return a view that implements ViewInterface
   */
  public static ViewInterface run(String type, IMusicModel model) {
    switch (type.toLowerCase()) {
      case "console": return new Console(new ReadOnlyMusicModel(model));
      case "gui": return new GUI(new ReadOnlyMusicModel(model), -1);
      case "midi": return new Midi(new ReadOnlyMusicModel(model), true);
      case "composite": return new CompositeView(new ReadOnlyMusicModel(model));
      default: throw new IllegalArgumentException("\"" + type + "\" is not a supported view type");
    }
  }
}