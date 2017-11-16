package backend.logger;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;
import backend.logger.changeLogs.ManagerChangeLog;
import backend.logger.changeLogs.PicChangeLog;
import backend.logger.changeLogs.TagChangeLog;

public class Logger implements Observer {

  private ArrayList<ManagerChangeLog> mapChanges;

  private ArrayList<PicChangeLog> picChanges;

  private ArrayList<TagChangeLog> tagChanges;

  public Logger() {

    this.mapChanges = new ArrayList<ManagerChangeLog>();
    this.picChanges = new ArrayList<PicChangeLog>();
    this.tagChanges = new ArrayList<TagChangeLog>();
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o instanceof Observable) {
      // TODO:11
      this.managerUpdate(o, arg);
    } else if (o instanceof Picture) {
      this.picUpdate(o, arg);
    } else if (o instanceof Tag) {
      this.tagUpdate(o, arg);
    }
  }



  private void managerUpdate(Observable o, Object arg) {
    PictureManager manager = (PictureManager) o;
    if (arg instanceof Tag) {// tag change
      Tag changedTag = (Tag) arg;
    }

  }

  private void picUpdate(Observable o, Object arg) {
    Picture newPic = (Picture) o;
    Picture oldPic = (Picture) arg;
    if (newPic.getDirectoryPath() != newPic.getDirectoryPath()) {
      // Directory Change
      PicChangeLog newPicChange = new PicChangeLog(oldPic, false);
      this.picChanges.add(newPicChange);

    } else if ((newPic.getTagName() == newPic.getTagName())
        && (newPic.getTaglessName() != oldPic.getTaglessName())) {// Name Change

      PicChangeLog newPicChange = new PicChangeLog(newPic, true);
      this.picChanges.add(newPicChange);

    } else if (newPic.getTags().size() > oldPic.getTags().size()) {
      // an Tag was added.
      for (Tag newTag : newPic.getTags()) {
        if (!oldPic.containsTag(newTag)) {
          PicChangeLog newPicChange = new PicChangeLog(oldPic, newTag);
          this.picChanges.add(newPicChange);
          return;
        }
      }
    }
  }

  private void tagUpdate(Observable o, Object arg) {
    Tag newTag = (Tag) o;
    if (arg instanceof Tag) {// Tag name Change
      Tag oldTag = (Tag) arg;
      TagChangeLog newTagChange = new TagChangeLog(newTag, oldTag);
      this.tagChanges.add(newTagChange);
    }
  }
}
