package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

public class ConcreteGuiViewPanel extends JPanel {
  int PIXEL_SIZE = 10;
  MusicCreator c;
  int min;
  int max;
  List<Note> list;

  // TODO should this be in the construtor?
  public ConcreteGuiViewPanel(MusicCreator c) {
    this.c = c;
    list = c.asList();
    min = Collections.min(list).getKeyVal();
    max = Collections.max(list).getKeyVal();
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    //g.drawString("Hello World", 25, 25);
    //g.drawOval(100,100,100,100);
    this.paintNotes(g);
    this.paintGrid(g);
    this.paintBeats(g);
    this.paintOctKey(g);
  }
  // Paint the grid
  private void paintGrid(Graphics g) {
    g.setColor(new Color(0, 0, 0));

    // For every
    for (int i = 0; i <= Math.ceil(c.getSongDuration() / 4.0); i++) {
      for (int j = min; j <= max; j++) {
        // CHANGE I MADE: ADDED (N * PIXEL_SIZE) so that it was slightly off center)
        g.drawRect(i * PIXEL_SIZE * 4 + (2 * PIXEL_SIZE), (j - min) * PIXEL_SIZE +(1 * PIXEL_SIZE) ,
                PIXEL_SIZE * 4, PIXEL_SIZE);
      }
    }

  }
  // Paints the beat numbers so that every four beats it displays beat number
  //  We can change this so that it looks more like the picture (every 16 beats)
  private void paintBeats(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    for (int i = 0; i< c.getSongDuration(); i+=4){
      g.drawString(""+ i, i * PIXEL_SIZE + (2 * PIXEL_SIZE) , PIXEL_SIZE );
    }
  }

  // Paints the notes
  private void paintNotes(Graphics g) {
    for (Note n : list) {
      // DRAWS THE TRAIL
      g.setColor(new Color(70, 200, 255));
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE + (2 * PIXEL_SIZE),
              (n.getKeyVal() - min) * PIXEL_SIZE + (1 * PIXEL_SIZE),
              n.getDuration()*PIXEL_SIZE, PIXEL_SIZE);
      // DRAWS THE STARTING HEADER
      g.setColor(new Color(204, 196, 36));
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE + (2 * PIXEL_SIZE) ,
              (n.getKeyVal() - min) * PIXEL_SIZE + (1 * PIXEL_SIZE),
              PIXEL_SIZE, PIXEL_SIZE);
    }
  }
  // TODO change the spacing
  private void paintOctKey(Graphics g) {
    g.setColor(Color.black);
    for (int i = min ; i <= max; i ++) {
      int noteVal = i % 12;

      int octaveVal = (int) Math.floor(i / 12);

      g.drawString(Note.Pitch.values()[noteVal].toNoteString() + octaveVal,
              PIXEL_SIZE, (i - min) * PIXEL_SIZE);
    }
  }

}