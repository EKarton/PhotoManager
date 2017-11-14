package backend.logger;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import backend.BiDirectionalHashMap;
import backend.Picture;
import backend.Tag;
import backend.logger.changeLogs.MapChangeLog;
import backend.logger.changeLogs.PicChangeLog;
import backend.logger.changeLogs.TagChangeLog;
import javafx.collections.ListChangeListener.Change;

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
    if (o instanceof BiDirectionalHashMap) {
      this.mapUpdate(o, arg);
    } else if (o instanceof Picture) {
      this.picUpdate(o, arg);
    } else if (o instanceof Tag) {
      this.tagUpdate(o, arg);
    }
  }



  public void mapUpdate(Observable o, Object arg) {
    BiDirectionalHashMap newMap = (BiDirectionalHashMap) o;

  }

  public void picUpdate(Observable o, Object arg) {
    Picture newPic = (Picture) o;
    Picture oldPic = (Picture) arg;
    
  }

  public void tagUpdate(Observable o, Object arg) {
    Tag newTag = (Tag) o;
    if (arg instanceof String) {
      String oldDescription = (String) arg;
      TagChangeLog newTagChange = new TagChangeLog(newTag, oldDescription);
      this.tagChanges.add(newTagChange);
    } else if (arg instanceof Tag) {
      Tag oldTag = (Tag) arg;
      TagChangeLog newTagChange = new TagChangeLog(newTag, oldTag);
      this.tagChanges.add(newTagChange);
    }
  }
}
