package backend.logger;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import backend.Picture;
import backend.Tag;
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
    if (o instanceof Observable) {
      //TODO:11
      this.mapUpdate(o, arg);
    } else if (o instanceof Picture) {
      this.picUpdate(o, arg);
    } else if (o instanceof Tag) {
      this.tagUpdate(o, arg);
    }
  }



  private void mapUpdate(Observable o, Object arg) {
    //TODO:11
   
  }

  private void picUpdate(Observable o, Object arg) {
    Picture newPic = (Picture) o;
    Picture oldPic = (Picture) arg;
    if (newPic.getDirectoryPath() == newPic.getDirectoryPath()) {
      // Name Change
      PicChangeLog newPicChange =
          new PicChangeLog(newPic.getTaglessName(), oldPic.getTaglessName(), true);
      // the Log will be recorded with Names.
      this.picChanges.add(newPicChange);
    } else {//Directory Change
      PicChangeLog newPicChange =//this Log will be recorded with directory paths.
          new PicChangeLog(newPic.getDirectoryPath(), oldPic.getDirectoryPath(), false);
      this.picChanges.add(newPicChange);
    }
  }

  private void tagUpdate(Observable o, Object arg) {
    Tag newTag = (Tag) o;
    if (arg instanceof String) {//Description Change
      String oldDescription = (String) arg;
      TagChangeLog newTagChange = new TagChangeLog(newTag, oldDescription);
      this.tagChanges.add(newTagChange);
    } else if (arg instanceof Tag) {// Tag name Change
      Tag oldTag = (Tag) arg;
      TagChangeLog newTagChange = new TagChangeLog(newTag, oldTag);
      this.tagChanges.add(newTagChange);
    }
  }
}
