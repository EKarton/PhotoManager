package backend.logger.changeLogs;


import backend.logger.Log;
import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;

/**
 * The MapChangeLog records any commands of Key,Value mapping that occurs to the BiDirectionalMap.
 *
 */

public class ManagerChangeLog extends Log {
  /**
   * true if it's a tagChange (deletion/addition), false implies it's a Picture
   * change(untracking/addition)
   */
  private Tag tagChange;

  private Picture picChange;

  /**
   * true, if it's addition, false if deletion
   */
  private boolean isAddition;

  private PictureManager manager;

  /**
   * records a tagChange
   * 
   * @param thisManager
   * @param tagChange
   */
  public ManagerChangeLog(PictureManager thisManager, Tag tagChange, boolean isAddition) {
    this.tagChange = tagChange;
    this.manager = thisManager;
    this.isAddition = isAddition;
  }

  public ManagerChangeLog(PictureManager thisManager, Picture picChange, boolean isAddition) {
    this.picChange = picChange;
    this.manager = thisManager;
    this.isAddition = isAddition;
  }

  public ManagerChangeLog(PictureManager thisManager) {
    // Initializing manager.
    this.manager = thisManager;
  }

  public Tag getTagChange() {
    return tagChange;
  }


  public Picture getPicChange() {
    return picChange;
  }


  public boolean isAddition() {
    return isAddition;
  }


  public PictureManager getManager() {
    return manager;
  }


}
