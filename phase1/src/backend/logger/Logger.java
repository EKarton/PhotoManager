package backend.logger;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import backend.logger.changeLogs.MapChangeLog;
import backend.logger.changeLogs.PicChangeLog;
import backend.logger.changeLogs.TagChangeLog;

public class Logger implements Observer {

  private ArrayList<MapChangeLog> mapChanges;

  private ArrayList<PicChangeLog> picChanges;

  private ArrayList<TagChangeLog> tagChanges;

  public Logger() {
    this.mapChanges = new ArrayList<MapChangeLog>();
    this.picChanges = new ArrayList<PicChangeLog>();
    this.tagChanges = new ArrayList<TagChangeLog>();

  }

  @Override
  public void update(Observable o, Object arg) {


  }

  
  public void mapUpdate(Observable o, Object arg) {

  }

  public void picUpdate(Observable o, Object arg) {

  }

  public void tagUpdate(Observable o, Object arg) {

  }

}
