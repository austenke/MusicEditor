# MusicEditor
Music Editor by Austen Keene:

To run the jar: 
java -cp hw05.jar cs3500.music.MusicEditor "filename" "view type"

example: java -cp hw05.jar cs3500.music.MusicEditor “mary-little-lamb.txt" "midi"
example: java -cp hw05.jar cs3500.music.MusicEditor "zoot-lw.txt" "composite"

——————————————————————————

Overall design:
The controller builds all key and mouse methods, then adds them to the given view if it can be. If the view is a composite view the GUI will draw in a red line to keep track with the midi. The composite view will use a timer that wakes up at regular intervals to update the gui with the completion of the midi as a decimal. If the midi is 50% of the way through the song, the gui will update the red line to be at .5 of the total width it needs to cover. All changes to the model go through the controller, and the controller itself keeps track of the model and the view and has methods for interacting with both. A composite contains a gui view and a midi view. If the view is a composite view and the song is completed, pressing space will start the song over again.

Adding and removing notes through user input:
To add a note, left click anywhere on the grid. A light grey square will pop up, previewing the location of the note. When the preview is light grey, that is for a downbeat. Click on it again and it will turn green, previewing it as a duration. Press enter to confirm that you want to place the note, and it will add the note you want to the model. The gui and midi will also update to include this new note. A duration cannot be added if there is no downbeat or duration in front of it.

To remove a note, right click on any note on the grid. You will delete that note and it’s duration if it has one. This will update the model, and the gui and midi will update as well to reflect this change.

Interfaces:
 - IMusicModel:
	This interface is implemented for all music models, and contains methods for adding notes to songs, and combining them

 - ViewInterface:
	This interface is implemented by all views and contains the run method, making the view render the model in whatever way it is supposed to.

 - GuiViewInterface:
	Subclass of ViewInterface, used to contain methods only needed by a gui view.

Classes:
 - MusicController
	Contains the view and the model, handles all model changes and initializes the mouse and key listeners to be used on the gui interfaces
 - ReadOnlyMusicModel
	A MusicModel that suppresses all mutator methods
 - MusicModel
	Implements IMusicModel, contains a toString method that allows it to be printed in the console as shown in the assignment
 - Pitch
	Contains an integer for pitch level, an integer for octave, and a list of integers for the notes that are being played each beat
 - Note:
	A note keeps track of whether or not it is playing, it’s volume, and it’s instrument. It is kept in a list by pitch
 - ViewBuilder:
	A class designed to take an argument and a model, and return the corresponding view of the model
 - Console:
	A view that renders the model as text in the console
 - Midi:
	A view that renders the model as audio from the MIDI
 - GUI:
	A view that renders the model as a GUI using swing
 - CompositeView:
	A view that renders the model as both a GUI and midi
 - ConcreteGuiViewPanel:
	A helper class that renders a jpanel of the model
 - CompositionBuilder:
	A class used by MusicReader to build a model from a text file
 - MusicReader:
	A class used to read text files and use CompositionBuilder to make a model from it
 - KeyboardHandler:
	A class that implements keyListener, has the ability to be modified so that it can be given a keycode and a runnable and execute the runnable when it detects the 	given keycode
 - MouseHandler:
	A class that implements mouseListener, has the ability to be modified so that it can be given a mouse button and a runnable and execute the runnable when it detects the given mouse button
