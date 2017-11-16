package backend.logger.changeLogs;

import backend.logger.Log;
import backend.models.Picture;
import backend.models.Tag;

/**
 * This represents a commands where a file was renamed, moved or deleted
 */
public class PicChangeLog extends Log {

  private Picture changedPicture;

  private Tag tagChange;

  /**
   * true if it's a nameChange, false implies it's directory change
   */
  private boolean nameChange;

  public PicChangeLog(Picture changedPicture, boolean nameChange) {
    super();
    this.changedPicture = changedPicture;
    this.nameChange = nameChange;
  }

  public PicChangeLog(Picture changedPicture, Tag tagChange) {
    super();
    this.changedPicture = changedPicture;
    this.tagChange = tagChange;
  }


  public boolean isNameChange() {
    return nameChange;
  }

  public Tag getTagChange() {
    return tagChange;
  }

  public Picture getChangedPicture() {
    return changedPicture;
  }
}
