package cs3500.music.view;

import java.util.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

/**
 * Created by NadineShaalan on 6/14/16.
 */
public class ConsoleView implements IView {
  public ConsoleView() {

  }

  public String render(MusicCreator c) {
    List<Note> listC = c.asList();
    int min =  Collections.min(listC).getKeyVal();
    int max = Collections.max(listC).getKeyVal();
    int dur = c.getSongDuration();
    // -------------- Creates empty array list to modify
    ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    for (int i = 0; i <= dur; i++) {
      ArrayList<String> temp = new ArrayList<String>();
      for (int j = min; j <= max; j++) {
        temp.add("     ");
      }
      list.add(temp);
    }

    // ----------- Fills the arraylist with proper strings ------------
    for (int i = 0; i <= dur; i++) {
      for (int j = min; j <= max; j++) {
        if(c.notesAtBeat(i).size() == 0) {
          // Do nothing
        }
        else if (noteAt(c.notesAtBeat(i), j) != null) {
          list.get(i).set(j - min, "  X  ");
          for (int k = 1; k < noteAt(c.notesAtBeat(i), j).getDuration(); k++) {
            if (!(list.get(i+k).get(j-min).equals("  X  "))) {
              list.get(i+k).set(j - min, "  |  ");
            }
          }
        }
      }
    }


    //---------- CONVERTS TO FINAL STRING ----------------------//
    // Create a new string builder
    StringBuilder result= new StringBuilder();
    // Amount of padding necessary
    int width = String.valueOf(c.getSongDuration()).length();

    // Print out the beat numbers with the proper padding
    for (int i = 0; i <= dur; i++) {
      result.append(String.format("%" + width + "s", i));
      for (String s: list.get(i)) {
        result.append(s);
      }
      result.append("\n");
    }
    return octaveRow(c) + result.toString();
  }


  private Note noteAt(java.util.List<Note> list, int key) {
    Note result = null;
    for (Note n: list) {
      if (n.getKeyVal() == key) {
        result = n;
      }
    }
    return result;
  }

  // Gets the top row of a render
  private String octaveRow(MusicCreator c) {
    StringBuilder s = new StringBuilder();
    int width = String.valueOf(c.getSongDuration()).length();
    int minNoteValue;
    int maxNoteValue;
    int dur = c.getSongDuration();
    if (dur == 0) {
      minNoteValue = 12;
      maxNoteValue = 24;
    } else {
      List<Note> list = c.asList();
      minNoteValue =  Collections.min(list).getKeyVal();
      maxNoteValue = Collections.max(list).getKeyVal();
    }
    for (int i = 0; i < width; i++) {
      s.append(" ");
    }
    for (int i = minNoteValue; i <= maxNoteValue; i++) {

      int noteVal = i % 12;

      int octaveVal = (int) Math.floor(i / 12);
      String actualString = Note.Pitch.values()[noteVal].toNoteString() + octaveVal;
      int strlen = actualString.length();
      if (strlen == 2) {
        s.append("  " + actualString + " ");
      } else if (strlen == 3) {
        s.append(" " + actualString + " ");
      } else {
        s.append(" " + actualString);
      }
    }
    return s.toString() + "\n";

  }
}