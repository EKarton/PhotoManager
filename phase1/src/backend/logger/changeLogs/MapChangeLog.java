package backend.logger.changeLogs;

import backend.Picture;
import backend.Tag;
import backend.logger.Log;
/**
 * The MapChangeLog records any commands of Key,Value mapping that occurs to the BiDirectionalMap.
 *
 */

public class MapChangeLog extends Log{
  private Object[] pictureChange;
  private Object[] tagChange;
  
  public MapChangeLog(Picture changedPicture, boolean changeStatus){
    this.pictureChange = new Object[2];
    this.pictureChange[0] = changedPicture;
    this.pictureChange[1] = changeStatus;
  }

  public MapChangeLog(Tag changedTag, boolean changeStatus){
    this.tagChange = new Object[2];
    this.tagChange[0] = changedTag;
    this.tagChange[1] = changeStatus;
  }

}
